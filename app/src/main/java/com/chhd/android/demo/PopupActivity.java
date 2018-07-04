package com.chhd.android.demo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chhd.android.common.ui.activity.base.ToolbarActivity;
import com.chhd.android.common.ui.view.popup.Popup;
import com.chhd.android.common.ui.view.popup.PopupGridView;
import com.chhd.android.common.ui.view.popup.PopupListView;
import com.chhd.android.common.util.ToastUtils;
import com.chhd.android.common.util.UiUtils;

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
                .setOnItemClickListener(new Popup.OnItemClickListener<User>() {

                    @Override
                    public void onItemClick(PopupWindow popup, User o, int position) {
                        PopupActivity.this.user = o;
                        ToastUtils.show(o.getName() + "");
                        popup.dismiss();
                    }
                })
                .setOnItemChildClickListener(new Popup.OnItemChildClickListener<User>() {
                    @Override
                    public void onItemChildClick(PopupWindow popup, User o, int position) {
                        PopupActivity.this.user = o;
                        ToastUtils.show(o.getName() + "");
                        popup.dismiss();
                    }
                })
                .show(v);
    }

    private Object user;

    private void show(View v) {
        new PopupListView
                .Builder(this)
                .setItems(userList)
                .setCheck(user)
                .setDividerHeight(3)
                .setOnItemClickListener(new Popup.OnItemClickListener<User>() {

                    @Override
                    public void onItemClick(PopupWindow popup, User o, int position) {
                        PopupActivity.this.user = o;
                        ToastUtils.show(o.getName() + "");
                        popup.dismiss();
                    }
                })
                .setOnItemChildClickListener(new Popup.OnItemChildClickListener<User>() {
                    @Override
                    public void onItemChildClick(PopupWindow popup, User o, int position) {
                        PopupActivity.this.user = o;
                        ToastUtils.show(o.getName() + "");
                        popup.dismiss();
                    }
                })
                .show(v);
    }

    public static class User {

        private String id;

        private String name;

        public User(String id, String name) {
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


