package com.chhd.android.demo

import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.chhd.android.common.ui.activity.base.ProgressTActivity

class ProgressDemoActivity : ProgressTActivity() {

    private var count = 0

    override fun getContentResId(): Int {
        return R.layout.activity_progress_demo
    }

    override fun onInit(savedInstanceState: Bundle?) {
        onPageError("出错了")
    }

    override fun onLoad() {
        if (count == 0) {
            count += 1
            return
        }
        onPageLoading()
        Handler().postDelayed({ onPageSuccess() }, 1000)
    }

    override fun getToolbarTitle(): CharSequence {
        return "进度"
    }


}
