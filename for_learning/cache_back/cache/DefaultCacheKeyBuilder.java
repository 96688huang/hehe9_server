package cn.hehe9.common.cache;

import jws.libs.Codec;
import org.apache.log4j.Logger;

/**
 * 缓存 KEY 的默认生成器.
 * <p>生成规则： CacheEntry.key + CacheEntry.sampler + CacheEntry.version ，如果加起来的长度大于 32 个字符，那么进行 md5 。</p>
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 系统：九游游戏中心客户端后台
 * <br> 开发：陈宏洪（chenhh3@ucweb.com）
 * <br> 创建时间：13-11-20 下午3:01
 * <br>==========================
 */
public class DefaultCacheKeyBuilder implements CacheKeyBuilder{

	private final Logger logger = Logger.getLogger(DefaultCacheKeyBuilder.class);

    private CacheEntry cacheEntry;

    public DefaultCacheKeyBuilder(CacheEntry cacheEntry) {
        this.cacheEntry = cacheEntry;
    }

    @Override
    public String buildCacheKey() {
        String key = cacheEntry.getKey() + '_' + cacheEntry.getSampler() + '_' + cacheEntry.getVersion();
		if (logger.isDebugEnabled()) {
			logger.debug("生成 的缓存 MD5 前的 KEY：" + key);
		}
        if(key.length() > 32){
            key = Codec.hexMD5(key);
        }
        return key;
    }

    @Override
    public String buildCachePrefix() {
        return cacheEntry.getKey();
    }
}
