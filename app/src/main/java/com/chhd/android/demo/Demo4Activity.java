package com.chhd.android.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.chhd.android.common.ui.activity.base.ProgressTActivity;

public class Demo4Activity extends ProgressTActivity {

    @Override
    protected int getContentResId() {
        return R.layout.activity_demo4;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        onPageError("出错了");
    }

    @Override
    protected void onLoad() {
        Log.i(TAG, "onLoad: ");
    }
}
