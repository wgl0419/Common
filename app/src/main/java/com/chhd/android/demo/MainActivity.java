package com.chhd.android.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.Utils;
import com.chhd.android.common.ui.activity.ToolbarActivity;


public class MainActivity extends ToolbarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_compound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CompoundActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_sp_utils).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SpUtilsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_fragment_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FragmentListActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.btn_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProgressDemoActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ListDemoActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_clear_edit_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ClearEditTextActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_click_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ClickBgActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_decoration_grid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, RvGridActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_decoration_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, RvListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PopupActivity.class);
                startActivity(intent);
            }
        });
    }
}
