package com.chhd.android.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.chhd.android.common.global.BaseApplication;

/**
 * 吐司工具类
 *
 * @author : 葱花滑蛋 (2017/11/30)
 */

public class ToastUtils {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static Toast toast;

    private ToastUtils() {
    }

    private static Context getContext() {
        return CommonUtils.getApplication();
    }

    private static String getString(int resId) {
        return getContext().getString(resId);
    }

    public static void show(int resId) {
        showLong(getString(resId));
    }

    public static void show(CharSequence text) {
        showLong(text);
    }

    public static void showShort(int resId) {
        showShort(getString(resId));
    }

    @SuppressLint("ShowToast")
    public static void showShort(final CharSequence text) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
                }
                toast.setText(text);
                toast.show();
            }
        });
    }

    public static void showLong(int resId) {
        showLong(getString(resId));
    }

    @SuppressLint("ShowToast")
    public static void showLong(final CharSequence text) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
                }
                toast.setText(text);
                toast.show();
            }
        });
    }
}
