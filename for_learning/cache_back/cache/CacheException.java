package cn.hehe9.common.cache;

import java.io.NotSerializableException;

/**
 * .
 * <p/>
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 系统：九游游戏中心客户端后台
 * <br> 开发：陈宏洪（chenhh3@ucweb.com）
 * <br> 创建时间：2014/8/26 9:40
 * <br>==========================
 */
public class CacheException extends Exception {

	private static final long serialVersionUID = 8353469474314758551L;

	public CacheException(String message) {
		super(message);
	}

	public CacheException(String string, NotSerializableException notSerializableException) {
		super(string, notSerializableException);
	}
}
