package cn.hehe9.service.job.comic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
import cn.hehe9.persistent.dao.ComicDao;
import cn.hehe9.persistent.dao.ComicEpisodeDao;
import cn.hehe9.persistent.entity.Comic;
import cn.hehe9.persistent.entity.ComicEpisode;
import cn.hehe9.service.job.base.BaseTask;

@Component
public class SohuEpisodeCollectService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(SohuEpisodeCollectService.class);

	@Resource
	private ComicDao comicDao;

	@Resource
	private ComicEpisodeDao comicEpisodeDao;

	private static final String COMIC_SOHU_EPISODE = ComConstant.LogPrefix.COMIC_SOHU_EPISODE;

	// 线程池
	private int processCount = Runtime.getRuntime().availableProcessors();
	private ExecutorService episodeThreadPool = Executors.newFixedThreadPool(processCount + 1);

	/** 用于比较Episode的属性名称 */
	private static List<String> episodeCompareFieldNames = new ArrayList<String>();
	static {
		// comic episode fields
		episodeCompareFieldNames.add("title");
		episodeCompareFieldNames.add("play_page_url");
		//		episodeCompareFieldNames.add("snapshot_url");
		//		episodeCompareFieldNames.add("file_url");
	}

	/**
	 * 解析某视频的播放url, 视频url, 第几集等信息
	 * 
	 * @param indexUrl
	 * @throws Exception
	 */
	public void collectEpisodeFromListPage(final Comic comic) {
		try {
			Document doc = JsoupUtil.connect(comic.getListPageUrl(), CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL,
					COMIC_SOHU_EPISODE, ReferrerUtil.SOHU);
			if (doc == null) {
				logger.error("{}collect episode from list page fail. comic = {}", COMIC_SOHU_EPISODE,
						JacksonUtil.encodeQuietly(comic));
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
			comic.setPosterBigUrl(posterBigUrl);

			// 简介
			String storyLine = doc.select("#ablum2").select("div.wz").text();
			if (StringUtils.isBlank(comic.getStoryLine())) {
				comic.setStoryLine(AppHelper.subString(storyLine, AppConfig.STORYLINE_MAX_LENGTH, "..."));
			}

			// 作者， 年份， 类型等信息
			// 动漫名称
			String name = doc.select("div.right div.blockRA h2 span").text();
			if (StringUtils.isNotBlank(name) && !comic.getName().contains(name)) {
				logger.error(
						"{}name from episode(net) is different with comic, nameFromEpisoceNet = {}, nameFromComic={}",
						new Object[] { COMIC_SOHU_EPISODE, name, comic.getName() });
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

			comic.setAuthor(AppHelper.subString(buf.toString(), AppConfig.TITLE_MAX_LENGTH, "..."));
			comicDao.udpate(comic);

			// 播放页url， 分集截图，集数等
			Element div = doc.select("div.similarLists").first();
			if (div == null) {
				div = doc.select("#similarLists").first();
			}
			if (div == null) {
				logger.error(COMIC_SOHU_EPISODE + "collect episodes fail, as element is null. comic = "
						+ JacksonUtil.encodeQuietly(comic));
				return;
			}

			Elements liElements = div.select("ul>li");

			List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>(liElements.size());
			for (final Element ele : liElements) {
				Future<Boolean> future = parseEpisodeAsync(comic, titleMap, ele);
				futureList.add(future);
			}

			// 等待检查 future task 是否完成
			String prefixLog = COMIC_SOHU_EPISODE + "collectEpisodeFromListPage";
			String partLog = String.format("comicId = %s, comicName = %s, liElementsSize = %s, futureListSize = %s",
					comic.getId(), comic.getName(), liElements.size(), futureList.size());
			waitForFutureTasksDone(futureList, logger, prefixLog, partLog);
		} catch (Exception e) {
			logger.error(
					COMIC_SOHU_EPISODE + "collectEpisodeFromListPage fail. comic : " + JacksonUtil.encodeQuietly(comic),
					e);
		}
	}

	private Future<Boolean> parseEpisodeAsync(final Comic comic, final Map<Integer, String> titleMap, final Element ele) {
		Future<Boolean> future = episodeThreadPool.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				parseEpisode(comic, titleMap, ele);
				return true;
			}
		});

		return future;
	}

	private void parseEpisode(Comic comic, Map<Integer, String> titleMap, Element ele) {
		try {
			String episodeNoStrText = ele.select("a").last().text();
			String playPageUrl = ele.select("a").last().attr("href");
			String snapshotUrl = ele.select("img").attr("src");

			// parse episodeNo
			// NOTE:有可能出现这样的内容: "第116话 视线360度!白眼的死角", 需要挑出集数.
			String subEpisodeNoStr = episodeNoStrText;
			if (episodeNoStrText.contains(" ")) {
				subEpisodeNoStr = episodeNoStrText.split(" ")[0];
			} else if (episodeNoStrText.contains("话")) {
				subEpisodeNoStr = episodeNoStrText.split("话")[0];
			} else if (episodeNoStrText.contains("集")) {
				subEpisodeNoStr = episodeNoStrText.split("集")[0];
			} else if (episodeNoStrText.contains("讲")) {
				subEpisodeNoStr = episodeNoStrText.split("讲")[0];
			}

			String episodeNoStr = StringUtil.pickInteger(subEpisodeNoStr);
			if (StringUtils.isBlank(episodeNoStr)) {
				// 没有集数, 并且没有播放url, 则不处理(有可能是预告信息)
				if (StringUtils.isBlank(playPageUrl)) {
					logger.error("{}parseEpisode fail. episodeNo is blank. comic : {}", COMIC_SOHU_EPISODE,
							JacksonUtil.encodeQuietly(comic));
					return;
				}
				episodeNoStr = "1"; // 没有集数, 则默认为1集
			}

			Integer episodeNo = Integer.parseInt(episodeNoStr);
			String fileUrl = parseComicFileUrl(playPageUrl);

			ComicEpisode episodeFromNet = new ComicEpisode();
			episodeFromNet.setComicId(comic.getId());
			episodeFromNet.setReadPageUrl(playPageUrl);
			episodeFromNet.setEpisodeNo(episodeNo);
			episodeFromNet.setPicUrls(fileUrl);
			episodeFromNet.setTitle(titleMap.get(episodeNo));

			ComicEpisode episodeFromDb = comicEpisodeDao.findByComicIdEpisodeNo(comic.getId(),
					episodeFromNet.getEpisodeNo());
			if (episodeFromDb == null) {
				comicEpisodeDao.save(episodeFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add comic episode : {}", COMIC_SOHU_EPISODE, JacksonUtil.encode(episodeFromNet));
				}
				return;
			}

			boolean isSame = BeanUtil.isFieldsValueSame(episodeFromNet, episodeFromDb, episodeCompareFieldNames, null);
			if (!isSame) {
				episodeFromNet.setId(episodeFromDb.getId()); // 主键id
				comicEpisodeDao.udpate(episodeFromNet); // 不同则更新
				logger.info("{}update comic episode : \r\n OLD : {}\r\n NEW : {}", new Object[] { COMIC_SOHU_EPISODE,
						JacksonUtil.encode(episodeFromDb), JacksonUtil.encode(episodeFromNet) });
			}
		} catch (Exception e) {
			logger.error(COMIC_SOHU_EPISODE + "parseEpisode fail. comic : " + JacksonUtil.encodeQuietly(comic), e);
		}
	}

	/**
	 * 解析播放页面的视频url
	 * 
	 * @param playPageUrl	播放页面url
	 * @return	视频url
	 * @throws IOException
	 */
	private String parseComicFileUrl(String playPageUrl) throws IOException {
		Document doc = JsoupUtil.connect(playPageUrl, CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL, COMIC_SOHU_EPISODE,
				ReferrerUtil.SOHU);
		if (doc == null) {
			return null;
		}

		String fileUrl = doc.select("meta[property=og:comicsrc]").attr("content");
		if (StringUtils.isBlank(fileUrl)) {
			fileUrl = doc.select("meta[property=og:comic]").attr("content");
		}
		return fileUrl;
	}
}
