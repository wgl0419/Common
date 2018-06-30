package com.chhd.android.demo;

import com.chhd.android.common.entity.BaseListData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListData<T> extends BaseListData<T> {

    private int start;

    private List<T> list;

    @SerializedName("have_next")
    private int hasMore;

    @Override
    public Integer getPageStart() {
        return start;
    }

    @Override
    public int getPageStart(boolean isLoadMore) {
        if (isLoadMore) {
            if (getPageStart() == null) {
                return 1;
            }
            return getPageStart() + 1;

        }
        return 0;
    }

    @Override
    public Boolean isPageNext() {
        return hasMore == 1;
    }

    @Override
    public List<T> getList() {
        return list;
    }
}
