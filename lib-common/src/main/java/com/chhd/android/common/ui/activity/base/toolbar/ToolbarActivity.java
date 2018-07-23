package com.chhd.android.common.ui.activity.base.toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.chhd.android.common.R;
import com.chhd.android.common.ui.activity.base.BaseActivity;
import com.chhd.android.common.ui.view.Toolbar;
import com.chhd.android.common.util.UiUtils;

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

        initActionBarHeight();

        toolbar.setTitle(getToolbarTitle());

        setToolbar(toolbar, getToolbarTitle(), showHomeAsUp());
    }

    /**
     * 根据是否沉浸式状态初始化菜单栏高度
     */
    private void initActionBarHeight() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
            return;
        }
        int flags = getWindow().getAttributes().flags;
        boolean b = (flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (b) {
            int height = UiUtils.getStatusBarHeight(this);
            appBarLayout.setPadding(0, height, 0, 0);
        }
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
    public final void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, container, true);
    }
}
