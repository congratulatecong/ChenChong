
package com.cong.chenchong.test;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;

public class TestActivity extends SlidingActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_widget);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        findViewById(R.id.iv_test).setOnClickListener(v ->
                Toast.makeText(TestActivity.this, "click", Toast.LENGTH_SHORT).show());
    }
}
