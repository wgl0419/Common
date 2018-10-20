package com.chhd.android.common.ui.fragment.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chhd.android.common.R;
import com.chhd.android.common.global.Const;

/**
 * PullToRefreshDialogFragment
 *
 * @author : 葱花滑蛋 (2018/10/16)
 */
public abstract class PullToRefreshDialogFragment<Adapter extends BaseQuickAdapter, Entity>
        extends ListDialogFragment<Adapter, Entity> {

    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void reLoad() {
        isLoadSuccess = false;
        isLoadComplete = false;
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
        return Const.SWIPE_REFRESH_LAYOUT_COLORS;
    }

    @Override
    protected void onPrepareLoad() {
        if (this.isAutoLoad()) {
            this.refresh();
        }
    }

    @Override
    protected void onPrepare(View view, @Nullable Bundle savedInstanceState) {
        super.onPrepare(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
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
