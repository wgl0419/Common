package com.chhd.android.demo;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chhd.android.common.ui.adapter.FragmentAdapter;
import com.chhd.android.common.ui.view.decoration.dp.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class RvListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_list);

        List<String> stringList = new ArrayList<>();
        stringList.add("妇女");
        stringList.add("不做");
        stringList.add("二十");
        stringList.add("多少");
        stringList.add("景观");

        RecyclerView rv1 = findViewById(R.id.rv1);
        rv1.setLayoutManager(new LinearLayoutManager(this));
        rv1.setAdapter(new ListAdapter(stringList));
        rv1.addItemDecoration(new SpaceItemDecoration(16));

        RecyclerView rv2 = findViewById(R.id.rv2);
        rv2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        rv2.setAdapter(new ListAdapter(stringList));
        rv2.addItemDecoration(new SpaceItemDecoration(16, SpaceItemDecoration.HORIZONTAL));

    }

    private static class ListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        ListAdapter(@Nullable List<String> data) {
            super(R.layout.item_list_text, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView textView = (TextView) helper.itemView;
            textView.setText(item);
        }
    }
}
