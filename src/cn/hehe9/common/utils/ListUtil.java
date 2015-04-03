package cn.hehe9.common.utils;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chenxs
 * @date 2013年11月27日
 */
public class ListUtil {
	/**
	 * 根据delimeter作为连接符连接list的值
	 * @param <T>
	 * @param list	列表值
	 * @param delimeter	连接符，默认为","
	 * @return
	 * 	由delimeter连接的字符串值
	 */
	public static <T> String combineWithDelimeter(List<T> list,String delimeter) {
		if(StringUtils.isEmpty(delimeter)) {
			delimeter = ",";
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i ++) {
			T t = list.get(i);
			if (t != null) {
				if (i > 0) {
					sb.append(delimeter);
				}
				sb.append(t);
			}
		}

		return sb.toString();
	}

	/**
	 * 通过指定的分割符进行分割
	 * TODO 暂时只支持int,long,String，有待支持所有类型
	 * @param splitStr      需要分割的字符串
	 * @param delimiter     分割符
	 * @param t             分割后的list元素类型
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> splitWithDelimeter(String splitStr, String delimiter, T t) {
		List<T> list = new ArrayList<T>();
		if (!StringUtils.isEmpty(splitStr)) {
			for (String str:splitStr.split(delimiter)) {
				if (t.getClass().isAssignableFrom(Integer.class)) {
					list.add((T)Integer.valueOf(str));
				} else if (t.getClass().isAssignableFrom(Long.class)) {
					list.add((T)Long.valueOf(str));
				} else if (t.getClass().isAssignableFrom(Double.class)) {
					list.add((T)Double.valueOf(str));
				} else if (t.getClass().isAssignableFrom(Float.class)) {
					list.add((T)Float.valueOf(str));
				} else if (t.getClass().isAssignableFrom(Boolean.class)){
					list.add((T)Boolean.valueOf(str));
				} else {
					list.add((T)str);
				}
			}
		}
		return list;
	}

	/**
	 * 去掉List中的空值，主要供调用rowcacheSelectMulti后使用
	 * @param <T>
	 * @param list	List值
	 * @return
	 * 	经过处理后的list值
	 */
	public static <T> List<T> eliminateNullValue(List<T> list) {
		List<T> resultList = new ArrayList<T>();
		for (T t:list) {
			if (t != null) {
				resultList.add(t);
			}
		}
		return resultList;
	}

	/**
	 * 取得list中fieldName指定的属性值，作为list
	 * @param <T>
	 * @param list		对象列表
	 * @param fieldName	属性值名称
	 * @return
	 *
	 */
	public static <T> List wrapFieldValueList(List<T> list,String fieldName) {
		List resultList = new ArrayList();
		for (T t:list) {
			if (t != null) {
				resultList.add(ReflectionUtil.getFieldValue(t, fieldName));
			}
		}
		return resultList;
	}

	/**
	 * 去除list中重复的对象
	 * @param list	需要去除重复对象的list
	 * @return
	 */
	public static <T> List eliminateRepeatedValue(List<T> list) {
		List<T> resultList = new ArrayList<T>();
		for (T t:list) {
			if (resultList.contains(t)) {
				continue;
			}
			resultList.add(t);
		}
		return resultList;
	}

	/**
	 * 将参数转化list
	 * @param t
	 * @return
	 * @throw 列表类型不一致
	 * @author chenxx
	 */
	public static <T> List<T> asList(T... t) {
		List<T> list = new ArrayList<T>();
		Class<?> clazz = null;
		for(int i=0; i<t.length; i++) {
			if(clazz != null && !clazz.equals(t[i].getClass())) {
				throw new RuntimeException("list item class not inconsistent");
			}
			clazz = t[i].getClass();
			list.add(t[i]);
		}
		return list;
	}

}

