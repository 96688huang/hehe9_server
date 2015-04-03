package cn.hehe9.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期相关工具类
 * @copyright 优视科技 2013 版权所有
 * @author  <a href="mailto:zhongxn@ucweb.com">Nic Zhong</a>
 * @version v1.0.0
 */
public class DateUtil {

    /** 常规日期格式，24小时制格式  **/
    public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public static final long ONE_HOUR = 1000*60*60;
    
    /**
     * 取得表示当天的时间对象
     * 
     * @return
     */
    public static Date getCurrentDay() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }
    
    /**
     * 解析简单格式的日期yyyy-MM-dd HH:mm字符串
     * @param simpleDateStr
     * @return
     */
    public static Date parseSimpleForMinute(String simpleDateStr){
        if(StringUtils.isEmpty(simpleDateStr))
            return null;
        try {
            DateFormat simpleDateFormatForMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return simpleDateFormatForMinute.parse(simpleDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 返回某个时间的"yyyy-MM-dd HH:mm"字符串
     * @param time
     * @return
     */
    public static String getTimeStringForMinute(Long time){
        DateFormat simpleDateFormatForMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormatForMinute.format(new Date(time));
    }
    
    /**
     * 解析简单格式yyyy-MM-dd HH:mm:ss的日期字符串
     * @param simpleDateStr
     * @return
     */
    public static Date parseSimple(String simpleDateStr){
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(StringUtils.isEmpty(simpleDateStr))
            return null;
        try {
            return simpleDateFormat.parse(simpleDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 返回某个时间的"yyyy-MM-dd HH:mm:ss"字符串
     * @param time
     * @return
     */
    public static String getTimeString(Long time){
        final DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(time));
    }
    
    /**
     * 返回日期的微秒数
     * @param date
     * @return
     */
    public static Long getTimeLong (Date date){
        if (date == null) {
            return null;
        }
        return date.getTime();
    }
    
    /**
     * 获取当前的时间
     *
     * @return
     */
    public static Date getCurrentTime() {
        return new Date();
    }

    /**
     * 获取当前的时间
     *
     * @return
     */
    public static String formatCurrentTime(String patten) {
        return formatDateToString(new Date(), patten);
    }

    /**
     * 将时间按格式转换为字符串，日期为空时转换为空字符串
     *
     * @param date
     * @param patten
     * @return
     */
    public static String formatDateToString(Date date, String patten) {
        if (null == date)
            return "";
        SimpleDateFormat sd = new SimpleDateFormat(patten);
        return sd.format(date);
    }
    
    /**
     * 将时间按24小时制格式("yyyy-MM-dd HH:mm:ss")转换为字符串，日期为空时转换为空字符串
     *
     * @param date
     * @return
     */
    public static String formatDateNormal(Date date) {
        if (null == date)
            return "";
        SimpleDateFormat sd = new SimpleDateFormat(NORMAL_DATE_FORMAT);
        return sd.format(date);
    }
    
    /**
     * 获取当天string。
     * @param date
     * @return
     */
    public static String getDayString(Date date) {
    	String format = "yyyy-MM-dd";
        SimpleDateFormat sd = new SimpleDateFormat(format);
        return sd.format(date);
    }
    
    /**
     * 将时间按24小时制格式("yyyy-MM-dd HH:mm:ss")转换为字符串，日期为空时转换为空字符串
     *
     * @param date
     * @return
     */
    public static String formatDateNormal(Long date) {
        if (null == date)
            return "";
        SimpleDateFormat sd = new SimpleDateFormat(NORMAL_DATE_FORMAT);
        return sd.format(new Date(date));
    }

    /**
     * 功能描述：将字符串按格式转换为时间，字符串为空时转换为null
     *
     * @param dateStr 时间字符串
     * @param patten 格式
     * @return
     * @author <a href="mailto:zengzx@ucweb.com">曾钟贤 </a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2012-5-2
     */
    public static Date formatStrToDate(String dateStr, String patten) {
        if (null == dateStr || "".equals(dateStr))
            return null;
        SimpleDateFormat sd = new SimpleDateFormat(patten);
        try {
            Date date = sd.parse(dateStr);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }
    
    /**
     * 将字符串按24小时制格式("yyyy-MM-dd HH:mm:ss")转换为时间
     * @param dateStr 时间字符串
     * @return 转换成的时间，字符串为空时转换为null
     */
    public static Date formatStrToNormalDate(String dateStr) {
        return formatStrToDate(dateStr, NORMAL_DATE_FORMAT);
    }

    /**
     * 获得按时间字符time(格式 "HH:mm:ss")转换的日期date
     * @param date
     * @param timeString 格式 "HH:mm:ss"
     * @return 非法返回null
     */
    public static Date getDateByTimeString(Date date, String timeString) {
        timeString = formatDateToString(date, String.format("yyyy-MM-dd %s", timeString));
        return formatStrToDate(timeString, NORMAL_DATE_FORMAT);
    }
    
    /**
     * 功能描述：获取对应日期的开始时间
     *
     * @param date
     * @return
     */
    public static Date getDayStart(Date date) {
        String dateStartString = formatDateToString(date, "yyyy-MM-dd 00:00:00");
        return formatStrToDate(dateStartString, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 功能描述：获取对应日期的结束时间
     *
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date) {
        String dateStartString = formatDateToString(date, "yyyy-MM-dd 23:59:59 999");
        return formatStrToDate(dateStartString, "yyyy-MM-dd HH:mm:ss SSS");
    }


    public static Date getDay(Date date) {
        String dateStartString = formatDateToString(date, "yyyy-MM-dd 00:00:00");
        return formatStrToDate(dateStartString, "yyyy-MM-dd HH:mm:ss");
    }
    
    public static Date getODay(Date date) {
    	String dateStartString = formatDateToString(date, "yyyy-MM-dd");
        return formatStrToDate(dateStartString, "yyyy-MM-dd");
    }

    /**
     * 功能描述：获取昨天
     *
     * @return
     * @author <a href="mailto:zhengzs@ucweb.com">郑志升</a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2012-8-15
     */
    public static Date getYesterday() {
        return getDayBefore(1);
    }

    /**
     * 功能描述：获取前day天的日期
     *
     * @param day
     * @return
     * @author <a href="mailto:zhengzs@ucweb.com">郑志升</a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2012-8-15
     */
    public static Date getDayBefore(Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - day);
        return calendar.getTime();
    }
    /**
     * 功能描述：获取后几年的日期.
     * @param date 修改前的日期
     * @param year 调整的年数
     * @return Date日期
     */
    public static Date getYearAfter(Date date,Integer year){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year);
        return calendar.getTime();
    }

    public static Date getDayBefore(Date date, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - day);
        return calendar.getTime();
    }

    public static Date getDayAfter(Date date,Integer day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
        return calendar.getTime();
    }
    
    public static Date getSecondAfter(Date date,Integer seconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND,calendar.get(Calendar.SECOND)+ seconds);
        return calendar.getTime();
    }
    
    public static Date getSecondBefore(Date date,Integer seconds){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(Calendar.SECOND,calendar.get(Calendar.SECOND) - seconds);
    	return calendar.getTime();
    }
    
    public static Date getMinuteAfter(Date date,Integer minutes){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE)+ minutes);
        return calendar.getTime();
    }
    
    public static Date getMinuteBefore(Date date,Integer minutes){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE) - minutes);
    	return calendar.getTime();
    }

    public static Date getHourAfter(Date date,Integer hour){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
        return calendar.getTime();
    }
    
    public static Date getHourBefore(Date date,Integer hour){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
        return calendar.getTime();
    }
    
    public static Date getMonthBefore(Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - month);
        calendar.add(Calendar.MONTH, - month);
        return calendar.getTime();
    }

    public static Date getMonthBefore(Date date, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // 跨年有问题??
        //calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - month);
        calendar.add(Calendar.MONTH, - month);
        return calendar.getTime();
    }

    public static Date getMonthAfter(Date date, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // 跨年有问题??
        //calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }
    /***
     *
     * 功能描述：获得当月，如2012-08
     *
     * @return
     * @author <a href="mailto:zhengzs@ucweb.com">郑志升</a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2012-8-17
     */
    public static String getMonth() {
        String dateStartString = formatDateToString(new Date(), "yyyy-MM");
        return dateStartString;
    }

    public static Date getLastMonth() {
        return getMonthBefore(1);
    }

    public static boolean isMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH) == 1;
    }

    public static boolean isMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_MONTH) == 1;
    }
    /**
     * 功能描述：获取对应日期的月头
     *
     * @param date
     * @return
     * @author <a href="mailto:zhengzs@ucweb.com">郑志升</a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2012-8-15
     */
    public static Date getMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * 获取昨天的开始时间
     * @param date
     * @return
     */
    public static Date getYesterdayStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * 获取昨天的结束时间
     * @return
     */
    public static Date getYesterdayEnd() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
    	calendar.set(Calendar.HOUR_OF_DAY, 23);
    	calendar.set(Calendar.MINUTE, 59);
    	calendar.set(Calendar.SECOND, 59);
    	calendar.set(Calendar.MILLISECOND, 999);
    	return calendar.getTime();
    }
    
    /**
     * 功能描述：获取对应日期的月尾
     *
     * @param date
     * @return
     * @author <a href="mailto:zhengzs@ucweb.com">郑志升</a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2012-8-15
     */
    public static Date getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static int getMonthDiff(Date bigDate, Date smallDate) {
        if (bigDate.compareTo(smallDate) <= 0) {
            return -1;
        }
        Calendar calInstance = Calendar.getInstance();
        calInstance.setTime(bigDate);
        int year1 = calInstance.get(Calendar.YEAR);
        int month1 = calInstance.get(Calendar.MONTH);
        calInstance.setTime(smallDate);
        int year2 = calInstance.get(Calendar.YEAR);
        int month2 = calInstance.get(Calendar.MONTH);
        return (year1 - year2) * 12 + month1 - month2;
    }

    /**
     * 获得天数差
     * @param bigDate
     * @param smallDate
     * @return 异常返回 -1
     */
    public static int getDayDiff(Date bigDate, Date smallDate) {
        if (bigDate == null || smallDate == null) {
            return -1;
        }
        return getDayDiff(bigDate.getTime(), smallDate.getTime());
    }
    
    /**
     * 获得天数差
     * @param bigTime
     * @param smallTime
     * @return 异常返回 -1
     */
    public static int getDayDiff(Long bigTime, Long smallTime) {
        if (bigTime == null || smallTime == null || bigTime < smallTime) {
            return -1;
        }
        Long diff = (bigTime - smallTime) / 86400000;
        return diff.intValue();
    }
    
    /**
     * 返回某个时间的"yyyy-MM-dd 00:00:00"字符串
     * @param time
     * @return
     */
    public static String getTimeZeroString(Long time){
        DateFormat simpleDateTimeZeroFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        return simpleDateTimeZeroFormat.format(new Date(time));
    }
    
	public static int compareDate(Date date1, Date date2) {
		if (date1.getTime() > date2.getTime()) {
			return 1;
		} else if (date1.getTime() < date2.getTime()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	/**
	 * 计算两个时间相差的秒数.
	 * @param lastTime 上次查询时间
	 * @param currentTime 当前时间
	 * @return
	 */
	public static int getIntervalSecond(Long lastTime, Long currentTime) {
	       long intervalMilli = currentTime - lastTime;
	       return (int) (intervalMilli / (1000));
	}
	
	/**
	 * 计算两个时间相差的小时数.
	 * @param lastTime 上次查询时间
	 * @param currentTime 当前时间
	 * @return
	 */
	public static int getIntervalHour(Long lastTime, Long currentTime) {
	       long intervalMilli = currentTime - lastTime;
	       return (int) (intervalMilli / ONE_HOUR);
	}
	
	/**
	 * 获取当前时间，日期格式为默认的yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT);
        return sdf.format(new Date());
	}
	
	
	/**
	 * 毫秒数转换为常见日期格式的字符串，如yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static String getDateString(Long time) {
		SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT);
		return sdf.format(new Date(time));
	}
}
