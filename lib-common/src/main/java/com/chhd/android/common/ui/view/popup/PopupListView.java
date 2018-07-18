package com.chhd.android.common.ui.view.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chhd.android.common.R;
import com.chhd.android.common.util.UiUtils;

import java.util.List;

/**
 * PopupListView
 *
 * @author : 葱花滑蛋 (2018/6/27)
 */

public class PopupListView<T> extends Popup<T> {

    private Drawable divider;
    private float dividerHeight = 0.5f;

    private PopupListView(Activity activity) {
        super(activity);
    }

    @Override
    AbsListView initAbsListView() {
        ListView listView = new ListView(activity);
        listView.setCacheColorHint(Color.TRANSPARENT);
        if (divider == null) {
            divider = new ColorDrawable(getColor(activity, R.color.color_line));
        }
        listView.setDivider(divider);
        listView.setDividerHeight(UiUtils.dp2px(activity, dividerHeight));
        listView.setClipToPadding(false);
        if (adapter != null) {
            listView.setAdapter(adapter);
        } else {
            adapter = new DefaultAdapter<T>(itemList, check);
            listView.setAdapter(adapter);
        }
        return listView;
    }

    public static class Builder extends Popup.Builder<PopupListView.Builder, PopupListView> {

        public Builder(Activity activity) {
            popup = new PopupListView(activity);
        }

        /**
         * 设置分割线颜色
         *
         * @param divider divider
         * @return Builder
         */
        public Builder setDivider(Drawable divider) {
            popup.divider = divider;
            return this;
        }

        /**
         * 设置分割线高度
         *
         * @param dividerHeight 分割线高度，单位dp
         * @return Builder
         */
        public Builder setDividerHeight(float dividerHeight) {
            popup.dividerHeight = dividerHeight;
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
            Holder holder;
            if (convertView == null) {
                convertView = buildTextView(context);
                holder = new Holder(convertView);
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
            textView.setBackgroundColor(Color.WHITE);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setMaxLines(2);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            int p16 = UiUtils.dp2px(context, 16f);
            int p12 = UiUtils.dp2px(context, 12f);
            textView.setPadding(p16, p12, p16, p12);
            textView.setTextColor(getColor(R.color.color_text_dark));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            return textView;
        }

        private class Holder {

            TextView textView;

            Holder(View itemView) {
                textView = (TextView) itemView;
            }
        }
    }
}
