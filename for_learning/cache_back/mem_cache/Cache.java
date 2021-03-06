package cn.hehe9.common.mem_cache;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hehe9.common.bean.AppConfig;
import cn.hehe9.common.cache.CacheException;
import cn.hehe9.common.utils.Time;


/**
 * The Cache. Mainly an interface to memcached or EhCache.
 * <p/>
 * Note: When specifying expiration == "0s" (zero seconds) the actual expiration-time may vary between different cache implementations
 */
public abstract class Cache {

	private static final Logger logger = LoggerFactory.getLogger(Cache.class); 
	
    /**
     * The underlying cache implementation
     */
    public static CacheImpl cacheImpl;

    /**
     * Sometime we REALLY need to change the implementation :)
     */
    public static CacheImpl forcedCacheImpl;

    /**
     * Add an element only if it doesn't exist.
     *
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @throws CacheException 
     */
    public static void add(String key, Object value, String expiration) throws CacheException {
        checkSerializable(value);
        cacheImpl.add(key, value, Time.parseDuration(expiration));
    }

    /**
     * Add an element only if it doesn't exist, and return only when
     * the element is effectively cached.
     *
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     *
     * @return If the element an eventually been cached
     * @throws CacheException 
     */
    public static boolean safeAdd(String key, Object value, String expiration) throws CacheException {
        checkSerializable(value);
        return cacheImpl.safeAdd(key, value, Time.parseDuration(expiration));
    }

    /**
     * Add an element only if it doesn't exist and store it indefinitely.
     *
     * @param key Element key
     * @param value Element value
     * @throws CacheException 
     */
    public static void add(String key, Object value) throws CacheException {
        checkSerializable(value);
        cacheImpl.add(key, value, Time.parseDuration(null));
    }

    /**
     * Set an element.
     *
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @throws CacheException 
     */
    public static void set(String key, Object value, String expiration) throws CacheException {
        checkSerializable(value);
        cacheImpl.set(key, value, Time.parseDuration(expiration));
    }

    /**
     * Set an element and return only when the element is effectively cached.
     *
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     *
     * @return If the element an eventually been cached
     * @throws CacheException 
     */
    public static boolean safeSet(String key, Object value, String expiration) throws CacheException {
        checkSerializable(value);
        return cacheImpl.safeSet(key, value, Time.parseDuration(expiration));
    }

    /**
     * Set an element and store it indefinitely.
     *
     * @param key Element key
     * @param value Element value
     * @throws CacheException 
     */
    public static void set(String key, Object value) throws CacheException {
        checkSerializable(value);
        cacheImpl.set(key, value, Time.parseDuration(null));
    }

    /**
     * Replace an element only if it already exists.
     *
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @throws CacheException 
     */
    public static void replace(String key, Object value, String expiration) throws CacheException {
        checkSerializable(value);
        cacheImpl.replace(key, value, Time.parseDuration(expiration));
    }

    /**
     * Replace an element only if it already exists and return only when the
     * element is effectively cached.
     *
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     *
     * @return If the element an eventually been cached
     * @throws CacheException 
     */
    public static boolean safeReplace(String key, Object value, String expiration) throws CacheException {
        checkSerializable(value);
        return cacheImpl.safeReplace(key, value, Time.parseDuration(expiration));
    }

    /**
     * Replace an element only if it already exists and store it indefinitely.
     *
     * @param key Element key
     * @param value Element value
     * @throws CacheException 
     */
    public static void replace(String key, Object value) throws CacheException {
        checkSerializable(value);
        cacheImpl.replace(key, value, Time.parseDuration(null));
    }

    /**
     * Increment the element value (must be a Number).
     *
     * @param key Element key
     * @param by The incr value
     *
     * @return The new value
     */
    public static long incr(String key, int by) {
        return cacheImpl.incr(key, by);
    }

    /**
     * Increment the element value (must be a Number) by 1.
     *
     * @param key Element key
     *
     * @return The new value
     */
    public static long incr(String key) {
        return cacheImpl.incr(key, 1);
    }

    /**
     * Decrement the element value (must be a Number).
     *
     * @param key Element key
     * @param by The decr value
     *
     * @return The new value
     */
    public static long decr(String key, int by) {
        return cacheImpl.decr(key, by);
    }

    /**
     * Decrement the element value (must be a Number) by 1.
     *
     * @param key Element key
     *
     * @return The new value
     */
    public static long decr(String key) {
        return cacheImpl.decr(key, 1);
    }

    /**
     * Retrieve an object.
     *
     * @param key The element key
     *
     * @return The element value or null
     */
    public static Object get(String key) {
        return cacheImpl.get(key);
    }

    /**
     * Bulk retrieve.
     *
     * @param key List of keys
     *
     * @return Map of keys & values
     */
    public static Map<String, Object> get(String... key) {
        return cacheImpl.get(key);
    }

    /**
     * Delete an element from the cache.
     *
     * @param key The element key
     */
    public static void delete(String key) {
        cacheImpl.delete(key);
    }

    /**
     * Delete an element from the cache and return only when the
     * element is effectively removed.
     *
     * @param key The element key
     *
     * @return If the element an eventually been deleted
     */
    public static boolean safeDelete(String key) {
        return cacheImpl.safeDelete(key);
    }

    /**
     * Clear all data from cache.
     */
    public static void clear() {
        cacheImpl.clear();
    }

    /**
     * Convenient clazz to get a value a class type;
     *
     * @param <T> The needed type
     * @param key The element key
     * @param clazz The type class
     *
     * @return The element value or null
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> clazz) {
        return (T) cacheImpl.get(key);
    }

    /**
     * Initialize the cache system.
     */
    public static void init() {
        if (forcedCacheImpl != null) {
            cacheImpl = forcedCacheImpl;
            return;
        }
        if (AppConfig.MEMCACHED_ENABLE) {
            try {
                cacheImpl = MemcachedImpl.getInstance(true);
                logger.info("Connected to memcached");
            } catch (Exception e) {
            	logger.error("Error while connecting to memcached", e);
                logger.warn("Fallback to local cache");
                cacheImpl = EhCacheImpl.getInstance();
            }
        } else {
            cacheImpl = EhCacheImpl.newInstance();
        }
    }

    /**
     * Stop the cache system.
     */
    public static void stop() {
        cacheImpl.stop();
    }

    /**
     * Utility that check that an object is serializable.
     * @throws CacheException 
     */
    static void checkSerializable(Object value) throws CacheException {
        if (value != null && !(value instanceof Serializable)) {
            throw new CacheException("Cannot cache a non-serializable value of type " + value.getClass().getName(), new NotSerializableException(value.getClass().getName()));
        }
    }

}
