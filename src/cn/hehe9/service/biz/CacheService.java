package cn.hehe9.service.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hehe9.action.ActionHelper;
import cn.hehe9.common.app.CacheKeyEnum;
import cn.hehe9.common.cache.CacheEntry;
import cn.hehe9.common.cache.CacheEntryFactory;
import cn.hehe9.common.cache.CacheManager;
import cn.hehe9.common.exceptions.UnexpectedException;
import cn.hehe9.common.utils.ListUtil;
import cn.hehe9.model.SitemapItem;
import cn.hehe9.persistent.entity.Comic;
import cn.hehe9.persistent.entity.ComicSource;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoSource;

/**
 * 缓存业务处理类
 */
@Component
public class CacheService {

	private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

	@Resource
	private VideoSourceService videoSourceService;

	@Resource
	private VideoService videoService;

	@Resource
	private ComicSourceService comicSourceService;

	@Resource
	private ComicService comicService;

	public void clearIndexPageCache() {
		try {
			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.INDEX_PAGE.getKey());
			CacheManager.getInstance().delete(entry);
		} catch (UnexpectedException e) {
			logger.error("clearIndexPageCache fail.", e);
		}
	}

	public void clearSourceComicsCache() {
		try {
			List<Integer> sourceIdList = getOrCreateComicSourceIdsCache();
			for (Integer sourceId : sourceIdList) {
				CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.SOURCE_COMICS.getKey(), sourceId.toString());
				CacheManager.getInstance().delete(entry);
			}
		} catch (Exception e) {
			logger.error("clearSourceComicsCache fail.", e);
		}
	}

	public void clearSourceVideosCache() {
		try {
			List<Integer> sourceIdList = getOrCreateVideoSourceIdsCache();
			for (Integer sourceId : sourceIdList) {
				CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.SOURCE_VIDEOS.getKey(), sourceId.toString());
				CacheManager.getInstance().delete(entry);
			}
		} catch (Exception e) {
			logger.error("clearSourceVideosCache fail.", e);
		}
	}

	/**
	 * 创建漫画来源id到缓存
	 *
	 * @param sourceIdList	漫画来源id列表
	 */
	public List<Integer> createComicSourceIdsCache() {
		try {
			List<ComicSource> sourceList = comicSourceService.list();
			if (CollectionUtils.isEmpty(sourceList)) {
				return null;
			}

			List<Integer> sourceIdList = ListUtil.wrapFieldValueList(sourceList, "id");
			if (CollectionUtils.isEmpty(sourceIdList)) {
				return null;
			}

			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.COMIC_SOURCE_IDS.getKey());
			entry.setValue(sourceIdList);
			CacheManager.getInstance().save(entry);
			return sourceIdList;
		} catch (Exception e) {
			logger.error("createSourceIdsCache fail.", e);
		}
		return null;
	}

	/**
	 * 保存首页到缓存
	 *
	 * @param content	首页内容
	 */
	public void createIndexPageCache(String content) {
		try {
			if (StringUtils.isBlank(content)) {
				return;
			}

			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.INDEX_PAGE.getKey());
			entry.setValue(content);
			CacheManager.getInstance().save(entry);
		} catch (Exception e) {
			logger.error("saveIndexPageCache faile.", e);
		}
	}

	/**
	 * 保存网站地图视频部分内容到缓存
	 *
	 * @param sitemapVideoList	网站地图视频部分内容
	 */
	public void createSitemapComicCache(List<SitemapItem> sitemapComicList) {
		try {
			if (CollectionUtils.isEmpty(sitemapComicList)) {
				return;
			}
			
			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.SITEMAP_COMICS.getKey());
			entry.setValue(sitemapComicList);
			CacheManager.getInstance().save(entry);
		} catch (Exception e) {
			logger.error("createSitemapComicCache faile.", e);
		}
	}

	/**
	 * 保存网站地图视频部分内容到缓存
	 *
	 * @param sitemapVideoList	网站地图视频部分内容
	 */
	public void createSitemapVideoCache(List<SitemapItem> sitemapVideoList) {
		try {
			if (CollectionUtils.isEmpty(sitemapVideoList)) {
				return;
			}

			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.SITEMAP_VIDEOS.getKey());
			entry.setValue(sitemapVideoList);
			CacheManager.getInstance().save(entry);
		} catch (Exception e) {
			logger.error("createSitemapVideoCache faile.", e);
		}
	}

	/**
	 * 创建来源漫画列表到缓存
	 *
	 * @param sourceIdList	来源漫画列表
	 */
	public List<Comic> createSourceComicsCache(List<Integer> sourceIdList) {
		try {
			List<Comic> allComics = comicService.listBrief(1, Short.MAX_VALUE);
			if (CollectionUtils.isEmpty(allComics)) {
				return null;
			}

			// 赋值漫画来源名称
			ActionHelper.setComicSourceName(allComics);

			// {key: sourceId, value: Comic list}
			Map<Integer, List<Comic>> sourceComicMap = new HashMap<Integer, List<Comic>>(sourceIdList.size());
			for (Integer sourceId : sourceIdList) {
				sourceComicMap.put(sourceId, new ArrayList<Comic>(2000));
			}

			// 按来源分类
			for (Comic v : allComics) {
				List<Comic> value = sourceComicMap.get(v.getSourceId());
				if (value == null) { // 上面已初始化容器, 此处仅判断是否为null
					logger.error("unexpect error, as comic not match source. comicId = " + v.getId());
					continue;
				}
				value.add(v);
			}

			// 按来源放入缓存
			clearSourceComicsCache();	// 先清除原来的缓存
			for (Integer sourceId : sourceComicMap.keySet()) {
				List<Comic> value = sourceComicMap.get(sourceId);
				CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.SOURCE_COMICS.getKey(), sourceId.toString());
				entry.setValue(value);
				CacheManager.getInstance().save(entry);
			}
			return allComics;
		} catch (Exception e) {
			logger.error("createSourceIdsCache fail.", e);
		}
		return null;
	}

	/**
	 * 创建来源视频列表到缓存
	 *
	 * @param sourceIdList	来源视频列表
	 */
	public List<Video> createSourceVideosCache(List<Integer> sourceIdList) {
		try {
			List<Video> allVideos = videoService.listBrief(1, Short.MAX_VALUE);
			if (CollectionUtils.isEmpty(allVideos)) {
				return null;
			}

			// 赋值视频来源名称
			ActionHelper.setSourceName(allVideos);

			// {key: sourceId, value: Video list}
			Map<Integer, List<Video>> sourceVideoMap = new HashMap<Integer, List<Video>>(sourceIdList.size());
			for (Integer sourceId : sourceIdList) {
				sourceVideoMap.put(sourceId, new ArrayList<Video>(2000));
			}

			// 按来源分类
			for (Video v : allVideos) {
				List<Video> value = sourceVideoMap.get(v.getSourceId());
				if (value == null) { // 上面已初始化容器, 此处仅判断是否为null
					logger.error("unexpect error, as video not match source. videoId = " + v.getId());
					continue;
				}
				value.add(v);
			}

			// 按来源放入缓存
			clearSourceVideosCache();	// 先清除原来的缓存
			for (Integer sourceId : sourceVideoMap.keySet()) {
				List<Video> value = sourceVideoMap.get(sourceId);
				CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.SOURCE_VIDEOS.getKey(), sourceId.toString());
				entry.setValue(value);
				CacheManager.getInstance().save(entry);
			}
			return allVideos;
		} catch (Exception e) {
			logger.error("createSourceIdsCache fail.", e);
		}
		return null;
	}

	/**
	 * 创建来源id到缓存
	 *
	 * @param sourceIdList	来源id列表
	 */
	public List<Integer> createVideoSourceIdsCache() {
		try {
			List<VideoSource> sourceList = videoSourceService.list();
			if (CollectionUtils.isEmpty(sourceList)) {
				return null;
			}

			List<Integer> sourceIdList = ListUtil.wrapFieldValueList(sourceList, "id");
			if (CollectionUtils.isEmpty(sourceIdList)) {
				return null;
			}

			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.VIDEO_SOURCE_IDS.getKey());
			entry.setValue(sourceIdList);
			CacheManager.getInstance().save(entry);
			return sourceIdList;
		} catch (Exception e) {
			logger.error("createSourceIdsCache fail.", e);
		}
		return null;
	}

	/**
	 * 从缓存获取漫画来源id列表
	 *
	 * @return	来源id列表
	 */
	public List<Integer> getComicSourceIdsCache() {
		try {
			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.COMIC_SOURCE_IDS.getKey());
			return CacheManager.getInstance().get(entry);
		} catch (Exception e) {
			logger.error("getSourceIdsCache fail.", e);
		}
		return null;
	}

	/**
	 * 从缓存获取首页内容
	 *
	 * @return	首页内容
	 */
	public String getIndexPageCache() {
		try {
			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.INDEX_PAGE.getKey());
			return CacheManager.getInstance().get(entry);
		} catch (Exception e) {
			logger.error("getIndexPageCache fail.", e);
		}
		return null;
	}

	/**
	 * 从缓存获取漫画来源id列表, 如果缓存中没有, 则创建并保存到缓存
	 *
	 * @return	来源id列表
	 */
	public List<Integer> getOrCreateComicSourceIdsCache() {
		try {
			List<Integer> sourceIdList = getComicSourceIdsCache();
			if (CollectionUtils.isEmpty(sourceIdList)) {
				sourceIdList = createComicSourceIdsCache();
			}
			return sourceIdList;
		} catch (Exception e) {
			logger.error("getOrCreateSourceIdsCache fail.", e);
		}
		return null;
	}

	/**
	 * 从缓存获取来源漫画列表, 如果缓存中没有, 则创建并保存到缓存
	 *
	 * @return	来源漫画列表
	 */
	public List<Comic> getOrCreateSourceComicsCache(List<Integer> sourceIdList) {
		try {
			List<Comic> allComics = new ArrayList<Comic>();
			for (Integer sourceId : sourceIdList) {
				List<Comic> sourceComicList = getSourceComicsCache(sourceId);
				if (CollectionUtils.isNotEmpty(sourceComicList)) {
					allComics.addAll(sourceComicList);
				}
			}

			if (CollectionUtils.isEmpty(allComics)) {
				allComics = createSourceComicsCache(sourceIdList);
			}
			return allComics;
		} catch (Exception e) {
			logger.error("getOrCreateSourceIdsCache fail.", e);
		}
		return null;
	}

	/**
	 * 从缓存获取来源视频列表, 如果缓存中没有, 则创建并保存到缓存
	 *
	 * @return	来源视频列表
	 */
	public List<Video> getOrCreateSourceVideosCache(List<Integer> sourceIdList) {
		try {
			List<Video> allVideos = new ArrayList<Video>();
			for (Integer sourceId : sourceIdList) {
				List<Video> sourceVideoList = getSourceVideosCache(sourceId);
				if (CollectionUtils.isNotEmpty(sourceVideoList)) {
					allVideos.addAll(sourceVideoList);
				}
			}

			if (CollectionUtils.isEmpty(allVideos)) {
				allVideos = createSourceVideosCache(sourceIdList);
			}
			return allVideos;
		} catch (Exception e) {
			logger.error("getOrCreateSourceIdsCache fail.", e);
		}
		return null;
	}

	/**
	 * 从缓存获取来源id列表, 如果缓存中没有, 则创建并保存到缓存
	 *
	 * @return	来源id列表
	 */
	public List<Integer> getOrCreateVideoSourceIdsCache() {
		try {
			List<Integer> sourceIdList = getVideoSourceIdsCache();
			if (CollectionUtils.isEmpty(sourceIdList)) {
				sourceIdList = createVideoSourceIdsCache();
			}
			return sourceIdList;
		} catch (Exception e) {
			logger.error("getOrCreateSourceIdsCache fail.", e);
		}
		return null;
	}

	/**
	 * 从缓存获取网站地图漫画部分内容
	 */
	public List<SitemapItem> getSitemapComicCache() {
		try {
			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.SITEMAP_COMICS.getKey());
			return CacheManager.getInstance().get(entry);
		} catch (Exception e) {
			logger.error("getSitemapComicCache fail.", e);
		}
		return null;
	}

	/**
	 * 从缓存获取网站地图视频部分内容
	 */
	public List<SitemapItem> getSitemapVideoCache() {
		try {
			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.SITEMAP_VIDEOS.getKey());
			return CacheManager.getInstance().get(entry);
		} catch (Exception e) {
			logger.error("getSitemapVideoCache fail.", e);
		}
		return null;
	}

	/**
	 * 从缓存获取来源漫画列表
	 *
	 * @return	来源漫画列表
	 */
	public List<Comic> getSourceComicsCache(Integer sourceId) {
		try {
			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.SOURCE_COMICS.getKey(), sourceId.toString());
			return CacheManager.getInstance().get(entry);
		} catch (Exception e) {
			logger.error("getSourceComicsCache fail.", e);
		}
		return null;
	}

	/**
	 * 从缓存获取来源视频列表
	 *
	 * @return	来源视频列表
	 */
	public List<Video> getSourceVideosCache(Integer sourceId) {
		try {
			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.SOURCE_VIDEOS.getKey(), sourceId.toString());
			return CacheManager.getInstance().get(entry);
		} catch (Exception e) {
			logger.error("getSourceVideosCache fail.", e);
		}
		return null;
	}
	
	/**
	 * 从缓存获取来源id列表
	 *
	 * @return	来源id列表
	 */
	public List<Integer> getVideoSourceIdsCache() {
		try {
			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.VIDEO_SOURCE_IDS.getKey());
			return CacheManager.getInstance().get(entry);
		} catch (Exception e) {
			logger.error("getSourceIdsCache fail.", e);
		}
		return null;
	}
	
	
	public List<String> getTest(){
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		return list;
	}
}
