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
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.service.job.base.BaseTask;

@Component
public class TencentComicCollectService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(TencentComicCollectService.class);

	@Resource
	private ComicDao comicDao;

	private static final String COMIC_TENCENT_COMIC = ComConstant.LogPrefix.COMIC_TENCENT_COMIC;

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
		//		comicCompareFieldNames.add("posterBigUrl");
		//		comicCompareFieldNames.add("posterMidUrl");
		//		comicCompareFieldNames.add("posterSmallUrl");
		//		comicCompareFieldNames.add("iconUrl");
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
	public void collectComics(final int sourceId, final String collectPageUrl, final String rootUrl, final int pageNo) {
		try {
			// 是否已包含根域名
			final String collectPageUrlTmp = AppHelper.addRootUrlIfNeeded(collectPageUrl, rootUrl);

			// 拼装url
			String collectPageUrlAdded = collectPageUrlTmp + pageNo;

			Document doc = JsoupUtil.connect(collectPageUrlAdded, CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL,
					COMIC_TENCENT_COMIC, ReferrerUtil.TENCENT);

			if (doc == null) {
				if (pageNo == 1) { // 第一次就解析不了, 说明有异常
					logger.error("{}collect comics fail. sourceId = {}, collectPageUrlFmt = {}", new Object[] {
							COMIC_TENCENT_COMIC, sourceId, collectPageUrlAdded });
				}
				return;
			}

			// 检查该页是否已经没有了漫画内容(正常页面, 是没有该element的 "ret-search-result-fail")
			Elements noResult = doc.select(".ret-search-result-fail");
			if (CollectionUtils.isNotEmpty(noResult) || doc.text().contains("未能找到您搜索的作品")) {
				return;
			}

			Elements liEles = doc.select(".ret-search-item");
			if (CollectionUtils.isEmpty(liEles)) {
				logger.error("{}ret-search-item Li elements not found, return. collectPageUrlAdded = {}",
						COMIC_TENCENT_COMIC, collectPageUrlAdded);
				return;
			}

			// 分别比较每部漫画的信息
			List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>(liEles.size());
			for (Element liItem : liEles) {
				Future<Boolean> future = parseComicInfoAsync(sourceId, rootUrl, liItem);
				futureList.add(future);
			}

			// 等待检查 future task 是否完成
			String prefixLog = COMIC_TENCENT_COMIC + "collectComics";
			String partLog = String.format("sourceId = %s, comicCount = %s, futureListSize = %s", sourceId,
					liEles.size(), futureList.size());
			waitForFutureTasksDone(futureList, logger, prefixLog, partLog);

			// 页码加1
			final int pageNoTmp = pageNo + 1;

			// 递归解析
			sleepRandom(10, 10, logger);

			// 启用新线程运行, 防止深度递归造成线程堆栈溢出
			runWithNewThread(new Runnable() {
				@Override
				public void run() {
					collectComics(sourceId, collectPageUrlTmp, rootUrl, pageNoTmp);
				}
			});
		} catch (Exception e) {
			logger.error(COMIC_TENCENT_COMIC + "collectComics fail, sourceId = " + sourceId + ", collectPageUrl = "
					+ collectPageUrl + ", rootUrl = " + rootUrl, e);
		}
	}

	private Future<Boolean> parseComicInfoAsync(final int sourceId, final String rootUrl, final Element liItem) {
		Future<Boolean> future = comicThreadPool.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				parseComicInfo(sourceId, rootUrl, liItem);
				return true;
			}
		});
		return future;
	}

	private void parseComicInfo(int sourceId, String rootUrl, Element liItem) {
		Comic comicFromNet = null;
		try {
			Element img_a = liItem.select(".mod-cover-list-thumb").first();
			String name = img_a.attr("title");
			String listPageUrl = img_a.attr("href");
			// 该 iconUrl 已做防盗链处理，故不读取。
			//			String iconUrl = img_a.select("img").first().attr("data-original");
			String updateRemark = liItem.select(".mod-cover-list-text").first().text();
			String author = liItem.select(".ret-works-author").first().attr("title");
			Elements types = liItem.select(".ret-works-tags a");
			StringBuilder typesBuf = new StringBuilder();
			for (Element type : types) {
				typesBuf.append(type.text()).append(ComConstant.SEP_STR);
			}

			String storyLine = liItem.select(".ret-works-decs").first().text();
			storyLine = AppHelper.subString(storyLine, AppConfig.STORYLINE_MAX_LENGTH, "...");

			comicFromNet = new Comic();
			comicFromNet.setSourceId(sourceId);
			comicFromNet.setName(AppConfig.getAliasNameIfExist(name));
			comicFromNet.setListPageUrl(AppHelper.addRootUrlIfNeeded(listPageUrl, rootUrl));
			//			comicFromNet.setIconUrl(iconUrl);
			comicFromNet.setAuthor(author);
			comicFromNet.setUpdateRemark(updateRemark);
			comicFromNet.setTypes(typesBuf.toString());
			comicFromNet.setStoryLine(storyLine);

			// first char
			String firstChar = Pinyin4jUtil.getFirstChar(comicFromNet.getName()).toUpperCase();
			if (ArrayUtils.contains(ComConstant.LETTERS, firstChar)) {
				comicFromNet.setFirstChar(firstChar);
			} else {
				comicFromNet.setFirstChar(ComConstant.OTHER_CNS);
			}

			List<Comic> list = comicDao.listExceptBigData(comicFromNet.getSourceId(), comicFromNet.getName());
			if (CollectionUtils.isEmpty(list)) {
				comicFromNet.setName(AppConfig.getAliasNameIfExist(comicFromNet.getName()));
				comicDao.save(comicFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add new comic : {}", COMIC_TENCENT_COMIC, JacksonUtil.encode(comicFromNet));
				}
				return;
			}

			boolean isMatcheRecord = false;
			for (Comic comicFromDb : list) {
				// NOTE : 同一漫画, 可能iconUrl 会不相同, 故先判断 listPageUrl是否相同
				boolean isListPageUrlSame = StringUtils.trimToEmpty(comicFromNet.getListPageUrl()).equalsIgnoreCase(
						StringUtils.trimToEmpty(comicFromDb.getListPageUrl()));
				if (isListPageUrlSame) {
					//					// 名字和icon相同, 则更新 (因为存在名字相同, 但属于不同漫画的漫画)
					//					boolean isNameSame = comicFromDb.getName().contains(comicFromNet.getName());
					//					if (isNameSame) {
					isMatcheRecord = true;
					// 比较关键字段是否有更新
					boolean isFieldsSame = BeanUtil.isFieldsValueSame(comicFromNet, comicFromDb,
							comicCompareFieldNames, null);
					if (!isFieldsSame) {
						comicFromNet.setId(comicFromDb.getId()); // id
						comicFromNet.setName(AppConfig.getAliasNameIfExist(comicFromNet.getName()));
						comicDao.udpate(comicFromNet); // 不同则更新
						logger.info("{}update comic : \r\n OLD : {}\r\n NEW : {}", new Object[] { COMIC_TENCENT_COMIC,
								compareFieldsToString(comicFromDb), compareFieldsToString(comicFromNet) });
					}
					break;
					//					}
				}
			}

			// 如果找不到要更新的记录, 则新增
			if (!isMatcheRecord) {
				comicFromNet.setName(AppConfig.getAliasNameIfExist(comicFromNet.getName()));
				comicDao.save(comicFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add new comic : {}", COMIC_TENCENT_COMIC, JacksonUtil.encode(comicFromNet));
				}
			}
		} catch (Exception e) {
			logger.error(COMIC_TENCENT_COMIC + "parseComicInfo fail. sourceId = " + sourceId + ", comic = "
					+ JacksonUtil.encodeQuietly(comicFromNet), e);
		}
	}

	private String compareFieldsToString(Comic comic) {
		StringBuilder buf = new StringBuilder(300);
		buf.append("sourceId = ").append(comic.getSourceId()).append(", ");
		buf.append("name = ").append(comic.getName()).append(", ");
		buf.append("updateRemark = ").append(comic.getUpdateRemark()).append(", ");
		buf.append("listPageUrl = ").append(comic.getListPageUrl());
		return buf.toString();
	}
}