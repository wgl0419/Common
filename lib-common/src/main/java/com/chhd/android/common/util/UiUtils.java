package com.chhd.android.common.util;

import android.content.Context;
import android.content.res.Resources;


/**
 * Ui工具类
 *
 * @author : 葱花滑蛋 (2017/11/30)
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
        return dp2px(getContext(), dp);
    }

    public static int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    public static int getStatusBarHeight() {
        return getStatusBarHeight(getContext());
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height",
                "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
