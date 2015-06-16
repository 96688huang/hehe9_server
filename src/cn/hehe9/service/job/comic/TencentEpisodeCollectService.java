package cn.hehe9.service.job.comic;

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
import cn.hehe9.common.utils.StringUtil;
import cn.hehe9.persistent.dao.ComicDao;
import cn.hehe9.persistent.dao.ComicEpisodeDao;
import cn.hehe9.persistent.entity.Comic;
import cn.hehe9.persistent.entity.ComicEpisode;
import cn.hehe9.service.job.base.BaseTask;

@Component
public class TencentEpisodeCollectService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(TencentEpisodeCollectService.class);

	@Resource
	private ComicDao comicDao;

	@Resource
	private ComicEpisodeDao comicEpisodeDao;

	private static final String COMIC_TENCENT_EPISODE = ComConstant.LogPrefix.COMIC_TENCENT_EPISODE;

	// 线程池
	private int processCount = Runtime.getRuntime().availableProcessors();
	private ExecutorService episodeThreadPool = Executors.newFixedThreadPool(processCount + 1);

	/** 用于比较Episode的属性名称 */
	private static List<String> episodeCompareFieldNames = new ArrayList<String>();
	static {
		// comic episode fields
		episodeCompareFieldNames.add("title");
		episodeCompareFieldNames.add("read_page_url");
		//		episodeCompareFieldNames.add("snapshot_url");
		//		episodeCompareFieldNames.add("file_url");
	}

	/**
	 * 解析某漫画的播放url, 漫画url, 第几集等信息
	 * 
	 * @param indexUrl
	 * @throws Exception
	 */
	public void collectEpisodeFromListPage(final Comic comic) {
		try {
			Document doc = JsoupUtil.connect(comic.getListPageUrl(), CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL,
					COMIC_TENCENT_EPISODE, ReferrerUtil.TENCENT);
			if (doc == null) {
				logger.error("{}collect episode from list page fail. comic = {}", COMIC_TENCENT_EPISODE,
						JacksonUtil.encodeQuietly(comic));
			}

			Element works_cover_Div = doc.select(".works-cover").first();
			Element img_a = works_cover_Div.select("a").first();
			String name = img_a.attr("title");
			String iconUrl = img_a.select("img").attr("src");
			String serializeStatus = works_cover_Div.select(".works-intro-status").text();
			String storyLine = doc.select(".works-intro-short").text();

			if (StringUtils.isNotBlank(name) && !comic.getName().contains(name)) {
				logger.error(
						"{}name from episode(net) is different with comic, nameFromEpisoceNet = {}, nameFromComic={}",
						new Object[] { COMIC_TENCENT_EPISODE, name, comic.getName() });
			}

			if (StringUtils.isBlank(comic.getStoryLine())) {
				comic.setStoryLine(AppHelper.subString(storyLine, AppConfig.STORYLINE_MAX_LENGTH, "..."));
			}

			Element works_ft_new_A = doc.select(".works-ft-new").first();
			String updateRemarkText = works_ft_new_A == null ? null : works_ft_new_A.text();
			if (StringUtils.isNotBlank(updateRemarkText)) {
				comic.setUpdateRemark(updateRemarkText.replace("[", "").replace("]", ""));
			}
			
			// NOTE: 下面代码是取分集数, 再组装成 updateRemark
			//			String updateRemarkText = works_ft_new_A.text().split(" ")[0].replace("[", "").replace("]", "");
			//			String episodeNoFromListPage = StringUtil.pickInteger(updateRemarkText);
			//			String episodeNoFromDb = StringUtil.pickInteger(comic.getUpdateRemark());
			//			if (!episodeNoFromListPage.equals(episodeNoFromDb)) {
			//				String updateRemark = "更新至" + updateRemarkText + "话";
			//				comic.setRemark(updateRemark);
			//			}

			comic.setIconUrl(iconUrl);
			comic.setSerializeStatus(serializeStatus);
			comicDao.udpate(comic);

			// 分集信息
			Element chapter_page_all_OL = doc.select("ol").first();
			Elements works_chapter_item_Spans = chapter_page_all_OL.select(".works-chapter-item");
			List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>(works_chapter_item_Spans.size());
			int episodeNoTmp = 1;
			for (final Element spanItem : works_chapter_item_Spans) {
				Future<Boolean> future = parseEpisodeAsync(comic, spanItem, episodeNoTmp);
				futureList.add(future);
				episodeNoTmp++;
			}

			// 等待检查 future task 是否完成
			String prefixLog = COMIC_TENCENT_EPISODE + "collectEpisodeFromListPage";
			String partLog = String.format("comicId = %s, comicName = %s, comicEpisodesCount = %s", comic.getId(),
					comic.getName(), works_chapter_item_Spans.size(), futureList.size());
			waitForFutureTasksDone(futureList, logger, prefixLog, partLog);
		} catch (Exception e) {
			logger.error(COMIC_TENCENT_EPISODE + "collectEpisodeFromListPage fail. comic : "
					+ JacksonUtil.encodeQuietly(comic), e);
			
// NOTE: 手工删除, 以免把已采集大部分分集信息的漫画删掉.
//			logger.error(COMIC_TENCENT_EPISODE + "collectEpisodeFromListPage fail, delete comic. comic : "
//					+ JacksonUtil.encodeQuietly(comic), e);
//			comicDao.deleteBy(comic.getId());
		}
	}

	private Future<Boolean> parseEpisodeAsync(final Comic comic, final Element spanItem, final int episodeNoTmp) {
		Future<Boolean> future = episodeThreadPool.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				parseEpisode(comic, spanItem, episodeNoTmp);
				return true;
			}
		});

		return future;
	}

	private void parseEpisode(Comic comic, Element spanItem, int episodeNoTmp) {
		try {
			ComicEpisode episodeFromNet = new ComicEpisode();
			Element a = spanItem.select("a").first();
			String title = a.text();
			Integer episodeNo = 0;

			String subEpisodeNoStr = null;
			if (title.contains(" ")) {
				subEpisodeNoStr = title.split(" ")[0];
			} else if (title.contains("话")) {
				subEpisodeNoStr = title.split("话")[0];
			} else if (title.contains("集")) {
				subEpisodeNoStr = title.split("集")[0];
			} else if (title.contains("讲")) {
				subEpisodeNoStr = title.split("讲")[0];
			}

			if (StringUtils.isBlank(subEpisodeNoStr)) {
				episodeNo = episodeNoTmp;
			} else {
				String episodeNoStr = StringUtil.pickInteger(subEpisodeNoStr);
				if (StringUtils.isBlank(episodeNoStr)) {
					episodeNo = episodeNoTmp;
				} else {
					episodeNo = Integer.parseInt(episodeNoStr);
					if (episodeNo.intValue() > 10000) { // NOTE: 超过10000， 则用自增分集号
						episodeNo = episodeNoTmp;
					}
				}
			}

			String readPageUrl = a.attr("href");
			episodeFromNet.setComicId(comic.getId());
			episodeFromNet.setTitle(title);
			episodeFromNet.setEpisodeNo(episodeNo);
			episodeFromNet.setReadPageUrl(AppHelper.addRootUrlIfNeeded(readPageUrl, comic.getRootUrl()));

			// 因为会出现同一集漫画, 再分上下集的情况, 在查询时, 以episodeNo不够准确, 故采用 readPageUrl作为查询条件.
			List<ComicEpisode> episodeFromDbList = comicEpisodeDao.findByReadPageUrl(episodeFromNet.getComicId(),
					episodeFromNet.getReadPageUrl());

			// 检查是否存在相同readPageUrl的情况
			if (CollectionUtils.isNotEmpty(episodeFromDbList) && episodeFromDbList.size() > 1) {
				logger.error("{}more than one comics has same readPageUrl. please check. episodeFromNet : {}",
						COMIC_TENCENT_EPISODE, JacksonUtil.encodeQuietly(episodeFromNet));
			}

			ComicEpisode episodeFromDb = CollectionUtils.isEmpty(episodeFromDbList) ? null : episodeFromDbList.get(0);
			if (episodeFromDb == null) {
				comicEpisodeDao.save(episodeFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add comic episode : {}", COMIC_TENCENT_EPISODE, JacksonUtil.encode(episodeFromNet));
				}
				return;
			}

			boolean isSame = BeanUtil.isFieldsValueSame(episodeFromNet, episodeFromDb, episodeCompareFieldNames, null);
			if (!isSame) {
				episodeFromNet.setId(episodeFromDb.getId()); // 主键id
				comicEpisodeDao.udpate(episodeFromNet); // 不同则更新
				logger.info("{}update comic episode : \r\n OLD : {}\r\n NEW : {}", new Object[] {
						COMIC_TENCENT_EPISODE, compareFieldsToString(episodeFromDb),
						compareFieldsToString(episodeFromNet) });
			}
		} catch (Exception e) {
			logger.error(COMIC_TENCENT_EPISODE + "parseEpisode fail. comic : " + JacksonUtil.encodeQuietly(comic), e);
		}
	}

	private String compareFieldsToString(ComicEpisode episode) {
		StringBuilder buf = new StringBuilder(300);
		buf.append("id = ").append(episode.getId()).append(", ");
		buf.append("comicId = ").append(episode.getComicId()).append(", ");
		buf.append("title = ").append(episode.getTitle()).append(", ");
		buf.append("readPageUrl = ").append(episode.getReadPageUrl());
		return buf.toString();
	}
}
