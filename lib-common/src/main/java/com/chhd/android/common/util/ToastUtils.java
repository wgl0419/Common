package com.chhd.android.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.chhd.android.common.global.BaseApplication;


/**
 * Created by 葱花滑蛋 on 2017/11/30.
 */

public class ToastUtils {

    private static Toast toast;

    private ToastUtils() {
    }

    private static Context getContext() {
        return CommonUtils.getApplication();
    }

    private static String getString(int resId) {
        return getContext().getString(resId);
    }

    public static void showShort(int resId) {
        showShort(getString(resId));
    }

    @SuppressLint("ShowToast")
    public static void showShort(CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }

    public static void showLong(int resId) {
        showLong(getString(resId));
    }

    @SuppressLint("ShowToast")
    public static void showLong(CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        }
        toast.setText(text);
        toast.show();
    }

    /* --------------------------扩展方法-------------------------- */

    @SuppressLint("ShowToast")
    public static void showShort(CharSequence text, View shakeView) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
        if (shakeView != null) {
            shakeView.requestFocus();
            ShakeUitls.on(shakeView);
        }
    }

    @SuppressLint("ShowToast")
    public static void showLong(CharSequence text, View shakeView) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        }
        toast.setText(text);
        toast.show();
        if (shakeView != null) {
            shakeView.requestFocus();
            ShakeUitls.on(shakeView);
        }
    }
}
