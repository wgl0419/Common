package com.chhd.android.common.ui.view.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chhd.android.common.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基类
 *
 * @author : 葱花滑蛋 (2018/7/3)
 */

public abstract class Popup<T> {

    static int INVALID = -1;

    final String TAG = this.getClass().getSimpleName();

    PopupWindow popupWindow;

    Activity activity;

    List<T> itemList = new ArrayList<>();
    T check;
    float alpha = 0.3f;
    boolean dismiss = true;

    ListAdapter<T> adapter;

    int popupWidth = ViewGroup.LayoutParams.MATCH_PARENT;
    int popupHeight = ViewGroup.LayoutParams.MATCH_PARENT;
    boolean focusable = true;
    boolean touchable = true;
    int anim = R.style.PopupFadeAnim;

    OnItemClickListener<T> onItemClickListener;
    OnItemChildClickListener<T> onItemChildClickListener;

    Popup(Activity activity) {
        this.activity = activity;
    }

    PopupWindow build() {
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        int color = Color.parseColor("#" + alpha2hex(alpha) + "000000");
        linearLayout.setBackgroundColor(color);
        AbsListView absListView = initAbsListView();
        adapter.setOnItemChildClickListener(new ListAdapter.OnItemChildClickListener<T>() {
            @Override
            public void onItemChildClick(T t, int position) {
                if (dismiss) {
                    popupWindow.dismiss();
                }
                if (onItemChildClickListener != null) {
                    onItemChildClickListener.onItemChildClick(popupWindow, t, position);
                }
            }
        });
        absListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dismiss) {
                    popupWindow.dismiss();
                }
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(popupWindow, itemList.get(position), position);
                }
            }
        });
        linearLayout.addView(absListView);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (touchable) {
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow = new PopupWindow(linearLayout, popupWidth, popupHeight);
        popupWindow.setFocusable(focusable);
        popupWindow.setOutsideTouchable(touchable);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setAnimationStyle(anim);
        return popupWindow;
    }

    abstract AbsListView initAbsListView();

    int getColor(Context context, int id) {
        return context.getResources().getColor(id);
    }

    int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * 透明度百分比转16进制
     *
     * @param alpha alpha
     * @return return
     */
    String alpha2hex(float alpha) {
        float temp = 255 * alpha * 100 * 1.0f / 100f;
        int round = Math.round(temp);
        String hexString = Integer.toHexString(round);
        if (hexString.length() < 2) {
            hexString += "0";
        }
        return hexString.toUpperCase();
    }

    public static class Builder<B extends Builder, P extends Popup> {

        P popup;
        B b;

        Builder() {
            b = (B) this;
        }

        /**
         * 设置数据源
         *
         * @param list 数据源，实体类需要重写“toString”
         * @param <T>  数据类型
         * @return Builder
         */
        public <T> B setItems(List<T> list) {
            popup.itemList = list;
            return b;
        }

        /**
         * 设置数据源
         *
         * @param t   数据源，实体类需要重写“toString”
         * @param <T> 数据类型
         * @return Builder
         */
        public <T> B setItems(T... t) {
            popup.itemList = Arrays.asList(t);
            return b;
        }

        /**
         * 设置已选项
         *
         * @param check 已选项，实体类需要重写“hashCode”和“equals”
         * @param <T>   数据类型
         * @return Builder
         */
        public <T> B setCheck(T check) {
            popup.check = check;
            return b;
        }

        /**
         * 是否点击Item或子View自动消失
         *
         * @param dismiss dismiss
         * @return Builder
         */
        public B setAutoDismiss(boolean dismiss) {
            popup.dismiss = dismiss;
            return b;
        }

        /**
         * 设置背景透明度
         *
         * @param alpha 透明度(0.0-1.0)
         * @return Builder
         */
        public B setBackgroundAlpha(float alpha) {
            popup.alpha = alpha;
            return b;
        }

        /**
         * 自定义适配器，当默认适配器不满足使用
         *
         * @param adapter adapter
         * @return Builder
         */
        public B setAdapter(ListAdapter adapter) {
            popup.adapter = adapter;
            return b;
        }

        /**
         * 设置Popup窗口大小
         *
         * @param popupWidth  默认“MATCH_PARENT”，单位：px
         * @param popupHeight 默认“MATCH_PARENT”，单位：px
         * @return Builder
         */
        public B setSize(int popupWidth, int popupHeight) {
            popup.popupWidth = popupWidth;
            popup.popupHeight = popupHeight;
            return b;
        }

        /**
         * 是否通过返回键取消，是否使其他控件不可点击
         *
         * @param focusable 默认是true，不建议修改
         * @return Builder
         */
        @Deprecated
        public B setFocusable(boolean focusable) {
            popup.focusable = focusable;
            return b;
        }

        /**
         * 是否点击外面消失
         *
         * @param touchable 默认是true
         * @return Builder
         */
        public B setOutsideTouchable(boolean touchable) {
            popup.touchable = touchable;
            return b;
        }

        /**
         * 设置动画
         *
         * @param anim style资源
         * @return Builder
         */
        public B setAnim(int anim) {
            popup.anim = anim;
            return b;
        }

        /**
         * 设置ItemView的点击事件
         *
         * @param onItemClickListener onItemClickListener
         * @return Builder
         */
        public <T> B setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
            popup.onItemClickListener = onItemClickListener;
            return b;
        }

        /**
         * 设置子View的点击事件，如果设置id自动添加点击事件
         *
         * @param onItemChildClickListener onItemChildClickListener
         * @return Builder
         */
        public <T> B setOnItemChildClickListener(OnItemChildClickListener<T> onItemChildClickListener) {
            popup.onItemChildClickListener = onItemChildClickListener;
            return b;
        }

        public PopupWindow build() {
            return popup.build();
        }

        public PopupWindow show(View view) {
            return show(view, 0, 0);
        }

        public PopupWindow show(View view, int xoff, int yoff) {
            PopupWindow popup = build();
            showAsDropDown(popup, view, xoff, yoff);
            return popup;
        }

        private void showAsDropDown(PopupWindow pw, View anchor, int xoff, int yoff) {
            if (Build.VERSION.SDK_INT >= 24) {
                Rect rect = new Rect();
                anchor.getGlobalVisibleRect(rect);
                int screenHeight = anchor.getResources().getDisplayMetrics().heightPixels;
                int height = screenHeight - rect.bottom - yoff;
                pw.setHeight(height);
                pw.showAsDropDown(anchor, xoff, yoff);
            } else {
                pw.showAsDropDown(anchor, xoff, yoff);
            }
        }
    }

    public interface OnItemChildClickListener<T> {

        void onItemChildClick(PopupWindow popup, T t, int position);
    }

    public interface OnItemClickListener<T> {

        void onItemClick(PopupWindow popup, T t, int position);
    }

    public static abstract class ListAdapter<T> extends BaseAdapter {

        protected Context context;

        protected List<T> itemList;
        protected T check;

        private OnItemChildClickListener<T> onItemChildClickListener;

        public void setOnItemChildClickListener(OnItemChildClickListener<T> onItemChildClickListener) {
            this.onItemChildClickListener = onItemChildClickListener;
        }

        public ListAdapter(List<T> itemList) {
            this.itemList = itemList;
        }

        public ListAdapter(List<T> itemList, T check) {
            this.itemList = itemList;
            this.check = check;
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public T getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            context = parent.getContext();
            View itemView = convert(position, convertView, parent);
            traversal(itemView, getItem(position), position);
            return itemView;
        }

        protected int getColor(int id) {
            return context.getResources().getColor(id);
        }

        protected int dp2px(float dp) {
            float density = context.getResources().getDisplayMetrics().density;
            return (int) (dp * density + 0.5f);
        }

        public abstract View convert(int position, View convertView, ViewGroup parent);

        private void traversal(View parent, T t, int position) {
            if (parent == null) {
                return;
            }
            if (parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                int childCount = viewGroup.getChildCount();
                for (int i = childCount - 1; i >= 0; i--) {
                    View child = viewGroup.getChildAt(i);
                    traversal(child, t, position);
                }
            }
            addChildClickListener(parent, t, position);
        }

        private void addChildClickListener(View view, final T t, final int position) {
            if (view.getId() == INVALID) {
                return;
            }
            if (view instanceof AdapterView) {
                return;
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemChildClickListener == null) {
                        return;
                    }
                    onItemChildClickListener.onItemChildClick(t, position);
                }
            });
        }

        public interface OnItemChildClickListener<T> {

            void onItemChildClick(T t, int position);
        }
    }
}
