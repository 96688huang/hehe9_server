package cn.hehe9.common.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bouncycastle.util.Strings;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * 实用工具类
 * @copyright 优视科技 2013 版权所有
 * @author  fengqx
 * @author  <a href="mailto:zhongxn@ucweb.com">Nic Zhong</a>
 * @version v1.0.0
 */
public class Utility {
    
    /**
     * 获得对象Json格式
     * @param o
     * @return
     */
    public static String getJson(Object o) {
        if (o == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(o);
    }
    
    /**
     * 解析json数据
     *
     * @param json
     * @return
     */
    public static JsonObject parseJson(String json) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject)parser.parse(json);
            return jsonObject;
        } catch (JsonSyntaxException ex) {
            return null;
        }
    }
    
    /**
     * 解析为JSON元素对象
     *
     * @param data
     * @return
     */
    public static JsonObject parse(String data) {
        JsonObject ret;
        try {
            ret = (new JsonParser()).parse(data).getAsJsonObject();
        } catch (Exception e) {
            return null;
        }
        return ret;
    }

    /**
     * 功能描述：将对象转换为Json格式的字符串
     *
     * @param json 待转换的对象
     * @return Json格式的字符串
     * @author <a href="mailto:zengzx@ucweb.com">曾钟贤 </a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2012-5-2
     */
    public static String formatJson(Object json) {

        Gson parser = new Gson();
        String jsonString = parser.toJson(json);
        return jsonString;
    }
    
    /**
     * *
     * <p/>
     * 功能描述：判断字符串是不是数字
     *
     * @param str
     * @return
     * @author <a href="mailto:zhengzs@ucweb.com">郑志升</a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2012-8-13
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 功能描述：根据路径名创建目录
     *
     * @param filePath 路径名
     * @return 当创建目录成功后，返回true,否则返回false.
     */
    public static boolean createDir(String filePath) {
        boolean isDone = false;
        File file = new File(filePath);
        if (file.exists())
            throw new RuntimeException("FileDirectory: " + filePath + " is already exist");
        isDone = file.mkdirs();
        return isDone;
    }
    
    /**
     * 判断字符串是否是null或者空字符串
     * @param text
     * @return
     * @deprecated  改用<tt>org.apache.commons.lang.StringUtils.isblank(text)</tt>
     */
    public static boolean isNullOrEmpty(String text) {
        return text == null || text.trim().length() == 0;
    }

    /**
     * 判断数值是否是null或者0
     *
     * @param i
     * @return
     */
    public static boolean isNullOrEmpty(Integer i) {
        return i == null || i.equals(0);
    }
    
    /**
     * 判断数值是否是null或者0L
     *
     * @param l
     * @return
     */
    public static boolean isNullOrEmpty(Long l) {
        return l == null || l.equals(0L);
    }

    /**
     * 判断字符串是否是超长的
     *
     * @param text 要判断的字符串，其中汉字是当成2个字节长度（GBK码）处理
     * @param maxLength 最大的长度
     * @return
     */
    public static Boolean isOverLength(String text, int maxLength) {
        if (Utility.isNullOrEmpty(text))
            return false;

        try {
            return (text.getBytes("gbk").length > maxLength);
        } catch (UnsupportedEncodingException e) {
            return text.length() > maxLength;
        }
    }
    
    /**
     * 截取过长字符 按字节截取
     *
     * @param inputStr
     * @param len
     * @param appendStr
     * @param removeHtml 是否要去除HTML标记
     * @return
     */
    public static String stringLeft(String inputStr, int len, String appendStr, Boolean removeHtml) {
        if (isNullOrEmpty(inputStr) || len <= 0)
            return "";

        if (removeHtml) {
            inputStr = removeHtml(inputStr);
        }

        inputStr = inputStr.trim();
        if (isNullOrEmpty(appendStr))
            appendStr = "";
        int leftLen = len - appendStr.length();
        int countLen = 0;
        int charCount = 0;

        char[] cArray = inputStr.toCharArray();
        for (char c : cArray) {
            if (c >= 0 && c < 255) {
                countLen++;
            } else {
                countLen += 2;
            }
            if (countLen > leftLen) {
                break;
            }
            charCount++;
        }
        if (charCount == inputStr.length()) {
            return inputStr; // 不需要裁剪
        } else {
            return inputStr.substring(0, charCount) + appendStr;
        }
    }

    /**
     * 去除HTML标记
     *
     * @param str
     * @return
     */
    public static String removeHtml(String str) {
        Pattern pattern = Pattern.compile("<[^>]*>|&nbsp;");
        Matcher matcher = pattern.matcher(str);
        str = matcher.replaceAll("");
        return str;
    }

    public static boolean getBoolean(Boolean value) {
        return (value != null && value) ? true : false;
    }

    /**
     * 格式化文件的大小
     *
     * @param size 文件的大小，单位：字节
     * @return 文件的大小表示形式，如 :102KB, 23MB
     */
    public static String formatFileSize(Integer size) {
        DecimalFormat df = new DecimalFormat("0.00");
        if (size < 1024) {
            return size + "bytes";
        } else if (size < (1024 * 1024)) {
            return df.format(size / 1024.0) + "KB";
        } else {
            return df.format(size / (1024.0 * 1024.0)) + "MB";
        }
    }

    /**
     * 转为整数值
     *
     * @param value
     * @param replacement
     * @return
     */
    public static int convertToInt(String value, int replacement) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return replacement;
        }
    }
    
    /**
     * List减法
     *
     * @param srcList 被减数
     * @param desList 减数
     * @param <T>
     * @return
     */
    public static <T extends Object> List<T> minusList(List<T> srcList, List<T> desList) {
        if (null == srcList || srcList.isEmpty()) {
            return new ArrayList<T>();
        }
        if (null == desList || desList.isEmpty()) {
            return srcList;
        }
        List<T> resultList = new ArrayList<T>();
        for (T src : srcList) {
            if (!desList.contains(src)) {
                resultList.add(src);
            }
        }
        return resultList;
    }

    /**
     * 获取文件的扩展名
     *
     * @param fileName 文件名，可带路径
     * @return 返回带"."号的文件扩展名，如".jpg"，如果没有扩展名则返回空字符值
     */
    public static String getFileExtensionName(String fileName) {
        if (Utility.isNullOrEmpty(fileName))
            return "";
        int p = fileName.lastIndexOf('.');
        if (p != -1)
            return fileName.substring(p);
        return "";
    }

    /**
     * 判断某个文件是否属于某些格式文件
     *
     * @param fileName 文件地址
     * @param exts 有效的文件扩展名，如"jpg;.jpeg;.png"
     * @return
     */
    public static Boolean isValidFiles(String fileName, String exts) {
        if (Utility.isNullOrEmpty(fileName))
            return false;

        String ext = getFileExtensionName(fileName);
        if (!Utility.isNullOrEmpty(ext)) {
            String[] extArray = exts.split(";");
            for (String e : extArray) {
                if (ext.equalsIgnoreCase(e)) {
                    return true;
                }
            }
            return false;
        } else {
            return Utility.isNullOrEmpty(exts);
        }
    }

    /**
     * 生成一个与时间有关的随机序号，长度是20位
     *
     * @return 样例：201103151201323c1f05
     */
    public static String createTimeRndSequnceCode() {
        Date time = new Date();
        StringBuffer buffer = new StringBuffer();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        buffer.append(dateFormat.format(time));

        // 增加随机值
        String uuid = Codec.UUID().substring(0, 6);
        buffer.append(uuid);
        return buffer.toString();
    }

    /**
     * 将列表的字符串形式(以,分隔)解析为长整形数组
     * @param values
     * @return
     */
    public static Long[] toLongArray(String values){
        if(values != null){
            String[] idSegs = Strings.split(values, ',');
            Long[] idArray = new Long[idSegs.length];
            for(int i=0; i<idSegs.length; i++){
                idArray[i] = Long.parseLong(idSegs[i]);
            }
            return idArray;
        }
        return new Long[0];
    }

    /**
     * 将列表的字符串形式(以,分隔)切割为字符串数组
     * @param values
     * @return
     */
    public static String[] toStringArray(String values){
        if(values != null){
            return Strings.split(values, ',');
        }
        return new String[0];
    }

    /**
     * 将用户ID数组拼装为字符串形式，以,分隔
     * @param userIds
     * @return
     */
    public static String joinLongArray(Long[] userIds){
        StringBuilder sb = new StringBuilder();
        if(userIds != null){
            for(int i=0; i<userIds.length; i++){
                sb.append(userIds[i]);
                if (i < userIds.length - 1) {
                    sb.append(',');
                }
            }
        }
        return sb.toString();
    }

    /**
     * 将两个数组进行合并
     * @param array1
     * @param array2
     * @return
     */
    public static Object[] combile(Object[] array1, Object[] array2){
        List<Object> list = new ArrayList<Object>();
        if (array1 != null) {
            list.addAll(Arrays.asList(array1));
        }
        if (array2 != null) {
            list.addAll(Arrays.asList(array2));
        }
        return list.toArray();
    }

    /**
     * 针对多值查询的便利方法，可根据数组长度构造出
     *        可在preparedStatement语句中使用的参数占位符字符串
     * @param values
     * @return
     */
    public static String buildParamMarks(Object[] values){
        StringBuilder sb = new StringBuilder();
        int length = values != null? values.length: 0;
        for(int i=0; i<length; i++){
            sb.append('?');
            if (i < length - 1) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    public static Integer getInt(String key, Map<String, String> map){
        if(map.containsKey(key)){
            try{
                return Integer.parseInt(map.get(key));
            }catch(NumberFormatException e){
                throw new IllegalArgumentException(String.format("参数%s必须是整数", key));
            }
        }
        return null;
    }

    public static Long getLong(String key, Map<String, String> map){
        if(map.containsKey(key)){
            try{
                return Long.parseLong(map.get(key));
            }catch(NumberFormatException e){
                throw new IllegalArgumentException(String.format("参数%s必须是整数", key));
            }
        }
        return null;
    }

    public static String getString(String key, Map<String, String> map){
        if(map.containsKey(key)){
            return map.get(key);
        }
        return null;
    }

    /**
     * 判断字符串是否是null或者空字符串
     * @param str
     * @return
     * @deprecated  改用<tt>org.apache.commons.lang.StringUtils.isblank(text)</tt>
     */
    public static Boolean isEmptyString(String str){
        if (str == null || str.length() == 0){
            return true;
        }
        return false;
    }
    
    /**
     * 校验电话号码
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }
}