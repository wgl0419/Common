package com.chhd.android.common.http.flowable;

import com.chhd.android.common.entity.BaseResponseData;
import com.chhd.android.common.http.ApiException;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

/**
 * 网络请求结果转换器
 *
 * @author : 葱花滑蛋 (2018/07/19)
 */
public class ResponseTransformer {

    /**
     * 过滤网络请求结果
     *
     * @param <T> 服务端返回的实体类
     * @return FlowableTransformer
     */
    public static <T> FlowableTransformer<BaseResponseData<T>, T> transform() {
        return new FlowableTransformer<BaseResponseData<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<BaseResponseData<T>> upstream) {
                return upstream.flatMap(new Function<BaseResponseData<T>, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(final BaseResponseData<T> responseData) throws Exception {
                        if (responseData.isSuccess() != null && responseData.isSuccess()) {
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
                            return Flowable.error(new ApiException(responseData.getCode(),
                                    responseData.getMessage(), responseData.getData()));
                        }
                    }
                });
            }
        };
    }
}
