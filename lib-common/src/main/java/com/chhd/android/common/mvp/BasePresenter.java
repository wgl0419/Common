package com.chhd.android.common.mvp;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

/**
 * BasePresenter
 *
 * @author : 葱花滑蛋 (2018/03/21)
 */

public class BasePresenter<View> {

    protected View view;

    public BasePresenter(View view) {
        this.view = view;
    }

    protected Application getApplication() {
        Application app = null;
        if (view instanceof Activity) {
            app = ((Activity) view).getApplication();
        } else if (view instanceof Fragment) {
            app = ((Fragment) view).getActivity().getApplication();
        }
        return app;
    }

    protected Activity getActivity() {
        Activity activity = null;
        if (view instanceof Activity) {
            activity = (Activity) view;
        } else if (view instanceof Fragment) {
            activity = ((Fragment) view).getActivity();
        }
        return activity;
    }

    protected Fragment getFragment() {
        Fragment fragment = null;
        if (view instanceof Fragment) {
            fragment = (Fragment) view;
        }
        return fragment;
    }
}
