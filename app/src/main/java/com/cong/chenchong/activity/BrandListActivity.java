package com.cong.chenchong.activity;

import java.util.ArrayList;
import java.util.List;

import com.cong.chenchong.R;
import com.cong.chenchong.bean.Brand;
import com.cong.chenchong.bean.BrandCategory;
import com.cong.chenchong.util.ContextUtils;
import com.cong.chenchong.widget.LoadingDialog;
import com.cong.chenchong.widget.SegmentBar;
import com.cong.chenchong.widget.SegmentBar.OnTabSelectionChanged;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BrandListActivity extends BaseActivity {

	private int mCurrentPage = 0;
	private String mBrandCode = "";
	private SegmentBar mSegmentBar;
	private List<BrandCategory> mBrandCategoryList;

    private GridView mGridView;
	private BrandAdapter mBrandAdapter;
	private List<Brand> mBrandList;

    private LoadingDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brand_list);

        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText(getIntent().getStringExtra("title"));

		mBrandCategoryList = new ArrayList<BrandCategory>();
		mSegmentBar = (SegmentBar) findViewById(R.id.segment_bar);
		mSegmentBar.setTabSelectionListener(mOnTabSelectionChanged);

		mBrandList = new ArrayList<Brand>();
		mBrandAdapter = new BrandAdapter();
        mGridView = (GridView) findViewById(R.id.grid_view);
		mGridView.setOnItemClickListener(mOnItemClickListener);
		mGridView.setAdapter(mBrandAdapter);

        mProgressDialog = new LoadingDialog(this);

        // getBrandCategory();
        initDatas();
	}

    private void initDatas() {
        mProgressDialog.show();
        mBrandCategoryList = new ArrayList<BrandCategory>();
        BrandCategory brandCategory = new BrandCategory();
        for (int i = 0; i < 20; i++) {
            brandCategory.setName("Tab" + i);
            brandCategory.setCode("Code" + i);
            mBrandCategoryList.add(brandCategory);
        }
        Log.v("cc", "mBrandCategoryList->" + mBrandCategoryList.size());
        // mSegmentBar.removeAllViews();
        // mProgressDialog.hide();
        for (int i = 0; i < mBrandCategoryList.size(); i++) {
            initBrandCategoryLayout(mSegmentBar, mBrandCategoryList.get(i));
        }
    }

    @Override
	protected void onDestroy() {
		mProgressDialog.dismiss();
		super.onDestroy();
	}

    // private void getBrandCategory() {
    // mProgressDialog.show();
    // JuranConsumerApplication.getMarketManager().getBrandCategory(mOnGetBrandCategoryFinishedListener);
    // }
    //
    // private OnGetBrandCategoryFinishedListener
    // mOnGetBrandCategoryFinishedListener = new
    // OnGetBrandCategoryFinishedListener() {
    //
    // @Override
    // public void onGetBrandCategoryFinished(Response response,
    // List<BrandCategory> brandCategoryList) {
    // if (response.getResponseHead().isSuccess()) {
    // mBrandCategoryList = brandCategoryList;
    //
    // mSegmentBar.removeAllViews();
    // for (int i = 0; i < brandCategoryList.size(); i++) {
    // initBrandCategoryLayout(mSegmentBar, brandCategoryList.get(i));
    // }
    //
    // } else {
    // AppUtils.handleNGResponse(BrandListActivity.this, response);
    // mProgressDialog.hide();
    // }
    // }
    //
    // };

	private void initBrandCategoryLayout(LinearLayout layoutCategory, BrandCategory brandCategory) {
		View view = getLayoutInflater().inflate(R.layout.layout_brand_category_item, layoutCategory, false);
		TextView txtName = (TextView) view.findViewById(R.id.txt_name);
		txtName.setText(brandCategory.getName());
		layoutCategory.addView(view, view.getLayoutParams());
	}

	private OnTabSelectionChanged mOnTabSelectionChanged = new OnTabSelectionChanged() {

		@Override
		public void onTabSelectionChanged(int index) {
			mBrandCode = mBrandCategoryList.get(index).getCode();
            // getBrandsByCategory(1);
            initItemDatas();
		}

	};

    private void initItemDatas() {
        // mProgressDialog.show();
        mBrandList = new ArrayList<Brand>();
        Brand brand = new Brand();
        for (int i = 0; i < 15; i++) {
            brand.setId(String.valueOf(i));
            brand.setName("Tab" + i);
            brand.setCode("Code" + i);
            brand.setLogo("http://p3.music.126.net/2GMw2LfdqFQPJ9wqx6L17g==/3287539767749374.jpg?param=200y200");
            mBrandList.add(brand);
        }
        // try {
        // Thread.sleep(5 * 1000);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        mProgressDialog.hide();
        mBrandAdapter.notifyDataSetChanged();
    }

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ContextUtils.showTips(BrandListActivity.this, R.drawable.tips_smile, "item" + position);
		}

	};

    // private void getBrandsByCategory(int currentPage) {
    // mProgressDialog.show();
    // JuranConsumerApplication.getMarketManager().getBrandsByCategory(mBrandCode,
    // currentPage, mOnGetBrandsByCategoryFinishedListener);
    // }
    //
    // private OnGetBrandsByCategoryFinishedListener
    // mOnGetBrandsByCategoryFinishedListener = new
    // OnGetBrandsByCategoryFinishedListener() {
    //
    // @Override
    // public void onGetBrandsByCategoryFinished(Response response, int
    // currentPage, List<Brand> brandList) {
    // if (response.getResponseHead().isSuccess()) {
    // mCurrentPage = currentPage;
    //
    // if (mCurrentPage == 1) {
    // mBrandList.clear();
    // mBrandList.addAll(brandList);
    // mBrandAdapter.notifyDataSetInvalidated();
    // } else {
    // mBrandList.addAll(brandList);
    // mBrandAdapter.notifyDataSetChanged();
    // }
    // } else {
    // AppUtils.handleNGResponse(BrandListActivity.this, response);
    // }
    //
    // mGridView.onRefreshComplete();
    // mProgressDialog.hide();
    // }
    //
    // };

	private class BrandAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mBrandList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.layout_brand_item, parent, false);
            }

			ImageView imgPic = (ImageView) convertView.findViewById(R.id.img_pic);
			TextView txtName = (TextView) convertView.findViewById(R.id.txt_name);

			Brand brand = mBrandList.get(position);

            // JuranConsumerApplication.getImageManager().setImage(imgPic,
            // brand.getLogo(), R.color.background);
			txtName.setText(brand.getName());

			return convertView;
		}

	}

}