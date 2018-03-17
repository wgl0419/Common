package com.chhd.android.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.chhd.android.common.ui.activity.H5Activity;
import com.chhd.android.common.ui.activity.base.ToolbarActivity;

public class MainActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(instance, Demo1Activity.class));
            }
        });

        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(instance, Demo2Activity.class));
            }
        });

        findViewById(R.id.tv3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(instance, Demo3Activity.class));

                H5Activity.start(instance, R.style.AppTheme, "", null);

//                setToolbarTitle("abc");
            }
        });

//        findViewById(R.id.tv3).setOnTouchListener(this);
//        findViewById(R.id.card1).setOnTouchListener(this);
    }

    @Override
    public int getContainerResId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 10, 0, "菜单1");
        menu.add(0, 11, 0, "菜单2");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected String getToolbarTitle() {
        return "hello";
    }
}
