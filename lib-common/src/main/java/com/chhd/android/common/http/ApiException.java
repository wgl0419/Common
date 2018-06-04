package com.chhd.android.common.http;

/**
 * @author : 葱花滑蛋 (2018/03/12)
 */

public class ApiException extends Throwable{

    private Integer code;
    private String errMsg;

    public ApiException(Integer code, String errMsg) {
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
