
package com.cong.chenchong.activity;

import com.cong.chenchong.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ToolActivity extends BaseActivity implements OnClickListener {

    private TextView mTxtQRCode;

    private TextView mTxtTelemanagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);

        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText(getIntent().getStringExtra("title"));

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
