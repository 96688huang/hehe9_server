package cn.hehe9.service.job.comic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
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
import cn.hehe9.common.utils.Pinyin4jUtil;
import cn.hehe9.common.utils.ReferrerUtil;
import cn.hehe9.persistent.dao.VideoDao;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoSource;
import cn.hehe9.service.job.base.BaseTask;

@Component
public class SohuComicCollectService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(SohuComicCollectService.class);

	@Resource
	private VideoDao videoDao;

	private static final String COMIC_SOHU_COMIC = ComConstant.LogPrefix.COMIC_SOHU_COMIC;

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
		videoCompareFieldNames.add("posterBigUrl");
		//		videoCompareFieldNames.add("posterMidUrl");
		//		videoCompareFieldNames.add("posterSmallUrl");
		videoCompareFieldNames.add("iconUrl");
		videoCompareFieldNames.add("listPageUrl");
		videoCompareFieldNames.add("updateRemark");
	}

	public void collect(VideoSource source) {
		// 页码默认为1
		collectVideos(source.getId(), source.getCollectPageUrl(), source.getRootUrl(), 1);
	}

	/**
	 * 解析所有动漫信息
	 * 
	 * @param url
	 * @throws IOException
	 */
	public void collectVideos(final int sourceId, String collectPageUrl, String rootUrl, int pageNo) {
		try {
			// 是否已包含根域名
			collectPageUrl = collectPageUrl.contains(rootUrl) ? collectPageUrl : (rootUrl + (!collectPageUrl
					.startsWith("/") ? "/" + collectPageUrl : collectPageUrl));

			// 拼装url
			collectPageUrl = collectPageUrl + pageNo;

			Document doc = JsoupUtil.connect(collectPageUrl, CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL,
					COMIC_SOHU_COMIC, ReferrerUtil.SOHU);
			if (doc == null) {
				if (pageNo == 1) { // 第一次就解析不了
					logger.error("{}collect videos fail. sourceId = {}, collectPageUrl = {}", new Object[] {
							COMIC_SOHU_COMIC, sourceId, collectPageUrl });
				}
				return;
			}

			Elements liEles = doc.select(".ret-search-item");
			List<Video> videoList = new ArrayList<Video>(liEles.size());
			for (Element liItem : liEles) {
				Element img_a = liItem.select(".mod-cover-list-thumb").first();
				String name = img_a.attr("title");
				String listPageUrl = img_a.attr("href");
				String iconUrl = img_a.select("img").first().attr("src");
				String updateRemark = liItem.select(".mod-cover-list-text").first().text();
				String author = liItem.select(".ret-works-author").first().attr("title");
				Elements types = liItem.select(".ret-works-tags a");
				StringBuilder typesBuf = new StringBuilder();
				for (Element type : types) {
					typesBuf.append(type.text()).append("`");
				}

				String storyLine = liItem.select(".ret-works-decs").first().text();
				storyLine = AppHelper.subString(storyLine, AppConfig.STORYLINE_MAX_LENGTH, "...");

				Video videoFromNet = new Video();
				videoFromNet.setName(name);
				videoFromNet.setListPageUrl(listPageUrl);
				videoFromNet.setIconUrl(iconUrl);
				videoFromNet.setAuthor(author);
				videoFromNet.setUpdateRemark(updateRemark);
				videoFromNet.setTypes(typesBuf.toString());
				videoFromNet.setStoryLine(storyLine);
			}

			// 页码加1
			pageNo++;

			// 分别比较每部漫画的信息
			List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>(liEles.size());
			for (Video videoFormNet : videoList) {
				Future<Boolean> future = parseVideoInfoAsync(videoFormNet);
				futureList.add(future);
			}

			// 等待检查 future task 是否完成
			String prefixLog = COMIC_SOHU_COMIC + "collectVideos";
			String partLog = String.format("sourceId = %s, videoCount = %s, futureListSize = %s", sourceId,
					videoList.size(), futureList.size());
			waitForFutureTasksDone(futureList, logger, prefixLog, partLog);

			pageNo++;

			// 递归解析
			sleepRandom(10, 10, logger);
			collectVideos(sourceId, collectPageUrl, rootUrl, pageNo);
		} catch (Exception e) {
			logger.error(COMIC_SOHU_COMIC + "collectVideos fail, sourceId = " + sourceId + ", collectPageUrl = "
					+ collectPageUrl + ", rootUrl = " + rootUrl, e);
		}
	}

	private Future<Boolean> parseVideoInfoAsync(final Video videoFromNet) {
		Future<Boolean> future = videoThreadPool.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				parseVideoInfo(videoFromNet);
				return true;
			}
		});
		return future;
	}

	private void parseVideoInfo(Video videoFromNet) {
		try {
			List<Video> list = videoDao.searchBriefByName(videoFromNet.getSourceId(), videoFromNet.getName());
			if (CollectionUtils.isEmpty(list)) {
				videoFromNet.setName(AppConfig.getAliasNameIfExist(videoFromNet.getName()));
				videoDao.save(videoFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add new video : {}", COMIC_SOHU_COMIC, JacksonUtil.encode(videoFromNet));
				}
				return;
			}

			boolean isMatcheRecord = false;
			for (Video videoFromDb : list) {
				// NOTE : 同一漫画, 可能iconUrl 会不相同, 故先判断 listPageUrl是否相同
				boolean isListPageUrlSame = StringUtils.trimToEmpty(videoFromNet.getListPageUrl()).equalsIgnoreCase(
						StringUtils.trimToEmpty(videoFromDb.getIconUrl()));
				if (isListPageUrlSame) {
					// 名字和icon相同, 则更新 (因为存在名字相同, 但属于不同漫画的漫画)
					boolean isNameSame = videoFromDb.getName().contains(videoFromNet.getName());
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
							logger.info("{}update video : \r\n OLD : {}\r\n NEW : {}", new Object[] { COMIC_SOHU_COMIC,
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
					logger.debug("{}add new video : {}", COMIC_SOHU_COMIC, JacksonUtil.encode(videoFromNet));
				}
			}
		} catch (Exception e) {
			logger.error(COMIC_SOHU_COMIC + "parseVideoInfo fail. video = " + JacksonUtil.encodeQuietly(videoFromNet),
					e);
		}
	}
}