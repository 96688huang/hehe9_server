package cn.hehe9.common.cache;

/**
 * 缓存KEY生成接口.
 * <p/>
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 系统：九游游戏中心客户端后台
 * <br> 开发：陈宏洪（chenhh3@ucweb.com）
 * <br> 创建时间：13-11-20 下午2:45
 * <br>==========================
 */
public interface CacheKeyBuilder {

    /**
     * 生成缓存KEY，用于跟缓存中间件进行存取，有可能是一个 MD5 后的字符串.
     * @return 缓存KEY，系统内部使用，可能是 MD5 串，不要用于业务识别
     */
    String buildCacheKey();

    /**
     * 生成缓存业务前缀，用于统计，比如：game-info 。
     * @return 字符串，有意义的，可识别的
     */
    String buildCachePrefix();
}
