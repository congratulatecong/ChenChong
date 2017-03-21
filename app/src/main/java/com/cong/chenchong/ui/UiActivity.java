
package com.cong.chenchong.ui;

import com.cong.chenchong.R;
import com.cong.chenchong.activity.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class UiActivity extends BaseActivity implements OnClickListener {

    private TextView mTvCommonWidget;

    private TextView mTvCustomLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        TextView tvTitle = (TextView) findViewById(R.id.txt_title);
        tvTitle.setText(getIntent().getStringExtra("title"));

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
