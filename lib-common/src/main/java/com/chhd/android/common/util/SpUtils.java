package com.chhd.android.common.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 软键盘工具类
 *
 * @author : 葱花滑蛋
 * @date :  2018/03/18
 */

public class SpUtils {

    private static final String NAME = "config";

    private static SharedPreferences sharedPreferences;

    private SpUtils() {
    }

    private static Context getContext() {
        return CommonUtils.getApplication();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String key, int defValue) {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getInt(key, defValue);
    }

    public static void putInt(String key, int value) {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putInt(key, value).commit();
    }

    public static long getLong(String key) {
        return getLong(key, 0L);
    }

    public static long getLong(String key, long defValue) {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getLong(key, defValue);
    }

    public static void putLong(String key, long value) {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putLong(key, value).commit();
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defValue) {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, defValue);
    }

    public static void putString(String key, String value) {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static float getFloat(String key) {
        return getFloat(key, 0);
    }

    public static float getFloat(String key, float defValue) {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getFloat(key, defValue);
    }

    public static void putDoulbe(String key, float value) {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putFloat(key, value).commit();
    }
}
