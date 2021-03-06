package com.chhd.android.common.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;

import com.chhd.android.common.mvp.IPageView;
import com.chhd.android.common.util.ToastUtils;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.observers.DisposableObserver;

/**
 * 网络请求回调
 *
 * @author : 葱花滑蛋 (2018/03/12)
 */
public abstract class HttpObserver<T> extends DisposableObserver<T> {

    private IPageView pageView;
    private ProgressDialog dialog;

    private boolean hasNext;
    private boolean hasError;

    protected int code = -Integer.MAX_VALUE;
    protected String message;
    protected T t;

    @Override
    protected final void onStart() {
        super.onStart();

        Context context = showProgressDialog();
        pageView = showPageView();

        if (pageView != null) {
            pageView.onPageLoading();
        } else if (context != null) {
            dialog = new ProgressDialog(context);
            dialog.setMessage("请稍等...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dispose();
                }
            });
            dialog.show();
        }
        onBefore();
    }

    @Override
    public final void onNext(T t) {
        hasNext = true;
        this.t = t;
        if (pageView != null) {
            pageView.onPageSuccess();
        }
        try {
            onSucceed(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void onError(Throwable e) {
        e.printStackTrace();

        hasError = true;

        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            code = apiException.getCode();
            message = apiException.getErrMsg();
            t = apiException.getData();
            onApiException(code, message, t);
        } else if (
                e instanceof TimeoutException ||
                        e instanceof SocketTimeoutException ||
                        e instanceof UnknownHostException) {
            message = "网络连接失败";
        } else {
            message = "出错了";
        }

        if (pageView != null) {
            pageView.onPageError(message);
            pageView.onPageComplete();
        } else {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        if (showToast()) {
            ToastUtils.show(message);
        }
        onFailed(e, message);
        onFinish();
    }

    @Override
    public final void onComplete() {
        if (pageView != null) {
            if (t == null) {
                pageView.onPageEmpty();
            }
            pageView.onPageComplete();
        } else if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (!hasNext && !hasError) {
            // 在ApiException，如果data是空，仅会走onComplete
            try {
                onSucceed(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        onFinish();
    }

    /**
     * 请求开始
     */
    protected void onBefore() {

    }

    /**
     * 请求成功
     *
     * @param t 服务端返回的实体类
     * @throws Exception NONE
     */
    protected abstract void onSucceed(@Nullable T t) throws Exception;

    /**
     * 请求失败，服务端异常
     *
     * @param code   服务端返回的状态码
     * @param errMsg 服务端返回的错误信息
     * @param t      服务端返回的实体类
     */
    protected void onApiException(Integer code, String errMsg, T t) {

    }

    /**
     * 请求失败
     *
     * @param e      异常
     * @param errMsg 服务端返回的错误信息
     */
    protected void onFailed(Throwable e, String errMsg) {

    }

    /**
     * 请求结束
     */
    protected void onFinish() {

    }

    /**
     * 是否弹出进度对话框
     *
     * @return Context
     */
    protected Context showProgressDialog() {
        return null;
    }

    /**
     * 是否显示请求进度、请求成功、请求失败、空布局
     *
     * @return IPageView
     */
    protected IPageView showPageView() {
        return null;
    }

    /**
     * 是否吐司错误提示
     *
     * @return boolean
     */
    protected boolean showToast() {
        return true;
    }

}
