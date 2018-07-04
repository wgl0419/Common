package com.chhd.android.common.ui.activity.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.chhd.android.common.R;
import com.chhd.android.common.ui.view.Toolbar;

/**
 * Toolbar界面
 *
 * @author : 葱花滑蛋 (2018/03/13)
 */

public class ToolbarActivity extends BaseActivity {

    protected AppBarLayout appBarLayout;
    protected Toolbar toolbar;
    protected FrameLayout container;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_toolbar);

        appBarLayout = findViewById(R.id.app_bar_layout);
        toolbar = findViewById(R.id.toolbar);
        container = findViewById(R.id.container);

        toolbar.setTitle(getToolbarTitle());

        setToolbar(toolbar, getToolbarTitle(), showHomeAsUp());
    }

    /**
     * 是否显示Home键
     *
     * @return boolean
     */
    protected boolean showHomeAsUp() {
        return true;
    }

    /**
     * 获取菜单栏标题
     *
     * @return String
     */
    protected CharSequence getToolbarTitle() {
        return getTitle();
    }

    /**
     * 设置菜单栏标题
     *
     * @param title 标题
     */
    protected void setToolbarTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            toolbar.setTitle(title);
        }
    }

    /**
     * 设置菜单栏布局
     *
     * @param view 自定义View
     */
    protected void setToolbarContainer(View view) {
        setToolbarContainer(view, false);
    }

    /**
     * 设置菜单栏布局
     *
     * @param view         自定义View
     * @param showHomeAsUp 是否显示Home键
     */
    protected void setToolbarContainer(View view, boolean showHomeAsUp) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setToolbarContainer(view);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, container, true);
    }
}
