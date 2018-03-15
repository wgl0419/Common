package com.chhd.android.common.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chhd.android.common.R;
import com.chhd.android.common.entity.BaseListData;
import com.chhd.android.common.global.Constant;

import java.util.List;

/**
 * author : 葱花滑蛋
 * time   : 2018/03/13
 * desc   : PullToRefreshActivity
 */

public abstract class PullToRefreshActivity<Adapter extends BaseQuickAdapter, Entity>
        extends ListActivity<Adapter, Entity> implements SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isAutoPullToRefresh()) {
            refresh();
        }
    }

    protected void refresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        }, 100);
    }

    @Override
    public void onPageLoading() {

    }

    public int[] setColorSchemeResources() {
        return Constant.SWIPE_REFRESH_LAYOUT_COLORS;
    }

    @Override
    protected final boolean isAutoLoad() {
        return false;
    }

    protected boolean isAutoPullToRefresh() {
        return true;
    }

    @Override
    public void onPrepare() {
        super.onPrepare();
        try {
            swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        } catch (Exception e) {
            throw new RuntimeException("Layout must have one SwipeRefreshLayout, and id must set swipe_refresh_layout.");
        }

        swipeRefreshLayout.setColorSchemeResources(setColorSchemeResources());
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        onLoad();
    }

    @Override
    public void onPageComplete() {
        super.onPageComplete();

        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onLoadSuccess(BaseListData<Entity> listData) {
        super.onLoadSuccess(listData);

        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onLoadSuccess(List<Entity> list) {
        super.onLoadSuccess(list);

        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onLoadError(String message) {
        super.onLoadError(message);

        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }
}
