package com.chhd.android.common.util;

import android.content.Context;
import android.content.res.Resources;

import com.chhd.android.common.global.BaseApplication;

/**
 * @author : 葱花滑蛋
 * @date : 2017/11/30
 */

public class UiUtils {

    private UiUtils() {
    }

    public static Context getContext() {
        return CommonUtils.getApplication();
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static String getString(int resId) {
        return getContext().getString(resId);
    }

    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    public static int getColor(int id) {
        return getResources().getColor(id);
    }

    public static int dp2px(float dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
