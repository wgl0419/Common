package com.chhd.android.common.http;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : 葱花滑蛋 (2018/03/14)
 */

public class RxHelper {

    /**
     * 切换线程：工作线程-》主线程
     *
     * @param <T> 泛型T
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> ioMainThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
