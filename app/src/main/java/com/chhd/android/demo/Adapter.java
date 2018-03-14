package com.chhd.android.demo;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class Adapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public Adapter(@Nullable List<Object> data) {
        super(R.layout.item_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

    }
}
