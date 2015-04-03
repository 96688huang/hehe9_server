package cn.hehe9.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Pinyin4jUtil {

	/**
	 * 返回拼音类型：全拼
	 */
	public static final String CONVERT_TYPE_ALL_CHARS = "ALL_CHARS";

	/**
	 * 返回拼音类型：各中方的首字母
	 */
	public static final String CONVERT_TYPE_HEAD_CHARS = "HEAD_CHAR";
	
	/**
	 * 字符串集合转换字符串(逗号分隔)
	 * 
	 * @param stringList
	 * @return
	 */
	public static String makeStringByStringList(List<String> stringList) {
		StringBuilder str = new StringBuilder();
		int i = 0;
		for (String s : stringList) {
			if (i == stringList.size() - 1) {
				str.append(s);
			} else {
				str.append(s + ",");
			}
			i++;
		}
		return str.toString().toLowerCase();
	}

	public static List<String> convertToAllChars(String string) {
		return convertTo(string, null);
	}
	
	public static List<String> convertToHeadChars(String string) {
		return convertTo(string, null);
	}

	/**
	 * 字符串转换为拼音
	 * 
	 * @param string
	 *            需要转换的字符串
	 * @param convertType
	 *            返回拼音结果类型
	 * @return 
	 *         如果retType为CONVERT_TYPE_ALL_CHARS，则返回全拼；
	 *         如果retType为CONVERT_TYPE_HEAD_CHARS, 则只返回转换后的首字母;
	 *         如果传入其他值，返回全拼
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static List<String> convertTo(String string, String convertType) {
		if (string != null && !string.trim().equalsIgnoreCase("")) {
			char[] srcChar;
			srcChar = string.toCharArray();
			// 汉语拼音格式输出类
			HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();

			// 输出设置，大小写，音标方式等
			hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

			String[][] temp = new String[string.length()][];
			for (int i = 0; i < srcChar.length; i++) {
				char c = srcChar[i];

				try {
					temp[i] = PinyinHelper.toHanyuPinyinStringArray(srcChar[i],
							hanYuPinOutputFormat);

					if (temp[i] == null) {// 如果str.charAt(i)非汉字，则保持原样
						temp[i] = new String[] { String.valueOf(srcChar[i]) };
					} else {
						// 如果retType是CONVERT_TYPE_HEAD_CHARS，则只取转换后的首字母
						if (CONVERT_TYPE_HEAD_CHARS.equalsIgnoreCase(convertType)) {
							String[] temptemp = new String[temp[i].length];
							for (int j = 0; j < temp[i].length; j++) {
								temptemp[j] = String.valueOf(temp[i][j]
										.charAt(0));
							}
							temp[i] = temptemp;
						}
					}

				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			}
			String[] pingyinArray = Exchange(temp);
			List<String> pinyinList = new ArrayList<String>();
			for (int i = 0; i < pingyinArray.length; i++) {
				pinyinList.add(pingyinArray[i]);
			}
			return pinyinList;
		}
		return null;
	}

	/**
	 * 递归
	 * 
	 * @param strJaggedArray
	 * @return
	 */
	public static String[] Exchange(String[][] strJaggedArray) {
		String[][] temp = DoExchange(strJaggedArray);
		return temp[0];
	}

	/**
	 * 递归
	 * 
	 * @param strJaggedArray
	 * @return
	 */
	private static String[][] DoExchange(String[][] strJaggedArray) {
		int len = strJaggedArray.length;
		if (len >= 2) {
			int len1 = strJaggedArray[0].length;
			int len2 = strJaggedArray[1].length;
			int newlen = len1 * len2;
			String[] temp = new String[newlen];
			int Index = 0;
			for (int i = 0; i < len1; i++) {
				for (int j = 0; j < len2; j++) {
					temp[Index] = strJaggedArray[0][i] + strJaggedArray[1][j];
					Index++;
				}
			}
			String[][] newArray = new String[len - 1][];
			for (int i = 2; i < len; i++) {
				newArray[i - 1] = strJaggedArray[i];
			}
			newArray[0] = temp;
			return DoExchange(newArray);
		} else {
			return strJaggedArray;
		}
	}

	public static void main(String[] args) {
		String str = "单单芳abc";
		System.out.println(makeStringByStringList(convertToAllChars(str)));
		System.out.println(makeStringByStringList(convertTo(str,
				CONVERT_TYPE_HEAD_CHARS)));
	}

}