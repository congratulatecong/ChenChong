package com.cong.chenchong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.floatwindow.FloatWindowService;
import com.cong.chenchong.global.SlidingActivity;

public class FloatWindowActivity extends SlidingActivity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_window);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        TextView tvOpenFloatWindow = (TextView) findViewById(R.id.tv_open_float_window);
        tvOpenFloatWindow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.tv_open_float_window:
                intent = new Intent(this, FloatWindowService.class);
                startService(intent);
                break;

            default:
                break;
        }
    }
}
