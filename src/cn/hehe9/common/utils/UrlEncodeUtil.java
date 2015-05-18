package cn.hehe9.common.utils;

public class UrlEncodeUtil {

	/**
	 * 先 base64 encode， 再url encode.<p>
	 * 方案一:先base64 encode, 再url encode, 最后用全角"％"代替"%";<p>
	 * 方案二:先用"_"代替"+", 用"-"代替"/" 最后用base64编码;<p>
	 * 注: 方法中采用了方案二;
	 * 
	 */
	public static String base64Encode(String msg) {
		try {
			//			return URLEncoder.encode(Base64Util.encode(msg), "UTF-8").replace("%", "％");

			return Base64Util.encode(msg).replace("+", "_").replace("/", "-");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 先url decode, 再  base64 decode.<p>
	 * 方案一: 先用全角"％"替换"%",再进行url decode 和 base64 decode解码操作;<p>
	 * 方案二: 先用"/"代替"-", 用"+"代替"_", 最后用base64解码;<p>
	 * 注: 方法中采用了方案二;
	 */
	public static String base64Decode(String base64EncodeStr) {
		try {
			//			base64EncodeStr = base64EncodeStr.replaceAll("％", "%");
			//			return Base64Util.decode(URLDecoder.decode(base64EncodeStr, "UTF-8"));

			base64EncodeStr = base64EncodeStr.replaceAll("-", "/").replace("_", "+");
			return Base64Util.decode(base64EncodeStr);
		} catch (Exception e) {
			return null;
		}
	}
}
