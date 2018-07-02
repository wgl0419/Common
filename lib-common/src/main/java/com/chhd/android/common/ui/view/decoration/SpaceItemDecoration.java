package com.chhd.android.common.ui.view.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 垂直列表 - 间距装饰器
 *
 * @author : 葱花滑蛋 (2017/11/21)
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    /**
     * item与item之间的边距
     */
    private int space;
    private int orientation = VERTICAL;

    /**
     * item与父容器的左边距
     */
    private int left;
    /**
     * 第一个item与父容器的上边距
     */
    private int top;
    /**
     * item与父容器的右边距
     */
    private int right;
    /**
     * 最后一个item与父容器的底边距
     */
    private int bottom;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    public SpaceItemDecoration(int space, int orientation) {
        this.space = space;
        this.orientation = orientation;
    }

    public SpaceItemDecoration(int space, int orientation, int left, int top, int right, int bottom) {
        this.space = space;
        this.orientation = orientation;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (orientation == VERTICAL) {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.set(left, top, right, 0);
            } else if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                outRect.set(left, space, right, bottom);
            } else {
                outRect.set(left, space, right, 0);
            }
        } else {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.set(left, top, 0, bottom);
            } else if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                outRect.set(space, top, right, bottom);
            } else {
                outRect.set(space, top, 0, bottom);
            }
        }
    }

}
