package com.cong.chenchong.activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;
import com.mobike.library.MobikeView;

public class SensorActivity extends SlidingActivity {

    private SensorManager sensorManager;
    private Sensor defaultSensor;

    private MobikeView viewImageTag;
    private View ivLeftEyeball, ivRightEyeball;

    private int[] imageTags = {
            R.drawable.share_facebook,
            R.drawable.share_qq_zone,
            R.drawable.share_wx_circle,
            R.drawable.share_qq,
            R.drawable.share_twitter,
            R.drawable.share_wx_friend,
            R.drawable.share_weibo,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        viewImageTag = (MobikeView) findViewById(R.id.view_image_tag);
        ivLeftEyeball = findViewById(R.id.iv_left_eyeball);
        ivRightEyeball = findViewById(R.id.iv_right_eyeball);

        initImageTags();
    }

    private void initImageTags() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.gravity = Gravity.CENTER;
        for (int imageTag : imageTags) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageTag);
            viewImageTag.addView(imageView, layoutParams);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewImageTag.getmMobike().onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewImageTag.getmMobike().onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, defaultSensor, SensorManager.SENSOR_DELAY_UI);
    }

    private SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                // --摩拜
                float x = event.values[0];
                float y = event.values[1] * 2.0f;
                viewImageTag.getmMobike().onSensorChanged(-x, y);

                // --ofo小黄人
                x -= 7.0f * event.values[0];
                y += 7.0f * event.values[1];

                //越界处理
                float normalSpace = 20;
                if (x < -normalSpace) {
                    x = -normalSpace;
                }
                if (x > 0) {
                    x = 0;
                }
                if (y > 0) {
                    y = 0;
                }
                if (y < -normalSpace) {
                    y = -normalSpace;
                }

                ivLeftEyeball.setTranslationY(y);
                ivLeftEyeball.setTranslationX(x);
                ivLeftEyeball.setRotation(x);
                ivRightEyeball.setTranslationX(x);
                ivRightEyeball.setTranslationY(y);
                ivRightEyeball.setRotation(x);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
