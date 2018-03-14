package com.chhd.android.common.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : 葱花滑蛋
 * time   : 2018/03/12
 * desc   : BasicParamsInterceptor
 */
public class BasicParamsInterceptor implements Interceptor {

    private Map<String, String> headers = new HashMap<>();

    private BasicParamsInterceptor() {
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
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
