package com.chhd.android.common.ui.view;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chhd.android.common.R;

import java.lang.reflect.Field;

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

        View view = View.inflate(getContext(), R.layout.toolbar_container, null);
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.actionBarSize, outValue, true);
        int height = (int) outValue.getDimension(getResources().getDisplayMetrics());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, height);
        params.gravity = Gravity.CENTER;
        addView(view, params);

        toolbarContainer = view.findViewById(R.id.toolbar_container);
        toolbarTitle = view.findViewById(R.id.toolbar_title);
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
            toolbarTitle.setText(title);

            try {
                Field field = android.support.v7.widget.Toolbar.class.getDeclaredField("mTitleTextView");
                field.setAccessible(true);
                TextView mTitleTextView = (TextView) field.get(this);
                toolbarTitle.setTextColor(mTitleTextView.getCurrentTextColor());
            } catch (Exception e) {
                e.printStackTrace();
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
