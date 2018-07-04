package com.chhd.android.common.mvp;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * IBaseView
 *
 * @author : 葱花滑蛋 (2018/03/14)
 */

public interface IBaseView {

    /**
     * 绑定onDestroy的生命周期，结合RxJava使用
     *
     * @param <T> 泛型T
     * @return 泛型T
     */
    <T> LifecycleTransformer<T> bindUntilDestroy();
}
