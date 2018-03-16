package com.chhd.android.demo;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.chhd.android.common.ui.activity.ToolbarActivity;
import com.chhd.android.common.util.UiUtils;

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

}
