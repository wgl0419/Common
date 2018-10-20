package com.chhd.android.common.util;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Json工具类
 *
 * @author : 葱花滑蛋 (2018/7/27)
 */
public class JsonUtils {

    private JsonUtils() {
    }

    public static <T> T parse(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

    public static <T> List<T> parseList(String json, final Class<T> clazz) {
        ParameterizedType type = new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{clazz};
            }

            @Override
            public Type getRawType() {
                return List.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
        return new Gson().fromJson(json, type);
    }

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }
}
