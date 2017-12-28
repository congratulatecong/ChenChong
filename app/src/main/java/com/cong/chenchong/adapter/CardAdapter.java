package com.cong.chenchong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.bean.Girl;

import java.util.List;

public class CardAdapter extends BaseAdapter {
    private Context context;
    private List<Girl> girls;

    public CardAdapter(Context context, List<Girl> girls) {
        this.context = context;
        this.girls = girls;
    }

    @Override
    public int getCount() {
        return girls.size();
    }

    @Override
    public Object getItem(int position) {
        return girls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_swipe_card, parent, false);
            holder = new ViewHolder();
            holder.ivGirl = (ImageView) convertView.findViewById(R.id.iv_girl);
            holder.tvGirlName = (TextView) convertView.findViewById(R.id.tv_girl_name);
            holder.tvGirlAge = (TextView) convertView.findViewById(R.id.tv_girl_age);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivGirl.setImageResource(girls.get(position).getPhoto());
        holder.tvGirlName.setText(girls.get(position).getName());
        holder.tvGirlAge.setText(String.valueOf(girls.get(position).getAge()));
        return convertView;
    }

    private class ViewHolder {
        ImageView ivGirl;
        TextView tvGirlName;
        TextView tvGirlAge;
    }
}
