package cn.hehe9.common.cache;

import cn.hehe9.common.exceptions.UnexpectedException;

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
     * 生成缓存KEY.
     * @return cache key
     * @throws UnexpectedException 
     */
    String buildKey() throws UnexpectedException;

}
