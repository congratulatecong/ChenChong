
package com.cong.chenchong.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;
import com.cong.chenchong.widget.TagFlowLayout;

public class TagFlowActivity extends SlidingActivity {
    private String[] mVals = new String[] {
        "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello", "Android", "Weclome", "Button ImageView", "TextView", "Helloworld", "Android", "Weclome Hello", "Button Text", "TextView"
    };

    private TagFlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_flow);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        mFlowLayout = (TagFlowLayout) findViewById(R.id.layout_tag_flow);

        initData();
    }

    public void initData() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (String mVal : mVals) {
            TextView tv = (TextView) mInflater.inflate(R.layout.tv_tag_flow, mFlowLayout, false);
            tv.setText(mVal);
            mFlowLayout.addView(tv);
        }

    }

}
