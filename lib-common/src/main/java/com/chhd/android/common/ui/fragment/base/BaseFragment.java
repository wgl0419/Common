package com.chhd.android.common.ui.fragment.base;

import com.chhd.android.common.ui.view.IBaseView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * author : 葱花滑蛋
 * time   : 2018/03/12
 * desc   : BaseFragment
 */

public class BaseFragment extends RxFragment implements IBaseView {

    @Override
    public <T> LifecycleTransformer<T> _bindToLifecycle() {
        return bindToLifecycle();
    }
}
