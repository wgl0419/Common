package com.chhd.android.common.util;

import android.app.Application;

import com.chhd.android.common.BuildConfig;
import com.chhd.android.common.http.interceptor.LogInterceptor;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

/**
 * 使用Util包下工具类，必须先初始化
 *
 * @author : 葱花滑蛋 (2018/01/27)
 */
public class CommonUtils {

    private static Application instance;

    private CommonUtils() {
    }

    public static void init(Application application) {
        if (instance == null) {
            instance = application;
            // chrome://inspect/#devices
            if (BuildConfig.DEBUG) {
                Stetho.initializeWithDefaults(instance);
            }
        }
    }

    static Application getApplication() {
        if (instance == null) {
            throw new NullPointerException("Please extend BaseApplication, or CommonUtils.init()");
        }
        return instance;
    }
}
