
package com.cong.chenchong.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ListView;

import com.cong.chenchong.R;
import com.cong.chenchong.adapter.SimpleTreeListViewAdapter;
import com.cong.chenchong.bean.FileBean;
import com.cong.chenchong.bean.OrgBean;
import com.cong.chenchong.global.SlidingActivity;
import com.cong.chenchong.util.ToastHelper;

import java.util.ArrayList;
import java.util.List;

public class TreeViewerActivity extends SlidingActivity {
    private ListView mListViewTreeViewer;

    private SimpleTreeListViewAdapter<OrgBean> mAdapter;

    private List<FileBean> mDatas;

    private List<OrgBean> mDatas2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_viewer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        mListViewTreeViewer = (ListView) findViewById(R.id.listview_tree_viewer);

        initDatas();
        try {
            mAdapter = new SimpleTreeListViewAdapter<>(mListViewTreeViewer, this, mDatas2, 1);
            mListViewTreeViewer.setAdapter(mAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        initEvent();
    }

    private void initEvent() {
        mAdapter.setOnTreeNodeClickListener((node, position) -> {
            if (node.isLeaf()) {
                ToastHelper.toast(node.getName());
            }
        });

        mListViewTreeViewer.setOnItemLongClickListener((parent, view, position, id) -> {
            // DialogFragment
            final EditText et = new EditText(TreeViewerActivity.this);
            new AlertDialog.Builder(TreeViewerActivity.this)
                    .setTitle("添加节点")
                    .setView(et)
                    .setPositiveButton("确定", (dialog, which) -> {
                        if (TextUtils.isEmpty(et.getText().toString())) {
                            return;
                        }
                        mAdapter.addExtraNode(position, et.getText().toString());
                    })
                    .setNegativeButton("取消", null)
                    .show();

            return true;
        });
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        FileBean bean = new FileBean(1, 0, "根目录1");
        mDatas.add(bean);
        bean = new FileBean(2, 0, "根目录2");
        mDatas.add(bean);
        bean = new FileBean(3, 0, "根目录3");
        mDatas.add(bean);
        bean = new FileBean(4, 1, "根目录1-1");
        mDatas.add(bean);
        bean = new FileBean(5, 1, "根目录1-2");
        mDatas.add(bean);
        bean = new FileBean(6, 5, "根目录1-2-1");
        mDatas.add(bean);
        bean = new FileBean(7, 3, "根目录3-1");
        mDatas.add(bean);
        bean = new FileBean(8, 3, "根目录3-2");
        mDatas.add(bean);

        // initDatas
        mDatas2 = new ArrayList<>();
        OrgBean bean2 = new OrgBean(1, 0, "根目录1");
        mDatas2.add(bean2);
        bean2 = new OrgBean(2, 0, "根目录2");
        mDatas2.add(bean2);
        bean2 = new OrgBean(3, 0, "根目录3");
        mDatas2.add(bean2);
        bean2 = new OrgBean(4, 1, "根目录1-1");
        mDatas2.add(bean2);
        bean2 = new OrgBean(5, 1, "根目录1-2");
        mDatas2.add(bean2);
        bean2 = new OrgBean(6, 5, "根目录1-2-1");
        mDatas2.add(bean2);
        bean2 = new OrgBean(7, 3, "根目录3-1");
        mDatas2.add(bean2);
        bean2 = new OrgBean(8, 3, "根目录3-2");
        mDatas2.add(bean2);

    }

}
