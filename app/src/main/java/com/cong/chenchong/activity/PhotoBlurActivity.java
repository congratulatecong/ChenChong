package com.cong.chenchong.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;
import com.cong.chenchong.util.BlurUtil;
import com.cong.chenchong.util.FastBlur;
import com.cong.chenchong.util.ToastHelper;

public class PhotoBlurActivity extends SlidingActivity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_blur);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ImageView ivBlur = (ImageView) findViewById(R.id.iv_blur);
        try {

            ivBlur.setImageBitmap(FastBlur.blur(BlurUtil.drawableToBitmap(ContextCompat.getDrawable(this, R.drawable.image_girl))));
//        ivBlur.setImageBitmap(BlurUtil.blur(this, getResources().getDrawable(R.drawable.image_girl)));
//        ivBlur.setImageResource(R.drawable.image_girl);
        } catch (Exception e) {
            e.printStackTrace();
            ToastHelper.toast("cc Exception");
            ivBlur.setImageResource(R.drawable.image_girl);
        }
        ivBlur.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_blur:
                ToastHelper.toast("cc blur");
                break;

            default:
                break;
        }
    }
}
