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

import cn.hehe9.common.app.AppConfig;
import cn.hehe9.common.app.AppHelper;
import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.common.utils.BeanUtil;
import cn.hehe9.common.utils.JacksonUtil;
import cn.hehe9.common.utils.JsoupUtil;
import cn.hehe9.common.utils.ListUtil;
import cn.hehe9.common.utils.Pinyin4jUtil;
import cn.hehe9.persistent.dao.VideoDao;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoSource;
import cn.hehe9.service.job.base.BaseTask;

@Component
public class YoukuVideoCollectService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(YoukuVideoCollectService.class);

	@Resource
	private VideoDao videoDao;

	private static final String YOUKU_VIDEO = ComConstant.LogPrefix.YOUKU_VIDEO;

	// 线程池
	private int processCount = Runtime.getRuntime().availableProcessors();
	private ExecutorService videoThreadPool = Executors.newFixedThreadPool(processCount + 1);

	/** 用于比较Video的属性名称 */
	private static List<String> videoCompareFieldNames = new ArrayList<String>();
	static {
		// video fields
		videoCompareFieldNames.add("name");
		//videoCompareFieldNames.add("author");
//		videoCompareFieldNames.add("playCountWeekly");
//		videoCompareFieldNames.add("playCountTotal");
		videoCompareFieldNames.add("posterBigUrl");
//		videoCompareFieldNames.add("posterMidUrl");
//		videoCompareFieldNames.add("posterSmallUrl");
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

			Document doc = JsoupUtil.connect(collectPageUrl, CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL, YOUKU_VIDEO);
			if (doc == null) {
				logger.error("{}collect videos fail. sourceId = {}, collectPageUrl = {}", new Object[] { YOUKU_VIDEO,
						sourceId, collectPageUrl });
			}

			Elements yk_co13_Eles = doc.select(".yk-col3");

			// 计数器
			final AtomicInteger videoCounter = createCouter();
			// 同步锁对象
			final Object videoSyncObj = createSyncObject();

			// 分别解析每部动漫的信息
			for (final Element item : yk_co13_Eles) {
				parseVideoInfoAsync(sourceId, item, yk_co13_Eles.size(), videoCounter, videoSyncObj);
			}

			// 等待被唤醒(被唤醒后, 重置计数器)
			int lastCount = waitingForNotify(videoCounter, yk_co13_Eles.size(), videoSyncObj, YOUKU_VIDEO, logger);
			if (logger.isDebugEnabled()) {
				logger.debug("{}任务线程被唤醒, 本次计算了的视频数 = {}, 重置计数器 = {}.", new Object[] { YOUKU_VIDEO, lastCount,
						videoCounter.get() });
			}

			// 下一页
			String nextPage = doc.select(".yk-pager .yk-pages .next a").attr("href");
			if (logger.isDebugEnabled()) {
				logger.debug("{}nextPage : ", YOUKU_VIDEO, nextPage);
			}

			// 递归解析
			if (StringUtils.isNotEmpty(nextPage)) {
				collectVideos(sourceId, nextPage, rootUrl);
			}
		} catch (Exception e) {
			logger.error(YOUKU_VIDEO + "collect videos fail, sourceId = " + sourceId + ", collectPageUrl = "
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
							new Object[] { YOUKU_VIDEO, videoCounter.get() + 1, totalVideoCount }) : null;
					notifyMasterThreadIfNeeded(videoCounter, totalVideoCount, videoSyncObj, logMsg, logger);
				}
			}
		};
		videoThreadPool.execute(videoTask);
	}

	private void parseVideoInfo(int sourceId, Element item) {
		Video videoFromNet = new Video();
		videoFromNet.setSourceId(sourceId);
		try {
			String iconUrl = item.select(".p .p-thumb img").attr("src");
			String name = item.select(".p .p-thumb img").attr("alt");
			if(StringUtils.isBlank(name)){
				name = item.select(".p .p-meta .p-meta-title").text();
			}
			
			String updateRemark = item.select(".p .p-thumb .p-thumb-taglb .p-status").text();
			
			String listPageUrl = item.select(".p .p-link a").attr("href");
			if(StringUtils.isBlank(listPageUrl)){
				listPageUrl = item.select(".p .p-meta .p-meta-title a").attr("href");
			}
			
//			String author = item.select(".p .p-meta .p-meta-entry .p-actor").text();
//			String playCountTotal = item.select(".p .p-meta .p-meta-entry .p-num").text();
			
			videoFromNet.setName(name);
			videoFromNet.setIconUrl(iconUrl);
			videoFromNet.setUpdateRemark(updateRemark);
			videoFromNet.setListPageUrl(listPageUrl);
//			videoFromNet.setAuthor(author);
//			videoFromNet.setPlayCountTotal(playCountTotal);

			// first char
			String firstChar = Pinyin4jUtil.getFirstChar(videoFromNet.getName()).toUpperCase();
			if (ArrayUtils.contains(ComConstant.LETTERS, firstChar)) {
				videoFromNet.setFirstChar(firstChar);
			} else {
				videoFromNet.setFirstChar(ComConstant.OTHER_CNS);
			}

			List<Video> list = videoDao.searchBriefByName(videoFromNet.getName());
			if (list == null || list.isEmpty()) {
				videoFromNet.setName(AppConfig.getAliasNameIfExist(videoFromNet.getName()));
				videoDao.save(videoFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add new video : {}", YOUKU_VIDEO, JacksonUtil.encode(videoFromNet));
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
							logger.info("{}update video : \r\n OLD : {}\r\n NEW : {}", new Object[] { YOUKU_VIDEO,
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
					logger.debug("{}add new video : {}", YOUKU_VIDEO, JacksonUtil.encode(videoFromNet));
				}
			}
		} catch (Exception e) {
			logger.error(YOUKU_VIDEO + "parse video info fail. video = " + JacksonUtil.encodeQuietly(videoFromNet), e);
		}
	}
}
