package com.chhd.android.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ClearEditTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_edit_text);

        final EditText et1 = findViewById(R.id.et1);
        final Button btnText = findViewById(R.id.btn_text);

        et1.setEnabled(false);
        et1.setText("987654");

        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1.setText("123456");
            }
        });
    }
}
