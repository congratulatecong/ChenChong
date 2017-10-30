
package com.cong.chenchong.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import com.cong.chenchong.constant.Constants;
import com.cong.chenchong.global.SlidingActivity;

/**
 * @deprecated use {@link SlidingActivity} instead
 */
@Deprecated
public class BaseActivity extends Activity implements OnGestureListener {

    // 增加手势关闭的功能
    protected GestureDetector mGestureDetector;

    protected final int MIN_DISTANCE_X_TO_FINISH = Constants.MIN_DISTANCE_X_TO_FINISH;

    protected final int MIN_VELOCITY_X_TO_FINISH = Constants.MIN_VELOCITY_X_TO_FINISH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 启动服务监听网络状态变化
        Intent intent = new Intent();
//        intent.setAction("com.cong.chenchong.NetworkConnectService");
//        intent.setPackage(getPackageName());
        intent.setClass(this, NetworkConnectService.class);
        startService(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, NetworkConnectService.class));
    }

    public void navigationBack(View view) {
        finish();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!Constants.LEFT_2_RIGHT_FLING_FOR_BACK) {
            return false;
        }

        if (e1 == null || e2 == null) {
            return false;
        }
        float dx = e2.getX() - e1.getX();
        float dy = Math.abs(e2.getY() - e1.getY());
        if (velocityX > MIN_VELOCITY_X_TO_FINISH && (dx > MIN_DISTANCE_X_TO_FINISH) && (dy < dx)) {
            finish();
            return true;
        }

        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!Constants.LEFT_2_RIGHT_FLING_FOR_BACK) {
            return super.dispatchTouchEvent(ev);
        }
        if (mGestureDetector == null) {
            mGestureDetector = new GestureDetector(this);
        }
        boolean bret = mGestureDetector.onTouchEvent(ev);
        if (bret) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

}
