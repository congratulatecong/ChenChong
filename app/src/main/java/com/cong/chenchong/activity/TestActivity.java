package com.cong.chenchong.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;

public class TestActivity extends SlidingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_live_template);

        ImageView ivGirl = (ImageView) findViewById(R.id.iv_girl);
        ivGirl.setOnClickListener(v ->
                Toast.makeText(TestActivity.this, "cc", Toast.LENGTH_SHORT).show());

    }
}
