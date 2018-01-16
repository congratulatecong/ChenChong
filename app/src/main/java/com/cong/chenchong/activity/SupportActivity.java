package com.cong.chenchong.activity;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;

public class SupportActivity extends SlidingActivity {

    private boolean isSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        LinearLayout layoutSupport = (LinearLayout) findViewById(R.id.layout_support);
        ImageView ivSupport = (ImageView) findViewById(R.id.iv_support);
        ViewSwitcher supportSwitcher = (ViewSwitcher) findViewById(R.id.support_switcher);
//        TextView tvSupportCount1 = (TextView) findViewById(R.id.tv_support_count1);
//        TextView tvSupportCount2 = (TextView) findViewById(R.id.tv_support_count2);

        layoutSupport.setOnClickListener(v -> {
            if (isSupport) {
                isSupport = false;
                ivSupport.setImageResource(R.drawable.ic_support_black);

                supportSwitcher.setInAnimation(this, R.anim.slide_top_in);
                supportSwitcher.setOutAnimation(this, R.anim.slide_bottom_out);
                ((TextView) supportSwitcher.getNextView()).setText("10086");
                supportSwitcher.showPrevious();
//                tvSupportCount1.setText("10086");
            } else {
                isSupport = true;
                ivSupport.setImageResource(R.drawable.ic_support_red);
                ivSupport.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_support));

                supportSwitcher.setInAnimation(this, R.anim.slide_bottom_in);
                supportSwitcher.setOutAnimation(this, R.anim.slide_top_out);
                ((TextView) supportSwitcher.getNextView()).setText("10087");
                supportSwitcher.showNext();
//                tvSupportCount2.setText("10087");
            }
        });

    }

}
