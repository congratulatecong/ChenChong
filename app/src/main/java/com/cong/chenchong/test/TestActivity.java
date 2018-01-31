
package com.cong.chenchong.test;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;
import com.cong.chenchong.util.ToastHelper;

public class TestActivity extends SlidingActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_widget);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        findViewById(R.id.iv_test).setOnClickListener(v -> ToastHelper.toast("click"));
    }
}
