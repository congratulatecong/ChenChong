
package com.cong.chenchong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;

public class ListViewActivity extends SlidingActivity implements OnClickListener {

    private TextView mTxtTreeViewer;

    private TextView mTxtExpandListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText(getIntent().getStringExtra("title"));

        mTxtTreeViewer = (TextView) findViewById(R.id.txt_tree_viewer);
        mTxtTreeViewer.setOnClickListener(this);
        mTxtExpandListView = (TextView) findViewById(R.id.txt_expand_listview);
        mTxtExpandListView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.txt_tree_viewer:
                intent.setClass(this, TreeViewerActivity.class);
                intent.putExtra("title", mTxtTreeViewer.getText().toString());
                startActivity(intent);
                break;

            case R.id.txt_expand_listview:
                intent.setClass(this, TreeViewerActivity.class);
                intent.putExtra("title", mTxtTreeViewer.getText().toString());
                startActivity(intent);
                break;
        }
    }
}
