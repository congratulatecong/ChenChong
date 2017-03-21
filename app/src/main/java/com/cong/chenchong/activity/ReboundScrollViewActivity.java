package com.cong.chenchong.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.cong.chenchong.R;

public class ReboundScrollViewActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rebound_scroll_view);
		
		TextView txtTitle = (TextView) findViewById(R.id.txt_title);
		txtTitle.setText(getIntent().getStringExtra("title"));
		
	}
}
