package com.cong.chenchong.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.cong.chenchong.R;
import com.cong.chenchong.util.BlurUtil;
import com.cong.chenchong.util.FastBlur;
import com.cong.chenchong.widget.SmileView;

public class SmileAppraiseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smile_appraise);

        View layoutContainer = findViewById(R.id.layout_container);
        Bitmap bitmap = BlurUtil.drawableToBitmap(ContextCompat.getDrawable(this, R.drawable.image_girl));
        Bitmap blurBitmap = FastBlur.blur(bitmap);
        layoutContainer.setBackground(new BitmapDrawable(getResources(), blurBitmap));

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        final ImageView ivSmileFace = (ImageView) findViewById(R.id.iv_smile_face);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ivSmileFace.getLayoutParams();
                layoutParams.bottomMargin = i * 3;
                ivSmileFace.setLayoutParams(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SmileView smileView = (SmileView) findViewById(R.id.smileView);
        smileView.setNum(80, 20);
    }
}
