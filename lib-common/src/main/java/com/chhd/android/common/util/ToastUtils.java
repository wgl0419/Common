package com.chhd.android.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.chhd.android.common.global.BaseApplication;


/**
 * Created by congh on 2017/11/30.
 */

public class ToastUtils {

    private static Toast toast;

    private ToastUtils() {
    }

    private static Context getContext() {
        return BaseApplication.getApplication();
    }

    @SuppressLint("ShowToast")
    public static void showShort(CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }

    @SuppressLint("ShowToast")
    public static void showShort(int resId) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT);
        }
        toast.setText(resId);
        toast.show();
    }

    @SuppressLint("ShowToast")
    public static void showLong(CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }

    @SuppressLint("ShowToast")
    public static void showLong(int resId) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT);
        }
        toast.setText(resId);
        toast.show();
    }
}
