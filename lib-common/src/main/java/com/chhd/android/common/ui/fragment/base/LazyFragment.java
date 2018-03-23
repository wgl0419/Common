package com.chhd.android.common.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * author : 葱花滑蛋
 * date   : 2018/03/14
 * desc   :
 */

public abstract class LazyFragment extends ProgressFragment {

    protected Boolean isVisibleToUser = null;
    protected boolean hasViewCreate = false;
    protected boolean hasLazyLoad = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
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
        if (isVisibleToUser == null) {
            hasLazyLoad = true;
            if (isAutoLoad())
                onLazyLoad();
        } else {
            if (isVisibleToUser && hasViewCreate && !hasLazyLoad) {
                hasLazyLoad = true;
                if (isAutoLoad())
                    onLazyLoad();
            }
        }
    }

    @Override
    public void onLoad() {

    }

    /**
     * 懒加载，适用于ViewPager,第一次可见时执行此方法
     */
    public abstract void onLazyLoad();
}
