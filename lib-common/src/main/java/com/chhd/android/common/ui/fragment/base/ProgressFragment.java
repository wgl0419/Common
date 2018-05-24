package com.chhd.android.common.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chhd.android.common.R;
import com.chhd.android.common.mvp.IPageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 葱花滑蛋
 * @date : 2018/03/14
 */

public abstract class ProgressFragment extends BaseFragment implements IPageView, View.OnClickListener {

    protected List<View> viewList = new ArrayList<>();

    protected View loadingView;
    protected View errorView;
    protected View emptyView;
    protected FrameLayout contentView;

    protected TextView tvError;
    protected TextView tvEmpty;

    protected Button btnRetry;
    protected Button btnRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onPrepare(view);

        onInit(view);

        if (isAutoLoad()) {
            onLoad();
        }
    }

    protected boolean isAutoLoad() {
        return true;
    }

    /**
     * 获取布局文件
     *
     * @return int
     */
    public abstract int getContentResId();

    public void onPrepare(View view) {
        loadingView = view.findViewById(R.id.loading);
        errorView = view.findViewById(R.id.error);
        emptyView = view.findViewById(R.id.empty);
        contentView = view.findViewById(R.id.content);

        tvError = view.findViewById(R.id.tv_error);
        tvEmpty = view.findViewById(R.id.tv_empty);

        btnRetry = view.findViewById(R.id.btn_retry);
        btnRefresh = view.findViewById(R.id.btn_refresh);

        viewList.add(loadingView);
        viewList.add(errorView);
        viewList.add(emptyView);
        viewList.add(contentView);

        showContentView();

        if (getContentResId() != 0) {
            LayoutInflater.from(getActivity()).inflate(getContentResId(), contentView, true);
        }

        btnRetry.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
    }

    /**
     * 初始化
     *
     * @param view 根布局
     */
    public abstract void onInit(View view);

    /**
     * 加载
     */
    public abstract void onLoad();

    private void showStatusView(int viewId) {
        for (View view : viewList) {
            if (view.getId() == viewId) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }


    private void showLoadingView() {
        showStatusView(R.id.loading);
    }

    private void showEmptyView() {
        showStatusView(R.id.empty);
    }

    private void showErrorView(String message) {
        showStatusView(R.id.error);
        tvError.setText(message);
    }

    private void showContentView() {
        showStatusView(R.id.content);
    }

    /**
     * 因为可能会在onResume方法中重新加载数据，如果已经时显示成功，则不再显示加载中、加载失败状态
     */
    protected boolean hasSuccess = false;
    /**
     * 同样可能会在onResume方法中重新加载数据的问题，这样第一次进入可能会分别在onCreate,onResume触发网络请求，所以衍生出此属性
     */
    protected boolean hasFirstLoadComplete = false;

    @Override
    public void onPageLoading() {
        if (!hasSuccess) {
            showLoadingView();
        }
    }

    @Override
    public void onPageSuccess() {
        hasSuccess = true;
        showContentView();
    }

    @Override
    public void onPageEmpty() {
        showEmptyView();
    }

    @Override
    public void onPageError(String message) {
        if (!hasSuccess) {
            showErrorView(message);
        }
    }

    @Override
    public void onPageComplete() {
        hasFirstLoadComplete = true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_retry || v.getId() == R.id.btn_refresh) {
            onLoad();
        }
    }
}
