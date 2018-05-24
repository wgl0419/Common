package com.chhd.android.common.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 有两种创建方法：1是通过静态方法；2是通过链式编程
 *
 * @author : 葱花滑蛋 (2018/03/12)
 */

public class RetrofitProvider {

    private static final HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    /* -------------------------- 通过静态方法创建Retrofit对象 -------------------------- */

    /**
     * 生成Retrofit对象
     *
     * @param baseUrl baseUrl
     * @return Retrofit
     */
    public static Retrofit newInstance(String baseUrl) {
        return newInstance(baseUrl, null);
    }

    /**
     * 生成Retrofit对象
     *
     * @param baseUrl baseUrl
     * @param headers 请求头部
     * @return Retrofit
     */
    public static Retrofit newInstance(String baseUrl, Map<String, String> headers) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(buildOkHttpClient(headers))
                .build();
    }

    private static OkHttpClient buildOkHttpClient(Map<String, String> headers) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(HTTP_LOGGING_INTERCEPTOR)
                .addNetworkInterceptor(new StethoInterceptor())
                .retryOnConnectionFailure(false);
        if (headers != null && !headers.isEmpty()) {
            builder.addInterceptor(buildBasicInterceptor(headers));
        }
        return builder.build();
    }

    private static Interceptor buildBasicInterceptor(Map<String, String> headers) {
        return new BasicParamsInterceptor.Builder().addHeaderParamsMap(headers).build();
    }

    /* -------------------------- 通过链式编程创建Retrofit对象 -------------------------- */

    private OkHttpClient.Builder okHttpBuilder;
    private Retrofit.Builder retrofitBuilder;

    private OkHttpClient okHttpClient;

    public RetrofitProvider() {
        okHttpBuilder = new OkHttpClient.Builder();
        retrofitBuilder = new Retrofit.Builder();
    }

    public RetrofitProvider setBaseUrl(String baseUrl) {
        retrofitBuilder.baseUrl(baseUrl);
        return this;
    }

    public RetrofitProvider setHeaders(Map<String, String> headers) {
        okHttpBuilder.addInterceptor(buildBasicInterceptor(headers));
        return this;
    }

    public RetrofitProvider setConnectTimeout(long timeout, TimeUnit unit) {
        okHttpBuilder.connectTimeout(timeout, unit);
        return this;
    }

    public RetrofitProvider setReadTimeout(long timeout, TimeUnit unit) {
        okHttpBuilder.readTimeout(timeout, unit);
        return this;
    }

    public RetrofitProvider setWriteTimeout(long timeout, TimeUnit unit) {
        okHttpBuilder.writeTimeout(timeout, unit);
        return this;
    }

    public RetrofitProvider setRetryOnConnectionFailure(boolean retryOnConnectionFailure) {
        okHttpBuilder.retryOnConnectionFailure(retryOnConnectionFailure);
        return this;
    }

    public RetrofitProvider setClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        return this;
    }

    public Retrofit build() {
        OkHttpClient okHttpClient = okHttpBuilder
                .addInterceptor(HTTP_LOGGING_INTERCEPTOR)
                .addNetworkInterceptor(new StethoInterceptor())
                .retryOnConnectionFailure(false)
                .build();
        if (this.okHttpClient != null) {
            okHttpClient = this.okHttpClient;
        }
        return retrofitBuilder.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
