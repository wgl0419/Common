package com.chhd.android.common.util;


import android.text.TextUtils;
import android.widget.TextView;

/**
 * 字符串工具类
 *
 * @author : 葱花滑蛋 (2018/03/09)
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

    public static boolean isBlank(CharSequence charSequence) {
        return isEmpty(charSequence.toString().trim());
    }

    public static boolean isBlank(TextView textView) {
        return isBlank(textView.getText());
    }

    public static boolean isNotBlank(CharSequence charSequence) {
        return !isBlank(charSequence);
    }

    public static boolean isNotBlank(TextView textView) {
        return isNotBlank(textView.getText());
    }

    /* -------------------------- 扩展方法 -------------------------- */

    public static boolean isEmpty(TextView textView, String errorMsg) {
        return isEmpty(textView, errorMsg, false);
    }

    public static boolean isEmpty(TextView textView, String errorMsg, boolean anim) {
        boolean isEmpty = isEmpty(textView);
        if (isEmpty) {
            if (anim) {
                ShakeUtils.on(textView);
            }
            textView.requestFocus();
            ToastUtils.show(errorMsg);
        }
        return isEmpty;
    }

    public static boolean isBlank(TextView textView, String errorMsg) {
        return isBlank(textView, errorMsg, false);
    }

    public static boolean isBlank(TextView textView, String errorMsg, boolean anim) {
        boolean isBlank = isBlank(textView);
        if (isBlank) {
            if (anim) {
                ShakeUtils.on(textView);
            }
            textView.requestFocus();
            ToastUtils.show(errorMsg);
        }
        return isBlank;
    }
}
