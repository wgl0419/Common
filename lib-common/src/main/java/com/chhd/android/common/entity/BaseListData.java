package com.chhd.android.common.entity;

import java.util.List;

/**
 * @author : 葱花滑蛋
 * @date : 2018/03/13
 */

public abstract class BaseListData<T> {

    /**
     * 请求开始位置
     *
     * @return int
     */
    public abstract int getStart();

    public int getStart(boolean isLoadMore) {
        if (isLoadMore) {
            return getStart() + getList().size();
        }
        return 0;
    }

    /**
     * 是否有下一页
     *
     * @return boolean
     */
    public abstract boolean hasMore();

    /**
     * 获取列表
     *
     * @return List
     */
    public abstract List<T> getList();
}
