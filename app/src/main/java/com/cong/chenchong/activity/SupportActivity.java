package com.cong.chenchong.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
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

        ViewSwitcher supportSwitcher = (ViewSwitcher) findViewById(R.id.support_switcher);
        LinearLayout layoutSupport = (LinearLayout) findViewById(R.id.layout_support);
        ImageView ivSupport = (ImageView) findViewById(R.id.iv_support);

        ViewSwitcher expandSwitcher = (ViewSwitcher) findViewById(R.id.expand_switcher);
        ImageView ivExpand = (ImageView) findViewById(R.id.iv_expand);
        View layoutExpand = findViewById(R.id.layout_expand);

        layoutExpand.setOnClickListener(v -> {
            rotateExpandBtn(ivExpand, layoutSupport.isShown());
            if (layoutSupport.isShown()) {
                expandSwitcher.showPrevious();
            } else {
                expandSwitcher.showNext();
            }
            layoutSupport.setVisibility(layoutSupport.isShown() ? View.GONE : View.VISIBLE);
        });

        layoutSupport.setOnClickListener(v -> {
            if (isSupport) {
                isSupport = false;
                ivSupport.setImageResource(R.drawable.ic_support_black);

                supportSwitcher.setInAnimation(this, R.anim.slide_top_in);
                supportSwitcher.setOutAnimation(this, R.anim.slide_bottom_out);
                ((TextView) supportSwitcher.getNextView()).setText("10086");
                supportSwitcher.showPrevious();
            } else {
                isSupport = true;
                ivSupport.setImageResource(R.drawable.ic_support_red);
                ivSupport.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_support));

                supportSwitcher.setInAnimation(this, R.anim.slide_bottom_in);
                supportSwitcher.setOutAnimation(this, R.anim.slide_top_out);
                ((TextView) supportSwitcher.getNextView()).setText("10087");
                supportSwitcher.showNext();
            }
        });

    }

    private void rotateExpandBtn(View view, boolean expand) {
        RotateAnimation rotateAnimation = new RotateAnimation(expand ? 180 : 0, expand ? 360 : 180,
                view.getWidth() / 2, view.getHeight() / 2);
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

}
