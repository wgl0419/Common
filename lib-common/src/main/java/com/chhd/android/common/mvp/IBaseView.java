package com.chhd.android.common.mvp;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * author : 葱花滑蛋
 * date   : 2018/03/14
 * desc   :
 */

public interface IBaseView {

    /**
     * 绑定onDestroy的生命周期，结合RxJava使用
     */
    <T> LifecycleTransformer<T> bindUntilDestroy();
}
