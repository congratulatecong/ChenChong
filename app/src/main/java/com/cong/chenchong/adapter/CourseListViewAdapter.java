
package com.cong.chenchong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.bean.Course;
import com.cong.chenchong.util.CommonUtil;

import java.util.List;

public class CourseListViewAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflate;

    private Context mContext;

    private List<Course> mCourseListData;

    public CourseListViewAdapter(Context mContext, List<Course> mCourseListData) {
        super();
        this.mContext = mContext;
        this.mCourseListData = mCourseListData;
        this.mLayoutInflate = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return CommonUtil.isListEmpty(mCourseListData) ? 0 : mCourseListData.size();
    }

    @Override
    public Object getItem(int position) {
        return CommonUtil.isListEmpty(mCourseListData) ? null : mCourseListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return CommonUtil.isListEmpty(mCourseListData) ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflate.inflate(R.layout.list_item_course, parent, false);
            holder = new ViewHolder();
            holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvLearner = (TextView) convertView.findViewById(R.id.tv_learner);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tv_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivPic.setImageResource(R.drawable.img_course_bg);
        holder.tvName.setText("Tony老师聊shell——环境变量配置文件");
        holder.tvLearner.setText(mContext.getResources().getString(R.string.learner_num, "99"));
        holder.tvDescription.setText("为你带来shell中的环境变量配置文");
        return convertView;
    }

    class ViewHolder {
        ImageView ivPic;

        TextView tvName, tvLearner, tvDescription;
    }

}
