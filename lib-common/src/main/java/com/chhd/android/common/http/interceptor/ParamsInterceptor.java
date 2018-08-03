package com.chhd.android.common.http.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 参数拦截器
 *
 * @author : 葱花滑蛋 (2018/03/12)
 */
public class ParamsInterceptor implements Interceptor {

    private final String TAG = this.getClass().getSimpleName();

    private Map<String, String> headers = new HashMap<>();

    private ParamsInterceptor() {
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        if (!headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.build();
        return chain.proceed(request);
    }

    public static class Builder {

        private ParamsInterceptor interceptor;

        public Builder() {
            interceptor = new ParamsInterceptor();
        }

        public Builder addHeaderParam(String key, String value) {
            interceptor.headers.put(key, value);
            return this;
        }

        public Builder addHeaderParamsMap(Map<String, String> headers) {
            interceptor.headers.putAll(headers);
            return this;
        }

        public ParamsInterceptor build() {
            return interceptor;
        }
    }
}
