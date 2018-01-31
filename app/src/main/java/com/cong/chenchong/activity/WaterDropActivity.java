
package com.cong.chenchong.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;
import com.cong.chenchong.util.ToastHelper;
import com.cong.chenchong.waterdrop.CoverManager;
import com.cong.chenchong.waterdrop.WaterDrop;

public class WaterDropActivity extends SlidingActivity implements OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_drop);
        CoverManager.getInstance().init(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ListView mListView = (ListView) findViewById(R.id.listview_water_drop);
        mListView.setAdapter(new DemoAdapter());
        mListView.setOnItemClickListener(this);

        CoverManager.getInstance().setMaxDragDistance(350);
        CoverManager.getInstance().setExplosionTime(150);
    }

    private class DemoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 99;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(WaterDropActivity.this).inflate(R.layout.list_item_water_drop, parent, false);
                holder = new ViewHolder();
                holder.waterDrop = (WaterDrop) convertView.findViewById(R.id.water_drop_badge_view);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.waterDrop.setText(String.valueOf(position));
            holder.waterDrop.setOnDragCompeteListener(() -> ToastHelper.toast("消除：" + position));

            return convertView;
        }

        private class ViewHolder {
            WaterDrop waterDrop;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        WaterDropDetailActivity.startActivity(this, "Transition Test", view.findViewById(R.id.iv_picture));
    }

}
