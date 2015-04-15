package cn.hehe9.common.cache.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cn.hehe9.common.utils.spring.SpringContextHolder;

/**
 * <b><code>MyXMemcachedClient</code></b>
 * <p>
 * 对XMemcached Client的二次封装
 * </p>
 * <b>Creation Time:</b> Jul 29, 2013 11:34:26 AM
 * @author Kenny Qi
 * @since lottery 1.0
 */
@Service("myXMemcachedClient")
@Lazy
public class MyXMemcachedClient implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(MyXMemcachedClient.class);
	@Resource(name="memcachedClient")
	private MemcachedClient memcachedClient;

	/**
	 * Set方法.
	 */
	public boolean set(String key, int expiredTime, Object value) {
		if (value == null)
			return false;
		try {
			return memcachedClient.set(key, expiredTime, value);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Set方法.
	 */
	public boolean set(String key, int expiredTime, Object value, boolean reloadable, int reloadTime, Object method,
			Object arg) {
		if (reloadable) {

		}

		if (value == null)
			return false;
		try {
			return memcachedClient.set(key, expiredTime, value);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return false;
		}
	}
	
	public boolean add(String key, int expiredTime, Object value) {
		if(value == null)
			return false;
		try {
			return memcachedClient.add(key, expiredTime, value);
		} catch(Exception ex) {
			logger.warn(ex.getMessage(), ex);
			return false;
		}
	}
	
	public long incr(String key, long delta) {
		if(delta < 0)
			return 0;
		try {
			return memcachedClient.incr(key, delta);
		} catch(Exception ex) {
			logger.warn(ex.getMessage(), ex);
			return 0;
		}
	}

	/**
	 * 设置List cache
	 *
	 * @param key
	 * @param expiredTime
	 * @param value
	 * @param mot 如:MemcachedObjectType.PRODUCT
	 * @return
	 * @since lottery 1.0
	 */
	/*
	public boolean setList(String key, int expiredTime, Object value, MemcachedObjectType mot) {
	if (value == null)
		return false;
	try {
		Set<String> set = memcachedClient.get(mot.getPrefix());
		memcachedClient.set(key, expiredTime, value);
		if (set == null)
			set = new HashSet<String>();
		set.add(key);
		memcachedClient.set(mot.getPrefix(), mot.getExpiredTime(), set);
		return true;
	} catch (Exception e) {
		logger.warn(e.getMessage(), e);
		return false;
	}
	}*/

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		try {
			return (T) memcachedClient.get(key);
		} catch (Exception e) {
			logger.warn("Get from memcached server fail,key is " + key, e);
			return null;
		}
	}
	
	public <T> GetsResponse<T> gets(String key) {
		try {
			return (GetsResponse<T>) memcachedClient.gets(key);
		} catch (Exception e) {
			logger.warn("Gets from memcached server fail,key is " + key, e);
			return null;
		}
	}

	/**
	 * 
	 * 获取List cache
	 *
	 * @param key
	 * @return
	 * @since lottery 1.0
	 */
	@SuppressWarnings("unchecked")
	public <T> T getList(String key) {
		try {
			List<String> list = memcachedClient.get(key);
			Map map = memcachedClient.get(list);
			if (map == null || map.size() < 1)
				return null;
			return (T) new ArrayList(map.values());
		} catch (Exception e) {
			logger.warn("Get from memcached server fail,key is " + key, e);
			return null;
		}
	}

	/**
	 * Delete方法.	 
	 */
	public boolean delete(String key) {
		if (key == null)
			return false;

		try {
			return memcachedClient.delete(key);
		} catch (Exception e) {
			logger.warn("The key[{}] is not exist, failed to delete. ", key);
			return false;
		}
	}

	/**
	 * 
	 * Delete 多个cache
	 *
	 * @param keys
	 * @since lottery 1.0
	 */
	public void delete(List<String> keys) {
		if (keys == null)
			return;
		try {
			for (String key : keys) {
				memcachedClient.deleteWithNoReply(key);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
	}

	public <T> boolean cas(final String key, final CASOperation<T> operation) {
		try {
			return memcachedClient.cas(key, operation);
		} catch (Exception e) {
			logger.warn("CAS from memcached server fail,key is " + key, e);
			return false;
		}
	}
	
	public <T> boolean cas(final String key, final int expiredTime,  final CASOperation<T> operation) {
		try {
			return memcachedClient.cas(key, expiredTime, operation);
		} catch (Exception e) {
			logger.warn("CAS from memcached server fail,key is " + key, e);
			return false;
		}
	}
	
	public boolean cas(String key, int expiredTime, Object value, long casValue) {
		try {
			return memcachedClient.cas(key, expiredTime, value, casValue);
		} catch (Exception e) {
			logger.warn("CAS from memcached server fail,key is " + key, e);
			return false;
		}
		
	}
	
	

	@Override
	public void destroy() throws Exception {
		memcachedClient.shutdown();
		memcachedClient = null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (memcachedClient == null) {
			memcachedClient = SpringContextHolder.getBean("memcachedClient");
		}
	}

	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}
	
	/*@Autowired
	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}*/

}