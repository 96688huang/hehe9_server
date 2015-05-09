package cn.hehe9.common.app;

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

	public static String addRootUrlIfNeeded(String pageUrl, String rootUrl) {
		if (rootUrl.endsWith("/")) {
			rootUrl = rootUrl.substring(0, rootUrl.length() - 1);
		}

		pageUrl = pageUrl.contains(rootUrl) ? pageUrl
				: (rootUrl + (!pageUrl.startsWith("/") ? "/" + pageUrl : pageUrl));
		return pageUrl;
	}

	/**
	 * 如果别名存在, 则获取;
	 *
	 * @param origName	原始名称
	 * @return	如果别名存在, 则返回别名; 否则, 返回视频名称;
	 */
	public static String getAliasNameIfExist(String origName) {
		String alias = AppConfig.ALIAS_MAP.get(origName);
		return StringUtils.isBlank(alias) ? origName : alias;
	}
}
