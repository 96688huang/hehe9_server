package cn.hehe9.common.utils;

import java.util.concurrent.TimeUnit;

/**
*
* 单位字符串换算
* 
* <br>==========================
* <br> 公司：优视科技
* <br> 开发：梁文刚
* <br> 版本：1.0
* <br> 创建时间：2013-4-24
* <br>==========================
*/
public class UnitUtil {
	
	/**
	 * 存储单位换算
	 * input=1K or 1k, return 1024
	 * input=1M or 1m, return 1024 * 1024
	 * @param input
	 * @return
	 */
	public static int parse(String input) {
		input = input.trim();
		String last = input.substring(input.length()-1, input.length());
		if (last.equals("m") || last.equals("M")) {
			String mNum = input.substring(0, input.length() - 1).trim();
			return Integer.valueOf(mNum) * 1024 * 1024;
		} else if (last.equals("k") || last.equals("K")) {
			String kNum = input.substring(0, input.length() - 1).trim();
			return Integer.valueOf(kNum) * 1024;
		} else {
			return Integer.valueOf(input);
		}
	}
	
	/**
	 * 时间换算
	 * H or h : 小时
	 * M or m: 分钟
	 * S or s: 秒
	 * 不带后缀是单位秒
	 * @param input
	 * @return
	 */
	public static int timeParse(String input) {
		input = input.trim();
		String last = input.substring(input.length()-1, input.length());
		if (last.equals("h") || last.equals("H")) {
			String hour = input.substring(0, input.length() - 1).trim();
			return Integer.valueOf(hour) * 60 * 60;
		} else if (last.equals("m") || last.equals("M")) {
			String minute = input.substring(0, input.length() - 1).trim();
			return Integer.valueOf(minute) * 60;
		} else if (last.equals("s") || last.equals("S")) {
			String minute = input.substring(0, input.length() - 1).trim();
			return Integer.valueOf(minute);
		} else {
			return Integer.valueOf(input);
		}
	}
	
	public static int msTimeParse(String input) {
		input = input.trim();
		String last = input.substring(input.length()-1, input.length());
		 if (input.endsWith("ms") || input.endsWith("MS")) {
			String ms = input.substring(0, input.length() - 2).trim();
			return Integer.valueOf(ms);
		 } else if (last.equals("h") || last.equals("H")) {
			String hour = input.substring(0, input.length() - 1).trim();
			return Integer.valueOf(hour) * 60 * 60 * 1000;
		} else if (last.equals("m") || last.equals("M")) {
			String minute = input.substring(0, input.length() - 1).trim();
			return Integer.valueOf(minute) * 60 * 1000;
		} else if (last.equals("s") || last.equals("S")) {
			String minute = input.substring(0, input.length() - 1).trim();
			return Integer.valueOf(minute) * 1000;
		} else {
			return Integer.valueOf(input);
		}
	}
	
	/**
	 * 时间换算
	 * H or h : 小时
	 * M or m: 分钟
	 * S or s: 秒
	 * ms or MS 毫秒
	 * 不带后缀是单位秒
	 * @param input
	 * @param unit  默认单位
	 * @return
	 */
	public static int timeParse(String input, TimeUnit defaultUnit) {
		if (defaultUnit != TimeUnit.SECONDS && defaultUnit != TimeUnit.MILLISECONDS) {
			throw new RuntimeException("only support SECONDS or MILLISECONDS");
		}
		boolean isSecond = (defaultUnit == TimeUnit.SECONDS) ? true : false;
		if (isSecond) {
			return timeParse(input);
		} else {
			return msTimeParse(input);
		}
	}
}
