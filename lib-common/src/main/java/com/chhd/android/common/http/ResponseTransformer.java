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
 * time   : 2018/03/13
 * desc   : HttpResponse
 */
public class ResponseTransformer {

    public static <T> ObservableTransformer<BaseResponseData<T>, T> observableTransform() {
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

    public static <T> FlowableTransformer<BaseResponseData<T>, T> flowableTransform() {
        return new FlowableTransformer<BaseResponseData<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<BaseResponseData<T>> upstream) {
                return upstream.flatMap(new Function<BaseResponseData<T>, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(final BaseResponseData<T> responseData) throws Exception {
                        if (responseData.isSuccess()) {
                            return Flowable.create(new FlowableOnSubscribe<T>() {
                                @Override
                                public void subscribe(FlowableEmitter<T> e) throws Exception {
                                    if (responseData.getData() != null) {
                                        e.onNext(responseData.getData());
                                    }
                                    e.onComplete();
                                }
                            }, BackpressureStrategy.LATEST);
                        } else {
                            return Flowable.error(new ApiException(responseData.getStatus(),
                                    responseData.getMessage()));
                        }
                    }
                });
            }
        };
    }
}
