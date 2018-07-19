package com.chhd.android.common.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * 列表界面，带Toolbar
 *
 * @author : 葱花滑蛋 (2018/03/15)
 */

public abstract class ListTActivity<Adapter extends BaseQuickAdapter, Entity> extends ProgressTActivity
        implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private boolean isLoadMore = false;

    protected RecyclerView recyclerView;

    protected BaseListData listData = new BaseListData() {
        @Override
        public Integer getPageStart() {
            return 0;
        }

        @Override
        public Boolean isPageNext() {
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

    /**
     * 获取列表适配器
     *
     * @return Adapter
     */
    protected abstract Adapter getAdapter();

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPrepareLoad();
    }

    protected void onPrepareLoad() {
        if (isAutoLoad()) {
            setLoadMore(false);
        }
    }

    @Override
    protected void onPrepare(Bundle savedInstanceState) {
        super.onPrepare(savedInstanceState);

        recyclerView = findViewById(R.id.recycler_view);
        if (recyclerView == null) {
            throw new NullPointerException("Layout must have one RecyclerView, " +
                    "and id must set recycler_view.");
        }

        adapter = getAdapter();
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                onLoadMore();
            }
        }, recyclerView);
        adapter.setEnableLoadMore(false);
        adapter.setHeaderFooterEmpty(true, true);
        adapter.setEmptyView(new View(this));
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        layoutManager = getLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    /**
     * 加载
     */
    @Override
    public void onLoad() {
    }

    @Override
    public void reLoad() {
        hasLoadSuccess = false;
        setLoadMore(false);
    }

    public void refresh() {
        setLoadMore(false);
    }

    /**
     * 上拉加载
     */
    void onLoadMore() {
        setLoadMore(true);
    }

    boolean isLoadMore() {
        return isLoadMore;
    }

    void setLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        onLoad(isLoadMore);
    }

    /**
     * 加载
     *
     * @param isLoadMore 是否上拉加载
     */
    public abstract void onLoad(boolean isLoadMore);

    /**
     * Item点击事件
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    /**
     * Item的子View点击事件
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onPageLoading() {
        showListLoading();
    }

    @Override
    public void onPageEmpty() {
        showListEmpty();
    }

    @Override
    public void onPageError(String message) {
        onLoadError(message);
    }


    /**
     * 加载列表成功
     *
     * @param listData listData
     */
    protected void onLoadSuccess(BaseListData<Entity> listData) {
        this.listData = listData;
        if (listData.getPageStart() == null || listData.getPageStart() == 0) {
            list.clear();
        }

        if (listData.getList() != null) {
            list.addAll(listData.getList());
        }
        adapter.notifyDataSetChanged();

        showListEmpty();
        if (list.isEmpty()) {
        } else {
            onPageSuccess();
        }

        adapter.setEnableLoadMore(true);
        if (listData.isPageNext() != null && listData.isPageNext()) {
            adapter.loadMoreComplete();
        } else {
            adapter.loadMoreEnd();
        }
    }

    /**
     * 加载列表成功
     *
     * @param list list
     */
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

    protected void showListLoading() {
        View loadingView = View.inflate(this, R.layout.layout_loading, null);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        loadingView.setLayoutParams(params);
        loadingView.setVisibility(View.VISIBLE);
        adapter.setEmptyView(loadingView);
    }

    /**
     * 显示列表空布局
     */
    protected void showListEmpty() {
        View emptyView = View.inflate(this, R.layout.layout_empty, null);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        emptyView.setLayoutParams(params);
        emptyView.setVisibility(View.VISIBLE);
        adapter.setEmptyView(emptyView);
    }

    /**
     * 显示列表失败布局
     *
     * @param message 服务端返回的错误信息
     */
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
                showListLoading();
                setLoadMore(false);
            }
        });
        tvError.setText(message);
        adapter.setEmptyView(errorView);
    }

    /**
     * 加载列表失败
     *
     * @param message 服务端返回的错误信息
     */
    protected void onLoadError(String message) {
        if (listData.getPageStart() == null || listData.getPageStart() == 0) {
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
