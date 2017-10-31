package com.cong.chenchong.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cong.chenchong.R;
import com.cong.chenchong.fragment.SwipeListviewFragment;
import com.cong.chenchong.fragment.SwipeScrollViewFragment;
import com.cong.chenchong.global.SlidingActivity;

public class SwipeRefreshActivity extends SlidingActivity implements OnClickListener {

	private Button btnSwipeListView;
	private Button btnSwipeScrollView;
	private boolean isLeftChecked = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipe_refresh);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

		btnSwipeListView = (Button) findViewById(R.id.btn_swipe_listview);
		btnSwipeListView.setOnClickListener(this);
		btnSwipeScrollView = (Button) findViewById(R.id.btn_swipe_scrollview);
		btnSwipeScrollView.setOnClickListener(this);
		initViewStatus();
	}

	private void initViewStatus() {
		// 注意导包！FragmentTransaction与Fragment包的版本应该保持一致！
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		if (isLeftChecked) {
			btnSwipeListView.setBackgroundResource(R.drawable.btn_segment_left_bg_pressed);
			btnSwipeListView.setTextColor(getResources().getColor(R.color.white));
			btnSwipeScrollView.setBackgroundResource(R.drawable.btn_segment_right_bg_normal);
			btnSwipeScrollView.setTextColor(getResources().getColor(R.color.black));
			ft.replace(R.id.layout_fragment, new SwipeListviewFragment());
		} else {
			btnSwipeListView.setBackgroundResource(R.drawable.btn_segment_left_bg_normal);
			btnSwipeListView.setTextColor(getResources().getColor(R.color.black));
			btnSwipeScrollView.setBackgroundResource(R.drawable.btn_segment_right_bg_pressed);
			btnSwipeScrollView.setTextColor(getResources().getColor(R.color.white));
			ft.replace(R.id.layout_fragment, new SwipeScrollViewFragment());
		}
		ft.commit();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_swipe_listview:
			if (isLeftChecked) {
				return;
			}
			isLeftChecked = true;
			initViewStatus();
			break;
		case R.id.btn_swipe_scrollview:
			if (!isLeftChecked) {
				return;
			}
			isLeftChecked = false;
			initViewStatus();
			break;

		default:
			break;
		}
	}
}
