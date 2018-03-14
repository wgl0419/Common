package com.chhd.android.common.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chhd.android.common.R;
import com.chhd.android.common.util.UiUtils;

/**
 * author : 葱花滑蛋
 * time   : 2018/03/13
 * desc   : ToolbarActivity
 */
public abstract class ToolbarActivity extends BaseActivity {

    protected AppBarLayout appBarLayout;
    protected Toolbar toolbar;
    protected RelativeLayout toolbarContainer;
    protected FrameLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        appBarLayout = findViewById(R.id.app_bar_layout);
        toolbar = findViewById(R.id.toolbar);
        toolbarContainer = findViewById(R.id.toolbar_container);
        container = findViewById(R.id.container);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(setDisplayHomeAsUpEnabled());

            if (!TextUtils.isEmpty(setToolbarTitle()))
                getSupportActionBar().setTitle(setToolbarTitle());
        }

        if (getContainerResId() != 0)
            LayoutInflater.from(this).inflate(getContainerResId(), container, true);

        if (Build.VERSION.SDK_INT >= 21) {
            setDefaultAppBarLayoutStateListAnimator(appBarLayout, setAppBarLayoutElevation());
        }
    }

    protected boolean setDisplayHomeAsUpEnabled() {
        return true;
    }

    protected String setToolbarTitle() {
        return "";
    }

    protected void setToolbarContainer(View view) {
        setToolbarContainer(view, false);
    }

    protected void setToolbarContainer(View view, boolean showHomeAsUp) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbarContainer.removeAllViews();
        toolbarContainer.addView(view);

        if (!showHomeAsUp)
            toolbar.setContentInsetsAbsolute(0, 0);
    }

    public abstract int getContainerResId();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


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
    protected float setAppBarLayoutElevation() {
        return UiUtils.dp2px(android.support.design.R.styleable.AppBarLayout_elevation);
    }
}
