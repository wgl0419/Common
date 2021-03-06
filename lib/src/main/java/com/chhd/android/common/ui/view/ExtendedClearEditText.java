package com.chhd.android.common.ui.view;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * 扩展clearTextChangedListeners方法
 *
 * @author : 葱花滑蛋 (2018/11/8)
 */
public class ExtendedClearEditText extends ClearEditText {

    private ArrayList<TextWatcher> mListeners = null;

    public ExtendedClearEditText(Context ctx) {
        super(ctx);
    }

    public ExtendedClearEditText(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
    }

    public ExtendedClearEditText(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        if (mListeners == null) {
            mListeners = new ArrayList<TextWatcher>();
        }
        mListeners.add(watcher);

        super.addTextChangedListener(watcher);
    }

    @Override
    public void removeTextChangedListener(TextWatcher watcher) {
        if (mListeners != null) {
            int i = mListeners.indexOf(watcher);
            if (i >= 0) {
                mListeners.remove(i);
            }
        }

        super.removeTextChangedListener(watcher);
    }

    public void clearTextChangedListeners() {
        if (mListeners != null) {
            for (TextWatcher watcher : mListeners) {
                super.removeTextChangedListener(watcher);
            }

            mListeners.clear();
            mListeners = null;
        }
    }

    public void setOnTextChangedListener(TextWatcher watcher) {
        clearTextChangedListeners();
        if (mListeners == null) {
            mListeners = new ArrayList<TextWatcher>();
        }
        mListeners.add(watcher);

        super.addTextChangedListener(watcher);
    }
}

