package com.chhd.android.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import com.chhd.android.common.ui.activity.H5Activity;
import com.chhd.android.common.ui.activity.base.ToolbarActivity;
import com.chhd.android.common.ui.view.Toolbar;

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

        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.actionBarSize,
                outValue, true);
        Log.i(TAG, "onCreate: " + outValue.getDimension(getResources().getDisplayMetrics()));

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setBackgroundColor(Color.GREEN);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(50, RelativeLayout.LayoutParams.MATCH_PARENT);
//        relativeLayout.setLayoutParams(layoutParams);
//        setToolbarContainer(relativeLayout);

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
//        toolbar.setToolbarContainer(relativeLayout);
        toolbar.setTitle("hello");
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
