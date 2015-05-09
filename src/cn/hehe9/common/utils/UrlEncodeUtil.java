package cn.hehe9.common.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlEncodeUtil {

	/**
	 * 先 base64 encode， 再url encode.<p>
	 * 仅对 %号 作了特殊替换处理：用"％"代替"%"
	 * 
	 */
	public static String base64Encode(String msg) {
		try {
			return URLEncoder.encode(Base64Util.encode(msg), "UTF-8").replace("%", "％");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 先url decode, 再  base64 decode.<p>
	 * 注: 会先用"％"替换"%",再进行解码操作;
	 */
	public static String base64Decode(String base64EncodeStr) {
		try {
			base64EncodeStr = base64EncodeStr.replaceAll("％", "%");
			return Base64Util.decode(URLDecoder.decode(base64EncodeStr, "UTF-8"));
		} catch (Exception e) {
			return null;
		}
	}
}
