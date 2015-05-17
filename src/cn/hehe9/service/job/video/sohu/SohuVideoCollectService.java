package cn.hehe9.service.job.video.sohu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
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
import cn.hehe9.common.utils.ReferrerUtil;
import cn.hehe9.persistent.dao.VideoDao;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoSource;
import cn.hehe9.service.job.base.BaseTask;

@Component
public class SohuVideoCollectService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(SohuVideoCollectService.class);

	@Resource
	private VideoDao videoDao;

	private static final String SOHU_VIDEO = ComConstant.LogPrefix.VIDEO_SOHU_VIDEO;

	private byte[] collectVideosSyncObj = new byte[0];

	// 线程池
	private int processCount = Runtime.getRuntime().availableProcessors();
	private ExecutorService videoThreadPool = Executors.newFixedThreadPool(processCount + 1);

	/** 用于比较Video的属性名称 */
	private static List<String> videoCompareFieldNames = new ArrayList<String>();
	static {
		// video fields
		videoCompareFieldNames.add("name");
		videoCompareFieldNames.add("firstChar");
		//videoCompareFieldNames.add("author");
		videoCompareFieldNames.add("playCountWeekly");
		videoCompareFieldNames.add("playCountTotal");
		//		videoCompareFieldNames.add("posterBigUrl");
		//		videoCompareFieldNames.add("posterMidUrl");
		//		videoCompareFieldNames.add("posterSmallUrl");
		videoCompareFieldNames.add("iconUrl");
		videoCompareFieldNames.add("listPageUrl");
		videoCompareFieldNames.add("updateRemark");
	}

	public void collect(VideoSource source) {
		collectVideos(source.getId(), source.getCollectPageUrl(), source.getRootUrl());

		// 由递归到最后的线程唤醒
		waitingForNotify(collectVideosSyncObj, SOHU_VIDEO, logger);
	}

	/**
	 * 解析所有动漫信息
	 * 
	 * @param url
	 * @throws IOException
	 */
	public void collectVideos(final int sourceId, String collectPageUrl, final String rootUrl) {
		try {
			// 是否已包含根域名
			collectPageUrl = collectPageUrl.contains(rootUrl) ? collectPageUrl : (rootUrl + (!collectPageUrl
					.startsWith("/") ? "/" + collectPageUrl : collectPageUrl));

			Document doc = JsoupUtil.connect(collectPageUrl, CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL, SOHU_VIDEO,
					ReferrerUtil.SOHU);
			if (doc == null) {
				logger.error("{}collect videos fail. sourceId = {}, collectPageUrl = {}", new Object[] { SOHU_VIDEO,
						sourceId, collectPageUrl });
			}

			Elements liEle = doc.select("ul.st-list>li");

			// 分别解析每部动漫的信息
			List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>(liEle.size());
			for (final Element liItem : liEle) {
				Future<Boolean> future = parseVideoInfoAsync(sourceId, liItem);
				futureList.add(future);
			}

			// 等待检查 future task 是否完成
			String prefixLog = SOHU_VIDEO + "collectVideos";
			String partLog = String.format("sourceId = %s, liEleSize = %s, videosCount = %s", sourceId,
					liEle.size(), liEle.size());
			waitForFutureTasksDone(futureList, logger, prefixLog, partLog);

			// 下一页
			Elements currentPageSpan = doc.select("body div.ssPages>span");
			Elements nextPageA = doc.select("body div.ssPages>a[title=下一页]");

			// log
			String currPageNo = currentPageSpan != null ? currentPageSpan.text() : null;
			final String nextPageUrl = nextPageA != null ? nextPageA.attr("href") : null;
			logger.info("{}currPageNo = {}, nextPageUrl : {}", new Object[] { SOHU_VIDEO, currPageNo, nextPageUrl });

			// 递归解析
			if (StringUtils.isNotBlank(nextPageUrl)) {
				// 启用新线程运行, 防止深度递归造成线程堆栈溢出
				runWithNewThread(new Runnable() {
					@Override
					public void run() {
						sleepRandom(10, 10, logger);
						collectVideos(sourceId, nextPageUrl, rootUrl);
					}
				});
			} else {
				// 递归到最后, 唤醒主线程
				notifyThread(collectVideosSyncObj);
			}
		} catch (Exception e) {
			logger.error(SOHU_VIDEO + "collectVideos fail, sourceId = " + sourceId + ", collectPageUrl = "
					+ collectPageUrl + ", rootUrl = " + rootUrl, e);
		}
	}

	private Future<Boolean> parseVideoInfoAsync(final int sourceId, final Element liItem) {
		Future<Boolean> future = videoThreadPool.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				parseVideoInfo(sourceId, liItem);
				return true;
			}
		});
		return future;
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
			if (StringUtils.isBlank(name)) {
				name = aEle.text();
			}
			// 记得取别名
			videoFromNet.setName(AppHelper.getAliasNameIfExist(name));
			// first char
			videoFromNet.setFirstChar(AppHelper.convertFirstChar(videoFromNet.getName()));

			// story line brief
			String storyLine = list_hover_Div.select("p.lh-info").html();
			videoFromNet.setStoryLine(AppHelper.subString(storyLine, AppConfig.STORYLINE_MAX_LENGTH, "..."));

			// play count total
			String playCountTotal = list_hover_Div.select("a.acount").first().html();
			videoFromNet.setPlayCountTotal(playCountTotal);

			List<Video> list = videoDao.listExceptBigData(sourceId, videoFromNet.getName());
			if (CollectionUtils.isEmpty(list)) {
				videoFromNet.setName(AppHelper.getAliasNameIfExist(videoFromNet.getName()));
				videoDao.save(videoFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add new video : {}", SOHU_VIDEO, JacksonUtil.encode(videoFromNet));
				}
				return;
			}

			boolean isMatcheRecord = false;
			for (Video videoFromDb : list) {
				// NOTE : 同一视频, 可能iconUrl 会不相同.
				// 比如 youku : http://r1.ykimg.com/0516000050751ED09792730D39069DFB 与 http://r2.ykimg.com/0516000050751ED09792730D39069DFB
				// 比如 suhu : (纳米神兵) http://photocdn.sohu.com/kis/fengmian/1191/1191688/1191688_ver_big.jpg 与 http://photocdn.sohu.com/kis/fengmian/1199/1199302/1199302_ver_big.jpg
				// 同一视频, 播放列表url应该是相同的;
				boolean isListPageUrlSame = StringUtils.trimToEmpty(videoFromNet.getListPageUrl()).equalsIgnoreCase(
						StringUtils.trimToEmpty(videoFromDb.getListPageUrl()));
				if (isListPageUrlSame) {
					//					// 名字和icon相同, 则更新 (因为存在名字相同, 但属于不同视频的视频)
					//					boolean isNameSame = videoFromDb.getName().contains(videoFromNet.getName());
					//					if (isNameSame) {
					isMatcheRecord = true;
					// 比较关键字段是否有更新
					boolean isFieldsSame = BeanUtil.isFieldsValueSame(videoFromNet, videoFromDb,
							videoCompareFieldNames, null);
					if (!isFieldsSame) {
						videoFromNet.setId(videoFromDb.getId()); // id
						videoFromNet.setName(AppHelper.getAliasNameIfExist(videoFromNet.getName()));
						videoDao.udpate(videoFromNet); // 不同则更新
						logger.info("{}update video : \r\n OLD : {}\r\n NEW : {}", new Object[] { SOHU_VIDEO,
								compareFieldsToString(videoFromDb), compareFieldsToString(videoFromNet) });
					}
					break;
					//					}
				}
			}

			// 如果找不到要更新的记录, 则新增
			if (!isMatcheRecord) {
				videoFromNet.setName(AppHelper.getAliasNameIfExist(videoFromNet.getName()));
				videoDao.save(videoFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add new video : {}", SOHU_VIDEO, JacksonUtil.encode(videoFromNet));
				}
			}
		} catch (Exception e) {
			logger.error(SOHU_VIDEO + "parseVideoInfo fail. video = " + JacksonUtil.encodeQuietly(videoFromNet), e);
		}
	}

	private String compareFieldsToString(Video video) {
		StringBuilder buf = new StringBuilder(300);
		buf.append("id = ").append(video.getId()).append(", ");
		buf.append("sourceId = ").append(video.getSourceId()).append(", ");
		buf.append("name = ").append(video.getName()).append(", ");
		buf.append("firstChar = ").append(video.getFirstChar()).append(", ");
		buf.append("updateRemark = ").append(video.getUpdateRemark()).append(", ");
		buf.append("playCountWeekly = ").append(video.getPlayCountWeekly()).append(", ");
		buf.append("playCountTotal = ").append(video.getPlayCountTotal()).append(", ");
		buf.append("iconUrl = ").append(video.getIconUrl()).append(", ");
		buf.append("listPageUrl = ").append(video.getListPageUrl());
		return buf.toString();
	}
}
