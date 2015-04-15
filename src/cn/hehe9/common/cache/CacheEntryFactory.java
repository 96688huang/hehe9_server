package cn.hehe9.common.cache;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.hehe9.common.utils.FileResourceLoader;
import cn.hehe9.common.utils.UnitUtil;

/**
 * CacheEntry 工厂，负责在程序启动的时候初始化 CacheEntry 和取得 CacheEntry，只在启动的时候读一次配置文件.
 * <p/>
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 系统：九游游戏中心客户端后台
 * <br> 开发：陈宏洪（chenhh3@ucweb.com）
 * <br> 创建时间：13-11-20 下午2:44
 * <br>==========================
 */
public class CacheEntryFactory {

    private String file;

    private static ConcurrentHashMap<String, CacheEntry> cacheEntries = new ConcurrentHashMap<String, CacheEntry>();

    private static Logger logger = Logger.getLogger(CacheEntryFactory.class);

    public void setFile(String file) {
        this.file = file;
    }


    public void init() {
        FileResourceLoader resourceLoader = new FileResourceLoader();
        String configFile = resourceLoader.getAbsolutePath(file);
        cacheEntries = parse(configFile);
    }

    /**
     * 解析缓存配置信息。
     *
     * @param file 配置文件绝对路径
     * @return Map列表，KEY值为 provider.id
     */
    ConcurrentHashMap<String, CacheEntry> parse(String file) {

        ConcurrentHashMap<String, CacheEntry> entries = new ConcurrentHashMap<String, CacheEntry>();
        try {
            logger.info("加载业务缓存配置信息：" + file);
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            Element root = document.getRootElement();
            String defaultVersion = root.attributeValue("version");
            String defaultNode = root.attributeValue("node");
            String defaultExpiration = root.attributeValue("expiration");

            List providerList = document.selectNodes("/cache-config/cache-entry");
            for (Object state : providerList) {
                Element el = (Element) state;
                CacheEntry cacheEntryConfig = parse(el, defaultVersion, defaultNode, defaultExpiration);
                entries.put(cacheEntryConfig.getKey(), cacheEntryConfig);
            }

        } catch (Exception e) {
            logger.error("无法加载业务缓存配置信息:" + e.toString());
        }

        final int size = entries.size();
        if (size == 0) {
            logger.info("没有配置业务缓存，请检查配置文件：" + file);
            return entries;
        }

        logger.info("共配置了 " + size + " 项业务缓存：" + entries.keySet());

        return entries;
    }

    public static CacheEntry parse(Element cacheDom4jElement){
        return parse(cacheDom4jElement,null,null,null);
    }

    public static CacheEntry parse(Element cacheDom4jElement, String defaultVer, String defaultNode, String defaultExpiration) {
        CacheEntry cacheEntryConfig = new CacheEntry();

        //key
        final String key = cacheDom4jElement.attributeValue("key").toLowerCase();
        cacheEntryConfig.setKey(key);

        //版本
        String version = cacheDom4jElement.attributeValue("version");
        if (StringUtils.isBlank(version)) {
            cacheEntryConfig.setVersion(defaultVer);
        } else {
            cacheEntryConfig.setVersion(version);
        }

        //缓存节点
        String cacheImpl = cacheDom4jElement.attributeValue("node");
        if (StringUtils.isBlank(cacheImpl)) {
            cacheEntryConfig.setNode(defaultNode);
        } else {
            cacheEntryConfig.setNode(cacheImpl);
        }

        //过期时间
        String expiration = cacheDom4jElement.attributeValue("expiration");
        if (StringUtils.isBlank(expiration)) {
            cacheEntryConfig.setExpiration(UnitUtil.timeParse(defaultExpiration,TimeUnit.SECONDS));
        } else {
            cacheEntryConfig.setExpiration(UnitUtil.timeParse(expiration, TimeUnit.SECONDS));
        }

        //自动加载时间
        String reloadAt = cacheDom4jElement.attributeValue("reload-at");
        if (StringUtils.isBlank(reloadAt)) {
            cacheEntryConfig.setReloadAt(0);
        } else {
            cacheEntryConfig.setReloadAt(UnitUtil.timeParse(reloadAt,TimeUnit.SECONDS));
        }

        //当值为 null 时，是否使用 NoneValue 代替
        String noneSupported = cacheDom4jElement.attributeValue("none-supported");
        if (StringUtils.isBlank(noneSupported)) {
            cacheEntryConfig.setNoneSupported(false);
        } else {
            cacheEntryConfig.setNoneSupported(Boolean.parseBoolean(noneSupported));
        }

        //注释
        String comment = cacheDom4jElement.attributeValue("comment");
        cacheEntryConfig.setComment(comment);

        return cacheEntryConfig;
    }

    /**
     * 根据配置（cache-config.xml）创建一个新的缓存项目实例。
     *
     * @param key 缓存的KEY
     * @return 如果在配置文件中找不到该 key 的话，那么返回 null
     */
    public static CacheEntry create(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        final CacheEntry cacheEntryConfig = cacheEntries.get(key.toLowerCase());
        if(cacheEntryConfig == null){
            return null;
        }

        CacheEntry instance = new CacheEntry();
        instance.setKey(cacheEntryConfig.getKey());
        instance.setExpiration(cacheEntryConfig.getExpiration());
        instance.setNode(cacheEntryConfig.getNode());
        instance.setVersion(cacheEntryConfig.getVersion());
        instance.setComment(cacheEntryConfig.getComment());
        instance.setReloadAt(cacheEntryConfig.getReloadAt());
        instance.setNoneSupported(cacheEntryConfig.isNoneSupported());

        return instance;
    }

    /**
     * 根据配置（cache-config.xml）创建一个新的缓存项目实例。
     *
     * @param key 缓存的KEY
     * @param sampler 缓存样本
     * @return 如果在配置文件中找不到该 key 的话，那么返回 null
     */
    public static CacheEntry create(String key, String sampler){
        CacheEntry cacheEntry = create(key);
        if(cacheEntry == null){
            return null;
        }
        cacheEntry.setSampler(sampler);
        return cacheEntry;
    }
}
