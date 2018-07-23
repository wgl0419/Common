package com.chhd.android.demo

import android.os.Bundle
import android.os.Handler
import com.chhd.android.common.ui.activity.base.toolbar.ProgressActivity

class ProgressDemoActivity : ProgressActivity() {

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
