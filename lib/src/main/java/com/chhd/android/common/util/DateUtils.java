package com.chhd.android.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author : 葱花滑蛋 (2018/7/19)
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

    public static String toString(long fromMillis) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN_DATE, Locale.getDefault());
        return format.format(new Date(fromMillis));
    }

    public static String toString(long fromMillis, String toPattern) {
        SimpleDateFormat format = new SimpleDateFormat(toPattern, Locale.getDefault());
        return format.format(new Date(fromMillis));
    }

    public static String toString(String from) {
        return toString(from, PATTERN_DATE);
    }

    public static String toString(String from, String toPattern) {
        return toString(from, PATTERN_DEFAULT_FULL, toPattern);
    }

    public static String toString(String from, String fromPattern, String toPattern) {
        String s;
        try {
            SimpleDateFormat format = new SimpleDateFormat(fromPattern, Locale.getDefault());
            Date date = format.parse(from);
            format = new SimpleDateFormat(toPattern, Locale.getDefault());
            s = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            s = "未知时间";
        }
        return s;
    }

    /* -------------------------- 转日期”毫秒值“ -------------------------- */

    public static long toLong(String from) {
        return toLong(from, PATTERN_DEFAULT_FULL);
    }

    public static long toLong(String from, String toPattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(toPattern, Locale.getDefault());
            return format.parse(from).getTime();
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

    public static String getToday(String toPattern) {
        SimpleDateFormat format = new SimpleDateFormat(toPattern, Locale.getDefault());
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

    public static String getYesterday(String toPattern) {
        return getLastDays(1, toPattern);
    }

    /**
     * 获取本周的第一天
     *
     * @return 默认”yyyy-MM-dd“
     */
    public static String getWeekFirstDay() {
        return getWeekFirstDay(PATTERN_DATE);
    }

    public static String getWeekFirstDay(String toPattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat format = new SimpleDateFormat(toPattern, Locale.getDefault());
        return format.format(calendar.getTime());
    }

    public static String getWeekFirstDay(String toPattern, String endDate) {
        return getWeekFirstDay(toPattern, endDate, PATTERN_DATE);
    }

    public static String getWeekFirstDay(String toPattern, String endDate, String fromPattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(fromPattern, Locale.getDefault());
            Date date = format.parse(endDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            format = new SimpleDateFormat(toPattern, Locale.getDefault());
            return format.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取上个月的今天
     *
     * @return 默认”yyyy-MM-dd“
     */
    public static String getBeforeMonthToday() {
        return getBeforeMonthToday(PATTERN_DATE);
    }

    public static String getBeforeMonthToday(String toPattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat(toPattern, Locale.getDefault());
        return format.format(calendar.getTime());
    }

    public static String getBeforeMonthToday(String toPattern, String endDate) {
        return getBeforeMonthToday(toPattern, endDate, PATTERN_DATE);
    }

    public static String getBeforeMonthToday(String toPattern, String endDate, String fromPattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(fromPattern, Locale.getDefault());
            Date date = format.parse(endDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -1);
            format = new SimpleDateFormat(toPattern, Locale.getDefault());
            return format.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取上个月的第一天
     *
     * @return 默认”yyyy-MM-dd“
     */
    public static String getBeforeMonthFirstDay() {
        return getBeforeMonthFirstDay(PATTERN_DATE);
    }

    public static String getBeforeMonthFirstDay(String toPattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat(toPattern, Locale.getDefault());
        return format.format(calendar.getTime());
    }

    public static String getBeforeMonthFirstDay(String toPattern, String endDate) {
        return getBeforeMonthFirstDay(toPattern, endDate, PATTERN_DATE);
    }

    public static String getBeforeMonthFirstDay(String toPattern, String endDate, String fromPattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(fromPattern, Locale.getDefault());
            Date date = format.parse(endDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            format = new SimpleDateFormat(toPattern, Locale.getDefault());
            return format.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取上个月的最后一天
     *
     * @return 默认”yyyy-MM-dd“
     */
    public static String getBeforeMonthLastDay() {
        return getBeforeMonthLastDay(PATTERN_DATE);
    }

    public static String getBeforeMonthLastDay(String toPattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat format = new SimpleDateFormat(toPattern, Locale.getDefault());
        return format.format(calendar.getTime());
    }

    public static String getBeforeMonthLastDay(String toPattern, String endDate) {
        return getBeforeMonthLastDay(toPattern, endDate, PATTERN_DATE);
    }

    public static String getBeforeMonthLastDay(String toPattern, String endDate, String fromPattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(fromPattern, Locale.getDefault());
            Date date = format.parse(endDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            format = new SimpleDateFormat(toPattern, Locale.getDefault());
            return format.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取前7天日期
     *
     * @return 默认”yyyy-MM-dd“
     */
    public static String getLast7Days() {
        return getLast7Days(PATTERN_DATE);
    }

    public static String getLast7Days(String toPattern) {
        return getLastDays(7, toPattern);
    }

    /**
     * 获取前30天日期
     *
     * @return 默认”yyyy-MM-dd“
     */
    public static String getLast30Days() {
        return getLast30Days(PATTERN_DATE);
    }

    public static String getLast30Days(String toPattern) {
        return getLastDays(30, toPattern);
    }

    private static String getLastDays(int days, String toPattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);
        SimpleDateFormat format = new SimpleDateFormat(toPattern, Locale.getDefault());
        return format.format(calendar.getTime());
    }

    public static String getLastDays(int days, String toPattern, String endDate) {
        return getLastDays(days, toPattern, endDate, PATTERN_DATE);
    }

    public static String getLastDays(int days, String toPattern, String endDate, String fromPattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(fromPattern, Locale.getDefault());
            Date date = format.parse(endDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -days);
            format = new SimpleDateFormat(toPattern, Locale.getDefault());
            return format.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
