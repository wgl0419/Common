package com.chhd.android.demo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chhd.android.common.ui.fragment.PullToRefreshFragment;


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
