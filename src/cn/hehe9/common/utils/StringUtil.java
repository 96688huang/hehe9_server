package cn.hehe9.common.utils;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	private static final Pattern REGEX_PATTERN_COMMA = Pattern.compile(",");

	/**
	 * 判断字符串是否是null或者空字符串
	 * @param str
	 * @return
	 * @deprecated 用 <tt>org.apache.commons.lang.StringUtils.isblank(text)</tt>
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNumber(char chr) {
		return chr > '0' && chr < '9';
	}

	public static boolean isLetter(char chr) {
		return chr > 'A' && chr < 'Z' || chr > 'a' && chr < 'z';
	}

	public static boolean isNumeric(String str) {
		if (isNullOrEmpty(str)) {
			return false;
		}
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		return str.length() == pos.getIndex();
	}

	/**
	 * 改进的32位FNV算法
	 *
	 * @param data 字符串
	 * @return int值
	 */

	public static int FNVHash(String data) {
		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (int i = 0; i < data.length(); i++) {
			hash = (hash ^ data.charAt(i)) * p;
		}
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;
		return hash;
	}

	//将字符串转换为Long数组
	public static List<Long> formatToLongList(String str, String flg) throws NumberFormatException {
		List<Long> list = null;
		if (str != null) {
			String[] splits = str.split(flg);
			if (splits != null && splits.length > 0) {
				list = new ArrayList<Long>(splits.length);
				for (String s : splits) {
					long obj = 0;
					try {
						obj = Long.parseLong(s);
					} catch (NumberFormatException e) {
						throw new NumberFormatException("error");
					}
					list.add(obj);
				}
			}
		}
		return list;
	}

	//将字符串转换为Integer数组
	public static List<Integer> formatToIntegerList(String str, String flg) throws NumberFormatException {
		List<Integer> list = null;
		if (str != null) {
			String[] splits = str.split(flg);
			if (splits != null && splits.length > 0) {
				list = new ArrayList<Integer>(splits.length);
				for (String s : splits) {
					int obj = 0;
					try {
						obj = Integer.parseInt(s);
					} catch (NumberFormatException e) {
						throw new NumberFormatException("error");
					}
					list.add(obj);
				}
			}
		}
		return list;
	}

	/**
	 * 从字符串中提取出数字。
	 *
	 * @param stringContainInteger 含多数字的字符串
	 * @param separator            分隔符
	 * @return 数字集合，如果字符串中没含有数字，那么返回空的集合
	 */
	public static List<Integer> extractIntegers(String stringContainInteger, String separator) {
		List<Integer> integers = new ArrayList<Integer>();
		if (StringUtils.isBlank(stringContainInteger) || StringUtils.isBlank(separator)) {
			return integers;
		}

		String[] splits = stringContainInteger.split(separator);
		if (splits != null && splits.length > 0) {
			integers = new ArrayList<Integer>(splits.length);
			for (String s : splits) {
				int obj = 0;
				try {
					obj = Integer.parseInt(s);
					integers.add(obj);
				} catch (Exception e) {
					//忽略这个异常
				}
			}
		}

		return integers;
	}

	/**
	 * 将List转为String
	 *
	 * @param list
	 * @param flg
	 * @return
	 */
	public static String formatListToString(List list, String flg) {
		if (list == null || list.size() < 1) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i)).append(flg);
		}
		String s = sb.toString();
		return s.substring(0, s.length() - 1);
	}

	/**
	 * 字符串截断，这里对中文处理为2个字符,并且省略号算1个中文站位
	 * 如：substringCN("niai我是谁",4,"…")
	 * 结果为："niai我…"
	 *
	 * @param str        字符串
	 * @param maxLength  最大中文长度，也就意味着是双倍的英文字符
	 * @param postfixStr 后缀字符串
	 * @return 截断的字符
	 */
	public static String substringCN(String str, int maxLength, String postfixStr) {
		if (str == null)
			return "";
		if (maxLength < 2) {
			return str;
		}
		if (str != null && str.getBytes().length <= maxLength * 2)
			return str;
		try {
			postfixStr = StringUtils.isBlank(postfixStr) ? "" : postfixStr;
			return mySubString(str, (maxLength - 1) * 2) + postfixStr;
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	//中英文组合截字
	public static String mySubString(String str, int length) throws UnsupportedEncodingException {
		if (length < 2)
			return str;
		Pattern p = Pattern.compile("^[^\\x00-\\xff]$");
		int i = 0, j = 0;
		for (char c : str.toCharArray()) {
			Matcher m = p.matcher(String.valueOf(c));
			i += m.find() ? 2 : 1;
			++j;
			if (i == length)
				break;
			if (i > length) {
				--j;
				break;
			}
		}
		return str.substring(0, j);
	}

	/**
	 * 获取字符串长度，中文算2个字符
	 *
	 * @param str
	 * @return
	 */
	public static int getENLength(String str) {
		if (isNullOrEmpty(str)) {
			return 0;
		}
		Pattern p = Pattern.compile("^[^\\x00-\\xff]$");
		int i = 0;
		for (char c : str.toCharArray()) {
			Matcher m = p.matcher(String.valueOf(c));
			i += m.find() ? 2 : 1;
		}
		return i;
	}

	/**
	 * 替换掉<br>、<br/>、<br />
	 *
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public static String replaceBr(String regex, String replacement) {
		if (isNullOrEmpty(regex)) {
			return "";
		}
		return regex.replaceAll("<br\\s*/?>", replacement);
	}

	public static String[] splitByComma(String str) {
		return REGEX_PATTERN_COMMA.split(str);
	}

	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}

	/**
	 * 将字符串编码成 Unicode 。
	 *
	 * @param theString   待转换成Unicode编码的字符串。
	 * @param escapeSpace 是否忽略空格。
	 * @return 返回转换后Unicode编码的字符串。
	 */
	public static String toUnicode(String theString, boolean escapeSpace) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
			case ' ':
				if (x == 0 || escapeSpace)
					outBuffer.append('\\');
				outBuffer.append(' ');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			case '=': // Fall through
			case ':': // Fall through
			case '#': // Fall through
			case '!':
				outBuffer.append('\\');
				outBuffer.append(aChar);
				break;
			default:
				if ((aChar < 0x0020) || (aChar > 0x007e)) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex((aChar >> 12) & 0xF));
					outBuffer.append(toHex((aChar >> 8) & 0xF));
					outBuffer.append(toHex((aChar >> 4) & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				} else {
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	/**
	 * 从 Unicode 码转换成编码前的特殊字符串。
	 *
	 * @param in       Unicode编码的字符数组。
	 * @param off      转换的起始偏移量。
	 * @param len      转换的字符长度。
	 * @param convtBuf 转换的缓存字符数组。
	 * @return 完成转换，返回编码前的特殊字符串。
	 */
	public String fromUnicode(char[] in, int off, int len, char[] convtBuf) {
		if (convtBuf.length < len) {
			int newLen = len * 2;
			if (newLen < 0) {
				newLen = Integer.MAX_VALUE;
			}
			convtBuf = new char[newLen];
		}
		char aChar;
		char[] out = convtBuf;
		int outLen = 0;
		int end = off + len;

		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				aChar = in[off++];
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = in[off++];
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
						}
					}
					out[outLen++] = (char) value;
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					out[outLen++] = aChar;
				}
			} else {
				out[outLen++] = (char) aChar;
			}
		}
		return new String(out, 0, outLen);
	}

	/**
	 * 将字符串数组转换成数字List
	 * @param strings
	 * @return
	 */
	public static List<Integer> convertToInteger(String[] strings) {
		List<Integer> integers = new ArrayList<Integer>();
		if (strings == null || strings.length == 0) {
			return integers;
		}

		for (String str : strings) {
			try {
				integers.add(Integer.valueOf(str));
			} catch (NumberFormatException e) {
				//忽略这个异常
			}
		}
		return integers;
	}

	/**
	 * 当字符串为null时，返回“”，否则返回原字符串信息
	 * @param str
	 * @return
	 */
	public static String nullToEmpty(String str) {
		return null == str ? "" : str;
	}

	/**
	 * 从字符串中挑选出数字
	 * @param string
	 * @return
	 */
	public static String pickInteger(String string) {
		return pick(string, "[0-9]");
	}

	/**
	 * 从字符串中挑选出数字
	 * @param string
	 * @return
	 */
	public static String pickDecimal(String string) {
		return pick(string, "[0-9//.]");
	}

	public static String pick(String string, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(string);
		StringBuilder no = new StringBuilder();
		while (m.find()) {
			no.append(m.group());
		}
		return no.toString();
	}
}
