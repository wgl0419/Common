package com.chhd.android.common.util;

import android.annotation.SuppressLint;
import android.app.Application;

/**
 * 使用Util包下工具类，必须先初始化
 *
 * @author : 葱花滑蛋 (2018/01/27)
 */

public class CommonUtils {

    @SuppressLint("StaticFieldLeak")
    private static Application instance;

    private CommonUtils() {
    }

    public static void init(Application application) {
        instance = application;
    }

    static Application getApplication() {
        if (instance == null) {
            throw new NullPointerException("Please extend BaseApplication, or CommonUtils.init()");
        }
        return instance;
    }
}
