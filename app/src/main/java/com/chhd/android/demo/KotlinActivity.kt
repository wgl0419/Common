package com.chhd.android.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        HttpUtils
                .retrofit("http://test.hulubao.com")
                .create(ApiService::class.java)

    }
}
