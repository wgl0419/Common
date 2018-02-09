package com.chhd.android.common.ui.view.compound;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用于有CompoundButton子类的ViewGroup，点击ViewGroup控制CompoundButton开关
 */
public class CompoundLinearLayout extends LinearLayout implements ViewTreeObserver.OnGlobalLayoutListener
        , View.OnClickListener {

    private List<CompoundButton> compoundButtonList = new ArrayList<>();
    private CompoundButton compoundButton;

    public CompoundLinearLayout(Context context) {
        this(context, null);
    }

    public CompoundLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompoundLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /**
     * 拦截触摸事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    /**
     * ViewGroup的OnLayout完成回调监听
     */
    @Override
    public void onGlobalLayout() {
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
        loopThroughChildView(this);
        if (compoundButtonList.isEmpty() || compoundButtonList.size() > 1) {
            throw new RuntimeException("compoundButtonList: " + compoundButtonList.size() + ", ViewGroup can only have one CompoundButton.");
        } else {
            compoundButton = compoundButtonList.get(0);
        }
        if (isClickable()) {
            setOnClickListener(this);
        } else {
            compoundButton.setEnabled(false);
        }
    }

    /**
     * 迭代遍历子View，寻找CompoundButton的子类
     */
    private void loopThroughChildView(View parent) {
        if (parent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) parent;
            int childCount = viewGroup.getChildCount();
            for (int i = 0, len = childCount; i < len; i++) {
                View child = viewGroup.getChildAt(i);
                if (child instanceof CompoundButton) {
                    compoundButtonList.add((CompoundButton) child);
                } else {
                    loopThroughChildView(child);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        compoundButton.setChecked(!compoundButton.isChecked());
    }
}
