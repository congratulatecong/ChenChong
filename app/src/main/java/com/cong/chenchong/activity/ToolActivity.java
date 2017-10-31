
package com.cong.chenchong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;

public class ToolActivity extends SlidingActivity implements OnClickListener {

    private TextView mTxtQRCode;

    private TextView mTxtTelemanagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        mTxtQRCode = (TextView) findViewById(R.id.txt_qr_code);
        mTxtQRCode.setOnClickListener(this);
        mTxtTelemanagement = (TextView) findViewById(R.id.txt_telemanagement);
        mTxtTelemanagement.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.txt_qr_code:
                intent.setClass(this, CaptureActivity.class);
                intent.putExtra("title", mTxtQRCode.getText().toString());
                startActivity(intent);
                break;

            case R.id.txt_telemanagement:
                // intent.setClass(this, TelemanagementActivity.class);
                intent.setClass(this, WiFiActivity.class);
                intent.putExtra("title", mTxtTelemanagement.getText().toString());
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
