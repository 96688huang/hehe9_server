package cn.hehe9.common.cache;

/**
 * 缓存项，封装了缓存一个对象所必须包含的信息，包括超时时间、KEY生成规则、缓存容器、实体.
 *
 * <p/>
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 系统：九游游戏中心客户端后台
 * <br> 开发：陈宏洪（chenhh3@ucweb.com）
 * <br> 创建时间：13-11-20 下午2:44
 * <br>==========================
 */
public class CacheEntry {

    protected String key;

    protected int expiration;

    protected String node;

    protected String version;

    protected Object value;

    protected CacheKeyBuilder keyBuilder;

    protected String sampler = "";

    protected String comment;

    protected Integer reloadAt;

    protected boolean noneSupported;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSampler() {
        return sampler;
    }

    public void setSampler(String sampler) {
        this.sampler = sampler;
    }

    public CacheEntry() {
        keyBuilder = new DefaultCacheKeyBuilder(this);
    }

    public CacheKeyBuilder getKeyBuilder() {
        return keyBuilder;
    }

    public void setKeyBuilder(CacheKeyBuilder keyBuilder) {
        this.keyBuilder = keyBuilder;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Integer getReloadAt() {
        return reloadAt;
    }

    public void setReloadAt(Integer reloadAt) {
        this.reloadAt = reloadAt;
    }

    public boolean isNoneSupported() {
        return noneSupported;
    }

    public void setNoneSupported(boolean noneSupported) {
        this.noneSupported = noneSupported;
    }

    @Override
    public String toString() {
        return "CacheEntry{" +
                "key='" + key + '\'' +
                ", expiration=" + expiration +
                ", node='" + node + '\'' +
                ", version='" + version + '\'' +
                ", sampler='" + sampler + '\'' +
                '}';
    }
}
