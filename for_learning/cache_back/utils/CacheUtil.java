package cn.hehe9.common.utils;

import org.apache.log4j.Logger;

import cn.hehe9.common.mem_cache.CacheImpl;
import cn.hehe9.common.mem_cache.EhCacheImpl;
import cn.hehe9.common.mem_cache.MemcachedImpl;

/**
 * 缓存工具类。
 *
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 开发：NieYong <nieyong@ucweb.com>
 * <br> 创建时间：2013-6-19下午2:51:19
 * <br>==========================
 */
public class CacheUtil {

    private final static Logger logger = Logger.getLogger(CacheUtil.class);

    private static Object ecLock = new Object();

    private CacheUtil() {
    }

    /**
     * 获取EhCache客户端实例。
     */
    public static CacheImpl getEhCache() {
        EhCacheImpl ec = EhCacheImpl.getInstance();
        if (null == ec) {
            synchronized (ecLock) {  // 规避JWS的EhCacheImpl.newInstance非线程安全的BUG：cache xxx already exists
                ec = EhCacheImpl.getInstance();
                if (null == ec) {
                    try {
                        ec = EhCacheImpl.newInstance();
                    } catch (Exception e) {
                        logger.error("创建EhCache客户端实例失败", e);
                    } // end of try catch
                }
            } // end of synchronized
        }
        if (null == ec) {
            logger.error("获取EhCache客户端实例失败，返回null");
        }

        return ec;
    }

    /**
     * 获取Memcached客户端实例。如果获取失败，将返回null
     */
    public static CacheImpl getMcCache() {
        MemcachedImpl mc = null;
        try {
            mc = MemcachedImpl.getInstance();
        } catch (Exception e) {
            logger.error("获取Memcached客户端实例失败", e);
        }

        return mc;
    }

}
