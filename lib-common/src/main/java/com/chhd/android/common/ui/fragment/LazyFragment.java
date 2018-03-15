package com.chhd.android.common.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

/**
 * author : 葱花滑蛋
 * time   : 2018/03/14
 * desc   : LazyFragment
 */

public abstract class LazyFragment extends ProgressFragment {

    protected Boolean hasViewCreate = false;
    protected Boolean hasLazyLoad = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        onPreLazyLoad();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hasViewCreate = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onPreLazyLoad();
    }

    @Override
    public void onDestroy() {
        hasViewCreate = false;
        hasLazyLoad = false;
        super.onDestroy();
    }

    private void onPreLazyLoad() {
        if (hasViewCreate && !hasLazyLoad) {
            hasLazyLoad = true;

            if (isAutoLoad())
                onLazyLoad();
        }
    }

    @Override
    public void onLoad() {

    }

    public abstract void onLazyLoad();
}
