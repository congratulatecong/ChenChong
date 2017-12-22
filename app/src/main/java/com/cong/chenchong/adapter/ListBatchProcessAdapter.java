package com.cong.chenchong.adapter;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.bean.BatchProcessBean;

import java.util.ArrayList;
import java.util.List;

public class ListBatchProcessAdapter extends RecyclerView.Adapter<ListBatchProcessAdapter.ViewHolder> {

    private boolean editable;
    private List<BatchProcessBean> beans;
    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos, List<BatchProcessBean> beans);
    }

    public void setEditMode(boolean editable) {
        this.editable = editable;
        notifyDataSetChanged();
    }

    public void notifyAdapter(List<BatchProcessBean> beans, boolean isAdd) {
        if (!isAdd) {
            this.beans = beans;
        } else {
            this.beans.addAll(beans);
        }
        notifyDataSetChanged();
    }

    public List<BatchProcessBean> getBeans() {
        if (beans == null) {
            beans = new ArrayList<>();
        }
        return beans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_batch_process, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BatchProcessBean bean = beans.get(position);
        holder.tvTitle.setText(bean.getTitle());
        holder.tvSource.setText(bean.getSource());
        holder.checkBox.setVisibility(editable ? View.VISIBLE : View.GONE);
        holder.checkBox.setChecked(bean.isSelect());

        holder.itemView.setOnClickListener(v -> itemClickListener.onItemClickListener(position, beans));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvSource;
        AppCompatCheckBox checkBox;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvSource = (TextView) itemView.findViewById(R.id.tv_source);
            checkBox = (AppCompatCheckBox) itemView.findViewById(R.id.check_box);
        }
    }

}
