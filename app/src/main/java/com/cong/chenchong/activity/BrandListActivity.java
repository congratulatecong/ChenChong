package com.cong.chenchong.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.bean.Brand;
import com.cong.chenchong.bean.BrandCategory;
import com.cong.chenchong.global.SlidingActivity;
import com.cong.chenchong.util.ContextUtils;
import com.cong.chenchong.widget.LoadingDialog;
import com.cong.chenchong.widget.SegmentBar;
import com.cong.chenchong.widget.SegmentBar.OnTabSelectionChanged;

import java.util.ArrayList;
import java.util.List;

public class BrandListActivity extends SlidingActivity {

    private SegmentBar mSegmentBar;
    private List<BrandCategory> mBrandCategoryList;

    private BrandAdapter mBrandAdapter;
    private List<Brand> mBrandList;

    private LoadingDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        mBrandCategoryList = new ArrayList<>();
        mSegmentBar = (SegmentBar) findViewById(R.id.segment_bar);
        mSegmentBar.setTabSelectionListener(mOnTabSelectionChanged);

        mBrandList = new ArrayList<>();
        mBrandAdapter = new BrandAdapter();
        GridView mGridView = (GridView) findViewById(R.id.grid_view);
        mGridView.setOnItemClickListener(mOnItemClickListener);
        mGridView.setAdapter(mBrandAdapter);

        mProgressDialog = new LoadingDialog(this);

        initData();
    }

    private void initData() {
        mProgressDialog.show();
        mBrandCategoryList = new ArrayList<>();
        BrandCategory brandCategory = new BrandCategory();
        for (int i = 0; i < 20; i++) {
            brandCategory.setName("Tab" + i);
            brandCategory.setCode("Code" + i);
            mBrandCategoryList.add(brandCategory);
        }
        for (int i = 0; i < mBrandCategoryList.size(); i++) {
            initBrandCategoryLayout(mSegmentBar, mBrandCategoryList.get(i));
        }
    }

    @Override
    protected void onDestroy() {
        mProgressDialog.dismiss();
        super.onDestroy();
    }

    private void initBrandCategoryLayout(LinearLayout layoutCategory, BrandCategory brandCategory) {
        View view = getLayoutInflater().inflate(R.layout.layout_brand_category_item, layoutCategory, false);
        TextView txtName = (TextView) view.findViewById(R.id.txt_name);
        txtName.setText(brandCategory.getName());
        layoutCategory.addView(view, view.getLayoutParams());
    }

    private OnTabSelectionChanged mOnTabSelectionChanged = index -> initItemData();

    private void initItemData() {
        mBrandList = new ArrayList<>();
        Brand brand = new Brand();
        for (int i = 0; i < 15; i++) {
            brand.setId(String.valueOf(i));
            brand.setName("Tab" + i);
            brand.setCode("Code" + i);
            brand.setLogo("http://p3.music.126.net/2GMw2LfdqFQPJ9wqx6L17g==/3287539767749374.jpg?param=200y200");
            mBrandList.add(brand);
        }
        mProgressDialog.hide();
        mBrandAdapter.notifyDataSetChanged();
    }

    private OnItemClickListener mOnItemClickListener = (parent, view, position, id) ->
            ContextUtils.showTips(BrandListActivity.this, R.drawable.tips_smile, "item" + position);

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

            TextView txtName = (TextView) convertView.findViewById(R.id.txt_name);
            Brand brand = mBrandList.get(position);
            txtName.setText(brand.getName());

            return convertView;
        }

    }

}