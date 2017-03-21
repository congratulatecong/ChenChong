
package com.cong.chenchong.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cong.chenchong.R;
import com.cong.chenchong.waterdrop.CoverManager;
import com.cong.chenchong.waterdrop.DropCover.OnDragCompeteListener;
import com.cong.chenchong.waterdrop.WaterDrop;

public class WaterDropActivity extends BaseActivity implements OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_drop);
        CoverManager.getInstance().init(this);

        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText(getIntent().getStringExtra("title"));

        ListView mListView = (ListView) findViewById(R.id.listview_water_drop);
        mListView.setAdapter(new DemoAdapter());
        mListView.setOnItemClickListener(this);

        CoverManager.getInstance().setMaxDragDistance(350);
        CoverManager.getInstance().setExplosionTime(150);
    }

    class DemoAdapter extends BaseAdapter {

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
            holder.waterDrop.setOnDragCompeteListener(new OnDragCompeteListener() {

                @Override
                public void onDrag() {
                    Toast.makeText(WaterDropActivity.this, "消除：" + position, Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

        private class ViewHolder {
            WaterDrop waterDrop;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(WaterDropActivity.this, "点击：" + position, Toast.LENGTH_SHORT).show();
    }

}
