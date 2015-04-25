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
import cn.hehe9.common.utils.ListUtil;
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

	/**
	 * 保存首页到缓存
	 *
	 * @param content	首页内容
	 */
	public void saveIndexPageCache(String content) {
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
	 * 创建来源id到缓存
	 *
	 * @param sourceIdList	来源id列表
	 */
	public List<Integer> createSourceIdsCache() {
		try {
			List<VideoSource> sourceList = videoSourceService.list();
			if (CollectionUtils.isEmpty(sourceList)) {
				return null;
			}

			List<Integer> sourceIdList = ListUtil.wrapFieldValueList(sourceList, "id");
			if (CollectionUtils.isEmpty(sourceIdList)) {
				return null;
			}

			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.SOURCE_IDS.getKey());
			entry.setValue(sourceIdList);
			CacheManager.getInstance().save(entry);
			return sourceIdList;
		} catch (Exception e) {
			logger.error("createSourceIdsCache fail.", e);
		}
		return null;
	}

	/**
	 * 从缓存获取来源id列表
	 *
	 * @return	来源id列表
	 */
	public List<Integer> getSourceIdsCache() {
		try {
			CacheEntry entry = CacheEntryFactory.create(CacheKeyEnum.SOURCE_IDS.getKey());
			return CacheManager.getInstance().get(entry);
		} catch (Exception e) {
			logger.error("getSourceIdsCache fail.", e);
		}
		return null;
	}

	/**
	 * 从缓存获取来源id列表, 如果缓存中没有, 则创建并保存到缓存
	 *
	 * @return	来源id列表
	 */
	public List<Integer> getOrCreateSourceIdsCache() {
		try {
			List<Integer> sourceIdList = getSourceIdsCache();
			if (CollectionUtils.isEmpty(sourceIdList)) {
				sourceIdList = createSourceIdsCache();
			}
			return sourceIdList;
		} catch (Exception e) {
			logger.error("getOrCreateSourceIdsCache fail.", e);
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
				if (value == null) {	// 上面已初始化容器, 此处仅判断是否为null
					logger.error("unexpect error, as video not match source. videoId = " + v.getId());
					continue;
				}
				value.add(v);
			}

			// 按来源放入缓存
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
}
