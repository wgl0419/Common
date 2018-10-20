package com.chhd.android.common.util;

import android.text.TextUtils;
import android.widget.TextView;

import java.math.BigDecimal;

/**
 * 数字类型工具类
 *
 * @author : 葱花滑蛋 (2018/03/09)
 */
public class NumberUtils {

    private static final int MATH_TYPE_ADD = 0;
    private static final int MATH_TYPE_SUB = 1;
    private static final int MATH_TYPE_MUL = 2;
    private static final int MATH_TYPE_DIV = 3;

    private NumberUtils() {
    }

    /* -------------------------- “字符串”转“数字” -------------------------- */

    public static int toInt(CharSequence value) {
        try {
            if (StringUtils.isNotBlank(value)) {
                if (value.toString().contains(".")) {
                    return (int) Double.parseDouble(value.toString());
                } else {
                    return Integer.parseInt(value.toString());
                }
            }
        } catch (Exception ignored) {
        }
        return 0;
    }

    public static int toInt(TextView value) {
        if (StringUtils.isBlank(value)) {
            return 0;
        }
        return toInt(value.getText());
    }

    public static int toInt(Number value) {
        if (value == null) {
            return 0;
        }
        return value.intValue();
    }

    public static long toLong(CharSequence value) {
        try {
            if (!TextUtils.isEmpty(value)) {
                return Long.parseLong(value.toString());
            }
        } catch (Exception ignored) {

        }
        return 0L;
    }

    public static long toLong(TextView value) {
        if (StringUtils.isBlank(value)) {
            return 0L;
        }
        return toLong(value.getText());
    }

    public static long toLong(Number value) {
        if (value == null) {
            return 0;
        }
        return value.longValue();
    }

    public static float toFloat(CharSequence value) {
        try {
            if (StringUtils.isNotBlank(value)) {
                return Float.parseFloat(value.toString());
            }
        } catch (Exception ignored) {

        }
        return 0f;
    }

    public static float toFloat(TextView value) {
        if (StringUtils.isBlank(value)) {
            return 0f;
        }
        return toFloat(value.getText());
    }

    public static float toFloat(Number value) {
        if (value == null) {
            return 0;
        }
        return value.floatValue();
    }

    public static float toFloat(Float value, int scale) {
        try {
            BigDecimal decimal = new BigDecimal(value.toString());
            return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
        } catch (Exception ignored) {
        }
        return 0f;
    }

    public static double toDouble(CharSequence value) {
        try {
            if (StringUtils.isNotBlank(value)) {
                return Double.parseDouble(value.toString());
            }
        } catch (Exception ignored) {
        }
        return 0;
    }

    public static double toDouble(TextView value) {
        if (StringUtils.isBlank(value)) {
            return 0;
        }
        return toDouble(value.getText());
    }

    public static double toDouble(Number value) {
        if (value == null) {
            return 0;
        }
        return value.doubleValue();
    }

    public static double toDouble(Double value, int scale) {
        try {
            BigDecimal decimal = new BigDecimal(value.toString());
            return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception ignored) {
        }
        return 0;
    }

    /* -------------------------- “数字”转“字符串” -------------------------- */

    public static String toString(CharSequence value, int scale) {
        BigDecimal decimal;
        try {
            if (!TextUtils.isEmpty(value)) {
                decimal = new BigDecimal(value.toString());
            } else {
                decimal = new BigDecimal("0");
            }
        } catch (Exception ignored) {
            decimal = new BigDecimal("0");
        }
        if (scale >= 0) {
            return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        } else {
            if (decimal.doubleValue() == 0.0) {
                return "0";
            }
            scale = Math.abs(scale);
            return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP)
                    .stripTrailingZeros().toPlainString();
        }
    }

    public static String toString(CharSequence value) {
        if (StringUtils.isBlank(value)) {
            return "0";
        }
        return value.toString();
    }

    public static String toString(TextView value, int scale) {
        if (StringUtils.isBlank(value)) {
            return toString("0", scale);
        }
        return toString(value.getText(), scale);
    }

    public static String toString(TextView value) {
        if (StringUtils.isBlank(value)) {
            return "0";
        }
        return value.getText().toString();
    }

    public static String toString(Number value, int scale) {
        if (value == null) {
            return toString("", scale);
        }
        return toString(value.toString(), scale);
    }

    public static String toString(Number value) {
        if (value == null) {
            return "0";
        }
        return value.toString();
    }

    /* -------------------------- 加减乘除 -------------------------- */

    public static BigDecimal add(Number... args) {
        return math(MATH_TYPE_ADD, args);
    }

    public static BigDecimal sub(Number... args) {
        return math(MATH_TYPE_SUB, args);
    }

    public static BigDecimal mul(Number... args) {
        return math(MATH_TYPE_DIV, args);
    }

    public static BigDecimal div(Number... args) {
        return math(MATH_TYPE_DIV, args);
    }

    private static BigDecimal math(int type, Number... args) {
        if (args == null) {
            return new BigDecimal(String.valueOf(0.0));
        }
        BigDecimal bigDecimal = null;
        for (Number arg : args) {
            if (arg == null) {
                arg = 0;
            }
            if (bigDecimal == null) {
                bigDecimal = new BigDecimal(arg.toString());
            } else {
                if (type == MATH_TYPE_ADD) {
                    bigDecimal = bigDecimal.add(new BigDecimal(arg.toString()));
                } else if (type == MATH_TYPE_SUB) {
                    bigDecimal = bigDecimal.subtract(new BigDecimal(arg.toString()));
                } else if (type == MATH_TYPE_MUL) {
                    bigDecimal = bigDecimal.multiply(new BigDecimal(arg.toString()));
                } else if (type == MATH_TYPE_DIV) {
                    bigDecimal = bigDecimal.divide(new BigDecimal(arg.toString()), BigDecimal.ROUND_HALF_UP);
                }
            }
        }
        assert bigDecimal != null;
        return bigDecimal;
    }
}
