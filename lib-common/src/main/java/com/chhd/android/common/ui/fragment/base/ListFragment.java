package com.chhd.android.common.ui.fragment.base;

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
 * @author : 葱花滑蛋
 * @date : 2018/03/15
 */

public abstract class ListFragment<Adapter extends BaseQuickAdapter, Entity> extends LazyFragment
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

    /**
     * 获取列表适配器
     *
     * @return Adapter
     */
    protected abstract Adapter getAdapter();

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public void onPrepare(View view) {
        super.onPrepare(view);

        try {
            recyclerView = view.findViewById(R.id.recycler_view);
        } catch (Exception e) {
            throw new RuntimeException("Layout must have one RecyclerView, and id must set recycler_view.");
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
        adapter.setEmptyView(new View(getActivity()));
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
    public void onLazyLoad() {
        onLoad(false);
    }

    /**
     * 上拉加载
     */
    public void onLoadMore() {
        onLoad(true);
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

    /**
     * 加载列表成功
     */
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

        adapter.setEnableLoadMore(true);
        if (listData.hasMore()) {
            adapter.loadMoreComplete();
        } else {
            adapter.loadMoreEnd();
        }
    }

    /**
     * 加载列表成功
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
    }

    /**
     * 显示列表空布局
     */
    protected void showListEmpty() {
        View emptyView = View.inflate(getActivity(), R.layout.layout_empty, null);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        emptyView.setLayoutParams(params);
        emptyView.setVisibility(View.VISIBLE);
        adapter.setEmptyView(emptyView);
    }

    /**
     * 显示列表失败布局
     */
    protected void showListError(String message) {
        View errorView = View.inflate(getActivity(), R.layout.layout_error, null);
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

    /**
     * 加载列表失败
     */
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
