package com.chhd.android.common.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.chhd.android.common.R;

/**
 * 带清除按钮的输入框
 *
 * @author : 陈伟强 (2018/5/25)
 */
public class ClearEditText extends android.support.v7.widget.AppCompatEditText
        implements TextWatcher {

    private final String TAG = this.getClass().getSimpleName();

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private int iconId;

    private void init(Context context, AttributeSet attrs) {
        addTextChangedListener(this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);
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
    }

    @Override
    public void afterTextChanged(Editable s) {
        initClearIcon(s);
    }

    private void initClearIcon(final CharSequence s) {
        post(new Runnable() {
            @Override
            public void run() {
                if (isEnabled()) {
                    if (TextUtils.isEmpty(s)) {
                        setCompoundDrawables(null, null, null, null);
                    } else {
                        // 如果EditText可用，并且内容不为空，设置删除按钮
                        Drawable drawable = ContextCompat.getDrawable(getContext(), iconId);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                                drawable.getMinimumHeight());
                        setCompoundDrawables(null, null, drawable, null);
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
            }
        }
        return super.onTouchEvent(event);
    }
}
