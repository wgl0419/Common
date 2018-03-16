package com.chhd.android.common.ui.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.chhd.android.common.ui.view.IBaseView;
import com.chhd.android.common.util.UiUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 葱花滑蛋
 * time   : 2018/03/09
 * desc   : BaseActivity
 */
public class BaseActivity extends RxAppCompatActivity implements IBaseView, View.OnTouchListener {

    public static List<Activity> activities = new ArrayList<>();

    protected final int MENU_ITEM_DEFAULT_ID = 10;

    protected Activity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        activities.add(this);
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
        if (imm == null) return;
        View view = getCurrentFocus();
        if (view == null) view = new View(this);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 是否开启，触摸EditText以外的地方，自动隐藏软键盘，默认开启
     */
    protected boolean isAutoHideKeyboard() {
        return true;
    }

    @Override
    public <T> LifecycleTransformer<T> _bindToLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ObjectAnimator upAnim = ObjectAnimator.ofFloat(v, "translationZ", dp2px(4));
                upAnim.setDuration(150);
                upAnim.setInterpolator(new DecelerateInterpolator());
                upAnim.start();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                ObjectAnimator downAnim = ObjectAnimator.ofFloat(v, "translationZ", 0);
                downAnim.setDuration(150);
                downAnim.setInterpolator(new AccelerateInterpolator());
                downAnim.start();
                break;
        }
        return false;
    }

    protected int dp2px(float dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
