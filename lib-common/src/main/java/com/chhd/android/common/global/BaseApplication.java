package com.chhd.android.common.global;

import android.app.Application;

import com.chhd.android.common.util.CommonUtils;
import com.facebook.stetho.Stetho;

/**
 * BaseApplication
 *
 * @author : 葱花滑蛋 (2017/11/24)
 */
public class BaseApplication extends Application {

    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        CommonUtils.init(this);
    }

    public static BaseApplication getApplication() {
        return application;
    }
}
