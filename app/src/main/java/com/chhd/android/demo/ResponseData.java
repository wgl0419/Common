package com.chhd.android.demo;


import com.chhd.android.common.entity.BaseResponseData;

public class ResponseData<T> implements BaseResponseData<T> {

    private int code;

    private String msg;

    private T data;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public boolean isSuccess() {
        return code == 0;
    }
}
