
package com.cong.chenchong.ui;

import com.cong.chenchong.R;
import com.cong.chenchong.activity.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class CommonWidgetActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TextView title = (TextView) findViewById(R.id.txt_title);
        title.setText(getIntent().getStringExtra("title"));

        View btn = findViewById(R.id.btn_ui_0);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(CommonWidgetActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
