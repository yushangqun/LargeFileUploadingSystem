package com.notary.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期转换类
 * 
 * @author csc修改
 * 
 */
public final class DateUtil {
	private static Log logger = LogFactory.getLog(DateUtil.class);

	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	public static final SimpleDateFormat SDF = new SimpleDateFormat(
			DEFAULT_DATE_PATTERN);

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isBlank(String s) {
		return (s == null || s.trim().length() == 0);
	}

	/**
	 * 判断字符串是否不为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotBlank(String s) {
		return !isBlank(s);
	}

	/**
	 * 将字符串传换成日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static Date parse(String date) {
		return parse(date, null);
	}

	/**
	 * 将字符串按format格式转换成日期形式
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date parse(String date, String format) {
		if (isBlank(date))
			return null;

		SimpleDateFormat formatter = SDF;
		if (isNotBlank(format) && !DEFAULT_DATE_PATTERN.equals(format))
			formatter = new SimpleDateFormat(format);

		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			logger.error("Parse date " + date + " java.util.Date Error!", e);
			return null;
		}
	}

	/**
	 * 将日期转换成字符串
	 * 
	 * @param d
	 * @return
	 */
	public static String format(Date d) {
		return format(d, null);
	}

	/**
	 * 将日期按format格式转换成字符串
	 * 
	 * @param d
	 * @param format
	 * @return
	 */
	public static String format(Date d, String format) {
		if (d == null)
			return "";

		SimpleDateFormat formatter = SDF;
		if (isNotBlank(format) && !DEFAULT_DATE_PATTERN.equals(format))
			formatter = new SimpleDateFormat(format);

		return formatter.format(d);
	}
	
	/**
	 * 获取当前月内最大一天的日期
	 * @param cal 当前时间
	 * @param format 输出格式
	 * @return
	 */
	public static String currentMonthMaxDate(Calendar cal,String format){
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String maxCurrent = DateUtil.format(cal.getTime(),format);
		return maxCurrent;
	}
	
	/**
	 * 获取当前月内最小一天的日期
	 * @param cal 当前时间
	 * @param format 输出格式
	 * @return
	 */
	public static String currentMonthMinDate(Calendar cal,String format){
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		String minCurrent = DateUtil.format(cal.getTime(),format);
		return minCurrent;
	}
	
	/**
	 * 获取上个月内最大一天的日期
	 * @param cal 当前时间
	 * @param format 输出格式
	 * @return
	 */
	public static String preMonthMaxDate(Calendar cal,String format){
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String maxCurrent = DateUtil.format(cal.getTime(),format);
		return maxCurrent;
	}
	
	/**
	 * 获取上个月内最小一天的日期
	 * @param cal 当前时间
	 * @param format 输出格式
	 * @return
	 */
	public static String preMonthMinDate(Calendar cal,String format){
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		String minCurrent = DateUtil.format(cal.getTime(),format);
		return minCurrent;
	}
	
	/**
	 * 获取当前周内的第一天
	 * @param cal 当前时间
	 * @param format 输出格式
	 * @return
	 */
	public static String currentWeekMinDate(Calendar cal,String format){
		cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
		String minWeek = DateUtil.format(cal.getTime(),format);
		return minWeek;
	}
	
	/**
	 * 获取当前周内的最后一天
	 * @param cal 当前时间
	 * @param format 输出格式
	 * @return
	 */
	public static String currentWeekMaxDate(Calendar cal,String format){
		cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
		String maxWeek = DateUtil.format(cal.getTime(),format);
		return maxWeek;
	}
	
	/**
	 * 获取时分秒格式时间
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
//		String a  = DateUtil.format(new Date(),"yyyy-MM-dd");
//		String b = a.substring(0, 8)+"01";
//		System.out.println("月初："+b);
//		
//		Calendar cal = Calendar.getInstance();
//		System.out.println(DateUtil.currentMonthMaxDate(cal, "yyyy-MM-dd"));
	}
}
