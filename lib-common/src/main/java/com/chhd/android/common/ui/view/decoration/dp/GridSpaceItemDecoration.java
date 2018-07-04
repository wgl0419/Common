package com.chhd.android.common.ui.view.decoration.dp;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 网格列表 - 间距装饰器，使用dp单位
 *
 * @author : 葱花滑蛋 (2018/7/3)
 */

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * item与item之间的水平边距
     */
    private float horSpace;

    /**
     * item与item之间的垂直边距
     */
    private float verSpace;

    /**
     * 第一列item与父容器的左边距
     */
    private float left;

    /**
     * 第一行item与父容器的上边距
     */
    private float top;

    /**
     * 最后一列item与父容器的右边距
     */
    private float right;

    /**
     * 最后一行item与父容器的底边距
     */
    private float bottom;

    /**
     * 构造方法
     *
     * @param space 间距，单位dp
     */
    public GridSpaceItemDecoration(float space) {
        this(space, space);
    }

    /**
     * 构造方法
     *
     * @param horSpace 水平间距，单位dp
     * @param verSpace 垂直间距，单位dp
     */
    public GridSpaceItemDecoration(float horSpace, float verSpace) {
        this.horSpace = horSpace;
        this.verSpace = verSpace;
    }

    /**
     * 构造方法
     *
     * @param space  间距，单位dp
     * @param left   第一列item与父容器的左边距，单位dp
     * @param top    第一行item与父容器的上边距，单位dp
     * @param right  最后一列item与父容器的右边距，单位dp
     * @param bottom 最后一行item与父容器的底边距，单位dp
     */
    public GridSpaceItemDecoration(float space,
                                   float left, float top, float right, float bottom) {
        this(space, space, left, top, right, bottom);
    }

    /**
     * 构造方法
     *
     * @param horSpace 水平间距，单位dp
     * @param verSpace 垂直间距，单位dp
     * @param left     第一列item与父容器的左边距，单位dp
     * @param top      第一行item与父容器的上边距，单位dp
     * @param right    最后一列item与父容器的右边距，单位dp
     * @param bottom   最后一行item与父容器的底边距，单位dp
     */
    public GridSpaceItemDecoration(float horSpace, float verSpace,
                                   float left, float top, float right, float bottom) {
        this.horSpace = horSpace;
        this.verSpace = verSpace;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Context context = parent.getContext();
        // item position
        int position = parent.getChildAdapterPosition(view);
        // spanCount
        int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        // item column
        int column = position % spanCount;

        // column * ((1f / spanCount) * space)
        outRect.left = column == 0 ? dp2px(context, left) :
                column * dp2px(context, horSpace) / spanCount;
        // space - (column + 1) * ((1f /    spanCount) * space)
        outRect.right = column == spanCount - 1 ? dp2px(context, right) :
                dp2px(context, horSpace) - (column + 1) * dp2px(context, horSpace) / spanCount;

        if (position >= spanCount) {
            // item top
            outRect.top = dp2px(context, verSpace);
        }
        outRect.top = position < spanCount ? dp2px(context, top) : dp2px(context, verSpace);

        int firstColumnLastRowIndex = getFirstColumnLastRowIndex(parent, spanCount);
        outRect.bottom = position >= firstColumnLastRowIndex ? dp2px(context, bottom) : 0;
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

    private int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
