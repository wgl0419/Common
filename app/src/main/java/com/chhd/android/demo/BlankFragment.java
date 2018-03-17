package com.chhd.android.demo;


import android.view.View;

import com.chhd.android.common.ui.fragment.base.PullToRefreshFragment;


public class BlankFragment extends PullToRefreshFragment<Adapter,Object> {

    @Override
    public int getContentResId() {
        return R.layout.layout_pull_to_refresh;
    }

    @Override
    public void onInit(View view) {

    }

    @Override
    public Adapter getAdapter() {
        return new Adapter(list);
    }

    @Override
    public void onLoad(boolean isLoadMore) {

    }
}
