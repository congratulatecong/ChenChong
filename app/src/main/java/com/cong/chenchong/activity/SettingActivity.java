package com.cong.chenchong.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.dtr.settingview.lib.SettingView;
import com.dtr.settingview.lib.SettingView.onSettingViewItemClickListener;
import com.dtr.settingview.lib.entity.SettingData;
import com.dtr.settingview.lib.entity.SettingViewItemData;
import com.dtr.settingview.lib.item.BasicItemViewH;

public class SettingActivity extends BaseActivity {

	private SettingView mSettingView = null;

	private SettingData mItemData = null;
	private SettingViewItemData mItemViewData = null;
	private List<SettingViewItemData> mListData = new ArrayList<SettingViewItemData>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		TextView txtTitle = (TextView) findViewById(R.id.txt_title);
		txtTitle.setText(getIntent().getStringExtra("title"));

		mSettingView = (SettingView) findViewById(R.id.main_setting_view);
		mSettingView.setOnSettingViewItemClickListener(new onSettingViewItemClickListener() {

			@Override
			public void onItemClick(int index) {
				switch (index) {
				case 0:
					startActivity(new Intent(SettingActivity.this, SettingQQStyleActivity.class));
					break;
				case 1:
					startActivity(new Intent(SettingActivity.this, SettingIOSStyleActivity.class));
					break;
				case 2:
					startActivity(new Intent(SettingActivity.this, SettingCommonLayoutActivity.class));
					break;

				default:
					break;
				}
			}
		});

		findViewById(R.id.img_qr_code).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(Intent.ACTION_VIEW);
//				intent.setData(Uri.parse("https://github.com/congratulatecong"));
//				startActivity(intent);
				Intent intent = new Intent(SettingActivity.this, ExplorerActivity.class);
				intent.putExtra("title", "Github");
				intent.putExtra("url", "https://github.com/congratulatecong");
				startActivity(intent);
			}
		});

		initView();
	}

	private void initView() {
		mItemViewData = new SettingViewItemData();
		mItemData = new SettingData();
		mItemData.setTitle("仿QQ界面效果");

		mItemViewData.setData(mItemData);
		mItemViewData.setItemView(new BasicItemViewH(SettingActivity.this));
		mListData.add(mItemViewData);

		mItemViewData = new SettingViewItemData();
		mItemData = new SettingData();
		mItemData.setTitle("仿iOS设置界面");

		mItemViewData.setData(mItemData);
		mItemViewData.setItemView(new BasicItemViewH(SettingActivity.this));
		mListData.add(mItemViewData);

		mItemViewData = new SettingViewItemData();
		mItemData = new SettingData();
		mItemData.setTitle("单个Item布局效果");

		mItemViewData.setData(mItemData);
		mItemViewData.setItemView(new BasicItemViewH(SettingActivity.this));
		mListData.add(mItemViewData);

		mSettingView.setAdapter(mListData);
	}
}
