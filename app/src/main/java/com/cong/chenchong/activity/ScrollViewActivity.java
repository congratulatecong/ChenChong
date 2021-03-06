package com.cong.chenchong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;

public class ScrollViewActivity extends SlidingActivity implements OnClickListener {
	private TextView txtReboundScrollView;
	private TextView txtElasticScrollView;
	private TextView txtElasticRefresh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scroll_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

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
