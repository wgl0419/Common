package com.chhd.android.common.entity;

import java.util.List;

/**
 * @author : 葱花滑蛋 (2018/03/13)
 */

public abstract class BaseListData<T> {

    /**
     * 请求开始位置
     *
     * @return int
     */
    public abstract Integer getPageStart();

    public int getPageStart(boolean isLoadMore) {
        if (isLoadMore) {
            if (getPageStart() == null) {
                return getList().size();
            }
            return getPageStart() + getList().size();

        }
        return 0;
    }

    /**
     * 是否有下一页
     *
     * @return boolean
     */
    public abstract Boolean isPageNext();

    /**
     * 获取列表
     *
     * @return List
     */
    public abstract List<T> getList();
}
