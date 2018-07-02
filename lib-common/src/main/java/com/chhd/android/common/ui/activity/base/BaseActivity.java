package com.chhd.android.common.ui.activity.base;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.chhd.android.common.R;
import com.chhd.android.common.mvp.IBaseView;
import com.chhd.android.common.util.UiUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseActivity
 *
 * @author : 葱花滑蛋 (2018/03/09)
 */

public class BaseActivity extends RxAppCompatActivity implements IBaseView, View.OnTouchListener {

    public static List<Activity> activities = new ArrayList<>();

    protected final int MENU_ITEM_DEFAULT_ID = 10;
    protected final String TAG = this.getClass().getSimpleName();

    protected Activity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        activities.add(this);

        initScreenOrientation();
    }

    /**
     * 设置菜单栏
     *
     * @param toolbar      toolbar
     * @param title        标题
     * @param showHomeAsUp 是否显示Home键
     */
    protected void setToolbar(Toolbar toolbar, CharSequence title, boolean showHomeAsUp) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }

    /**
     * 初始化屏幕的方向
     */
    private void initScreenOrientation() {
        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.screenOrientation, outValue, true);

        if (outValue.data == 1) {
            // 横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (outValue.data == 2) {
            // 竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    @Override
    protected void onDestroy() {
        activities.remove(this);
        super.onDestroy();
    }

    /**
     * 触摸EditText以外的地方隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev) && isAutoHideKeyboard()) {
                hideSoftInput();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 是否开启，触摸EditText以外的地方，自动隐藏软键盘，默认开启
     *
     * @return boolean
     */
    protected boolean isAutoHideKeyboard() {
        return true;
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntilDestroy() {
        return bindUntilEvent(ActivityEvent.DESTROY);
    }

    /**
     * 默认实现Home键功能
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * view.setOnTouchListener(this)，实现按压效果
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ObjectAnimator upAnim = ObjectAnimator.ofFloat(v, "translationZ",
                        UiUtils.dp2px(4));
                upAnim.setDuration(150);
                upAnim.setInterpolator(new DecelerateInterpolator());
                upAnim.start();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                ObjectAnimator downAnim = ObjectAnimator.ofFloat(v, "translationZ",
                        0);
                downAnim.setDuration(150);
                downAnim.setInterpolator(new AccelerateInterpolator());
                downAnim.start();
                break;
            default:
                break;
        }
        return false;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu instanceof MenuBuilder) {
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;
            // 如果主题色是白色，右侧图标使用和返回箭头、标题文字一样的颜色
            if (color == -1) {
                MenuBuilder builder = (MenuBuilder) menu;
                ArrayList<MenuItemImpl> actionItems = builder.getActionItems();
                for (MenuItemImpl item : actionItems) {
                    Drawable icon = item.getIcon();
                    if (icon != null) {
                        color = ContextCompat.getColor(instance, R.color.color_text_dark);
                        icon.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                    }
                }
            }
        }
        return super.onCreateOptionsMenu(menu);
    }
}
