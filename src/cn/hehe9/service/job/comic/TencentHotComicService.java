package cn.hehe9.service.job.comic;

import java.util.ArrayList;
import java.util.List;

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
import cn.hehe9.common.constants.ComicSourceName;
import cn.hehe9.common.utils.JacksonUtil;
import cn.hehe9.common.utils.JsoupUtil;
import cn.hehe9.common.utils.ListUtil;
import cn.hehe9.common.utils.ReferrerUtil;
import cn.hehe9.persistent.entity.Comic;
import cn.hehe9.persistent.entity.ComicSource;
import cn.hehe9.service.biz.ComicService;
import cn.hehe9.service.biz.ComicSourceService;
import cn.hehe9.service.job.base.BaseTask;

@Component
public class TencentHotComicService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(TencentHotComicService.class);

	private static final String COMIC_TENCENT_HOT_COMIC = ComConstant.LogPrefix.COMIC_TENCENT_HOT_COMIC;

	@Resource
	private ComicSourceService comicSourceService;

	@Resource
	private ComicService comicService;

	@Resource
	private TencentService tencentService;

	public void collectHotComics() {
		try {
			String hotComicCollectPageUrl = AppConfig.TENCENT_HOT_COMIC_COLLECT_PAGE_URL;
			Document doc = JsoupUtil.connect(hotComicCollectPageUrl, CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL,
					COMIC_TENCENT_HOT_COMIC, ReferrerUtil.TENCENT);
			String sourceName = ComicSourceName.TENCENT.getName();
			ComicSource source = comicSourceService.findByName(sourceName);

			Element rank_list_wrap_DIV = doc.select(".rank-list-wrap").first();
			Elements comicsA = rank_list_wrap_DIV.select(".rank-ul a");
			List<Comic> hotComics = new ArrayList<Comic>(comicsA.size());
			for (Element comicItem : comicsA) {
				String name = comicItem.text();
				String listPageUrl = comicItem.attr("href");

				// 根据 listPageUrl 查询出 Comic对象
				if (StringUtils.isBlank(listPageUrl)) {
					logger.error("{}listPageUrl parsed is empty. please check. name={}", new Object[] {
							COMIC_TENCENT_HOT_COMIC, name });
					continue;
				}

				listPageUrl = AppHelper.addRootUrlIfNeeded(listPageUrl, source.getRootUrl());
				List<Comic> comicList = comicService.findBriefByListPageUrl(source.getId(), listPageUrl);
				if (CollectionUtils.isEmpty(comicList)) {
					// 没有对应的漫画, 则新增.
					Comic comic = new Comic();
					comic.setSourceId(source.getId());
					comic.setName(AppHelper.getAliasNameIfExist(name));
					comic.setListPageUrl(listPageUrl);
					comicService.save(comic);
					hotComics.add(comic);
					logger.info("{}add new hot comic. sourceId = {}, name = {}, listPageUrl = {}", new Object[] {
							COMIC_TENCENT_HOT_COMIC, source.getId(), name, listPageUrl });
					continue;
				}

				hotComics.add(comicList.get(0));
				if (comicList.size() > 1) { // 是否有listPageUrl相同的情况
					logger.error(
							"{}more than one comics have same listPageUrl. sourceId = {}, name={}, listPageUrl = {}",
							new Object[] { COMIC_TENCENT_HOT_COMIC, source.getId(), name, listPageUrl });
				}
			}

			// 如果采集到热门漫画, 则重置排名
			if (CollectionUtils.isNotEmpty(hotComics)) {
				comicService.updateRank(source.getId(), AppConfig.DEFAULT_RANK);
			}

			// 设置排名
			int totalUpdateComics = 0;
			for (int i = 0; i < hotComics.size(); i++) {
				Comic comic = hotComics.get(i);
				comic.setRank(i + 1);
				totalUpdateComics += comicService.update(comic);
			}

			// 更新分集信息
			String prefixLog = COMIC_TENCENT_HOT_COMIC + "collectHotComics";
			String partLog = String.format("sourceId = %s, hotComicListSize = %s", source.getId(), hotComics.size());
			tencentService.collectEpisoeFromListPageWithFuture(source, hotComics, prefixLog, partLog);
			logger.info(
					"{}finish to update hot comics, need to update {}, total update {}, comic names : {}",
					new Object[] { COMIC_TENCENT_HOT_COMIC, hotComics.size(), totalUpdateComics,
							ListUtil.wrapFieldValueList(hotComics, "name") });
		} catch (Exception e) {
			logger.error("{}collectHotComics fail.", e);
		} finally {
			// 删除没有分集的视频记录
			List<Comic> comicList = comicService.listNoEpisodeComics();
			if (CollectionUtils.isEmpty(comicList)) {
				return;
			}
			List<Integer> comicIds = ListUtil.wrapFieldValueList(comicList, "id");
			int rows = comicService.delete(comicIds);

			StringBuilder buf = new StringBuilder(1000);
			for (Comic v : comicList) {
				buf.append(JacksonUtil.encodeQuietly(v)).append("\r\n");
			}
			logger.info("{}delete no episode comics record, need to delete {}, total delete {}, as below:\r\n{}",
					new Object[] { COMIC_TENCENT_HOT_COMIC, comicList.size(), rows, buf.toString() });
		}
	}
}
