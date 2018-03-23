package com.chhd.android.common.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * author : 葱花滑蛋
 * date   : 2018/03/12
 * desc   :
 */

public class RetrofitProvider {

    private static final HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    /**
     * 生成Retrofit对象
     */
    public static Retrofit newInstance(String baseUrl) {
        return newInstance(baseUrl, null);
    }

    /**
     * 生成Retrofit对象
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

    /**
     * 创建api
     *
     * @param baseUrl baseUrl
     * @param clazz   请求头部
     */
    public static <T> T createApi(String baseUrl, Class<T> clazz) {
        return createApi(baseUrl, null, clazz);
    }

    /**
     * 创建api
     *
     * @param baseUrl baseUrl
     * @param headers 请求头部
     */
    public static <T> T createApi(String baseUrl, Map<String, String> headers, Class<T> clazz) {
        return newInstance(baseUrl, headers).create(clazz);
    }

    private static OkHttpClient buildOkHttpClient(Map<String, String> headers) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(HTTP_LOGGING_INTERCEPTOR)
                .addNetworkInterceptor(new StethoInterceptor())
                .retryOnConnectionFailure(true);
        if (headers != null && !headers.isEmpty()) {
            builder.addInterceptor(buildBasicInterceptor(headers));
        }
        return builder.build();
    }

    private static Interceptor buildBasicInterceptor(Map<String, String> headers) {
        return new BasicParamsInterceptor.Builder().addHeaderParamsMap(headers).build();
    }
}
