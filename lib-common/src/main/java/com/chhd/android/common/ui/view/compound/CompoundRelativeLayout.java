package com.chhd.android.common.ui.view.compound;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用于有CompoundButton子类的ViewGroup，点击ViewGroup控制CompoundButton开关，务必加上android:clickable="true"，建议加上android:focusable="true"
 *
 * @author : 葱花滑蛋
 * @date : 2018/03/12
 */

public class CompoundRelativeLayout extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener
        , View.OnClickListener {

    private CompoundButton compoundButton;
    private OnCompoundClickListener onCompoundClickListener;

    public CompoundRelativeLayout(Context context) {
        this(context, null);
    }

    public CompoundRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompoundRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        compoundButton = findViewWithTag("compound");
        if (isClickable()) {
            setOnClickListener(this);
        } else {
            compoundButton.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        compoundButton.setChecked(!compoundButton.isChecked());
        if (onCompoundClickListener != null) {
            onCompoundClickListener.onCompoundClick(compoundButton, compoundButton.isChecked());
        }
    }

    public boolean isChecked() {
        if (compoundButton == null) {
            compoundButton = findViewWithTag("compound");
        }
        return compoundButton.isChecked();
    }

    public void setChecked(boolean isChecked) {
        if (compoundButton == null) {
            compoundButton = findViewWithTag("compound");
        }
        compoundButton.setChecked(isChecked);
    }

    public void setOnCompoundClickListener(OnCompoundClickListener onCompoundClickListener) {
        this.onCompoundClickListener = onCompoundClickListener;
    }

    public interface OnCompoundClickListener {

        /**
         * 点击事件
         *
         * @param compoundButton 子类compoundButton
         * @param isChecked      isChecked
         */
        void onCompoundClick(CompoundButton compoundButton, boolean isChecked);
    }
}
