package com.chhd.android.common.entity;

/**
 * author : 葱花滑蛋
 * time   : 2018/03/13
 * desc   : BaseResponseData
 */
public interface BaseResponseData<T> {

    public  int getStatus();

    public  String getMessage();

    public  T getData();

    public  boolean isSuccess();
}
