package com.chhd.android.common.http;

/**
 * 网络请求异常
 *
 * @author : 葱花滑蛋 (2018/03/12)
 */
@SuppressWarnings("unchecked")
public class ApiException extends Throwable {

    private Integer code;
    private String errMsg;
    private Object data;

    public ApiException(Integer code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public ApiException(Integer code, String errMsg, Object data) {
        this.code = code;
        this.errMsg = errMsg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public <T> T getData() {
        return (T) data;
    }
}
