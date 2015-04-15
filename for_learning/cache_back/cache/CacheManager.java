package cn.hehe9.common.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import cn.hehe9.common.mem_cache.CacheImpl;
import cn.hehe9.common.mem_cache.MemcachedImpl;
import cn.hehe9.common.utils.CacheUtil;
import cn.hehe9.common.utils.DateUtil;

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

    private final Logger logger = Logger.getLogger(CacheManager.class);

    private static volatile CacheManager instance;

    private Map<String, Long> reloading = new ConcurrentHashMap<String, Long>(100);

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
            synchronized (CacheManager.class) {
                if (instance == null) {
                    instance = new CacheManager();
                }
            }
        }
        return instance;
    }

    /**
     * 将缓存项保存到缓存中。
     * <p>注意 null 的处理情况：
     * <ul>
     *     <li>如果值为 null，但是 none-supported 为 false ，那么不会保存到缓存中</li>
     *     <li>如果值为 null，但是配置了 none-supported 为 true ，那么会使用一个 NoneValue 实例存到缓存中</li>
     * </ul>
     * </p>
     *
     * @param cacheEntry 缓存项
     * @see MemcachedImpl MemcachedImpl 如果值为 null ，也会创建缓存到中间件
     */
    public void save(CacheEntry cacheEntry) {
        String cacheKey = cacheEntry.getKeyBuilder().buildCacheKey();
        final Object value = cacheEntry.getValue();

        if(!cacheEntry.isNoneSupported() && value == null){
            if(logger.isDebugEnabled()){
                logger.debug(String.format("CacheEntry 的值为 null，并且配置了 none-supported 为 false ，不保存到缓存中。KEY：%s，Sampler：%s", cacheEntry.getKey(),cacheEntry.getSampler()));
            }
            return;
        }

        CacheImpl cache = getCacheImpl(cacheEntry.getNode());
        if(cache == null){
            logger.warn("缓存中间件：" + cacheEntry.getNode() + " 为 null，无法保存");
            return;
        }

        final int expiration = cacheEntry.getExpiration();
        if(logger.isDebugEnabled()){
            logger.debug("添加缓存，KEY：" + cacheKey + "，超时时间（秒）：" + expiration);
        }
        CacheValue cacheValue = new CacheValue(value);
        if(value == null){
            cacheValue.setValue(new NoneValue());
        }
        cacheValue.setExpireTime(cacheValue.getCreateTime() + expiration * 1000);
        cache.set(cacheKey, cacheValue, expiration);
        reloading.remove(cacheKey);

        CacheStatLog.getInstance().log(cacheEntry.getKeyBuilder().buildCachePrefix(), CacheStatOpt.SET,1);
    }

    /**
     * 保存新的值到缓存中。
     *
     * @param cacheEntry 缓存项信息
     * @param newValue 新值
     */
    public void saveNewValue(CacheEntry cacheEntry, Object newValue){
        if(cacheEntry == null){
            return;
        }
        cacheEntry.setValue(newValue);
        save(cacheEntry);
    }

    /**
     * 保存值到缓存中.
     * @param configKey 配置 KEY，请参考 cache-config.xml
     * @param sampler 样本
     * @param newValue 新值
     */
    public void save(String configKey, String sampler, Object newValue){
        CacheEntry cacheEntry = CacheEntryFactory.create(configKey,sampler);
        if(cacheEntry == null){
            return;
        }
        saveNewValue(cacheEntry,newValue);
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
     * 从缓存中取得对象。
     * @param configKey 缓存KEY，请参考 cache-key.xml
     * @param sampler 样本
     * @param <T> 缓存对象
     * @return 如果找不到的话，那么返回 null
     */
    public <T> T get(String configKey, String sampler){
        CacheEntry cacheEntry = CacheEntryFactory.create(configKey,sampler);
        return (T) get(cacheEntry);
    }

    /**
     * 取得缓存对象.
     *
     * @param cacheEntry 缓存项信息
     * @param <T>        缓存对象（数据对象）
     * @return
     *     <ul>
     *         <li>正常情况，返回具体的对象</li>
     *         <li>当缓存项存在，但是没值，返回 NoneValue</li>
     *         <li>当缓存项不存在，返回 null</li>
     *     </ul>
     */
    public <T> T get(CacheEntry cacheEntry) {
        if (cacheEntry == null) {
            return null;
        }

        //读取缓存
        String cacheKey = cacheEntry.getKeyBuilder().buildCacheKey();
        final String prefix = cacheEntry.getKeyBuilder().buildCachePrefix();
        CacheImpl cache = getCacheImpl(cacheEntry.getNode());
        if(cache == null){
            logger.warn("缓存中间件：" + cacheEntry.getNode() + " 为 null，无法取值");
            return null;
        }
        Object cachedObject = cache.get(cacheKey);

        //缓存不存在
        if (cachedObject == null) {
            if(logger.isDebugEnabled()){
                logger.debug("无法取得缓存：" + cacheKey);
            }
//            CacheStatLog.getInstance().log(prefix, CacheStatOpt.GETMISS,1);
            return null;
        }

        CacheValue cacheValue = (CacheValue) cachedObject;

        //如果到达缓存失效的阀值，那么本次请求返回 null
        long now = System.currentTimeMillis();
        if (cacheEntry.getReloadAt() != null && cacheEntry.getReloadAt() > 0) {
            long boundary = cacheValue.createTime + (cacheEntry.getReloadAt() * 1000);
            if (now > boundary && !reloading.containsKey(cacheKey)) {
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("缓存 %s 快过期了，本次请求返回 null ，让调用方提前执行一次业务操作，过期时间：%s", cacheKey, DateUtil.formatDateNormal(cacheValue.getExpireTime())));
                }
                reloading.put(cacheKey, System.currentTimeMillis());
                return null;
            }
        }

//        CacheStatLog.getInstance().log(prefix, CacheStatOpt.GETHIT,1);

        return (T) cacheValue.getValue();
    }

    /**
     * 删除缓存对象。
     * @param cacheEntry 缓存项信息
     */
    public void delete(CacheEntry cacheEntry) {
        if(cacheEntry == null){
            return;
        }
        String cacheKey = cacheEntry.getKeyBuilder().buildCacheKey();
        CacheImpl cache = getCacheImpl(cacheEntry.getNode());
        cache.delete(cacheKey);
//        CacheStatLog.getInstance().log(cacheEntry.getKeyBuilder().buildCachePrefix(), CacheStatOpt.DELETE,1);
    }

}
