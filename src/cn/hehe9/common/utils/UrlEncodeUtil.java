package cn.hehe9.common.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlEncodeUtil {

	/**
	 * 先 base64 encode， 再url encode.<p>
	 * 仅对 加号 作了特殊替换处理：base64encodeStr.replaceAll("\\+", "%2B")
	 * 
	 */
	public static String base64Encode(String msg) {
		try {
			return URLEncoder.encode(Base64Util.encode(msg).replaceAll("\\+", "%2B"), "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 先 base64 decode, 再 url decode
	 */
	public static String base64Decode(String base64EncodeStr) {
		try {
			return URLDecoder.decode(Base64Util.decode(base64EncodeStr), "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}
}
