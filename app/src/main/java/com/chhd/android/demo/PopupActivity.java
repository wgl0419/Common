package com.chhd.android.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chhd.android.common.ui.activity.ToolbarActivity;
import com.chhd.android.common.ui.view.popup.Popup;
import com.chhd.android.common.ui.view.popup.PopupGridView;
import com.chhd.android.common.ui.view.popup.PopupListView;
import com.chhd.android.common.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class PopupActivity extends ToolbarActivity {

    private List<User> userList;

    {
        userList = new ArrayList<>();
        userList.add(new User("0", "张三"));
        userList.add(new User("1", "招野"));
        userList.add(new User("2", "主管"));
        userList.add(new User("3", "李四"));
        userList.add(new User("4", "天王盖地虎，小鸡炖蘑菇"));
        userList.add(new User("5", "订单"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(v);
            }
        });

        findViewById(R.id.btn_show_grid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGrid(v);
            }
        });

    }

    private void showGrid(View v) {
        new PopupGridView
                .Builder(this)
                .setItems(userList)
                .setCheck(user)
                .setSpacing(8, 16)
                .setOnItemClickListener(new Popup.OnItemClickListener() {

                    @Override
                    public void onItemClick(PopupWindow popup, View view, int position) {
                    }
                })
                .setOnItemChildClickListener(new Popup.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(PopupWindow popup, View view, int position) {
                    }
                })
                .show(v);
    }

    private Object user;

    private void show(View v) {
        new PopupListView
                .Builder(this)
                .setItems(new DefAdapter(userList))
                .setOnItemClickListener(new Popup.OnItemClickListener() {

                    @Override
                    public void onItemClick(PopupWindow popup, View view, int position) {
                        ToastUtils.show("onItemClick: " + view);
                    }
                })
                .setOnItemChildClickListener(new Popup.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(PopupWindow popup, View view, int position) {
                        ToastUtils.show("onItemChildClick: " + view);
                    }
                })
                .show(v);
    }

    private static class DefAdapter extends Popup.ListAdapter {

        public DefAdapter(List itemList) {
            super(itemList);
        }

        @Override
        public View convert(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context)
                        .inflate(R.layout.item_list_text, parent, false);
            }
            return convertView;
        }
    }

    private static class User {

        private String id;

        private String name;

        User(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            User user = (User) o;

            return id != null ? id.equals(user.id) : user.id == null;
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }
    }
}


