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
import cn.hehe9.persistent.dao.ComicDao;
import cn.hehe9.persistent.entity.Comic;
import cn.hehe9.persistent.entity.ComicSource;
import cn.hehe9.service.job.base.BaseTask;

@Component
public class SohuComicCollectService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(SohuComicCollectService.class);

	@Resource
	private ComicDao comicDao;

	private static final String COMIC_SOHU_COMIC = ComConstant.LogPrefix.COMIC_SOHU_COMIC;

	// 线程池
	private int processCount = Runtime.getRuntime().availableProcessors();
	private ExecutorService comicThreadPool = Executors.newFixedThreadPool(processCount + 1);

	/** 用于比较Comic的属性名称 */
	private static List<String> comicCompareFieldNames = new ArrayList<String>();
	static {
		// comic fields
		comicCompareFieldNames.add("name");
		//		comicCompareFieldNames.add("author");
		//		comicCompareFieldNames.add("playCountWeekly");
		//		comicCompareFieldNames.add("playCountTotal");
		comicCompareFieldNames.add("posterBigUrl");
		//		comicCompareFieldNames.add("posterMidUrl");
		//		comicCompareFieldNames.add("posterSmallUrl");
		comicCompareFieldNames.add("iconUrl");
		comicCompareFieldNames.add("listPageUrl");
		comicCompareFieldNames.add("updateRemark");
	}

	public void collect(ComicSource source) {
		// 页码默认为1
		collectComics(source.getId(), source.getCollectPageUrl(), source.getRootUrl(), 1);
	}

	/**
	 * 解析所有动漫信息
	 * 
	 * @param url
	 * @throws IOException
	 */
	public void collectComics(final int sourceId, String collectPageUrl, String rootUrl, int pageNo) {
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
					logger.error("{}collect comics fail. sourceId = {}, collectPageUrl = {}", new Object[] {
							COMIC_SOHU_COMIC, sourceId, collectPageUrl });
				}
				return;
			}

			Elements liEles = doc.select(".ret-search-item");
			List<Comic> comicList = new ArrayList<Comic>(liEles.size());
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

				Comic comicFromNet = new Comic();
				comicFromNet.setName(name);
				comicFromNet.setListPageUrl(listPageUrl);
				comicFromNet.setIconUrl(iconUrl);
				comicFromNet.setAuthor(author);
				comicFromNet.setUpdateRemark(updateRemark);
				comicFromNet.setTypes(typesBuf.toString());
				comicFromNet.setStoryLine(storyLine);
			}

			// 页码加1
			pageNo++;

			// 分别比较每部漫画的信息
			List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>(liEles.size());
			for (Comic comicFormNet : comicList) {
				Future<Boolean> future = parseComicInfoAsync(comicFormNet);
				futureList.add(future);
			}

			// 等待检查 future task 是否完成
			String prefixLog = COMIC_SOHU_COMIC + "collectComics";
			String partLog = String.format("sourceId = %s, comicCount = %s, futureListSize = %s", sourceId,
					comicList.size(), futureList.size());
			waitForFutureTasksDone(futureList, logger, prefixLog, partLog);

			pageNo++;

			// 递归解析
			sleepRandom(10, 10, logger);
			collectComics(sourceId, collectPageUrl, rootUrl, pageNo);
		} catch (Exception e) {
			logger.error(COMIC_SOHU_COMIC + "collectComics fail, sourceId = " + sourceId + ", collectPageUrl = "
					+ collectPageUrl + ", rootUrl = " + rootUrl, e);
		}
	}

	private Future<Boolean> parseComicInfoAsync(final Comic comicFromNet) {
		Future<Boolean> future = comicThreadPool.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				parseComicInfo(comicFromNet);
				return true;
			}
		});
		return future;
	}

	private void parseComicInfo(Comic comicFromNet) {
		try {
			List<Comic> list = comicDao.searchBriefByName(comicFromNet.getSourceId(), comicFromNet.getName());
			if (CollectionUtils.isEmpty(list)) {
				comicFromNet.setName(AppConfig.getAliasNameIfExist(comicFromNet.getName()));
				comicDao.save(comicFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add new comic : {}", COMIC_SOHU_COMIC, JacksonUtil.encode(comicFromNet));
				}
				return;
			}

			boolean isMatcheRecord = false;
			for (Comic comicFromDb : list) {
				// NOTE : 同一漫画, 可能iconUrl 会不相同, 故先判断 listPageUrl是否相同
				boolean isListPageUrlSame = StringUtils.trimToEmpty(comicFromNet.getListPageUrl()).equalsIgnoreCase(
						StringUtils.trimToEmpty(comicFromDb.getIconUrl()));
				if (isListPageUrlSame) {
					// 名字和icon相同, 则更新 (因为存在名字相同, 但属于不同漫画的漫画)
					boolean isNameSame = comicFromDb.getName().contains(comicFromNet.getName());
					if (isNameSame) {
						isMatcheRecord = true;
						// 比较关键字段是否有更新
						boolean isFieldsSame = BeanUtil.isFieldsValueSame(comicFromNet, comicFromDb,
								comicCompareFieldNames, null);
						if (!isFieldsSame) {
							comicFromNet.setId(comicFromDb.getId()); // id
							comicFromNet.setName(AppConfig.getAliasNameIfExist(comicFromNet.getName()));
							comicDao.udpate(comicFromNet); // 不同则更新

							// for log
							comicFromNet.setStoryLine(null);
							logger.info("{}update comic : \r\n OLD : {}\r\n NEW : {}", new Object[] { COMIC_SOHU_COMIC,
									JacksonUtil.encode(comicFromDb), JacksonUtil.encode(comicFromNet) });
						}
						break;
					}
				}
			}

			// 如果找不到要更新的记录, 则新增
			if (!isMatcheRecord) {
				comicFromNet.setName(AppConfig.getAliasNameIfExist(comicFromNet.getName()));
				comicDao.save(comicFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add new comic : {}", COMIC_SOHU_COMIC, JacksonUtil.encode(comicFromNet));
				}
			}
		} catch (Exception e) {
			logger.error(COMIC_SOHU_COMIC + "parseComicInfo fail. comic = " + JacksonUtil.encodeQuietly(comicFromNet),
					e);
		}
	}
}