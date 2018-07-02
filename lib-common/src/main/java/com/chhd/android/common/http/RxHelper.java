package com.chhd.android.common.http;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 网络请求帮助类
 *
 * @author : 葱花滑蛋 (2018/03/14)
 */

public class RxHelper {

    /**
     * 切换线程：工作线程-》主线程
     *
     * @param <T> 服务端返回的实体类
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
