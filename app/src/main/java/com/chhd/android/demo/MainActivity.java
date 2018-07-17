package com.chhd.android.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chhd.android.common.ui.activity.H5Activity;
import com.chhd.android.common.ui.activity.base.ToolbarActivity;
import com.chhd.android.common.util.NumberUtils;
import com.chhd.android.common.util.SpUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends ToolbarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_clear_edit_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, ClearEditTextActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_click_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, ClickBgActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_decoration_grid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, RvGridActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_decoration_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, RvListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, PopupActivity.class);
                startActivity(intent);
            }
        });

        Map<Float, Integer> map = new HashMap<>();
        map.put(1.1f, 1);
        map.put(1.2f, 2);
        map.put(1.3f, 3);
        Log.i("debug-app", "" + map);
        SpUtils.put("bb", map);
        Map<Float, Integer> a = SpUtils.get("bb");
        Log.i("debug-app", "" + a);
    }
}
