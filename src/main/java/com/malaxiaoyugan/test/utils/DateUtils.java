package com.malaxiaoyugan.test.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期操作类
 */
public class DateUtils {

	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	/**
	 * 获取YYYY-MM-DD hh:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	public static String getTimestamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	private static final String DEFAULT_CONVERT_PATTERN = "yyyyMMddHHmmssSSS";

	/**
	 * 获取当前时间字符串(默认格式:yyyyMMddHHmmssSSS)
	 *
	 * @return
	 */
	public static String getCurrentTimeStrDefault() {
		return getCurrentTimeStr(DEFAULT_CONVERT_PATTERN);
	}

	/**
	 * 获取指定时间字符串(默认格式:yyyyMMddHHmmssSSS)
	 * @param date
	 * @return
	 */
	public static String getTimeStrDefault(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_CONVERT_PATTERN);
		return dateFormat.format(date);
	}

	/**
	 * 获取当前时间字符串
	 *
	 * @param pattern 转换格式
	 * @return
	 */
	public static String getCurrentTimeStr(String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(new Date());
	}

	/**
	 * 获取指定时间字符串
	 * @param date
	 * @return
	 */
	public static String getTimeStr(Date date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 判断时间字符串是否为默认格式
	 * @param dateTimeStr
	 * @return
	 */
	public static boolean isValidDefaultFormat(String dateTimeStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_CONVERT_PATTERN);
		try {
			dateFormat.parse(dateTimeStr);
			return true;
		} catch (Exception e) {
			// 如果抛出异常，说明格式不正确
			return false;
		}
	}
}
