package cn.hehe9.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtil {

	private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	/**
	 * 反射比较两个对象的属性值, 可以指定比较的属性名, 如果不指定, 则比较所有的属性值.
	 * 
	 * @param objA
	 * @param objB
	 * @param compareFieldNames	指定比较的属性名
	 * @param exceptFieldNames	排除比较的属性名
	 * @return
	 */
	public static boolean isFieldsValueSame(Object objA, Object objB, List<String> compareFieldNames,
			List<String> exceptFieldNames) {
		Map<String, Object> fieldsFromA = getFields(objA);
		Map<String, Object> fieldFromB = getFields(objB);
		if (compareFieldNames == null || compareFieldNames.size() == 0) {
			compareFieldNames = new ArrayList<String>(fieldsFromA.keySet());
		}

		for (String fieldName : compareFieldNames) {
			if (exceptFieldNames != null && exceptFieldNames.contains(fieldName)) {
				continue;
			}

			Object valueFromA = fieldsFromA.get(fieldName);
			Object valueFromB = fieldFromB.get(fieldName);
			if (valueFromA == null && valueFromB == null) {
				continue;
			}

			if (!ObjectUtils.equals(valueFromA, valueFromB)) {
				return false;
			}
		}
		return true;
	}

	public static Map<String, Object> getFields(Object app) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Field[] fields = app.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				String name = fields[i].getName();
				Object value = fields[i].get(app);
				map.put(name, value);
			}
		} catch (Exception e) {
			logger.error("Fail to get declared fields from Class : " + (app == null ? null : app.getClass().getName()),
					e);
		}
		return map;
	}
}
