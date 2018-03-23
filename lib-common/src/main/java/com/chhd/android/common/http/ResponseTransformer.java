package com.chhd.android.common.http;

import com.chhd.android.common.entity.BaseResponseData;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * author : 葱花滑蛋
 * date   : 2018/03/13
 * desc   :
 */

public class ResponseTransformer {

    /**
     * 过滤网络请求结果
     */
    public static <T> ObservableTransformer<BaseResponseData<T>, T> transform() {
        return new ObservableTransformer<BaseResponseData<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponseData<T>> upstream) {
                return upstream.flatMap(new Function<BaseResponseData<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(final BaseResponseData<T> responseData) throws Exception {
                        if (responseData.isSuccess()) {
                            return Observable.create(new ObservableOnSubscribe<T>() {
                                @Override
                                public void subscribe(ObservableEmitter<T> e) throws Exception {
                                    if (responseData.getData() != null) {
                                        e.onNext(responseData.getData());
                                    }
                                    e.onComplete();
                                }
                            });
                        } else {
                            return Observable.error(new ApiException(responseData.getStatus(),
                                    responseData.getMessage()));
                        }
                    }
                });
            }
        };
    }
}
