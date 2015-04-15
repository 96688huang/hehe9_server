package cn.hehe9.common.cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.hehe9.common.utils.DateUtil;

/**
 * .
 * <p/>
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 系统：九游游戏中心客户端后台
 * <br> 开发：陈宏洪（chenhh3@ucweb.com）
 * <br> 创建时间：2014/8/25 13:52
 * <br>==========================
 */
public class CachableMethodExecutor {

    private static Logger logger = Logger.getLogger(CachableMethodExecutor.class);

    public Object execute(CacheConfig cacheConfig, Cachable cachable, final MethodInvocation mi) throws Throwable {

        final Method method = mi.getMethod();
        String methodId = getMethodId(mi);

        //生成 key
        String key = cachable.key();
        if(StringUtils.isBlank(key)){
            key = cacheConfig.key();
        }

        final CacheEntry cacheEntry = CacheEntryFactory.create(key);
        if (cacheEntry == null) {
            return new CacheException("无法从配置文件（cache-config.xml）中找到配置：" + key);
        }

        //生成 sampler
        final Annotation[][] paramAnnotations = method.getParameterAnnotations();
        Object[] args = mi.getArguments();
        String sampler = methodId;
        for (int i = 0; i < paramAnnotations.length; i++) {
            for (Annotation a : paramAnnotations[i]) {
                if (a instanceof CacheSample) {
                    sampler += args[i];
                }
            }
        }
        cacheEntry.setSampler(sampler);

        //sampler 不允许为空
        if (StringUtils.isBlank(cacheEntry.getSampler())) {
            throw new CacheException(String.format("Cache 的 sampler 值不允许为空，请检查配置，Cache Key：%s，方法：%s", key, methodId));
        }

        //
        CacheValue cachedValue = CacheManager.getInstance().get(cacheEntry);
        Object proceedResult = null;
        if (cachedValue == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("没有缓存，执行目标方法：" + methodId);
            }
            proceedResult = mi.proceed();
            cachedValue = new CacheValue(proceedResult);
            CacheManager.getInstance().saveNewValue(cacheEntry, cachedValue);
            return proceedResult;
        }

        final String createTime = DateUtil.formatDateNormal(cachedValue.createTime);
        final String expireTime = DateUtil.formatDateNormal(cachedValue.createTime + (cacheEntry.getExpiration() * 1000));
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("缓存中有数据，创建时间：%s，过期时间：%s", createTime, expireTime));
        }
        proceedResult = cachedValue.getValue();

        return proceedResult;
    }

    public String getMethodId(MethodInvocation mi) {
        final Method method = mi.getMethod();
        Class clazz = method.getDeclaringClass();
        String methodName = method.getName();
        String className = clazz.getName();
        String methodFullName = className + "#" + methodName;
        return methodFullName;
    }
}
