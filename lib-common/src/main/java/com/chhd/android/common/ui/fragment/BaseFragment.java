package com.chhd.android.common.ui.fragment;

import com.chhd.android.common.mvp.IBaseView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * BaseFragment
 *
 * @author : 葱花滑蛋 (2018/03/12)
 */
public class BaseFragment extends RxFragment implements IBaseView {

    @Override
    public <T> LifecycleTransformer<T> bindUntilDestroy() {
        return bindUntilEvent(FragmentEvent.DESTROY);
    }
}
