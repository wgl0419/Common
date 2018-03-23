package com.chhd.android.common.ui.activity.base;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chhd.android.common.R;
import com.chhd.android.common.util.UiUtils;

import java.lang.reflect.Field;

/**
 * author : 葱花滑蛋
 * date   : 2018/03/13
 * desc   :
 */

public abstract class ToolbarActivity extends BaseActivity {

    protected AppBarLayout appBarLayout;
    protected Toolbar toolbar;
    protected RelativeLayout toolbarContainer;
    protected TextView toolbarTitle;
    protected FrameLayout container;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        appBarLayout = findViewById(R.id.app_bar_layout);
        toolbar = findViewById(R.id.toolbar);
        toolbarContainer = findViewById(R.id.toolbar_container);
        toolbarTitle = findViewById(R.id.toolbar_title);
        container = findViewById(R.id.container);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp());
        }

        initToolbarTitle(getToolbarTitle());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue outValue = new TypedValue();
            getTheme().resolveAttribute(R.attr.actionBarElevation,
                    outValue, true);
            float elevation = outValue.getDimension(getResources().getDisplayMetrics());
            elevation = getAppBarLayoutElevation() == -1 ? elevation : getAppBarLayoutElevation();
            setDefaultAppBarLayoutStateListAnimator(appBarLayout, elevation);
        }

        if (getContainerResId() != 0)
            LayoutInflater.from(this).inflate(getContainerResId(), container, true);

    }

    /**
     * 是否显示Home键
     */
    protected boolean showHomeAsUp() {
        return true;
    }

    /**
     * 获取菜单栏标题
     */
    protected String getToolbarTitle() {
        return null;
    }

    /**
     * 设置菜单栏标题
     */
    protected void setToolbarTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            initToolbarTitle(title);
        }
    }

    private void initToolbarTitle(CharSequence title) {
        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.actionBarTitleGravity,
                outValue, true);
        if (Gravity.CENTER != outValue.data) {
            if (title != null)
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(title);
                }
        } else {
            toolbar.setTitle("");
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("");
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }

            title = title == null ? getString(R.string.app_name) : title;
            toolbarTitle.setText(title);
        }
    }

    /**
     * 设置菜单栏布局
     */
    protected void setToolbarContainer(View view) {
        setToolbarContainer(view, false);
    }

    /**
     * 设置菜单栏布局
     */
    protected void setToolbarContainer(View view, boolean showHomeAsUp) {
        toolbar.setTitle("");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        if (view.getLayoutParams() == null) {
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(layoutParams);
        }
        toolbarContainer.removeAllViews();
        toolbarContainer.addView(view);

        if (!showHomeAsUp)
            toolbar.setContentInsetsAbsolute(0, 0);
    }

    public abstract int getContainerResId();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setDefaultAppBarLayoutStateListAnimator(final View view, final float elevation) {
        final StateListAnimator sla = new StateListAnimator();
        sla.addState(new int[0],
                ObjectAnimator.ofFloat(view, "elevation", elevation).setDuration(0));
        view.setStateListAnimator(sla);
    }

    /**
     * 设置AppBarLayout的底部阴影
     */
    @SuppressLint("PrivateResource")
    protected float getAppBarLayoutElevation() {
        return -1;
    }
}
