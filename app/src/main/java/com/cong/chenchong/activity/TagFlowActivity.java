
package com.cong.chenchong.activity;

import com.cong.chenchong.R;
import com.cong.chenchong.widget.TagFlowLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

public class TagFlowActivity extends BaseActivity {
    private String[] mVals = new String[] {
        "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello", "Android", "Weclome", "Button ImageView", "TextView", "Helloworld", "Android", "Weclome Hello", "Button Text", "TextView"
    };

    private TagFlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_flow);

        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText(getIntent().getStringExtra("title"));
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
