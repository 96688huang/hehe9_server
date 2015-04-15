package cn.hehe9.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hehe9.common.cache.cache.CacheImpl;
import cn.hehe9.common.cache.cache.MemcachedImpl;
import cn.hehe9.common.exceptions.UnexpectedException;
import cn.hehe9.common.utils.CacheUtil;

/**
 * Cache 管理程序，负责对缓存进行存取.
 * <p/>
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 系统：九游游戏中心客户端后台
 * <br> 开发：陈宏洪（chenhh3@ucweb.com）
 * <br> 创建时间：13-11-20 下午2:45
 * <br>==========================
 */
public class CacheManager {

	private static CacheImpl mc;

	private static CacheImpl ec;

	private final Logger logger = LoggerFactory.getLogger(CacheManager.class);

	private static volatile CacheManager instance;

	private static final byte[] syncObj = new byte[0];

	private CacheManager() {
		logger.info("实例化 CacheManager");
		//初始化 ehCache
		ec = CacheUtil.getEhCache();
		//初始化 MemcachedCache
		mc = CacheUtil.getMcCache();
	}

	/**
	 * 取得一个 CacheManager 实例。
	 *
	 * @return CacheManager 实例
	 */
	public static CacheManager getInstance() {
		//简单地通过“双重校验锁”的方式保证单例
		if (instance == null) {
			synchronized (syncObj) {
				if (instance == null) {
					instance = new CacheManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 将缓存项保存到缓存中。
	 * <p>注意：如果值为 null ，也会在中间件中创建缓存项（至少 memcached 会）。</p>
	 *
	 * @param cacheEntry 缓存项
	 * @throws UnexpectedException 
	 * @see MemcachedImpl MemcachedImpl 如果值为 null ，也会创建缓存到中间件
	 */
	public void save(CacheEntry cacheEntry) throws UnexpectedException {
		String cacheKey = cacheEntry.getKeyBuilder().buildKey();
		CacheImpl cache = getCacheImpl(cacheEntry.getNode());
		if (cache == null) {
			logger.warn("Cache node " + cacheEntry.getNode() + "is null, can not save");
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("add cache, KEY: " + cacheKey + ", time out (sec)：" + cacheEntry.getExpiration());
		}
		cache.set(cacheKey, cacheEntry.getValue(), cacheEntry.getExpiration());
	}

	/**
	 * 保存新的值到缓存中。
	 *
	 * @param cacheEntry 缓存项信息
	 * @param newValue 新值
	 * @throws UnexpectedException 
	 */
	public void saveNewValue(CacheEntry cacheEntry, Object newValue) throws UnexpectedException {
		cacheEntry.setValue(newValue);
		save(cacheEntry);
	}

	/**
	 * 根据缓存中间件的名称取得缓存中间件实例。
	 *
	 * @param node 中间件名称，mc 或者 ec，不区分大小写
	 * @return 如果找不到该名称对应的中间件实例，那么返回 MemcachedCache （MC）实例
	 */
	private CacheImpl getCacheImpl(String node) {
		if ("ec".equalsIgnoreCase(node)) {
			return ec;
		}
		return mc;
	}

	/**
	 * 取得缓存对象.
	 *
	 * @param cacheEntry 缓存项信息
	 * @param <T>        缓存对象（数据对象）
	 * @return 如果 cacheEntry 为 null 或者没找到缓存的对象，那么返回 null
	 * @throws UnexpectedException 
	 */
	public <T> T get(CacheEntry cacheEntry) throws UnexpectedException {
		if (cacheEntry == null) {
			return null;
		}
		String cacheKey = cacheEntry.getKeyBuilder().buildKey();
		CacheImpl cache = getCacheImpl(cacheEntry.getNode());
		if (cache == null) {
			logger.warn("Cache node: " + cacheEntry.getNode() + " is null, can not get value");
			return null;
		}
		Object cachedObject = cache.get(cacheKey);
		if (cachedObject == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Cache not found, cache KEY: " + cacheKey);
			}
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Find cache : " + cacheKey + ", data type: " + cachedObject.getClass().getName());
		}

		return (T) cachedObject;
	}

	/**
	 * 删除缓存对象。
	 * @param cacheEntry 缓存项信息
	 * @throws UnexpectedException 
	 */
	public void delete(CacheEntry cacheEntry) throws UnexpectedException {
		if (cacheEntry == null) {
			return;
		}
		String cacheKey = cacheEntry.getKeyBuilder().buildKey();
		CacheImpl cache = getCacheImpl(cacheEntry.getNode());
		cache.delete(cacheKey);
	}
}
