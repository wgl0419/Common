package com.chhd.android.common.global;

import android.app.Application;

import com.chhd.android.common.util.CommonUtils;
import com.facebook.stetho.Stetho;

/**
 * author : 葱花滑蛋
 * time   : 2017/11/24
 * desc   : BaseApplication
 */

public class BaseApplication extends Application {

    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        CommonUtils.init(this);

        Stetho.initializeWithDefaults(this);
    }

    public static BaseApplication getApplication() {
        return application;
    }
}
