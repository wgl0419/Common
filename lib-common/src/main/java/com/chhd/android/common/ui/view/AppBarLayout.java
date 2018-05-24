package com.chhd.android.common.ui.view;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.chhd.android.common.R;

/**
 * @author : 葱花滑蛋
 * @date : 2018/5/21.
 */

public class AppBarLayout extends android.support.design.widget.AppBarLayout {

    public AppBarLayout(Context context) {
        this(context, null);
    }

    public AppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(R.attr.actionBarElevation,
                    outValue, true);
            float elevation = outValue.getDimension(getResources().getDisplayMetrics());
            setDefaultAppBarLayoutStateListAnimator(this, elevation);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setDefaultAppBarLayoutStateListAnimator(final View view, final float elevation) {
        final StateListAnimator sla = new StateListAnimator();
        sla.addState(new int[0],
                ObjectAnimator.ofFloat(view, "elevation", elevation).setDuration(0));
        view.setStateListAnimator(sla);
    }
}
