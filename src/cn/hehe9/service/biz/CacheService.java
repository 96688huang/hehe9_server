package cn.hehe9.service.biz;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hehe9.common.app.CacheKeyEnum;
import cn.hehe9.common.cache.CacheEntry;
import cn.hehe9.common.cache.CacheEntryFactory;
import cn.hehe9.common.cache.CacheManager;

/**
 * 缓存业务处理类
 */
@Component
public class CacheService {

	private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

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
			logger.error("save index page cache faile.", e);
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
			logger.error("get index page from cache fail.", e);
		}
		return null;
	}
}
