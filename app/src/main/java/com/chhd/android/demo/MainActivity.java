package com.chhd.android.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chhd.android.common.util.ToastUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToastUtils.showLong("hello");
    }
}
