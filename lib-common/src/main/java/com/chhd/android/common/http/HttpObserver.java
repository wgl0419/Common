package com.chhd.android.common.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.chhd.android.common.mvp.IPageView;
import com.chhd.android.common.util.ToastUtils;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.observers.DisposableObserver;

/**
 * author : 葱花滑蛋
 * date   : 2018/03/12
 * desc   :
 */

public abstract class HttpObserver<T> extends DisposableObserver<T> {

    private IPageView iPageView;
    private ProgressDialog dialog;
    private T t;

    @Override
    protected final void onStart() {
        super.onStart();

        Context context = showProgressDialog();
        iPageView = showPageView();

        if (iPageView != null) {
            iPageView.onPageLoading();
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
        _onStart();
    }

    @Override
    public final void onNext(T t) {
        this.t = t;
        if (iPageView != null) {
            iPageView.onPageSuccess();
        }
        onSucceed(t);
    }

    @Override
    public final void onError(Throwable e) {
        e.printStackTrace();

        String errMsg;
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            errMsg = apiException.getErrMsg();
        } else if (e instanceof TimeoutException || e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
            errMsg = "网络连接失败";
        } else {
            errMsg = "出错了";
        }

        if (iPageView != null) {
            iPageView.onPageError(errMsg);
            iPageView.onPageComplete();
        } else {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        if (showToast()) {
            ToastUtils.show(errMsg);
        }

        onFailed(e);
        onFinish();
    }

    @Override
    public final void onComplete() {
        if (iPageView != null) {
            if (t == null)
                iPageView.onPageEmpty();
            iPageView.onPageComplete();
        } else if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        onFinish();
    }

    /**
     * 请求开始
     */
    protected void _onStart() {

    }

    /**
     * 请求成功
     */
    protected abstract void onSucceed(T t);

    /**
     * 请求失败
     */
    protected void onFailed(Throwable e) {

    }

    /**
     * 请求结束
     */
    protected void onFinish() {

    }

    /**
     * 是否弹出进度对话框
     */
    protected Context showProgressDialog() {
        return null;
    }

    /**
     * 是否显示请求进度、请求成功、请求失败等布局
     */
    protected IPageView showPageView() {
        return null;
    }

    /**
     * 是否吐司错误提示
     */
    protected boolean showToast() {
        return true;
    }

}
