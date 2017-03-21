package com.cong.chenchong.activity;

import com.cong.chenchong.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class RefreshActivity extends BaseActivity implements OnClickListener {
	
	
	private TextView mTxtSwipeRefresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refresh);
		
		TextView txtTitle = (TextView) findViewById(R.id.txt_title);
		txtTitle.setText(getIntent().getStringExtra("title"));
		
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
