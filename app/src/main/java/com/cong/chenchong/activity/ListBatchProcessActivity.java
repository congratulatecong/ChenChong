package com.cong.chenchong.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.adapter.DividerItemDecoration;
import com.cong.chenchong.adapter.ListBatchProcessAdapter;
import com.cong.chenchong.bean.BatchProcessBean;
import com.cong.chenchong.global.SlidingActivity;

import java.util.ArrayList;
import java.util.List;

public class ListBatchProcessActivity extends SlidingActivity {

    private ListBatchProcessAdapter listBatchProcessAdapter;
    private List<BatchProcessBean> beans = new ArrayList<>();
    private boolean isSelectAll;
    private boolean editable;
    private int index;

    Toolbar toolbar;
    TextView tvSelectCount;
    Button btnDelete;
    Button btnSelectAll;
    LinearLayout llBatchProcess;
    RecyclerView rvBatchProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_batch_process);

        initToolbar();
        initView();
        initData();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.inflateMenu(R.menu.menu_list_batch_process);
        toolbar.setOnMenuItemClickListener(item -> {
            updateEditMode();
            return false;
        });
    }

    private void initView() {
        rvBatchProcess = (RecyclerView) findViewById(R.id.rv_batch_process);
        llBatchProcess = (LinearLayout) findViewById(R.id.ll_batch_process);
        tvSelectCount = (TextView) findViewById(R.id.tv_select_count);
        btnSelectAll = (Button) findViewById(R.id.btn_select_all);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        btnDelete.setOnClickListener(v -> deleteItem());
        btnSelectAll.setOnClickListener(v -> selectAll());
    }

    private void initData() {
        listBatchProcessAdapter = new ListBatchProcessAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvBatchProcess.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecorationHeader = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        itemDecorationHeader.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.divider_main_bg_height_1));
        rvBatchProcess.addItemDecoration(itemDecorationHeader);
        rvBatchProcess.setAdapter(listBatchProcessAdapter);

        for (int i = 0; i < 30; i++) {
            BatchProcessBean bean = new BatchProcessBean();
            bean.setTitle("这真的是第" + i + "项");
            bean.setSource("来源: " + i);
            beans.add(bean);
            listBatchProcessAdapter.notifyAdapter(beans, false);
        }

        listBatchProcessAdapter.setOnItemClickListener((pos, batchProcessList) -> {
            if (editable) {
                BatchProcessBean bean = batchProcessList.get(pos);
                boolean isSelect = bean.isSelect();
                if (!isSelect) {
                    index++;
                    bean.setSelect(true);
                    if (index == batchProcessList.size()) {
                        isSelectAll = true;
                        btnSelectAll.setText("取消全选");
                    }
                } else {
                    bean.setSelect(false);
                    index--;
                    isSelectAll = false;
                    btnSelectAll.setText("全选");
                }
                setBatchProcessLayoutStatus(index);
                listBatchProcessAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setBatchProcessLayoutStatus(int selectedCount) {
        tvSelectCount.setText(getString(R.string.selected_count, selectedCount));
        btnDelete.setEnabled(selectedCount != 0);
    }

    private void selectAll() {
        if (listBatchProcessAdapter == null) return;

        if (!isSelectAll) {
            for (int i = 0, j = listBatchProcessAdapter.getBeans().size(); i < j; i++) {
                listBatchProcessAdapter.getBeans().get(i).setSelect(true);
            }
            index = listBatchProcessAdapter.getBeans().size();
            btnDelete.setEnabled(true);
            btnSelectAll.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = listBatchProcessAdapter.getBeans().size(); i < j; i++) {
                listBatchProcessAdapter.getBeans().get(i).setSelect(false);
            }
            index = 0;
            btnDelete.setEnabled(false);
            btnSelectAll.setText("全选");
            isSelectAll = false;
        }
        listBatchProcessAdapter.notifyDataSetChanged();
        setBatchProcessLayoutStatus(index);
    }

    private void deleteItem() {
        if (index == 0) {
            btnDelete.setEnabled(false);
            return;
        }

        final AlertDialog builder = new AlertDialog.Builder(this).create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancel = (Button) builder.findViewById(R.id.btn_cancel);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancel == null || sure == null) return;

        if (index == 1) {
            msg.setText("删除后不可恢复，是否删除该条目？");
        } else {
            msg.setText("删除后不可恢复，是否删除这" + index + "个条目？");
        }
        cancel.setOnClickListener(v -> builder.dismiss());
        sure.setOnClickListener(v -> {
            for (int i = listBatchProcessAdapter.getBeans().size(), j = 0; i > j; i--) {
                BatchProcessBean myLive = listBatchProcessAdapter.getBeans().get(i - 1);
                if (myLive.isSelect()) {
                    listBatchProcessAdapter.getBeans().remove(myLive);
                    index--;
                }
            }
            index = 0;
            setBatchProcessLayoutStatus(index);
            if (listBatchProcessAdapter.getBeans().size() == 0) {
                llBatchProcess.setVisibility(View.GONE);
            }
            listBatchProcessAdapter.notifyDataSetChanged();
            builder.dismiss();
        });
    }

    private void updateEditMode() {
        if (!editable) {
            toolbar.getMenu().findItem(R.id.menu_item_edit).setTitle("取消");
            llBatchProcess.setVisibility(View.VISIBLE);
            editable = true;
        } else {
            toolbar.getMenu().findItem(R.id.menu_item_edit).setTitle("编辑");
            llBatchProcess.setVisibility(View.GONE);
            editable = false;
            clearAll();
        }
        listBatchProcessAdapter.setEditMode(editable);
    }

    private void clearAll() {
        isSelectAll = false;
        btnSelectAll.setText("全选");
        setBatchProcessLayoutStatus(0);
    }
}
