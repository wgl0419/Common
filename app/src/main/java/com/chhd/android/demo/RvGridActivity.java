package com.chhd.android.demo;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chhd.android.common.ui.view.decoration.dp.GridSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class RvGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_grid);

        List<String> stringList = new ArrayList<>();
        stringList.add("妇女");
        stringList.add("不做");
        stringList.add("二十");
        stringList.add("多少");
        stringList.add("景观");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new ListAdapter(stringList));
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(8, 8));
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
