package com.chhd.android.common.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chhd.android.common.R;
import com.chhd.android.common.mvp.IPageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 进度界面，不带Toolbar
 *
 * @author : 葱花滑蛋 (2018/03/13)
 */
public abstract class ProgressActivity extends BaseActivity implements IPageView {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        onPrepare(savedInstanceState);

        onInit(savedInstanceState);

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

    protected void onPrepare(Bundle savedInstanceState) {
        loadingView = findViewById(R.id.loading);
        errorView = findViewById(R.id.error);
        emptyView = findViewById(R.id.empty);
        contentView = findViewById(R.id.content);

        tvError = findViewById(R.id.tv_error);
        tvEmpty = findViewById(R.id.tv_empty);

        btnRetry = findViewById(R.id.btn_retry);
        btnRefresh = findViewById(R.id.btn_refresh);

        viewList.add(loadingView);
        viewList.add(errorView);
        viewList.add(emptyView);
        viewList.add(contentView);

        showContentView();

        if (getContentResId() != 0) {
            LayoutInflater.from(this).inflate(getContentResId(), contentView, true);
        }

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLoad();
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLoad();
            }
        });
    }

    /**
     * 初始化
     */
    protected abstract void onInit(@Nullable Bundle savedInstanceState);

    /**
     * 加载
     */
    public abstract void onLoad();

    /**
     * 重新加载，带加载进度动画
     */
    public void reLoad() {
        hasLoadSuccess = false;
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
    boolean hasLoadSuccess = false;

    /**
     * 是否加载完毕
     */
    boolean isLoadComplete = false;

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
        isLoadComplete = true;
    }

    protected boolean isLoadComplete() {
        return isLoadComplete;
    }
}
