package com.chhd.android.demo;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiService {

    @GET("/api/index/index")
    Flowable<String> index();

    @FormUrlEncoded
    @POST("/api/course/light")
    Single<String> edit(@Field("course_id") String course_id);

    @GET("api/order/rentList")
    Observable<ResponseData<ListData<Object>>> getRentList(@Query("start") String start);
}
