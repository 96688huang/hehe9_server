package cn.hehe9.service.job.sohu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

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
import cn.hehe9.common.utils.StringUtil;
import cn.hehe9.persistent.dao.VideoDao;
import cn.hehe9.persistent.dao.VideoEpisodeDao;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoEpisode;
import cn.hehe9.service.job.base.BaseTask;

@Component
public class SohuEpisodeCollectService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(SohuEpisodeCollectService.class);

	@Resource
	private VideoDao videoDao;

	@Resource
	private VideoEpisodeDao videoEpisodeDao;

	private static final String SOHU_EPISODE = ComConstant.LogPrefix.SOHU_EPISODE;

	// 线程池
	private int processCount = Runtime.getRuntime().availableProcessors();
	private ExecutorService episodeThreadPool = Executors.newFixedThreadPool(processCount + 1);

	/** 用于比较Episode的属性名称 */
	private static List<String> episodeCompareFieldNames = new ArrayList<String>();
	static {
		// video episode fields
		episodeCompareFieldNames.add("title");
		episodeCompareFieldNames.add("play_page_url");
		episodeCompareFieldNames.add("snapshot_url");
		episodeCompareFieldNames.add("file_url");
	}

	/**
	 * 解析某视频的播放url, 视频url, 第几集等信息
	 * 
	 * @param indexUrl
	 * @throws Exception
	 */
	public void collectEpisodeFromListPage(final Video video) {
		try {
			Document doc = JsoupUtil.connect(video.getListPageUrl(), CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL,
					SOHU_EPISODE, ReferrerUtil.SOHU);
			if (doc == null) {
				logger.error("{}collect episode from list page fail. video = {}", SOHU_EPISODE,
						JacksonUtil.encodeQuietly(video));
			}

			// 分集标题
			Elements pagecontDiv = doc.select(".pagecont");
			//{ key : episodeNo, value : title }
			final Map<Integer, String> titleMap = new HashMap<Integer, String>();
			for (Element pagecontItem : pagecontDiv) {
				Elements listJs = pagecontItem.select(".listJs");
				for (Element listJsItem : listJs) {
					String bitText = listJsItem.select(".bti").text();
					String wzText = listJsItem.select(".wz").text();

					String episodeNo = StringUtil.pickInteger(bitText);
					if (StringUtils.isNotBlank(episodeNo)) {
						titleMap.put(Integer.parseInt(episodeNo),
								AppHelper.subString(wzText, AppConfig.TITLE_MAX_LENGTH));
					}
				}
			}

			// 大图
			String posterBigUrl = doc.select("#picFocus>a>img").attr("src");
			video.setPosterBigUrl(posterBigUrl);

			// 简介
			String storyLine = doc.select("#ablum2").select("div.wz").text();
			if (StringUtils.isBlank(video.getStoryLine())) {
				video.setStoryLine(AppHelper.subString(storyLine, AppConfig.STORYLINE_MAX_LENGTH, "..."));
			}

			// 作者， 年份， 类型等信息
			// 动漫名称
			String name = doc.select("div.right div.blockRA h2 span").text();
			if (StringUtils.isNotBlank(name) && !video.getName().contains(name)) {
				logger.error(
						"{}name from episode(net) is different with video, nameFromEpisoceNet = {}, nameFromVideo={}",
						new Object[] { SOHU_EPISODE, name, video.getName() });
			}

			StringBuffer buf = new StringBuffer();
			Elements holeEles = doc.select("div.right div.blockRA div.cont p");
			for (Element element : holeEles) {
				if (buf.length() > AppConfig.TITLE_MAX_LENGTH) {
					break;
				}
				String authorEtc = element.text();
				buf.append(authorEtc).append("<br/>"); // 加上<br/>, 以便在页面上显示换行效果;
			}

			video.setAuthor(AppHelper.subString(buf.toString(), AppConfig.TITLE_MAX_LENGTH, "..."));
			videoDao.udpate(video);

			// 播放页url， 分集截图，集数等
			Element div = doc.select("div.similarLists").first();
			if (div == null) {
				div = doc.select("#similarLists").first();
			}
			if (div == null) {
				logger.error(SOHU_EPISODE + "collect episodes fail, as element is null. video = "
						+ JacksonUtil.encodeQuietly(video));
				return;
			}

			Elements liElements = div.select("ul>li");

			// 计数器
			final AtomicInteger episodeCounter = createCouter();
			// 同步锁对象
			final Object episodeSyncObj = createSyncObject();

			for (final Element ele : liElements) {
				parseEpisodeAsync(video, titleMap, ele, liElements.size(), episodeCounter, episodeSyncObj);
			}

			// 等待被唤醒(被唤醒后, 重置计数器)
			int lastCount = waitingForNotify(episodeCounter, liElements.size(), episodeSyncObj, SOHU_EPISODE, logger);
			if (logger.isDebugEnabled()) {
				logger.debug("{}collectEpisodeFromListPage : 任务线程被唤醒, 本次计算了的分集数 = {}, 重置计数器 = {}.", new Object[] {
						SOHU_EPISODE, lastCount, episodeCounter.get() });
			}
		} catch (Exception e) {
			logger.error(SOHU_EPISODE + "collectEpisodeFromListPage fail. video : " + JacksonUtil.encodeQuietly(video),
					e);
		}
	}

	private void parseEpisodeAsync(final Video video, final Map<Integer, String> titleMap, final Element ele,
			final int totalEpisodeCount, final AtomicInteger episodeCounter, final Object episodeSyncObj) {
		Runnable episodeTask = new Runnable() {
			public void run() {
				try {
					parseEpisode(video, titleMap, ele);
				} finally {
					String logMsg = logger.isDebugEnabled() ? String.format(
							"%s parseEpisodeAsync : 准备唤醒任务线程. 本线程已计算了 %s 个分集, 本次计算分集数 = %s", new Object[] {
									SOHU_EPISODE, episodeCounter.get() + 1, totalEpisodeCount }) : null;
					notifyMasterThreadIfNeeded(episodeCounter, totalEpisodeCount, episodeSyncObj, logMsg, logger);
				}
			}
		};
		episodeThreadPool.execute(episodeTask);
	}

	private void parseEpisode(Video video, Map<Integer, String> titleMap, Element ele) {
		try {
			String episodeNoStr = ele.select("a").last().text();
			String playPageUrl = ele.select("a").last().attr("href");
			String snapshotUrl = ele.select("img").attr("src");

			// parse episodeNo
			// NOTE:有可能出现这样的内容: "第116话 视线360度!白眼的死角", 需要挑出集数.
			String subEpisodeNoStr = episodeNoStr;
			if (episodeNoStr.contains(" ")) {
				subEpisodeNoStr = episodeNoStr.split(" ")[0];
			} else if (episodeNoStr.contains("话")) {
				subEpisodeNoStr = episodeNoStr.split("话")[0];
			} else if (episodeNoStr.contains("集")) {
				subEpisodeNoStr = episodeNoStr.split("集")[0];
			} else if (episodeNoStr.contains("讲")) {
				subEpisodeNoStr = episodeNoStr.split("讲")[0];
			}

			String episodeNo = StringUtil.pickInteger(subEpisodeNoStr);
			if (StringUtils.isBlank(episodeNo)) {
				// 没有集数, 并且没有播放url, 则不处理(有可能是预告信息)
				if (StringUtils.isBlank(playPageUrl)) {
					logger.error("{}parseEpisode fail. episodeNo is blank. video : {}", SOHU_EPISODE,
							JacksonUtil.encodeQuietly(video));
					return;
				}
				episodeNo = "1"; // 没有集数, 则默认为1集
			}

			String fileUrl = parseVideoFileUrl(playPageUrl);

			VideoEpisode episodeFromNet = new VideoEpisode();
			episodeFromNet.setVideoId(video.getId());
			episodeFromNet.setSnapshotUrl(snapshotUrl);
			episodeFromNet.setPlayPageUrl(playPageUrl);
			episodeFromNet.setEpisodeNo(Integer.parseInt(episodeNo));
			episodeFromNet.setFileUrl(fileUrl);
			episodeFromNet.setTitle(titleMap.get(episodeNo));

			VideoEpisode episodeFromDb = videoEpisodeDao.findByVideoIdEpisodeNo(video.getId(),
					episodeFromNet.getEpisodeNo());
			if (episodeFromDb == null) {
				videoEpisodeDao.save(episodeFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add video episode : {}", SOHU_EPISODE, JacksonUtil.encode(episodeFromNet));
				}
				return;
			}

			boolean isSame = BeanUtil.isFieldsValueSame(episodeFromNet, episodeFromDb, episodeCompareFieldNames, null);
			if (!isSame) {
				episodeFromNet.setId(episodeFromDb.getId()); // 主键id
				videoEpisodeDao.udpate(episodeFromNet); // 不同则更新
				logger.info("{}update video episode : \r\n OLD : {}\r\n NEW : {}", new Object[] { SOHU_EPISODE,
						JacksonUtil.encode(episodeFromDb), JacksonUtil.encode(episodeFromNet) });
			}
		} catch (Exception e) {
			logger.error(SOHU_EPISODE + "parseEpisode fail. video : " + JacksonUtil.encodeQuietly(video), e);
		}
	}

	/**
	 * 解析播放页面的视频url
	 * 
	 * @param playPageUrl	播放页面url
	 * @return	视频url
	 * @throws IOException
	 */
	private String parseVideoFileUrl(String playPageUrl) throws IOException {
		Document doc = JsoupUtil.connect(playPageUrl, CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL, SOHU_EPISODE,
				ReferrerUtil.SOHU);
		if (doc == null) {
			return null;
		}

		String fileUrl = doc.select("meta[property=og:videosrc]").attr("content");
		if (StringUtils.isBlank(fileUrl)) {
			fileUrl = doc.select("meta[property=og:video]").attr("content");
		}
		return fileUrl;
	}
}
