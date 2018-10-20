package com.chhd.android.common.ui.fragment.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chhd.android.common.mvp.IBaseView;
import com.chhd.android.common.util.CommonUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatDialogFragment;


/**
 * BaseDialogFragment
 *
 * @author : 葱花滑蛋 (2018/10/16)
 */
public class BaseDialogFragment extends RxAppCompatDialogFragment implements IBaseView {

    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CommonUtils.init(getActivity().getApplication());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntilDestroy() {
        return bindUntilEvent(FragmentEvent.DESTROY);
    }
}
