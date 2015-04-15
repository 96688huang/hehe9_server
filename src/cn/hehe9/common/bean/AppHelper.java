package cn.hehe9.common.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * 助手类
 */
public class AppHelper {

	public static String subString(String content, int subLength) {
		return subString(content, 0, subLength, "");
	}

	public static String subString(String content, int subLength, String suffix) {
		return subString(content, 0, subLength, suffix);
	}

	public static String subString(String content, int startIndex, int endIndex) {
		return subString(content, startIndex, endIndex, "");
	}

	public static String subString(String content, int startIndex, int endIndex, String suffix) {
		return StringUtils.substring(content, startIndex, endIndex) + suffix;
	}
}
