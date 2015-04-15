package cn.hehe9.common.cache.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.DefaultHashAlgorithm;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.transcoders.SerializingTranscoder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hehe9.common.app.AppConfig;
import cn.hehe9.common.exceptions.ConfigurationException;

/**
 * Memcached implementation (using http://code.google.com/p/spymemcached/)
 *
 * expiration is specified in seconds
 */
public class MemcachedImpl implements CacheImpl {

    private static MemcachedImpl uniqueInstance;

    MemcachedClient client;

    SerializingTranscoder tc;
    
    private static final Logger logger = LoggerFactory.getLogger(MemcachedImpl.class);

    /**
     * 用于互斥的锁
     */
    private static byte[] lock = new byte[0];
    
    public static MemcachedImpl getInstance() throws IOException {
        return getInstance(false);
    }

    public static MemcachedImpl getInstance(boolean forceClientInit) throws IOException {
        
        if (uniqueInstance == null) {
            synchronized (lock) {
                if (uniqueInstance == null) {
                    uniqueInstance = new MemcachedImpl();
                }
            }
        } else if (forceClientInit) {
            // When you stop the client, it sets the interrupted state of this thread to true. If you try to reinit it with the same thread in this state,
            // Memcached client errors out. So a simple call to interrupted() will reset this flag
            Thread.interrupted();
            uniqueInstance.initClient();
        }
        return uniqueInstance;

    }

    private MemcachedImpl() throws IOException {
        tc = new SerializingTranscoder() {

            @Override
            protected Object deserialize(byte[] data) {
                try {
                    return new ObjectInputStream(new ByteArrayInputStream(data)) {

                        @Override
                        protected Class<?> resolveClass(ObjectStreamClass desc)
                                throws IOException, ClassNotFoundException {
//                            return Class.forName(desc.getName(), false, Jws.classloader);
                        	return Class.forName(desc.getName(), false, this.getClass().getClassLoader());
                        }
                    }.readObject();
                } catch (Exception e) {
                	logger.error("Could not deserialize", e);
                }
                return null;
            }

            @Override
            protected byte[] serialize(Object object) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    new ObjectOutputStream(bos).writeObject(object);
                    return bos.toByteArray();
                } catch (IOException e) {
                	logger.error("Could not serialize", e);
                }
                return null;
            }
        };
        initClient();
    }

    public void initClient() throws IOException {
        System.setProperty("net.spy.log.LoggerImpl", "net.spy.memcached.compat.log.Log4JLogger");

        List<InetSocketAddress> addrs;
        if (StringUtils.isNotBlank(AppConfig.MEMCACHE_HOST)) {
            addrs = AddrUtil.getAddresses(AppConfig.MEMCACHE_HOST);
        } else if (AppConfig.MEMCACHE_HOST_LIST != null && AppConfig.MEMCACHE_HOST_LIST.size() > 0) {
            int nb = 1;
            String addresses = "";
//            while (Jws.configuration.containsKey("memcached." + nb + ".host")) {
//                addresses += Jws.configuration.get("memcached." + nb + ".host") + " ";
//                nb++;
//            }
            for(String memcacheHost : AppConfig.MEMCACHE_HOST_LIST){
              addresses += memcacheHost + " ";
              nb++;
            }
            addrs = AddrUtil.getAddresses(addresses);
        } else {
            throw new ConfigurationException("Bad configuration for memcached: missing host(s)");
        }

        ConnectionFactoryBuilder builder = new ConnectionFactoryBuilder()
                .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                .setReadBufferSize(1024 * 1024)
                .setOpTimeout(3 * 1000)
                .setUseNagleAlgorithm(true)
                .setFailureMode(FailureMode.Cancel);

        if (StringUtils.isNoneBlank(AppConfig.MEMCACHE_USER)) {
            String memcacheUser = AppConfig.MEMCACHE_USER;
            String memcachePassword = AppConfig.MEMCACHE_PWD;
            if (memcachePassword == null) {
                throw new ConfigurationException("Bad configuration for memcached: missing password");
            }

            // Use plain SASL to connect to memcached
            AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"},
                    new PlainCallbackHandler(memcacheUser, memcachePassword));
            builder.setAuthDescriptor(ad);
        }

        if (AppConfig.MEMCACHE_USE_CONSISTENT_HASH) {
            builder.setHashAlg(DefaultHashAlgorithm.KETAMA_HASH)
                    .setLocatorType(ConnectionFactoryBuilder.Locator.CONSISTENT);
        }

        client = new MemcachedClient(builder.build(), addrs);
    }

    public void add(String key, Object value, int expiration) {
        client.add(key, expiration, value, tc);
    }

    public Object get(String key) {
        Future<Object> future = client.asyncGet(key, tc);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return null;
    }

    public void clear() {
        client.flush();
    }

    public void delete(String key) {
        client.delete(key);
    }

    public Map<String, Object> get(String[] keys) {
        Future<Map<String, Object>> future = client.asyncGetBulk(tc, keys);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return Collections.<String, Object>emptyMap();
    }

    public long incr(String key, int by) {
        return client.incr(key, by, 0);
    }

    public long incr(String key, int by, long value) {
        return client.incr(key, by, value);
    }

    public long decr(String key, int by) {
        return client.decr(key, by, 0);
    }

    public long decr(String key, int by, long value) {
        return client.decr(key, by, value);
    }

    public void replace(String key, Object value, int expiration) {
        client.replace(key, expiration, value, tc);
    }

    public boolean safeAdd(String key, Object value, int expiration) {
        Future<Boolean> future = client.add(key, expiration, value, tc);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return false;
    }

    public boolean safeDelete(String key) {
        Future<Boolean> future = client.delete(key);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return false;
    }

    public boolean safeReplace(String key, Object value, int expiration) {
        Future<Boolean> future = client.replace(key, expiration, value, tc);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return false;
    }

    public boolean safeSet(String key, Object value, int expiration) {
        Future<Boolean> future = client.set(key, expiration, value, tc);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return false;
    }

    public void set(String key, Object value, int expiration) {
        client.set(key, expiration, value, tc);
    }

    public void stop() {
        client.shutdown();
    }

}
