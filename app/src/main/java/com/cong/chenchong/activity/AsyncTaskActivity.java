package com.cong.chenchong.activity;

import com.cong.chenchong.R;
import com.cong.chenchong.adapter.CourseListViewAdapter;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class AsyncTaskActivity extends BaseActivity {

    private ListView mListView;

    private CourseListViewAdapter mClassListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText(getIntent().getStringExtra("title"));

        mListView = (ListView) findViewById(R.id.lv_mian);
        mListView.setAdapter(mClassListViewAdapter);
    }
}
