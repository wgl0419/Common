package com.chhd.android.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author : 陈伟强 (2018/7/19)
 */

public class DateUtils {

    /**
     * SQL存储时间默认格式
     */
    public static final String PATTERN_DEFAULT_FULL = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_TIME = "HH:mm:ss";

    private DateUtils() {
    }

    /* -------------------------- 转日期”字符串“ -------------------------- */

    public static String toString(long millis, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(new Date(millis));
    }

    public static String toString(String str, String pattern) {
        return toString(str, PATTERN_DEFAULT_FULL, pattern);
    }

    public static String toString(String str, String fromPattern, String toPattern) {
        String s;
        try {
            SimpleDateFormat format = new SimpleDateFormat(fromPattern, Locale.getDefault());
            Date date = format.parse(str);
            format = new SimpleDateFormat(toPattern, Locale.getDefault());
            s = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            s = "未知时间";
        }
        return s;
    }

    /* -------------------------- 转日期”毫秒值“ -------------------------- */

    public static long toLong(String str) {
        return toLong(str, PATTERN_DEFAULT_FULL);
    }

    public static long toLong(String str, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
            return format.parse(str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /* -------------------------- 获取日期字符串，默认”yyyy-MM-dd“ -------------------------- */

    /**
     * 获取今天
     *
     * @return 默认”yyyy-MM-dd“
     */
    public static String getToday() {
        return getToday(PATTERN_DATE);
    }

    public static String getToday(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(new Date());
    }

    /**
     * 获取昨天
     *
     * @return 默认”yyyy-MM-dd“
     */
    public static String getYesterday() {
        return getYesterday(PATTERN_DATE);
    }

    public static String getYesterday(String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(calendar.getTime());
    }

    /**
     * 获取本周的第一天
     *
     * @return 默认”yyyy-MM-dd“
     */
    public static String getWeekFirstDay() {
        return getWeekFirstDay(PATTERN_DATE);
    }

    public static String getWeekFirstDay(String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(calendar.getTime());
    }

    /**
     * 获取上个月的今天
     *
     * @return 默认”yyyy-MM-dd“
     */
    public static String getBeforeMonthToday() {
        return getBeforeMonthToday(PATTERN_DATE);
    }

    public static String getBeforeMonthToday(String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(calendar.getTime());
    }

    /**
     * 获取上个月的第一天
     *
     * @return 默认”yyyy-MM-dd“
     */
    public static String getBeforeMonthFirstDay() {
        return getBeforeMonthFirstDay(PATTERN_DATE);
    }

    public static String getBeforeMonthFirstDay(String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(calendar.getTime());
    }

    /**
     * 获取上个月的最后一天
     *
     * @return 默认”yyyy-MM-dd“
     */
    public static String getBeforeMonthLastDay() {
        return getBeforeMonthLastDay(PATTERN_DATE);
    }

    public static String getBeforeMonthLastDay(String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(calendar.getTime());
    }
}
