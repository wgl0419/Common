package com.chhd.android.common.util;

import android.view.MotionEvent;
import android.view.View;

/**
 * author : 葱花滑蛋
 * date   : 2018/12/24
 * desc   : 滑动工具类
 */

public class SlideHelper {

    private SlideHelper() {
    }

    /**
     * 处理子View与父View的滑动冲突，请求父View不要拦截触摸事件，由子View完全消费
     *
     * @param view 子View
     */
    public static void requestParentDisallowInterceptTouchEvent(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }
}
