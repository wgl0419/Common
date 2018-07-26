package com.chhd.android.common.http.flowable;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 网络请求帮助类
 *
 * @author : 葱花滑蛋 (2018/07/19)
 */
public class RxHelper {

    /**
     * 切换线程：工作线程-》主线程
     *
     * @param <T> 服务端返回的实体类
     * @return FlowableTransformer
     */
    public static <T> FlowableTransformer<T, T> ioMainThread() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
