package com.chhd.android.common.ui.view.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author : 葱花滑蛋
 * @date :  2017/11/16
 */

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 多少列
     */
    private int spanCount;
    /**
     * item与item之间的边距
     */
    private int space;

    /**
     * 第一列item与父容器的左边距
     */
    private int left;
    /**
     * 第一行item与父容器的上边距
     */
    private int top;
    /**
     * 最后一列item与父容器的右边距
     */
    private int right;
    /**
     * 最后一行item与父容器的底边距
     */
    private int bottom;

    public GridSpaceItemDecoration(int spanCount, int space) {
        this.spanCount = spanCount;
        this.space = space;
    }

    public GridSpaceItemDecoration(int spanCount, int space, int left, int top, int right, int bottom) {
        this.spanCount = spanCount;
        this.space = space;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // item position
        int position = parent.getChildAdapterPosition(view);
        // item column
        int column = position % spanCount;

        // column * ((1f / spanCount) * space)
        outRect.left = column == 0 ? left : column * space / spanCount;
        // space - (column + 1) * ((1f /    spanCount) * space)
        outRect.right = column == spanCount - 1 ? right : space - (column + 1) * space / spanCount;

        if (position >= spanCount) {
            // item top
            outRect.top = space;
        }
        outRect.top = position < spanCount ? top : space;

        int firstColumnLastRowIndex = getFirstColumnLastRowIndex(parent, spanCount);
        outRect.bottom = position >= firstColumnLastRowIndex ? bottom : 0;
    }

    /**
     * 获取第一列最后一行的下标
     */
    private int getFirstColumnLastRowIndex(RecyclerView parent, int spanCount) {
        int index = 0;
        try {
            int childCount = parent.getChildCount();
            // 余数
            int mod = childCount % spanCount;
            if (mod != 0) {
                index = childCount - mod - 1;
            } else {
                index = childCount - spanCount - 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }
}
