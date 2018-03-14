package com.chhd.android.common.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * author : 葱花滑蛋
 * time   : 2018/03/14
 * desc   : LazyFragment
 */

public abstract class LazyFragment extends ProgressFragment {

    protected Boolean isVisibleToUser = false;
    protected Boolean hasViewCreate = false;
    protected Boolean hasLazyLoad = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        onPreLazyLoad();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hasViewCreate = true;
        onPreLazyLoad();
    }

    @Override
    public void onDestroy() {
        hasViewCreate = false;
        hasLazyLoad = false;
        super.onDestroy();
    }

    private void onPreLazyLoad() {
        if (isVisibleToUser && hasViewCreate && !hasLazyLoad) {
            hasLazyLoad = true;
            onLazyLoad();
        }
    }

    protected abstract void onLazyLoad();
}
