
package com.cong.chenchong.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;
import com.cong.chenchong.util.ToastHelper;

public class CommonWidgetActivity extends SlidingActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        View btn = findViewById(R.id.btn_ui_0);
        btn.setOnClickListener(v -> ToastHelper.toast("clicked"));
    }
}
