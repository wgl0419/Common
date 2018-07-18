package com.chhd.android.common.ui.view.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.TextView;

import com.chhd.android.common.R;
import com.chhd.android.common.util.UiUtils;

import java.util.List;

/**
 * PopupGridView
 *
 * @author : 葱花滑蛋 (2018/7/3)
 */

public class PopupGridView<T> extends Popup<T> {

    private int bgColor = INVALID;
    private int column = 4;
    private float horSpacing = 8;
    private float verSpacing = 8;
    private float pLeft = 8;
    private float pTop = 8;
    private float pRight = 8;
    private float pBottom = 8;

    private PopupGridView(Activity activity) {
        super(activity);
    }

    @Override
    AbsListView initAbsListView() {
        GridView gridView = new GridView(activity);
        if (bgColor == INVALID) {
            gridView.setBackgroundColor(Color.parseColor("#fbfcfe"));
        } else {
            gridView.setBackgroundColor(bgColor);
        }
        gridView.setPadding(UiUtils.dp2px(activity, pLeft), UiUtils.dp2px(activity, pTop),
                UiUtils.dp2px(activity, pRight), UiUtils.dp2px(activity, pBottom));
        gridView.setCacheColorHint(Color.TRANSPARENT);
        gridView.setNumColumns(column);
        gridView.setHorizontalSpacing(UiUtils.dp2px(activity, horSpacing));
        gridView.setVerticalSpacing(UiUtils.dp2px(activity, verSpacing));
        gridView.setClipToPadding(false);
        if (adapter != null) {
            gridView.setAdapter(adapter);
        } else {
            adapter = new DefaultAdapter<>(itemList, check);
            gridView.setAdapter(adapter);
        }
        return gridView;
    }

    public static class Builder extends Popup.Builder<PopupGridView.Builder, PopupGridView> {

        public Builder(Activity activity) {
            popup = new PopupGridView(activity);
        }

        /**
         * 设置GridView的背景颜色
         *
         * @param color color
         * @return Builder
         */
        public Builder setBackground(@ColorInt int color) {
            popup.bgColor = color;
            return this;
        }

        /**
         * 设置多少列
         *
         * @param column 列
         * @return Builder
         */
        public Builder setColumn(int column) {
            popup.column = column;
            return this;
        }

        /**
         * 设置列表项之间左右、上下边距
         *
         * @param horSpacing 左右边距，单位dp
         * @param verSpacing 上下边距，单位dp
         * @return Builder
         */
        public Builder setSpacing(float horSpacing, float verSpacing) {
            popup.horSpacing = horSpacing;
            popup.verSpacing = verSpacing;
            return this;
        }

        /**
         * 设置GridView上下左右间距
         *
         * @param l 左，单位dp
         * @param t 上，单位dp
         * @param r 右，单位dp
         * @param b 下，单位dp
         * @return Builder
         */
        public Builder setPadding(int l, int t, int r, int b) {
            popup.pLeft = l;
            popup.pTop = t;
            popup.pRight = r;
            popup.pBottom = b;
            return this;
        }
    }

    private static class DefaultAdapter<T> extends ListAdapter<T> {

        public DefaultAdapter(List<T> itemList) {
            super(itemList);
        }

        public DefaultAdapter(List<T> itemList, T check) {
            super(itemList, check);
        }

        @Override
        public View convert(int position, View convertView, ViewGroup parent) {
            DefaultAdapter.Holder holder;
            if (convertView == null) {
                convertView = buildTextView(context);
                holder = new DefaultAdapter.Holder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.textView.setText(getItem(position).toString());
            if (check != null && check.equals(getItem(position))) {
                TypedValue outValue = new TypedValue();
                context.getTheme().resolveAttribute(R.attr.colorAccent, outValue, true);
                int color = outValue.data;
                holder.textView.setTextColor(color);
            } else {
                holder.textView.setTextColor(getColor(R.color.color_text_dark));
            }
            return convertView;
        }

        private TextView buildTextView(Context context) {
            TextView textView = new TextView(context);
            ViewCompat.setBackground(textView, buildDefaultDrawable());
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setMaxLines(1);
            textView.setGravity(Gravity.CENTER);
            int p4 = UiUtils.dp2px(context, 4f);
            int p8 = UiUtils.dp2px(context, 8f);
            textView.setPadding(p4, p8, p4, p8);
            textView.setTextColor(getColor(R.color.color_text_dark));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            return textView;
        }

        private Drawable buildDefaultDrawable() {
            int strokeWidth = UiUtils.dp2px(context, 0.5f);
            int roundRadius = UiUtils.dp2px(context, 2);
            int strokeColor = getColor(R.color.color_line);
            int fillColor = Color.WHITE;

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(roundRadius);
            gd.setColor(fillColor);
            gd.setStroke(strokeWidth, strokeColor);
            return gd;
        }

        private class Holder {

            TextView textView;

            Holder(View itemView) {
                textView = (TextView) itemView;
            }
        }
    }
}
