package com.chhd.android.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chhd.android.common.entity.BaseListData;
import com.chhd.android.common.entity.BaseResponseData;
import com.chhd.android.common.http.HttpObserver;
import com.chhd.android.common.http.ResponseTransformer;
import com.chhd.android.common.http.RetrofitProvider;
import com.chhd.android.common.http.RxHelper;
import com.chhd.android.common.ui.activity.base.ListTActivity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ListActivity extends ListTActivity<ListActivity.ListAdapter, Object> {

    @Override
    protected ListAdapter getAdapter() {
        return new ListAdapter(list);
    }

    @Override
    protected int getContentResId() {
        return R.layout.layout_pull_to_refresh;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {

    }

    @Override
    protected void onLoad(final boolean isLoadMore) {
        retrofit()
                .create(Api.class)
                .getList(listData.getPageStart(isLoadMore), 10)
                .compose(RxHelper.<ResponseData<ListData<Object>>>ioMainThread())
                .compose(ResponseTransformer.<ListData<Object>>transform())
                .compose(this.<ListData<Object>>bindUntilDestroy())
                .subscribe(new HttpObserver<ListData<Object>>() {
                    @Override
                    protected void onSucceed(ListData<Object> t) throws Exception {
                        t.setStart(listData.getPageStart(isLoadMore));
                        onLoadSuccess(t);
                    }
                });
    }

    @Override
    protected CharSequence getToolbarTitle() {
        return "列表";
    }

    public Retrofit retrofit() {
        return RetrofitProvider.newInstance("http://api.jisuapi.com/");
    }

    public interface Api {

        @GET("news/get?channel=头条&appkey=a51fa7d0325d05f2")
        Observable<ResponseData<ListData<Object>>> getList(@Query("start") int start, @Query("num") int num);
    }

    public static class ListAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

        public ListAdapter(@Nullable List<Object> data) {
            super(R.layout.item_list_text, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {

        }
    }

    public static class ResponseData<T> implements BaseResponseData<T> {

        private Integer status;

        private String msg;

        private T result;

        @Override
        public Integer getCode() {
            return status;
        }

        @Override
        public String getMessage() {
            return null;
        }

        @Override
        public T getData() {
            return result;
        }

        @Override
        public Boolean isSuccess() {
            return status == 0;
        }
    }

    public static class ListData<T> extends BaseListData<T> {

        private List<T> list;

        private int start;

        public void setStart(int start) {
            this.start = start;
        }

        @Override
        public Integer getPageStart() {
            return start;
        }

        @Override
        public Boolean isPageNext() {
            return !list.isEmpty();
        }

        @Override
        public List<T> getList() {
            return list;
        }
    }
}
