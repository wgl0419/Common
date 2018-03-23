package com.chhd.android.common.util;

import android.app.Application;

/**
 * author : 葱花滑蛋
 * date   : 2018/01/27
 * desc   : 使用Util包下工具类，必须先初始化
 */

public class CommonUtils {

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
