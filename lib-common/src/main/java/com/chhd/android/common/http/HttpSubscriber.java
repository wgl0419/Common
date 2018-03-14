package com.chhd.android.common.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.chhd.android.common.ui.view.IPageView;
import com.chhd.android.common.util.ToastUtils;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * author : 葱花滑蛋
 * time   : 2018/03/13
 * desc   : HttpSubscriber
 */

public abstract class HttpSubscriber<T> extends ResourceSubscriber<T> {

    private IPageView iPageView;
    private ProgressDialog dialog;

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
            ToastUtils.show(errMsg);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        onFailed(e);
        onFinish();
    }

    @Override
    public final void onComplete() {
        if (iPageView != null) {
            iPageView.onPageComplete();
        } else if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        onFinish();
    }

    protected void _onStart() {

    }

    public abstract void onSucceed(T t);

    protected void onFailed(Throwable e) {

    }

    protected void onFinish() {

    }

    protected Context showProgressDialog() {
        return null;
    }

    protected IPageView showPageView() {
        return null;
    }
}
