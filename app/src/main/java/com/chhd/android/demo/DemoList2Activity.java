package com.chhd.android.demo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;

public class DemoList2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pull_to_refresh);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                swipeRefreshLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }
}
