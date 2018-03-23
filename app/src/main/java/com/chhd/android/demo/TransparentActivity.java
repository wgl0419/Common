package com.chhd.android.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TransparentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
