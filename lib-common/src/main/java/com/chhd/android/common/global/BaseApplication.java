package com.chhd.android.common.global;

import android.app.Application;

import com.chhd.android.common.util.CommonUtils;

/**
 * Created by 葱花滑蛋 on 2017/11/24.
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
