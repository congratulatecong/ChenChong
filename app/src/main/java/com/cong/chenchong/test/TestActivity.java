
package com.cong.chenchong.test;

import com.cong.chenchong.R;
import com.cong.chenchong.activity.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_widget);

        TextView title = (TextView) findViewById(R.id.txt_title);
        title.setText(getIntent().getStringExtra("title"));

        findViewById(R.id.iv_test).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
