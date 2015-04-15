package cn.hehe9.common.utils;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jws.Logger;
import jws.dal.Cache;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;

/**
 * 缓存读写客户端
 * @copyright 优视科技 2013 版权所有
 * @author  <a href="mailto:zhongxn@ucweb.com">Nic Zhong</a>
 * @version v1.0.0
 */
public class CacheClient {

    private static Gson gson = new Gson();

    /**
     * 读取缓存
     * 
     * @param <T>
     * @param key
     * @param prefix
     * @param clas
     * @return
     */
    public static <T> T get(String key, String prefix, Class<T> clas) {
        if (key == null || prefix == null || clas == null)
            return null;
        try {
            Object rawObject = Cache.get(key, prefix);
            if (rawObject == null)
                return null;
            String rawString = rawObject.toString();
            if (Utility.isNullOrEmpty(rawString)) {
                return null;
            }
            return gson.fromJson(rawString, clas);
        } catch (RuntimeException e) {
            Logger.error(e, "get cache data error -- prefix: %s, key: %s, class: %s", prefix, key,
                    clas.getName());
            return null;
        }
    }
    
    /**
     * 读取缓存
     * 
     * @param <T>
     * @param key
     * @param prefix
     * @param classType
     * @return
     */
    public static <T> T get(String key, String prefix, Type classType) {
        if (key == null || prefix == null || classType == null)
            return null;
        try {
            Object rawObject = Cache.get(key, prefix);
            if (rawObject == null)
                return null;
            String rawString = rawObject.toString();
            if (Utility.isNullOrEmpty(rawString)) {
                return null;
            }
            return (T)gson.fromJson(rawString, classType);
        } catch (RuntimeException e) {
            Logger.error(e, "get cache data error -- prefix: %s, key: %s, type: %s", prefix, key,classType);
            return null;
        }
    }
    
    /**
     * 读取缓存
     * 
     * @param key
     * @param prefix
     * @return
     */
    public static String getCacheRaw(String key, String prefix) {
        if (key == null || prefix == null)
            return null;
        try {
            Object rawObject = Cache.get(key, prefix);
            if (rawObject == null)
                return null;
            String rawString = rawObject.toString();
            return rawString;
        } catch (RuntimeException e) {
            Logger.error(e, "get cache data error -- prefix: %s, key: %s", prefix, key);
            return null;
        }
    }
    

    /**
     * 获取多个缓存
     * 
     * @param <T>
     * @param keys
     * @param prefix
     * @param clas
     * @return
     */
    public static <T> Map<String, T> getMulti(List<String> keys, String prefix, Class<T> clas) {
        if (CollectionUtils.isEmpty(keys) || prefix == null || clas == null) {
            return Collections.EMPTY_MAP;
        }
        try {
            Map<String, Object> rawObjectMap = Cache.getMulti(keys, prefix);
            // 当jws后续版本修复“多余日志”问题[Bug:0181334]时可恢复为以下调用方式
            // Map<String, Object> rawObjectMap = Cache.getMulti(keys, prefix);
            
            if (rawObjectMap == null) {
                return Collections.EMPTY_MAP;
            }
            Map<String, T> resultMap = new HashMap<String, T>();
            for (Map.Entry<String, Object> entry : rawObjectMap.entrySet()) {
                Object rawValue = entry.getValue();
                if (rawValue == null) {
                    continue;
                }
                String rawString = rawValue.toString();
                if (Utility.isNullOrEmpty(rawString)) {
                    continue;
                }
                T value = gson.fromJson(rawString, clas);
                resultMap.put(entry.getKey(), value);
            }
            return resultMap;
        } catch (RuntimeException e) {
            Logger.error(e, "get multi cache data error -- prefix: %s, keys: %s, class: %s",
                    prefix, StringUtils.join(keys, ','), clas.getName());
            return Collections.EMPTY_MAP;
        }
    }

    /**
     * 写入缓存
     * 
     * @param key
     * @param prefix
     * @param value
     * @return
     */
    public static boolean set(String key, String prefix, Object value) {
        if (key == null || prefix == null || value == null)
            return false;
        try {
            String rawString = gson.toJson(value);
            return Cache.set(key, rawString, prefix);
        } catch (RuntimeException e) {
            Logger.error(e, "set cache data error -- prefix: %s, key: %s, objectClass: %s", prefix,
                    key, value.getClass().getName());
            return false;
        }
    }
    
    
    /**
     * 缓存set，可设置缓存时间
     * @param key
     * @param value
     * @param prefix
     * @param cachetime 
     * @throws Exception
     */
    public static boolean set(String key, String prefix, Object value, Integer cachetime) {
        if (key == null || prefix == null || value == null || cachetime == null)
            return false;
        try {
            String rawString = gson.toJson(value);
            return Cache.set(key, rawString, cachetime, prefix);
        } catch (RuntimeException e) {
            Logger.error(e, "set cache data error -- prefix: %s, key: %s, objectClass: %s", prefix,
                    key, value.getClass().getName());
            return false;
        }
    }
    
    /**
     * 替换写入缓存
     * 
     * @param key
     * @param prefix
     * @param value
     * @return
     */
    public static boolean replace(String key, String prefix, Object value) {
        if (key == null || prefix == null || value == null)
            return false;
        try {
            String rawString = gson.toJson(value);
            return Cache.replace(key, rawString, prefix);
        } catch (RuntimeException e) {
            Logger.error(e, "set cache data error -- prefix: %s, key: %s, objectClass: %s", prefix,
                    key, value.getClass().getName());
            return false;
        }
    }
    
    /**
     * 替换写入缓存
     * 
     * @param key
     * @param prefix
     * @param value
     * @param cachetime 
     * @return
     */
    public static boolean replace(String key, String prefix, Object value, Integer cachetime) {
        if (key == null || prefix == null || value == null)
            return false;
        try {
            String rawString = gson.toJson(value);
            return Cache.replace(key, rawString, cachetime, prefix);
        } catch (RuntimeException e) {
            Logger.error(e, "set cache data error -- prefix: %s, key: %s, objectClass: %s", prefix,
                    key, value.getClass().getName());
            return false;
        }
    }

    public static boolean incr(String key, String prefix, int by, Long defaultValue) {
        try {
            Cache.incr(key, by, defaultValue, prefix);
        } catch (Exception e) {
            Logger.error(e, "incr cache data error -- prefix: %s, key: %s, value: %d", prefix, key,
                    by);
            // 发生异常重新设置该值
            Cache.set(key, defaultValue, prefix);
        }

        return true;
    }

    /**
     * 删除缓存
     * 
     * @param key
     * @param prefix
     * @return
     */
    public static boolean delete(String key, String prefix) {
        if (key == null || prefix == null)
            return false;
        try {
            Cache.delete(key, prefix);
            return true;
        } catch (RuntimeException e) {
            Logger.error(e, "delete cache data error -- prefix: %s, key: %s", prefix, key);
            return false;
        }
    }
}
