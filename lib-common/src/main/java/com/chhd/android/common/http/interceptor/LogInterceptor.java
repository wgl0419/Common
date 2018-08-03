package com.chhd.android.common.http.interceptor;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 日志拦截器
 *
 * @author : 葱花滑蛋 (2018/8/2)
 */
public class LogInterceptor implements Interceptor {

    private final Charset UTF8 = Charset.forName("UTF-8");
    private final String TAG = LogInterceptor.class.getSimpleName();
    private final int MAX_LEN = 4000;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        log(chain);
        return chain.proceed(request);
    }

    private void log(Chain chain) {
        try {
            Request request = chain.request();
            Response response = null;
            Exception ex = null;
            try {
                response = chain.proceed(request);
            } catch (Exception e) {
                ex = e;
            }
            StringBuilder builder = new StringBuilder();
            builder.append("\n<-- ").append(request.method()).append("\n");
            builder.append("url:\t\t").append(request.url()).append("\n");
            builder.append(buildHeadersLog(request));
            builder.append(buildParamsLog(request));
            if (response == null) {
                builder.append(ex);
                builder.append("\n<-- END ").append(request.method()).append("\n");
                ignoreLimitLog(Log.ERROR, builder.toString());
            } else {
                builder.append(buildResultLog(response));
                builder.append("\n<-- END ").append(request.method()).append("\n");
                ignoreLimitLog(Log.DEBUG, builder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ignoreLimitLog(int priority, String msg) {
        while (!msg.isEmpty()) {
            int lastNewLine = msg.lastIndexOf('\n', MAX_LEN);
            int nextEnd = lastNewLine != -1 ? lastNewLine : Math.min(MAX_LEN, msg.length());
            String next = msg.substring(0, nextEnd);
            Log.println(priority, TAG, next);
            if (lastNewLine != -1) {
                msg = msg.substring(nextEnd + 1);
            } else {
                msg = msg.substring(nextEnd);
            }
        }
    }

    /**
     * 打印头部
     *
     * @param request request
     * @return StringBuilder
     */
    private StringBuilder buildHeadersLog(Request request) {
        StringBuilder builder = new StringBuilder();
        Headers headers = request.headers();
        if (headers == null || headers.size() == 0) {
            return builder;
        }
        for (int i = 0; i < headers.size(); i++) {
            String key = headers.name(i);
            String value = headers.get(key);
            if (i == 0) {
                builder.append("headers:").append("\t")
                        .append(key).append(" = ").append(value).append("\n");
            } else {
                builder.append("\t\t\t").append(key).append(" = ").append(value).append("\n");
            }
        }
        return builder;
    }

    /**
     * 打印参数
     *
     * @param request request
     * @return StringBuilder
     */
    private StringBuilder buildParamsLog(Request request) throws Exception {
        StringBuilder builder = new StringBuilder();
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            builder.append(buildGetLog(request));
        } else {
            if (requestBody instanceof FormBody) {
                builder.append(buildFormLog((FormBody) requestBody));
            } else if (requestBody instanceof MultipartBody) {
                builder.append(buildMultipartLog((MultipartBody) requestBody));
            } else {
                builder.append(buildBufferLog(requestBody));
            }
        }
        return builder;
    }

    private StringBuilder buildGetLog(Request request) throws Exception {
        StringBuilder builder = new StringBuilder();
        HttpUrl httpUrl = request.url();
        Set<String> keys = httpUrl.queryParameterNames();
        if (keys == null || keys.isEmpty()) {
            return builder;
        }
        JSONObject jsonObject = new JSONObject();
        builder.append("params:\t\t");
        for (String key : keys) {
            String value = httpUrl.queryParameter(key);
            jsonObject.put(key, value);
        }
        builder.append(jsonObject.toString()).append("\n");
        return builder;
    }

    private StringBuilder buildFormLog(FormBody body) throws Exception {
        StringBuilder builder = new StringBuilder();
        if (body == null || body.size() == 0) {
            return builder;
        }
        builder.append("params:\t\t");
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < body.size(); i++) {
            jsonObject.put(body.encodedName(i), body.encodedValue(i));
        }
        builder.append(jsonObject.toString()).append("\n");
        return builder;
    }

    private StringBuilder buildMultipartLog(MultipartBody body) throws Exception {
        StringBuilder builder = new StringBuilder();
        if (body == null || body.size() == 0) {
            return builder;
        }
        builder.append("params:\t\t");
        List<MultipartBody.Part> parts = body.parts();
        JSONObject jsonObject = new JSONObject();
        for (MultipartBody.Part part : parts) {
            Headers headers = part.headers();
            if (headers == null) {
                return builder;
            }
            String key = headers.value(0);
            RequestBody requestBody = part.body();
            MediaType type = requestBody.contentType();
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            String value = buffer.readUtf8();
            if (type == null) {
                return builder;
            }
            if ("text".equals(type.type())) {
                jsonObject.put(key, value);
            } else {
                jsonObject.put(key, "");
            }
        }
        builder.append(jsonObject.toString()).append("\n");
        return builder;
    }

    private StringBuilder buildBufferLog(RequestBody requestBody) throws Exception {
        StringBuilder builder = new StringBuilder();
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        String utf8 = buffer.readUtf8();
        if (TextUtils.isEmpty(utf8)) {
            return builder;
        }
        builder.append("params:\t\t").append(utf8).append("\n");
        return builder;
    }

    private StringBuilder buildResultLog(Response response) throws Exception {
        StringBuilder builder = new StringBuilder();
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        long contentLength = responseBody.contentLength();
        if (contentLength != 0) {
            builder.append("result:\t\t").append(buffer.clone().readString(charset));
        }
        return builder;
    }
}
