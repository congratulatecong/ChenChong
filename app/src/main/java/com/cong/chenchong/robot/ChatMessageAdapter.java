
package com.cong.chenchong.robot;

import java.util.Calendar;
import java.util.List;

import com.cong.chenchong.R;
import com.cong.chenchong.robot.ChatMessage.Type;
import com.cong.chenchong.util.LocalTimeUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatMessageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;

    private List<ChatMessage> mDatas;

    private final Calendar mCalendar1 = Calendar.getInstance();

    private final Calendar mCalendar2 = Calendar.getInstance();

    private final Calendar mCalendarCurrent = Calendar.getInstance();

    public ChatMessageAdapter(Context context, List<ChatMessage> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        mCalendarCurrent.setTimeInMillis(System.currentTimeMillis());
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = mDatas.get(position);
        if (chatMessage.getType() == Type.INCOMING) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessage = mDatas.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            if (getItemViewType(position) == 0) {
                convertView = mInflater.inflate(R.layout.list_item_robot_from_msg, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = (TextView) convertView.findViewById(R.id.id_form_msg_date);
                viewHolder.mMsg = (TextView) convertView.findViewById(R.id.id_from_msg_info);
            } else {
                convertView = mInflater.inflate(R.layout.list_item_robot_to_msg, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = (TextView) convertView.findViewById(R.id.id_to_msg_date);
                viewHolder.mMsg = (TextView) convertView.findViewById(R.id.id_to_msg_info);
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // viewHolder.mDate.setText(df.format(chatMessage.getDate()));
        viewHolder.mMsg.setText(chatMessage.getMsg());
        viewHolder.mMsg.setTextSize(mTxtSize);
        // if (needBubbleDivider(position)) {
            setDivider(viewHolder.mDate, System.currentTimeMillis());
        // }
        return convertView;
    }

    private int mTxtSize = 0;

    public void setTxtSize(int txtSize) {
        mTxtSize = txtSize;
    }

    private boolean needBubbleDivider(int position) {
        if (position == 0) {
            return true;
        }

        ChatMessage curChatMessage = mDatas.get(position);
        ChatMessage earlyChatMessage = mDatas.get(position - 1);
        long curTime = curChatMessage.getDate().getTime();
        long earlyTime = earlyChatMessage.getDate().getTime();
        mCalendar1.setTimeInMillis(curTime);
        mCalendar2.setTimeInMillis(earlyTime);

        return !LocalTimeUtils.isSameLocalDay(mCalendar1, mCalendar2);
    }

    private void setDivider(TextView tv, long time) {
        mCalendar1.setTimeInMillis(time);
        String timeStr = LocalTimeUtils.getLocalDateWithWeekday(mCalendar1, mCalendarCurrent);
        tv.setText(timeStr);
        tv.setVisibility(View.VISIBLE);
    }

    private final class ViewHolder {
        TextView mDate;

        TextView mMsg;
    }

}
