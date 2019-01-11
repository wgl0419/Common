package com.chhd.android.demo;

import com.chhd.android.common.util.DateUtils;
import com.chhd.android.common.util.NumberUtils;

/**
 * @author : 葱花滑蛋 (2018/7/17)
 */

public class Test {

    public static void main(String[] args) {
        double d = NumberUtils.toDouble(-0.4);
        String s = String.format("本单欠款：¥%.2f", d);
        System.out.println(s);
    }
}
