package com.chhd.android.demo;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chhd.android.common.ui.fragment.PullToRefreshFragment;

import java.util.List;

public class FragmentTabActivity extends AppCompatActivity {

    Fragment fragment1 = new ListFragment();
    Fragment fragment2 = new ListFragment();
    Fragment fragment3 = new ListFragment();
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_tab);

        final FrameLayout flContainer = findViewById(R.id.fl_container);
        RadioGroup rg = findViewById(R.id.rg);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (currentFragment != null) {
                    getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
                }
                if (checkedId == R.id.rb1) {
                    currentFragment = fragment1;
                    if (fragment1.isAdded()) {
                        getSupportFragmentManager().beginTransaction().show(fragment1).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, fragment1).commit();
                    }
                } else if (checkedId == R.id.rb2) {
                    currentFragment = fragment2;
                    if (fragment2.isAdded()) {
                        getSupportFragmentManager().beginTransaction().show(fragment2).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, fragment2).commit();
                    }
                } else {
                    currentFragment = fragment3;
                    if (fragment3.isAdded()) {
                        getSupportFragmentManager().beginTransaction().show(fragment3).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, fragment3).commit();
                    }
                }
            }
        });
    }

    public static class ListFragment extends PullToRefreshFragment<ListAdapter, Object> {

        @Override
        protected ListAdapter onCreateAdapter() {
            return new ListAdapter(list);
        }

        @Override
        public void onLoad(boolean isLoadMore) {
            LogUtils.i("onLoad: " + this);
        }

        @Override
        protected int getContentResId() {
            return R.layout.fragment_list;
        }

        @Override
        protected void onInit(View view, @Nullable Bundle savedInstanceState) {
            TextView tv = view.findViewById(R.id.tv);
            tv.setText(toString());
        }
    }

    public static class ListAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

        public ListAdapter(@Nullable List<Object> data) {
            super(R.layout.item_list_text, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {

        }
    }
}
