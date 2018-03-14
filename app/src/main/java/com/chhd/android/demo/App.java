package com.chhd.android.demo;

import com.chhd.android.common.global.BaseApplication;
import com.facebook.stetho.Stetho;

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
