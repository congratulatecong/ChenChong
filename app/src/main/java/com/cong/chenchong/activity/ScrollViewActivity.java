package com.cong.chenchong.activity;

import com.cong.chenchong.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ScrollViewActivity extends BaseActivity implements OnClickListener {
	private TextView txtReboundScrollView;
	private TextView txtElasticScrollView;
	private TextView txtElasticRefresh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scroll_view);

		String title = getIntent().getStringExtra("title");
		TextView txtTitle = (TextView) findViewById(R.id.txt_title);
		txtTitle.setText(title);

		txtReboundScrollView = (TextView) findViewById(R.id.txt_rebound_scroll_view);
		txtReboundScrollView.setOnClickListener(this);

		txtElasticScrollView = (TextView) findViewById(R.id.txt_elastic_scroll_view);
		txtElasticScrollView.setOnClickListener(this);
		
		txtElasticRefresh = (TextView) findViewById(R.id.txt_elastic_refresh);
		txtElasticRefresh.setOnClickListener(this);
	}

	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.txt_rebound_scroll_view:
			intent.setClass(this, ReboundScrollViewActivity.class);
			intent.putExtra("title", txtReboundScrollView.getText().toString());
			startActivity(intent);
			break;
		case R.id.txt_elastic_scroll_view:
			intent.setClass(this, ElasticScrollViewActivity.class);
			intent.putExtra("title", txtElasticScrollView.getText().toString());
			startActivity(intent);
			break;
		case R.id.txt_elastic_refresh:
			intent.setClass(this, ElasticRefreshActivity.class);
			intent.putExtra("title", txtElasticRefresh.getText().toString());
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
