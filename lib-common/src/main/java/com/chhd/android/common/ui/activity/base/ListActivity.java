package com.chhd.android.common.ui.activity.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chhd.android.common.R;
import com.chhd.android.common.entity.BaseListData;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 葱花滑蛋
 * date   : 2018/03/15
 * desc   :
 */

public abstract class ListActivity<Adapter extends BaseQuickAdapter, Entity> extends ProgressActivity
        implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    protected RecyclerView recyclerView;

    protected BaseListData listData = new BaseListData() {
        @Override
        public int getStart() {
            return 0;
        }

        @Override
        public boolean hasMore() {
            return false;
        }

        @Override
        public List getList() {
            return null;
        }
    };
    protected List<Entity> list = new ArrayList<>();

    protected Adapter adapter;
    protected RecyclerView.LayoutManager layoutManager;

    @Override
    public void onPageError(String message) {
        onLoadError(message);
    }

    public abstract Adapter getAdapter();

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    public void onPrepare() {
        super.onPrepare();

        try {
            recyclerView = findViewById(R.id.recycler_view);
        } catch (Exception e) {
            throw new RuntimeException("Layout must have one RecyclerView, and id must set recycler_view.");
        }

        adapter = getAdapter();
        adapter.openLoadAnimation();
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                onLoadMore();
            }
        }, recyclerView);
        adapter.setHeaderFooterEmpty(true, true);
        adapter.setEmptyView(new View(this));
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        layoutManager = getLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoad() {
        onLoad(false);
    }

    public void onLoadMore() {
        onLoad(true);
    }

    public abstract void onLoad(boolean isLoadMore);

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    protected void onLoadSuccess(BaseListData<Entity> listData) {
        this.listData = listData;
        if (this.listData.getStart() == 0) {
            this.list.clear();
        }
        this.list.addAll(listData.getList());
        adapter.notifyDataSetChanged();

        showListEmpty();
        if (list.isEmpty()) {
        } else {
            onPageSuccess();
        }

        if (listData.hasMore()) {
            adapter.loadMoreComplete();
        } else {
            adapter.loadMoreEnd();
        }
    }

    protected void onLoadSuccess(List<Entity> list) {
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();

        showListEmpty(); // 当网络请求成功设置一个空布局
        if (this.list.isEmpty()) {

        } else {
            onPageSuccess();
        }

        adapter.loadMoreEnd(true);
    }

    protected void showListEmpty() {
        View emptyView = View.inflate(this, R.layout.layout_empty, null);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        emptyView.setLayoutParams(params);
        emptyView.setVisibility(View.VISIBLE);
        adapter.setEmptyView(emptyView);
    }

    protected void showListError(String message) {
        View errorView = View.inflate(this, R.layout.layout_error, null);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        errorView.setLayoutParams(params);
        errorView.setVisibility(View.VISIBLE);
        Button btnRetry = errorView.findViewById(R.id.btn_retry);
        TextView tvError = errorView.findViewById(R.id.tv_error);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoad();
            }
        });
        tvError.setText(message);
        adapter.setEmptyView(errorView);
    }

    protected void onLoadError(String message) {
        if (listData.getStart() == 0) {
            showListError(message);
        } else {
            adapter.loadMoreFail();
        }
    }

    @Override
    public void onDestroy() {
        list.clear();
        super.onDestroy();
    }
}
