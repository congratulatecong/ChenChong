package com.cong.chenchong.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.bean.AndroidKernel;
import com.cong.chenchong.util.Utils;

public class MainAdapter extends BaseAdapter {
	private Context mContext;
	private List<AndroidKernel> mAndroidKernelList;

	public MainAdapter(Context context, List<AndroidKernel> androidKernelList) {
		super();
		this.mContext = context;
		this.mAndroidKernelList = androidKernelList;
	}

	@Override
    public int getCount() {
		return Utils.isListEmpty(mAndroidKernelList) ? 0 : mAndroidKernelList.size();
	}

	@Override
    public AndroidKernel getItem(int position) {
		return Utils.isListEmpty(mAndroidKernelList) ? null : mAndroidKernelList.get(position);
	}

	@Override
    public long getItemId(int position) {
		return Utils.isListEmpty(mAndroidKernelList) ? 0 : position;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_main, parent, false);
			holder = new ViewHolder();
			holder.mTxtTitle = (TextView) convertView.findViewById(R.id.txt_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
        holder.mTxtTitle.setText(getItem(position).getTitle());
		return convertView;
	}

	private class ViewHolder {
		TextView mTxtTitle;
	}

}
