
package com.cong.chenchong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;

public class UiActivity extends SlidingActivity implements OnClickListener {

    private TextView mTvCommonWidget;

    private TextView mTvCustomLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        mTvCommonWidget = (TextView) findViewById(R.id.tv_common_widget);
        mTvCommonWidget.setOnClickListener(this);
        mTvCustomLoading = (TextView) findViewById(R.id.tv_custom_loading);
        mTvCustomLoading.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_common_widget:
                intent.setClass(this, CommonWidgetActivity.class);
                intent.putExtra("title", mTvCommonWidget.getText().toString());
                startActivity(intent);
                break;

            case R.id.tv_custom_loading:
                intent.setClass(this, CustomLoadingActivity.class);
                intent.putExtra("title", mTvCustomLoading.getText().toString());
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
