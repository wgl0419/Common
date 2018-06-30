package com.chhd.android.common.util;

import android.text.TextUtils;
import android.widget.TextView;

import java.math.BigDecimal;

/**
 * NumberUtils
 *
 * @author : 葱花滑蛋 (2018/03/09)
 */

public class NumberUtils {

    private NumberUtils() {
    }

    public static int getInt(CharSequence value) {
        try {
            if (!TextUtils.isEmpty(value)) {
                return Integer.parseInt(value.toString());
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public static int getInt(TextView textView) {
        return getInt(textView);
    }

    public static double getDouble(CharSequence value) {
        try {
            if (!TextUtils.isEmpty(value)) {
                return Double.parseDouble(value.toString());
            }
        } catch (Exception e) {

        }
        return 0.0;
    }

    public static double getDouble(TextView textView) {
        return getDouble(textView.getText());
    }

    public static String getString(CharSequence value, int scale) {
        try {
            if (!TextUtils.isEmpty(value)) {
                double d = Double.parseDouble(value.toString());
                BigDecimal decimal = new BigDecimal(d);
                return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
            }
        } catch (Exception e) {

        }
        return "0";
    }

    public static String getString(TextView textView, int scale) {
        return getString(textView.getText(), scale);
    }

    public static String getString(double value, int scale) {
        return getString(Double.toString(value), scale);
    }
}
