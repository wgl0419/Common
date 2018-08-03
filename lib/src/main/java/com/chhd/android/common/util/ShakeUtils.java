package com.chhd.android.common.util;

import android.content.Context;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * 抖动工具类
 *
 * @author : 葱花滑蛋 (2017/11/30)
 */
public class ShakeUtils {

    private ShakeUtils() {
    }

    /**
     * 产生一个左右抖动动画
     *
     * @param view 被作用的控件
     */
    public static void on(View view) {
        Context context = view.getContext();
        TranslateAnimation animation = new TranslateAnimation(0, UiUtils.dp2px(context, 3),
                0, 0);
        animation.setDuration(500);
        animation.setInterpolator(new CycleInterpolator(3));
        view.startAnimation(animation);
    }
}
