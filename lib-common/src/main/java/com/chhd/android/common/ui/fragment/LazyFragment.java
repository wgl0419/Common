package com.chhd.android.common.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * 懒加载界面
 *
 * @author : 葱花滑蛋 (2018/03/14)
 */
public abstract class LazyFragment extends ProgressFragment {

    protected Boolean isVisibleToUser = null;
    protected boolean isViewCreate = false;
    protected boolean isLazyLoad = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        onPreLazyLoad();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreate = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onPreLazyLoad();
    }

    @Override
    public void onDestroy() {
        isViewCreate = false;
        isLazyLoad = false;
        super.onDestroy();
    }

    private void onPreLazyLoad() {
        if (isVisibleToUser == null) {
            isLazyLoad = true;
            onLazyLoad();
        } else {
            if (isVisibleToUser && isViewCreate && !isLazyLoad) {
                isLazyLoad = true;
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
    protected abstract void onLazyLoad();

}
