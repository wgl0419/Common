package com.chhd.android.common.util;

import android.content.Context;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * author : 葱花滑蛋
 * time   : 2018/11/30
 * desc   : 抖动工具类，产生一个左右抖动动画，与StringUtils产生联动效果
 */
public class ShakeUtils {

    private ShakeUtils() {
    }

    public static void on(View view) {
        Context context = view.getContext();
        TranslateAnimation animation = new TranslateAnimation(0, dp2px(context, 3),
                0, 0);
        animation.setDuration(500);
        animation.setInterpolator(new CycleInterpolator(3));
        view.startAnimation(animation);
    }

    private static int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
