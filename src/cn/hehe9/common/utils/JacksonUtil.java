package cn.hehe9.common.utils;

import org.apache.commons.lang.ArrayUtils;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

import java.text.SimpleDateFormat;

/**
 * 用途。
 * <p/>
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 系统(子系统)：九游游戏中心Appserver
 * <br> 开发：liuyong@ucweb.com
 * <br> 创建时间：2013-9-4下午02:54:21
 * <br>==========================
 */
public class JacksonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ObjectMapper includeObjectMapper = new ObjectMapper();
    private static ObjectMapper excludeObjectMapper = new ObjectMapper();

    static {
        objectMapper.setDateFormat(new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSSSSS"));
        // 设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
        objectMapper.setDeserializationConfig(
                objectMapper.getDeserializationConfig()
                        .without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES));
        // 设置不输出值为 null 的属性
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        // 忽略未知filterId异常
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));

        includeObjectMapper.setDateFormat(new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSSSSS"));
        // 设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
        includeObjectMapper.setDeserializationConfig(
                includeObjectMapper.getDeserializationConfig()
                        .without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES));
        //设置不输出值为 null 的属性
        includeObjectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        includeObjectMapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);

        excludeObjectMapper.setDateFormat(new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSSSSS"));
        // 设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
        excludeObjectMapper.setDeserializationConfig(
                excludeObjectMapper.getDeserializationConfig()
                        .without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES));
        //设置不输出值为 null 的属性
        excludeObjectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        excludeObjectMapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    /**
     * 将JSON字符串根据指定的Class反序列化成Java对象。
     *
     * @param json      JSON字符串
     * @param pojoClass Java对象Class
     * @return 反序列化生成的Java对象
     * @throws Exception 如果反序列化过程中发生错误，将抛出异常
     */
    public static Object decode(String json, Class<?> pojoClass)
            throws Exception {
        try {
            return objectMapper.readValue(json, pojoClass);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 将JSON字符串根据指定的Class反序列化成Java对象。
     *
     * @param json      JSON字符串
     * @param reference 类型引用
     * @return 反序列化生成的Java对象
     * @throws Exception 如果反序列化过程中发生错误，将抛出异常
     */
    public static Object decode(String json, TypeReference<?> reference) throws Exception {
        try {
            return objectMapper.readValue(json, reference);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 将Java对象序列化成JSON字符串。
     *
     * @param obj 待序列化生成JSON字符串的Java对象
     * @return JSON字符串
     * @throws Exception 如果序列化过程中发生错误，将抛出异常
     */
    public static String encode(Object obj) throws Exception {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * 将Java对象序列化成JSON字符串, 如果序列化失败, 则返回空字符串
     *
     * @param obj 待序列化生成JSON字符串的Java对象
     * @return JSON字符串
     */
    public static String encodeQuietly(Object obj){
    	try {
    		return objectMapper.writeValueAsString(obj);
    	} catch (Exception e) {
    		// ignore exception
    	}
    	return "";
    }

    /**
     * 过滤Java对象。
     *
     * @param source  需要处理的java对象
     * @param includes  需要保留的节点
     * @param excludes  需要过滤的节点
     * @param filterId  过滤器编码
     * @return Object 类型为source对应的类型
     * @throws Exception
     */
    public static Object filter(Object source, String[] includes,
                                String[] excludes, String filterId) throws Exception {
        if(source == null) {
            return null;
        }
        if(ArrayUtils.isEmpty(includes) && ArrayUtils.isEmpty(excludes)) {
            return source;
        }

        boolean hadFilter = false;
        String jsonResult = null;
        if(ArrayUtils.isNotEmpty(includes)) {
            SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider().addFilter(filterId,
                    SimpleBeanPropertyFilter.filterOutAllExcept(includes));
            jsonResult = includeObjectMapper.writer(simpleFilterProvider).writeValueAsString(source);
            hadFilter = true;
        }

        if(ArrayUtils.isNotEmpty(excludes)) {
            SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider().addFilter(filterId,
                    SimpleBeanPropertyFilter.serializeAllExcept(excludes));
            excludeObjectMapper.getSerializationConfig().addMixInAnnotations(Object.class, source.getClass());
            Object target = source;
            if(hadFilter) {
                target = decode(jsonResult, source.getClass());
            }
            jsonResult = excludeObjectMapper.writer(simpleFilterProvider).writeValueAsString(target);
            hadFilter = true;
        }

        return hadFilter ? decode(jsonResult, source.getClass()) : source;
    }
}
