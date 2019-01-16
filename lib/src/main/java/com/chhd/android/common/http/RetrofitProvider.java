package com.chhd.android.common.http;

import com.chhd.android.common.BuildConfig;
import com.chhd.android.common.http.interceptor.LogInterceptor;
import com.chhd.android.common.http.interceptor.ParamsInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Retrofit提供者
 *
 * @author : 葱花滑蛋 (2018/03/12)
 */
public class RetrofitProvider {

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
                .retryOnConnectionFailure(false);
        if (headers != null && !headers.isEmpty()) {
            builder.addInterceptor(buildParamsInterceptor(headers));
        }
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new LogInterceptor());
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        return builder.build();
    }

    private static Interceptor buildParamsInterceptor(Map<String, String> headers) {
        return new ParamsInterceptor.Builder().addHeaderParamsMap(headers).build();
    }
}
