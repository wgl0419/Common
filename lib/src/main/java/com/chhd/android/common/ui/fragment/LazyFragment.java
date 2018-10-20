package com.chhd.android.common.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.chhd.android.common.R;
import com.chhd.android.common.util.UiUtils;

/**
 * 懒加载界面
 *
 * @author : 葱花滑蛋 (2018/03/14)
 */
public abstract class LazyFragment extends ProgressFragment {

    protected Boolean isVisibleToUser = null;
    protected boolean isViewCreate = false;
    protected boolean isLazyLoad = false;

    protected boolean isVisited = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            this.isVisited = true;
        }
        this.isVisibleToUser = isVisibleToUser;
        onPreLazyLoad();
        if (isAutoLoad() && isVisibleToUser && isLoadComplete() && isOpenVisibleLoad()) {
            onVisibleLoad();
        }
    }

    /**
     * 可见并第一次加载完毕回调此方法
     */
    @Override
    protected void onVisibleLoad() {
        onLazyLoad();
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

    @Override
    protected void onResumeLoad() {
        onLazyLoad();
    }

}
