package com.cong.chenchong.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cong.chenchong.R;
import com.cong.chenchong.util.BlurUtil;
import com.cong.chenchong.util.FastBlur;

public class PhotoBlurActivity extends BaseActivity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_blur);

        TextView title = (TextView) findViewById(R.id.txt_title);
        title.setText(getIntent().getStringExtra("title"));

        ImageView ivBlur = (ImageView) findViewById(R.id.iv_blur);
        try {

            ivBlur.setImageBitmap(FastBlur.blur(BlurUtil.drawableToBitamp(ContextCompat.getDrawable(this, R.drawable.image_open_360))));
//        ivBlur.setImageBitmap(BlurUtil.blur(this, getResources().getDrawable(R.drawable.image_open_360)));
//        ivBlur.setImageResource(R.drawable.image_open_360);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "cc Exception", Toast.LENGTH_SHORT).show();
            ivBlur.setImageResource(R.drawable.image_open_360);
        }
        ivBlur.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_blur:
                Toast.makeText(this, "cc blur", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
}
