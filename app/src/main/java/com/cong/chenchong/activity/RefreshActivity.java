package com.cong.chenchong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;

public class RefreshActivity extends SlidingActivity implements OnClickListener {


    private TextView mTxtSwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        mTxtSwipeRefresh = (TextView) findViewById(R.id.txt_swipe_refresh);
        mTxtSwipeRefresh.setOnClickListener(this);
    }

    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.txt_swipe_refresh:
                intent.setClass(this, SwipeRefreshActivity.class);
                intent.putExtra("title", mTxtSwipeRefresh.getText().toString());
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
