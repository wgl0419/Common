package com.chhd.android.demo;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chhd.android.common.http.HttpObserver;
import com.chhd.android.common.http.ResponseTransformer;
import com.chhd.android.common.http.RxHelper;
import com.chhd.android.common.ui.activity.PullToRefreshTActivity;
import com.chhd.android.common.ui.view.IPageView;

public class DemoList1Activity extends PullToRefreshTActivity<Adapter, Object> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentResId() {
        return R.layout.layout_pull_to_refresh;
    }

    @Override
    public Adapter getAdapter() {
        return new Adapter(list);
    }

    @Override
    public void onLoad(boolean isLoadMore) {
        HttpUtils
                .retrofit("http://test.hulubao.com")
                .create(ApiService.class)
                .getRentList(listData.getStart(isLoadMore) + "")
                .compose(RxHelper.<ResponseData<ListData<Object>>>observableIoMainThread())
                .compose(ResponseTransformer.<ListData<Object>>observableTransform())
                .subscribe(new HttpObserver<ListData<Object>>() {
                    @Override
                    public void onSucceed(ListData<Object> listData) {
                        onLoadSuccess(listData);
                    }

                    @Override
                    protected IPageView showPageView() {
                        return (IPageView) instance;
                    }
                });
    }
}
