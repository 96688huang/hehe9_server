package cn.hehe9.service.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hehe9.common.bean.AppConfig;
import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.common.utils.BeanUtil;
import cn.hehe9.common.utils.JacksonUtil;
import cn.hehe9.common.utils.JsoupUtil;
import cn.hehe9.common.utils.ListUtil;
import cn.hehe9.common.utils.Pinyin4jUtil;
import cn.hehe9.persistent.dao.VideoDao;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoSource;

@Component
public class SohuVideoCollectService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(SohuVideoCollectService.class);

	@Resource
	private VideoDao videoDao;

	private static final String SOHU_VIDEO = ComConstant.LogPrefix.SOHU_VIDEO;

	// 线程池
	private int processCount = Runtime.getRuntime().availableProcessors();
	private ExecutorService videoThreadPool = Executors.newFixedThreadPool(processCount + 1);

	/** 用于比较Video的属性名称 */
	private static List<String> videoCompareFieldNames = new ArrayList<String>();
	static {
		// video fields
		videoCompareFieldNames.add("name");
		videoCompareFieldNames.add("author");
		videoCompareFieldNames.add("playCountWeekly");
		videoCompareFieldNames.add("playCountTotal");
		videoCompareFieldNames.add("posterBigUrl");
		videoCompareFieldNames.add("posterMidUrl");
		videoCompareFieldNames.add("posterSmallUrl");
		videoCompareFieldNames.add("iconUrl");
		videoCompareFieldNames.add("listPageUrl");
		videoCompareFieldNames.add("updateRemark");
	}

	public void collect(VideoSource vs) {
		collectVideos(vs.getId(), vs.getCollectPageUrl(), vs.getRootUrl());
	}

	/**
	 * 解析所有动漫信息
	 * 
	 * @param url
	 * @throws IOException
	 */
	public void collectVideos(final int sourceId, String collectPageUrl, String rootUrl) {
		try {
			// 是否已包含根域名
			collectPageUrl = collectPageUrl.contains(rootUrl) ? collectPageUrl : (rootUrl + (!collectPageUrl
					.startsWith("/") ? "/" + collectPageUrl : collectPageUrl));

			Document doc = JsoupUtil.connect(collectPageUrl, CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL, SOHU_VIDEO);
			if (doc == null) {
				logger.error("{}collect videos fail. sourceId = {}, collectPageUrl = {}", new Object[] { SOHU_VIDEO,
						sourceId, collectPageUrl });
			}

			Elements liEle = doc.select("ul.st-list>li");

			// 计数器
			final AtomicInteger videoCounter = createCouter();
			// 同步锁对象
			final Object videoSyncObj = createSyncObject();

			// 分别解析每部动漫的信息
			for (final Element liItem : liEle) {
				parseVideoInfoAsync(sourceId, liItem, liEle.size(), videoCounter, videoSyncObj);

				//				return; // TODO
			}

			// 等待被唤醒(被唤醒后, 重置计数器)
			int lastCount = waitingForNotify(videoCounter, liEle.size(), videoSyncObj, SOHU_VIDEO, logger);
			if (logger.isDebugEnabled()) {
				logger.debug("{}任务线程被唤醒, 本次计算了的视频数 = {}, 重置计数器 = {}.", new Object[] { SOHU_VIDEO, lastCount,
						videoCounter.get() });
			}

			// 下一页
			String nextPage = doc.select("body div.ssPages>a[title=下一页]").attr("href");
			if (logger.isDebugEnabled()) {
				logger.debug("{}nextPage : ", SOHU_VIDEO, nextPage);
			}

			// 递归解析
			if (StringUtils.isNotEmpty(nextPage)) {
				collectVideos(sourceId, nextPage, rootUrl);
			}
		} catch (Exception e) {
			logger.error(SOHU_VIDEO + "collect videos fail, sourceId = " + sourceId + ", collectPageUrl = "
					+ collectPageUrl + ", rootUrl = " + rootUrl, e);
		}
	}

	private void parseVideoInfoAsync(final int sourceId, final Element liItem, final int totalVideoCount,
			final AtomicInteger videoCounter, final Object videoSyncObj) {
		Runnable videoTask = new Runnable() {
			public void run() {
				try {
					parseVideoInfo(sourceId, liItem);
				} finally {
					String logMsg = logger.isDebugEnabled() ? String.format("%s准备唤醒任务线程. 本线程已计算了 %s 个视频, 本次计算视频数 = %s",
							new Object[] { SOHU_VIDEO, videoCounter.get() + 1, totalVideoCount }) : null;
					notifyMasterThreadIfNeeded(videoCounter, totalVideoCount, videoSyncObj, logMsg, logger);
				}
			}
		};
		videoThreadPool.execute(videoTask);
	}

	private void parseVideoInfo(int sourceId, Element liItem) {
		Video videoFromNet = new Video();
		videoFromNet.setSourceId(sourceId);
		try {
			Elements st_picDiv = liItem.select("div.st-pic");

			// 分集列表页url, sk码, icon
			Elements items = st_picDiv.select("a[href]");
			String listPageUrl = items.attr("href");
			String sk = items.attr("_s_k");
			String iconUrl = items.select("img").attr("src");

			videoFromNet.setListPageUrl(listPageUrl);
			videoFromNet.setIconUrl(iconUrl);

			// update remark
			String updateRemark = st_picDiv.select("span.maskTx").html();
			videoFromNet.setUpdateRemark(updateRemark);

			// play count per week
			String playCountWeekly = liItem.select("p.num-bf").html();
			videoFromNet.setPlayCountWeekly(playCountWeekly);

			Elements list_hover_Div = liItem.select("div.list-hover");

			// name
			Element aEle = list_hover_Div.select("a[_s_k=" + sk + "]").first();
			String name = aEle.attr("title");
			if (StringUtils.isEmpty(name)) {
				name = aEle.html();
			}
			videoFromNet.setName(name);

			// first char
			String firstChar = Pinyin4jUtil.getFirstChar(videoFromNet.getName()).toUpperCase();
			if (ArrayUtils.contains(ComConstant.LETTERS, firstChar)) {
				videoFromNet.setFirstChar(firstChar);
			} else {
				videoFromNet.setFirstChar(ComConstant.OTHER_CNS);
			}

			// story line brief
			String storyLine = list_hover_Div.select("p.lh-info").html();
			videoFromNet.setStoryLine(storyLine);

			// play count total
			String playCountTotal = list_hover_Div.select("a.acount").first().html();
			videoFromNet.setPlayCountTotal(playCountTotal);

			List<Video> list = videoDao.searchBriefByName(videoFromNet.getName());
			if (list == null || list.isEmpty()) {
				videoFromNet.setName(AppConfig.getAliasNameIfExist(videoFromNet.getName()));
				videoDao.save(videoFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add new video : {}", SOHU_VIDEO, JacksonUtil.encode(videoFromNet));
				}
				return;
			}

			boolean isMatcheRecord = false;
			for (Video videoFromDb : list) {
				boolean isIconUrlSame = videoFromNet.getIconUrl().trim().equals(videoFromDb.getIconUrl().trim());
				if (isIconUrlSame) {
					// 名字和icon相同, 则更新 (因为存在名字相同, 但属于不同视频的视频)
					boolean isNameSame = ListUtil.asList(
							StringUtils.deleteWhitespace(videoFromDb.getName()).split(ComConstant.LEFT_SLASH))
							.contains(StringUtils.deleteWhitespace(videoFromNet.getName()));
					if (isNameSame) {
						isMatcheRecord = true;
						// 比较关键字段是否有更新
						boolean isFieldsSame = BeanUtil.isFieldsValueSame(videoFromNet, videoFromDb,
								videoCompareFieldNames, null);
						if (!isFieldsSame) {
							videoFromNet.setId(videoFromDb.getId()); // id
							videoFromNet.setName(AppConfig.getAliasNameIfExist(videoFromNet.getName()));
							videoDao.udpate(videoFromNet); // 不同则更新

							// for log
							videoFromNet.setStoryLine(null);
							videoFromNet.setStoryLineBrief(null);
							logger.info("{}update video : \r\n OLD : {}\r\n NEW : {}", new Object[] { SOHU_VIDEO,
									JacksonUtil.encode(videoFromDb), JacksonUtil.encode(videoFromNet) });
						}
						break;
					}
				}
			}

			// 如果找不到要更新的记录, 则新增
			if (!isMatcheRecord) {
				videoFromNet.setName(AppConfig.getAliasNameIfExist(videoFromNet.getName()));
				videoDao.save(videoFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add new video : {}", SOHU_VIDEO, JacksonUtil.encode(videoFromNet));
				}
			}
		} catch (Exception e) {
			logger.error(SOHU_VIDEO + "parse video info fail. video = " + JacksonUtil.encodeQuietly(videoFromNet), e);
		}
	}
}
