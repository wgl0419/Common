package com.chhd.android.common.ui.view.decoration.dp;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chhd.android.common.util.UiUtils;

/**
 * 垂直列表 - 间距装饰器，使用dp单位
 *
 * @author : 葱花滑蛋 (2018/7/3)
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    /**
     * item与item之间的边距
     */
    private float space;

    private int orientation = VERTICAL;

    /**
     * 第一个item与父容器的左边距
     */
    private float left;

    /**
     * 第一个item与父容器的上边距
     */
    private float top;

    /**
     * 第一个item与父容器的右边距
     */
    private float right;

    /**
     * 最后一个item与父容器的底边距
     */
    private float bottom;

    /**
     * 构造方法
     *
     * @param space 间距，单位dp
     */
    public SpaceItemDecoration(float space) {
        this.space = space;
    }

    /**
     * 构造方法
     *
     * @param space       间距，单位dp
     * @param orientation 方向，HORIZONTAL 或 VERTICAL
     */
    public SpaceItemDecoration(float space, int orientation) {
        this.space = space;
        this.orientation = orientation;
    }

    /**
     * 构造方法
     *
     * @param space       间距，单位dp
     * @param orientation 方向，HORIZONTAL 或 VERTICAL
     * @param left        第一个item与父容器的上边距，单位dp
     * @param top         第一个item与父容器的上边距，单位dp
     * @param right       第一个item与父容器的右边距，单位dp
     * @param bottom      最后一个item与父容器的底边距，单位dp
     */
    public SpaceItemDecoration(float space, int orientation,
                               float left, float top, float right, float bottom) {
        this.space = space;
        this.orientation = orientation;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Context context = parent.getContext();
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        int lastPosition = parent.getAdapter().getItemCount() - 1;
        if (orientation == VERTICAL) {
            if (childAdapterPosition == 0) {
                outRect.set(UiUtils.dp2px(context, left), UiUtils.dp2px(context, top),
                        UiUtils.dp2px(context, right), 0);
            } else if (childAdapterPosition == lastPosition) {
                outRect.set(UiUtils.dp2px(context, left), UiUtils.dp2px(context, space),
                        UiUtils.dp2px(context, right), UiUtils.dp2px(context, bottom));
            } else {
                outRect.set(UiUtils.dp2px(context, left), UiUtils.dp2px(context, space),
                        UiUtils.dp2px(context, right), 0);
            }
        } else {
            if (childAdapterPosition == 0) {
                outRect.set(UiUtils.dp2px(context, left), UiUtils.dp2px(context, top),
                        0, UiUtils.dp2px(context, bottom));
            } else if (childAdapterPosition == lastPosition) {
                outRect.set(UiUtils.dp2px(context, space), UiUtils.dp2px(context, top),
                        UiUtils.dp2px(context, right), UiUtils.dp2px(context, bottom));
            } else {
                outRect.set(UiUtils.dp2px(context, space), UiUtils.dp2px(context, top),
                        0, UiUtils.dp2px(context, bottom));
            }
        }
    }

}
