package com.chhd.android.common.util;

import android.app.Application;

/**
 * Created by 葱花滑蛋 on 2018/1/27.
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
            throw new RuntimeException("Please extend BaseApplication, or CommonUtils.init()");
        }
        return instance;
    }
}
