package cn.hehe9.common.cache.cache;

import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EhCache implementation.
 *
 * <p>Ehcache is an open source, standards-based cache used to boost performance,
 * offload the database and simplify scalability. Ehcache is robust, proven and
 * full-featured and this has made it the most widely-used Java-based cache.</p>
 *
 * @see http://ehcache.org/
 *
 * expiration is specified in seconds
 */
public class EhCacheImpl implements CacheImpl {

	private static EhCacheImpl uniqueInstance;

	CacheManager cacheManager;

	net.sf.ehcache.Cache cache;

	private static final String cacheName = "cache";

	private static final Logger logger = LoggerFactory.getLogger(EhCacheImpl.class);

	/**
	 * 单例
	 * @author eason
	 *
	 */
	static class SingletonHolder {
		private static EhCacheImpl instance = new EhCacheImpl();
	}

	private EhCacheImpl() {
		this.cacheManager = CacheManager.create();
		this.cacheManager.addCache(cacheName);
		this.cache = cacheManager.getCache(cacheName);
	}

	public static EhCacheImpl getInstance() {
		return uniqueInstance;
	}

	public static EhCacheImpl newInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = SingletonHolder.instance;
		}
		return uniqueInstance;
	}

	public void add(String key, Object value, int expiration) {
		if (cache.get(key) != null) {
			return;
		}
		Element element = new Element(key, value);
		element.setTimeToLive(expiration);
		cache.put(element);
	}

	public void clear() {
		cache.removeAll();
	}

	public synchronized long decr(String key, int by) {
		Element e = cache.get(key);
		if (e == null) {
			return -1;
		}
		long newValue = ((Number) e.getValue()).longValue() - by;
		Element newE = new Element(key, newValue);
		newE.setTimeToLive(e.getTimeToLive());
		cache.put(newE);
		return newValue;
	}

	public void delete(String key) {
		cache.remove(key);
	}

	public Object get(String key) {
		Element e = cache.get(key);
		return (e == null) ? null : e.getValue();
	}

	public Map<String, Object> get(String[] keys) {
		Map<String, Object> result = new HashMap<String, Object>(keys.length);
		for (String key : keys) {
			result.put(key, get(key));
		}
		return result;
	}

	public synchronized long incr(String key, int by) {
		Element e = cache.get(key);
		if (e == null) {
			return -1;
		}
		long newValue = ((Number) e.getValue()).longValue() + by;
		Element newE = new Element(key, newValue);
		newE.setTimeToLive(e.getTimeToLive());
		cache.put(newE);
		return newValue;

	}

	public long incr(String key, int by, long value) {
		throw new RuntimeException();
	}

	public long decr(String key, int by, long value) {
		throw new RuntimeException();
	}

	public void replace(String key, Object value, int expiration) {
		if (cache.get(key) == null) {
			return;
		}
		Element element = new Element(key, value);
		element.setTimeToLive(expiration);
		cache.put(element);
	}

	public boolean safeAdd(String key, Object value, int expiration) {
		try {
			add(key, value, expiration);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean safeDelete(String key) {
		try {
			delete(key);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	public boolean safeReplace(String key, Object value, int expiration) {
		try {
			replace(key, value, expiration);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	public boolean safeSet(String key, Object value, int expiration) {
		try {
			set(key, value, expiration);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	public void set(String key, Object value, int expiration) {
		Element element = new Element(key, value);
		element.setTimeToLive(expiration);
		cache.put(element);
	}

	public void stop() {
		cacheManager.shutdown();
	}

}
