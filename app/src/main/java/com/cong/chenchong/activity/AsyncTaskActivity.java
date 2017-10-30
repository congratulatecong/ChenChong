package com.cong.chenchong.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.adapter.CourseListViewAdapter;
import com.cong.chenchong.global.SlidingActivity;

public class AsyncTaskActivity extends SlidingActivity {

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
