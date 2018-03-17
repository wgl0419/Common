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
 * time   : 2018/03/13
 * desc   : ToolbarActivity
 */
public abstract class ToolbarActivity extends BaseActivity {

    protected AppBarLayout appBarLayout;
    protected Toolbar toolbar;
    protected RelativeLayout toolbarContainer;
    protected FrameLayout container;

    @SuppressLint("ResourceType")
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

    protected boolean showHomeAsUp() {
        return true;
    }

    protected String getToolbarTitle() {
        return null;
    }

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
                toolbar.setTitle(title);
        } else {
            TextView tvToolbarTitle = (TextView) View.inflate(this, R.layout.toolbar_title, null);
            title = title == null ? getString(R.string.app_name) : title;
            tvToolbarTitle.setText(title);
            setToolbarContainer(tvToolbarTitle, true);
            outValue = new TypedValue();
            getTheme().resolveAttribute(R.attr.titleTextColor,
                    outValue, true);
            try {
                Field field = Toolbar.class.getDeclaredField("mTitleTextView");
                field.setAccessible(true);
                TextView mTitleTextView = (TextView) field.get(toolbar);
                tvToolbarTitle.setTextColor(mTitleTextView.getCurrentTextColor());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void setToolbarContainer(View view) {
        setToolbarContainer(view, false);
    }

    protected void setToolbarContainer(View view, boolean showHomeAsUp) {
        toolbar.setTitle("");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayShowTitleEnabled(false);
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
