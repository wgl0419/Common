package com.chhd.android.common.http;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截请求参数
 *
 * @author : 葱花滑蛋 (2018/03/12)
 */

public class BasicParamsInterceptor implements Interceptor {

    private final String TAG = this.getClass().getSimpleName();

    private Map<String, String> headers = new HashMap<>();

    private BasicParamsInterceptor() {
    }

    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        if (!headers.isEmpty()) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("Request Headers\r\n");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
                logBuilder.append(entry.getKey() + " = " + entry.getValue() + "\r\n");
            }
            Log.d(TAG, logBuilder.toString());
        }
        Request request = builder.build();
        return chain.proceed(request);
    }

    public static class Builder {

        private BasicParamsInterceptor interceptor;

        public Builder() {
            interceptor = new BasicParamsInterceptor();
        }

        public Builder addHeaderParam(String key, String value) {
            interceptor.headers.put(key, value);
            return this;
        }

        public Builder addHeaderParamsMap(Map<String, String> headers) {
            interceptor.headers.putAll(headers);
            return this;
        }

        public BasicParamsInterceptor build() {
            return interceptor;
        }
    }
}
