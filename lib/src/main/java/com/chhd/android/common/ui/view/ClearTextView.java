package com.chhd.android.common.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.chhd.android.common.R;


/**
 * 带清除按钮的文本框
 *
 * @author : 葱花滑蛋 (2018/9/11)
 */
public class ClearTextView extends android.support.v7.widget.AppCompatTextView
        implements TextWatcher {


    private final String TAG = this.getClass().getSimpleName();

    public ClearTextView(Context context) {
        this(context, null);
    }

    public ClearTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public ClearTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private Drawable defStartIcon;
    private Drawable defEndIcon;
    private Drawable defTopIcon;
    private Drawable defBottomIcon;

    private int iconId;

    private void init(Context context, AttributeSet attrs) {
        addTextChangedListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            defStartIcon = getCompoundDrawablesRelative()[0];
            defEndIcon = getCompoundDrawablesRelative()[2];
            defTopIcon = getCompoundDrawablesRelative()[1];
            defBottomIcon = getCompoundDrawablesRelative()[3];
        }
        if (defStartIcon == null) {
            defStartIcon = getCompoundDrawables()[0];
        }
        if (defEndIcon == null) {
            defEndIcon = getCompoundDrawables()[2];
        }
        if (defTopIcon == null) {
            defTopIcon = getCompoundDrawables()[1];
        }
        if (defBottomIcon == null) {
            defBottomIcon = getCompoundDrawables()[3];
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearTextView);
        iconId = typedArray.getResourceId(R.styleable.ClearEditText_clearIcon,
                R.drawable.ic_clear_gray_16dp);
        typedArray.recycle();

        initClearIcon(getText());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        initClearIcon(s);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private void initClearIcon(final CharSequence s) {
        post(new Runnable() {
            @Override
            public void run() {
                if (isEnabled()) {
                    if (TextUtils.isEmpty(s)) {
                        setCompoundDrawables(defStartIcon, defTopIcon, defEndIcon, defBottomIcon);
                    } else {
                        // 如果EditText可用，并且内容不为空，设置删除按钮
                        Drawable drawable = ContextCompat.getDrawable(getContext(), iconId);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                                drawable.getMinimumHeight());
                        setCompoundDrawables(defStartIcon, defTopIcon, drawable, defBottomIcon);
                    }
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Drawable drawable = getCompoundDrawables()[2];
        if (drawable != null && event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getX() > (getWidth() - getPaddingRight() - drawable.getIntrinsicWidth())) {
                setText("");
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
}
