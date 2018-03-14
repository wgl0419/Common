package com.chhd.android.common.ui.view;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * author : 葱花滑蛋
 * time   : 2018/03/14
 * desc   : IBaseView
 */

public interface IBaseView {

    <T> LifecycleTransformer<T> _bindToLifecycle();
}
