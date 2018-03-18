package com.chhd.android.common.ui.view;

/**
 * author : 葱花滑蛋
 * date   : 2018/03/12
 * desc   :
 */

public interface IPageView extends IBaseView {

    /**
     * 页面加载中
     */
    void onPageLoading();

    /**
     * 页面加载成功
     */
    void onPageSuccess();

    /**
     * 页面加载失败
     */
    void onPageError(String errMsg);

    /**
     * 页面内容为空
     */
    void onPageEmpty();

    /**
     * 页面加载结束
     */
    void onPageComplete();
}
