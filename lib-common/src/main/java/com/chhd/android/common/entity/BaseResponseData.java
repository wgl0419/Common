package com.chhd.android.common.entity;

/**
 * 网络请求结果实体基类
 *
 * @author : 葱花滑蛋 (2018/03/13)
 */

public interface BaseResponseData<T> {

    /**
     * 请求状态码
     *
     * @return int
     */
    Integer getCode();

    /**
     * 请求信息
     *
     * @return String
     */
    String getMessage();

    /**
     * 请求结果
     *
     * @return 泛型T
     */
    T getData();

    /**
     * 请求是否成功
     *
     * @return boolean
     */
    Boolean isSuccess();
}