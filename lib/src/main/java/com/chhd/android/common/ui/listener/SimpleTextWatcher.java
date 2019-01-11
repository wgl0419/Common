package com.chhd.android.common.ui.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * SimpleTextWatcher
 *
 * @author : 葱花滑蛋 (2018/10/20)
 */
public abstract class SimpleTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onTextChange(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 字符串变更监听
     *
     * @param s 字符串
     */
    public abstract void onTextChange(CharSequence s);
}
