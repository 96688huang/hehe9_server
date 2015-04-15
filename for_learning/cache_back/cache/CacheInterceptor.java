package cn.hehe9.common.cache;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

/**
 * Cachable 方法拦截程序，如果检测到方法有 Cachable 注解的话，那么调用 CachableMethodExecutor 作进一步的处理.
 * <pre>
 * 配置：
 *   &lt;bean id=&quot;cacheInterceptor&quot; class=&quot;common.cache.CacheInterceptor&quot;&gt;
 *     &lt;property name=&quot;cachableMethodExecutor&quot; ref=&quot;cachableMethodExecutor&quot;/&gt;
 *   &lt;/bean&gt;
 *   &lt;bean name=&quot;cachableMethodExecutor&quot; class=&quot;common.cache.CachableMethodExecutor&quot;/&gt;
 *   &lt;aop:config proxy-target-class=&quot;true&quot;&gt;
 *     &lt;aop:pointcut id=&quot;cachePointcut&quot; expression=&quot;@annotation(common.cache.Cachable)&quot;/&gt;
 *     &lt;aop:advisor advice-ref=&quot;cacheInterceptor&quot; pointcut-ref=&quot;cachePointcut&quot;/&gt;
 *   &lt;/aop:config&gt;
 * </pre>
 * <p>具体使用：
 * 在类添加 @CacheConfig，在需要缓存的方法添加 @Cachable，最后在方法参数添加 @CacheSample
 * </p>
 * <p/>
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 系统：九游游戏中心客户端后台
 * <br> 开发：陈宏洪（chenhh3@ucweb.com）
 * <br> 创建时间：2014/8/20 11:21
 * <br>==========================
 */
public class CacheInterceptor implements MethodInterceptor {

    private static Logger logger = Logger.getLogger(CacheInterceptor.class);

    private CachableMethodExecutor cachableMethodExecutor;


    @Override
    public Object invoke(final MethodInvocation mi) throws Throwable {

        final Method method = mi.getMethod();
        String methodId = getMethodId(mi);
        boolean cacheSupported = false;

        CacheConfig cacheConfig = method.getDeclaringClass().getAnnotation(CacheConfig.class);
        Cachable cachable = method.getAnnotation(Cachable.class);
        if (cacheConfig == null || cachable != null) {
            cacheSupported = true;
        }

        logger.info(String.format("方法（%s）是否支持缓存：%s", methodId, cacheSupported));

        if (cachable == null) {
            return mi.proceed();
        }

        Object proceedResult = null;
        try {
            proceedResult = cachableMethodExecutor.execute(cacheConfig, cachable, mi);
        } catch (CacheException cacheException) {
            logger.warn("缓存使用出现异常:" + cacheException.getMessage() + "，现直接执行业务方法");
            if(logger.isDebugEnabled()){
                logger.error(cacheException);
            }
            proceedResult = mi.proceed();
        }

        return proceedResult;

    }

    public String getMethodId(MethodInvocation mi) {
        final Method method = mi.getMethod();
        Class clazz = method.getDeclaringClass();
        String methodName = method.getName();
        String className = clazz.getName();
        return className + "#" + methodName;
    }

    public void setCachableMethodExecutor(CachableMethodExecutor cachableMethodExecutor) {
        this.cachableMethodExecutor = cachableMethodExecutor;
    }
}
