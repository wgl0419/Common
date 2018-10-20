package com.chhd.android.demo;

import com.blankj.utilcode.util.Utils;
import com.chhd.android.common.global.BaseApplication;

/**
 * @author : 葱花滑蛋 (2018/7/2)
 */

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
    }
}
