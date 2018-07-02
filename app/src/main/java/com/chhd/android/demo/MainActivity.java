package com.chhd.android.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.chhd.android.common.ui.activity.H5Activity;
import com.chhd.android.common.ui.activity.base.ToolbarActivity;

import java.util.HashMap;
import java.util.TreeMap;

public class MainActivity extends ToolbarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_web).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                H5Activity.start(instance, R.style.AppTheme_Light, "https://fir.im/b1ce");
            }
        });
    }
}
