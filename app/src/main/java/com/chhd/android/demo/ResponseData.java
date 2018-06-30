package com.chhd.android.demo;


import com.chhd.android.common.entity.BaseResponseData;

public class ResponseData<T> implements BaseResponseData<T> {

    private int code;

    private String msg;

    private T data;

    @Override
    public Integer getCode() {
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
    public Boolean isSuccess() {
        return code == 0;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }
}
