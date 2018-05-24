package com.chhd.android.common.ui.view;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chhd.android.common.R;
import com.facebook.stetho.common.LogUtil;

import java.lang.reflect.Field;

/**
 * @author : 葱花滑蛋
 * @date :  2018/03/19
 */

public class Toolbar extends android.support.v7.widget.Toolbar {

    private final String TAG = this.getClass().getSimpleName();

    private RelativeLayout toolbarContainer;
    private TextView toolbarTitle;

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.toolbar_container, null);
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.actionBarSize, outValue, true);
        int height = (int) outValue.getDimension(getResources().getDisplayMetrics());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, height);
        params.gravity = Gravity.CENTER;
        addView(view, params);

        toolbarContainer = view.findViewById(R.id.toolbar_container);
        toolbarTitle = view.findViewById(R.id.toolbar_title);

        if (getBackground() != null && getBackground() instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) getBackground();
            // 如果Toolbar是白色背景
            if (colorDrawable.getColor() == -1) {
                int color = ContextCompat.getColor(getContext(), R.color.color_text_dark);
                // 左侧是黑色返回箭头
                setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
                if (getNavigationIcon() != null) {
                    getNavigationIcon().mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }
                // 底部增加一条灰色的分割线
                setBackgroundResource(R.drawable.bg_white_line_bottom);
                // 右则溢出图标是黑色
                Drawable drawable = ContextCompat.getDrawable(getContext(),
                        R.drawable.ic_more_vert_black_24dp);
                setOverflowIcon(drawable);
                if (getOverflowIcon() != null) {
                    getOverflowIcon().mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }
                if (toolbarTitle != null) {
                    toolbarTitle.setTextColor(color);
                }
            }
        }
    }

    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getString(resId));
    }

    @Override
    public void setTitle(CharSequence title) {

        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.actionBarTitleGravity,
                outValue, true);

        if (Gravity.CENTER != outValue.data) {
            super.setTitle(title);
        } else {
            if (toolbarTitle != null) {
                toolbarTitle.setText(title);
            }
        }
    }

    public void setToolbarContainer(View view) {
        setTitle("");
        if (view.getLayoutParams() == null) {
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(layoutParams);
        }
        toolbarContainer.removeAllViews();
        toolbarContainer.addView(view);
        setContentInsetsAbsolute(0, 0);
    }
}
