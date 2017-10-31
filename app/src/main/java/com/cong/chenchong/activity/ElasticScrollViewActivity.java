package com.cong.chenchong.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;

public class ElasticScrollViewActivity extends SlidingActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_elastic_scroll_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
	}

}
