package cn.hehe9.common.cache;

import java.io.Serializable;

/**
 * .
 * <p/>
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 系统：九游游戏中心客户端后台
 * <br> 开发：陈宏洪（chenhh3@ucweb.com）
 * <br> 创建时间：2014/8/21 11:50
 * <br>==========================
 */
public class CacheValue implements Serializable{

    private static final long serialVersionUID = 3200664254765377708L;

    public long createTime;

    public long expireTime;

    public Object value;

    public CacheValue(Object value) {
        this.createTime = System.currentTimeMillis();
        this.value = value;
    }

    public CacheValue(long createTime, Object value) {
        this.createTime = createTime;
        this.value = value;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
