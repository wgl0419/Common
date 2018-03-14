package com.chhd.android.common.entity;

import java.util.List;

/**
 * author : 葱花滑蛋
 * time   : 2018/03/13
 * desc   : BaseListData
 */
public abstract class BaseListData<T> {

    public abstract int getStart();

    public int getStart(boolean isLoadMore) {
        if (isLoadMore) {
            return getStart() + getList().size();
        }
        return 0;
    }

    public abstract boolean hasMore();

    public abstract List<T> getList();
}
