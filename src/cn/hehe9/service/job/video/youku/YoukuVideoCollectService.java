package cn.hehe9.service.job.video.youku;

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
public class YoukuVideoCollectService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(YoukuVideoCollectService.class);

	@Resource
	private VideoDao videoDao;

	private static final String YOUKU_VIDEO = ComConstant.LogPrefix.VIDEO_YOUKU_VIDEO;

	private byte[] collectVideosSyncObj = new byte[0];

	// 线程池
	private int processCount = Runtime.getRuntime().availableProcessors();
	private ExecutorService videoThreadPool = Executors.newFixedThreadPool(processCount + 1);

	/** 用于比较Video的属性名称 */
	private static List<String> videoCompareFieldNames = new ArrayList<String>();
	static {
		// video fields
		videoCompareFieldNames.add("name");
		//		videoCompareFieldNames.add("author");
		//		videoCompareFieldNames.add("playCountWeekly");
		//		videoCompareFieldNames.add("playCountTotal");
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
		waitingForNotify(collectVideosSyncObj, YOUKU_VIDEO, logger);
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

			Document doc = JsoupUtil.connect(collectPageUrl, CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL, YOUKU_VIDEO,
					ReferrerUtil.YOUKU);
			if (doc == null) {
				logger.error("{}collectVideos fail. sourceId = {}, collectPageUrl = {}", new Object[] { YOUKU_VIDEO,
						sourceId, collectPageUrl });
			}

			Elements yk_co13_Eles = doc.select(".yk-col3");

			// 分别解析每部动漫的信息
			List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>(yk_co13_Eles.size());
			for (final Element item : yk_co13_Eles) {
				Future<Boolean> future = parseVideoInfoAsync(sourceId, item);
				futureList.add(future);
			}

			// 等待检查 future task 是否完成
			String prefixLog = YOUKU_VIDEO + "collectVideos";
			String partLog = String.format("sourceId = %s, videosCount = %s", sourceId,
					yk_co13_Eles.size(), futureList.size());
			waitForFutureTasksDone(futureList, logger, prefixLog, partLog);

			// 下一页
			Elements currentPageA = doc.select(".yk-pager .yk-pages .current>span");
			Elements nextPageA = doc.select(".yk-pager .yk-pages .next a");

			// log
			String currPageNo = currentPageA != null ? currentPageA.text() : null;
			final String nextPageUrl = nextPageA != null ? nextPageA.attr("href") : null;
			logger.info("{}currPageNo = {}, nextPageUrl : {}", new Object[] { YOUKU_VIDEO, currPageNo, nextPageUrl });

			if (nextPageA == null) {
				return;
			}

			// 递归解析
			if (StringUtils.isNotBlank(nextPageUrl)) {
				sleepRandom(10, 10, logger);

				// 启用新线程运行, 防止深度递归造成线程堆栈溢出
				runWithNewThread(new Runnable() {
					@Override
					public void run() {
						collectVideos(sourceId, nextPageUrl, rootUrl);
					}
				});
			} else {
				// 递归到最后, 唤醒主线程
				notifyThread(collectVideosSyncObj);
			}
		} catch (Exception e) {
			logger.error(YOUKU_VIDEO + "collectVideos fail, sourceId = " + sourceId + ", collectPageUrl = "
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

	private void parseVideoInfo(int sourceId, Element item) {
		Video videoFromNet = new Video();
		videoFromNet.setSourceId(sourceId);
		try {
			String iconUrl = item.select(".p .p-thumb img").attr("src");
			String name = item.select(".p .p-thumb img").attr("alt");
			if (StringUtils.isBlank(name)) {
				name = item.select(".p .p-meta .p-meta-title").text();
			}

			String updateRemark = item.select(".p .p-thumb .p-thumb-taglb .p-status").text();

			String listPageUrl = item.select(".p .p-link a").attr("href");
			if (StringUtils.isBlank(listPageUrl)) {
				listPageUrl = item.select(".p .p-meta .p-meta-title a").attr("href");
			}

			//			String author = item.select(".p .p-meta .p-meta-entry .p-actor").text();
			//			String playCountTotal = item.select(".p .p-meta .p-meta-entry .p-num").text();

			videoFromNet.setName(AppHelper.getAliasNameIfExist(name));
			videoFromNet.setIconUrl(iconUrl);
			videoFromNet.setUpdateRemark(updateRemark);
			videoFromNet.setListPageUrl(listPageUrl);
			//			videoFromNet.setAuthor(author);
			//			videoFromNet.setPlayCountTotal(playCountTotal);

			// first char
			videoFromNet.setFirstChar(AppHelper.convertFirstChar(videoFromNet.getName()));

			List<Video> list = videoDao.listExceptBigData(sourceId, videoFromNet.getName());
			if (CollectionUtils.isEmpty(list)) {
				videoFromNet.setName(AppHelper.getAliasNameIfExist(videoFromNet.getName()));
				videoDao.save(videoFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add new video : {}", YOUKU_VIDEO, JacksonUtil.encode(videoFromNet));
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
						logger.info("{}update video : \r\n OLD : {}\r\n NEW : {}", new Object[] { YOUKU_VIDEO,
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
					logger.debug("{}add new video : {}", YOUKU_VIDEO, JacksonUtil.encode(videoFromNet));
				}
			}
		} catch (Exception e) {
			logger.error(YOUKU_VIDEO + "collectVideos fail. video = " + JacksonUtil.encodeQuietly(videoFromNet), e);
		}
	}

	private String compareFieldsToString(Video video) {
		StringBuilder buf = new StringBuilder(300);
		buf.append("id = ").append(video.getId()).append(", ");
		buf.append("sourceId = ").append(video.getSourceId()).append(", ");
		buf.append("name = ").append(video.getName()).append(", ");
		buf.append("updateRemark = ").append(video.getUpdateRemark()).append(", ");
		buf.append("iconUrl = ").append(video.getIconUrl()).append(", ");
		buf.append("listPageUrl = ").append(video.getListPageUrl());
		return buf.toString();
	}
}
