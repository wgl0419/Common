package com.chhd.android.common.http;

import com.chhd.android.common.entity.BaseResponseData;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * 网络请求结果转换器
 *
 * @author : 葱花滑蛋 (2018/03/13)
 */
public class ResponseTransformer {

    /**
     * 过滤网络请求结果
     *
     * @param <T> 服务端返回的实体类
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<BaseResponseData<T>, T> transform() {
        return new ObservableTransformer<BaseResponseData<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponseData<T>> upstream) {
                return upstream.flatMap(new Function<BaseResponseData<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(final BaseResponseData<T> responseData)
                            throws Exception {
                        if (responseData.isSuccess() != null && responseData.isSuccess()) {
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
                            return Observable.error(new ApiException(responseData.getCode(),
                                    responseData.getMessage(), responseData.getData()));
                        }
                    }
                });
            }
        };
    }
}
