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
 * 应用于有CompoundButton子View的ViewGroup，点击ViewGroup控制CompoundButton开关
 *
 * @author : 葱花滑蛋 (2018/03/12)
 */
public class CompoundRelativeLayout extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener
        , View.OnClickListener {

    private List<CompoundButton> checkList = new ArrayList<>();

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
        compoundButton = getCompoundButton();
        if (isClickable()) {
            setOnClickListener(this);
        } else {
            compoundButton.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        compoundButton.toggle();
        if (onCompoundClickListener != null) {
            onCompoundClickListener.onCompoundClick(compoundButton, compoundButton.isChecked());
        }
    }

    public boolean isChecked() {
        compoundButton = getCompoundButton();
        return compoundButton.isChecked();
    }

    public void setChecked(boolean isChecked) {
        compoundButton = getCompoundButton();
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

    private CompoundButton getCompoundButton() {
        if (compoundButton == null) {
            checkList.clear();
            findCompoundButton(this);
            if (checkList.isEmpty() || checkList.size() > 1) {
                throw new RuntimeException("ViewGroup must have one CompoundButton, " +
                        "and count can not more than one");
            }
            compoundButton = checkList.get(0);
        }
        return compoundButton;
    }

    private void findCompoundButton(View parent) {
        if (parent == null) {
            return;
        }
        if (parent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) parent;
            int childCount = viewGroup.getChildCount();
            for (int i = childCount - 1; i >= 0; i--) {
                View child = viewGroup.getChildAt(i);
                findCompoundButton(child);
            }
        }
        if (parent instanceof CompoundButton) {
            checkList.add((CompoundButton) parent);
        }
    }
}
