package com.chhd.android.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

/**
 * SpUtils
 *
 * @author : 葱花滑蛋 (2018/03/18)
 */
@SuppressLint("ApplySharedPref")
public class SpUtils {

    private static final String NAME = getContext().getPackageName();

    private static SharedPreferences sharedPreferences;

    private SpUtils() {
    }

    private static Context getContext() {
        return CommonUtils.getApplication();
    }

    public static SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    /* -------------------------- 保存获取基本类型 -------------------------- */

    public static void init(String name) {
        sharedPreferences = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /* -------------------------- 保存获取基本类型 -------------------------- */

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    public static void put(String key, Boolean value) {
        if (value == null) {
            value = false;
        }
        getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String key, int defValue) {
        return getSharedPreferences().getInt(key, defValue);
    }

    public static void put(String key, Integer value) {
        if (value == null) {
            value = 0;
        }
        getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static long getLong(String key) {
        return getLong(key, 0L);
    }

    public static long getLong(String key, long defValue) {
        return getSharedPreferences().getLong(key, defValue);
    }

    public static void put(String key, Long value) {
        if (value == null) {
            value = 0L;
        }
        getSharedPreferences().edit().putLong(key, value).commit();
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defValue) {
        return getSharedPreferences().getString(key, defValue);
    }

    public static void put(String key, String value) {
        getSharedPreferences().edit().putString(key, value).commit();
    }

    public static float getFloat(String key) {
        return getFloat(key, 0);
    }

    public static float getFloat(String key, float defValue) {
        return getSharedPreferences().getFloat(key, defValue);
    }

    public static void put(String key, Float value) {
        if (value == null) {
            value = 0F;
        }
        getSharedPreferences().edit().putFloat(key, value).commit();
    }

    public static void remove(String key) {
        getSharedPreferences().edit().remove(key).commit();
    }

    public static void clear() {
        getSharedPreferences().edit().clear().commit();
    }

    /* -------------------------- 保存获取对象 -------------------------- */

    public static <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, null);
    }

    public static <T> T get(String key, Class<T> clazz, T defValue) {
        String json = getSharedPreferences().getString(key, "");
        T value = JsonUtils.parse(json, clazz);
        if (value == null) {
            value = defValue;
        }
        return value;
    }

    public static <T> List<T> getList(String key, final Class<T> clazz) {
        return getList(key, clazz, null);
    }

    public static <T> List<T> getList(String key, final Class<T> clazz, List<T> defValue) {
        String json = getSharedPreferences().getString(key, "");
        List<T> list = JsonUtils.parseList(json, clazz);
        if (list == null) {
            return defValue;
        }
        return list;
    }

    public static <T> void put(String key, T value) {
        getSharedPreferences().edit().putString(key, JsonUtils.toJson(value)).commit();
    }
}
