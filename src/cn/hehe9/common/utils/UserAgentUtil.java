package cn.hehe9.common.utils;

import org.apache.commons.lang3.StringUtils;

import cn.hehe9.common.constants.OS;

public class UserAgentUtil {

	public static final String CHROME = "Mozilla/5.0 (Windows NT 5.2) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30";
	public static final String FIREFOX = "Mozilla/5.0 (Windows NT 5.1; rv:5.0) Gecko/20100101 Firefox/5.0";
	public static final String IE8 = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET4.0E; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C)";
	public static final String IE9 = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)";
	public static final String _360_BROWSER_IE8 = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET4.0E; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C)";

	public static boolean isPc(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		if (userAgent.contains("Windows NT")) {
			return true;
		}
		if (userAgent.contains("Mac OS")) {
			return true;
		}
		return false;
	}

	public static String getOS(String userAgent) {
		if (StringUtils.isBlank(userAgent)) {
			return "";
		}
		if (userAgent.indexOf("iPhone") > 0) {
			return OS.IOS;
		} else if (userAgent.indexOf("ipad") > 0) {
			return OS.IOS;
		} else if (userAgent.indexOf("Android") > 0) {
			return OS.ANDROID;
		} else if (userAgent.indexOf("Windows Phone") > 0) {
			return OS.WINPHONE;
		} else if (userAgent.indexOf("Windows") > 0) {
			return OS.WINDOWS;
		} else if (userAgent.indexOf("Mac") > 0) {
			return OS.MAC;
		} else {
			return "";
		}
	}
}
