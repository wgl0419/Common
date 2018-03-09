package com.chhd.android.common.util;


import android.text.TextUtils;
import android.widget.TextView;

/**
 * author : 葱花滑蛋
 * time   : 2018/03/09
 * desc   : 字符串工具类
 */
public class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return TextUtils.isEmpty(charSequence);
    }

    public static boolean isEmpty(TextView textView) {
        return isEmpty(textView.getText());
    }

    public static boolean isNotEmpty(CharSequence charSequence) {
        return !TextUtils.isEmpty(charSequence);
    }

    public static boolean isNotEmpty(TextView textView) {
        return isNotEmpty(textView.getText());
    }

    /* --------------------------扩展方法-------------------------- */

    public static boolean isEmpty(TextView textView, String errorMsg) {
        boolean isEmpty = isEmpty(textView);
        if (isEmpty) {
            textView.requestFocus();
            ShakeUtils.on(textView);
            ToastUtils.show(errorMsg);
        }
        return isEmpty;
    }
}
