package com.chhd.android.common.http;

/**
 * @author : 葱花滑蛋
 * @date : 2018/03/12
 */

public class ApiException extends Throwable{

    private int code;
    private String errMsg;

    public ApiException(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public int getCode() {
        return code;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
