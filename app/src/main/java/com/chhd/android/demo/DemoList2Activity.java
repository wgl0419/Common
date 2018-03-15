package com.chhd.android.demo;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import com.chhd.android.common.ui.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class DemoList2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_list2);

        ViewPager viewPager = findViewById(R.id.view_pager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new BlankFragment());
        fragments.add(new BlankFragment());
        fragments.add(new BlankFragment());

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments));
    }
}
