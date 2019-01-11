package com.chhd.android.common.ui.fragment;

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

    protected IPageView pageView = this;

    protected List<View> viewList = new ArrayList<>();

    protected View loadingView;
    protected View errorView;
    protected View emptyView;
    protected FrameLayout contentView;

    protected TextView tvError;
    protected TextView tvEmpty;

    protected Button btnRetry;
    protected Button btnRefresh;

    boolean isStopped = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onPrepare(view, savedInstanceState);

        onInit(view, savedInstanceState);

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

    protected void onPrepare(View view, @Nullable Bundle savedInstanceState) {
        isLoadSuccess = false;
        isLoadComplete = false;

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
                isLoadSuccess = false;
                isLoadComplete = false;
                onLoad();
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLoadSuccess = false;
                isLoadComplete = false;
                onLoad();
            }
        });
    }

    /**
     * 初始化
     *
     * @param view               根布局
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void onInit(View view, @Nullable Bundle savedInstanceState);

    /**
     * 加载
     */
    public abstract void onLoad();

    /**
     * 重新加载，带加载进度动画
     */
    public void reLoad() {
        isLoadSuccess = false;
        isLoadComplete = false;
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


    public void showLoadingView() {
        showStatusView(R.id.loading);
    }

    public void showEmptyView() {
        showStatusView(R.id.empty);
    }

    public void showErrorView(String message) {
        showStatusView(R.id.error);
        tvError.setText(message);
    }

    public void showContentView() {
        showStatusView(R.id.content);
    }

    /**
     * 是否加载成功
     * 因为可能会在onResume方法中重新加载数据，如果已经时显示成功，则不再显示加载中、加载失败状态
     */
    boolean isLoadSuccess = false;

    /**
     * 是否加载完毕
     */
    boolean isLoadComplete = false;

    @Override
    public void onPageLoading() {
        if (!isLoadSuccess) {
            showLoadingView();
        }
    }

    @Override
    public void onPageSuccess() {
        isLoadSuccess = true;
        showContentView();
    }

    @Override
    public void onPageEmpty() {
        showEmptyView();
    }

    @Override
    public void onPageError(String message) {
        if (!isLoadSuccess) {
            showErrorView(message);
        }
    }

    @Override
    public void onPageComplete() {
        isLoadComplete = true;
    }

    protected boolean isLoadComplete() {
        return isLoadComplete;
    }

    @Override
    public void onStop() {
        isStopped = true;
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isAutoLoad() && isStopped && isLoadComplete() && isOpenResumeLoad()) {
            onResumeLoad();
        }
    }

    /**
     * 可见并第一次加载完毕回调此方法
     */
    protected void onResumeLoad() {
        onLoad();
    }

    protected boolean isOpenResumeLoad() {
        return getActivity().getResources().getBoolean(R.bool.resume_load);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (isAutoLoad() && isLoadComplete() && isOpenVisibleLoad() && !hidden) {
            onVisibleLoad();
        }
    }

    protected void onVisibleLoad() {
        onLoad();
    }

    protected boolean isOpenVisibleLoad() {
        return getActivity().getResources().getBoolean(R.bool.visible_load);
    }
}
