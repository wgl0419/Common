package com.chhd.android.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.Button;

import com.chhd.android.common.ui.view.compound.CompoundLinearLayout;

public class CompoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compound);

        CompoundLinearLayout cvg = findViewById(R.id.cvg);
        cvg.setChecked(!cvg.isChecked());
    }
}
