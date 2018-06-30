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
 * 进度界面
 *
 * @author : 葱花滑蛋 (2018/03/14)
 */

public abstract class ProgressFragment extends BaseFragment implements IPageView {

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
    protected abstract int getContentResId();

    protected void onPrepare(View view) {
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

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoad();
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoad();
            }
        });
    }

    /**
     * 初始化
     *
     * @param view 根布局
     */
    protected abstract void onInit(View view);

    /**
     * 加载
     */
    protected abstract void onLoad();

    /**
     * 重新加载，带加载进度动画
     */
    protected void reLoad() {
        hasLoadSuccess = false;
        hasLoadComplete = false;
        onLoad();
    }

    private void showStatusView(int viewId) {
        for (View view : viewList) {
            if (view.getId() == viewId) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }


    protected void showLoadingView() {
        showStatusView(R.id.loading);
    }

    protected void showEmptyView() {
        showStatusView(R.id.empty);
    }

    protected void showErrorView(String message) {
        showStatusView(R.id.error);
        tvError.setText(message);
    }

    protected void showContentView() {
        showStatusView(R.id.content);
    }

    /**
     * 是否加载成功
     * 因为可能会在onResume方法中重新加载数据，如果已经时显示成功，则不再显示加载中、加载失败状态
     */
    boolean hasLoadSuccess = false;
    /**
     * 是否加载完毕
     */
    protected boolean hasLoadComplete = false;

    @Override
    public void onPageLoading() {
        if (!hasLoadSuccess) {
            showLoadingView();
        }
    }

    @Override
    public void onPageSuccess() {
        hasLoadSuccess = true;
        showContentView();
    }

    @Override
    public void onPageEmpty() {
        showEmptyView();
    }

    @Override
    public void onPageError(String message) {
        if (!hasLoadSuccess) {
            showErrorView(message);
        }
    }

    @Override
    public void onPageComplete() {
        hasLoadComplete = true;
    }

}
