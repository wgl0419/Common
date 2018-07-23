package com.chhd.android.common.ui.activity.base.toolbar;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chhd.android.common.R;
import com.chhd.android.common.global.Constant;

/**
 * 下拉刷新界面，带Toolbar
 *
 * @author : 葱花滑蛋 (2018/03/13)
 */

public abstract class PullToRefreshActivity<Adapter extends BaseQuickAdapter, Entity>
        extends ListActivity<Adapter, Entity> {

    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onPrepareLoad() {
        if (isAutoLoad()) {
            refresh();
        }
    }

    @Override
    public void reLoad() {
        hasLoadSuccess = false;
        refresh();
    }

    @Override
    public void refresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                setLoadMore(false);
            }
        }, 100);
    }

    protected int[] getColorSchemeResources() {
        return Constant.SWIPE_REFRESH_LAYOUT_COLORS;
    }

    @Override
    protected void onPrepare(Bundle savedInstanceState) {
        super.onPrepare(savedInstanceState);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        if (swipeRefreshLayout == null) {
            throw new NullPointerException("Layout must have one SwipeRefreshLayout, " +
                    "and id must set swipe_refresh_layout.");
        }

        swipeRefreshLayout.setColorSchemeResources(getColorSchemeResources());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setLoadMore(false);
            }
        });
    }

    @Override
    public void onPageLoading() {
        if (!swipeRefreshLayout.isRefreshing() && !isLoadMore()) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onPageSuccess() {
        super.onPageSuccess();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onPageEmpty() {
        super.onPageEmpty();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onPageError(String message) {
        super.onPageError(message);
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onPageComplete() {
        super.onPageComplete();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
