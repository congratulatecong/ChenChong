package com.cong.chenchong.waterdrop;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * The thead will finish when you called setRunning(false) or Explosion ended.
 *
 * @author Peter.Ding
 *
 */
public class ExplosionUpdateThread extends Thread {
    private SurfaceHolder mHolder;
    private DropCover mDropCover;
    private boolean isRunning = false;

    public ExplosionUpdateThread(SurfaceHolder holder, DropCover dropCover) {
        mHolder = holder;
        mDropCover = dropCover;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        boolean isAlive = true;
        while (isRunning && isAlive) {
            Canvas canvas = mHolder.lockCanvas();
            if (canvas != null) {
                isAlive = mDropCover.render(canvas);
                mHolder.unlockCanvasAndPost(canvas);
                mDropCover.update();
            }
        }
        // Fixed : java.lang.IllegalStateException: Hardware acceleration can
        // only be used with a single UI thread.
        // mDropCover.clearViews();

        /*
         * 或者在AndroidManifest.xml的Application标签下增加android:hardwareAccelerated=
         * "false"，关闭硬件加速，API需要minSdkVersion为11以上才支持，android4.4默认开启硬件加速。
         */
        mDropCover.post(new Runnable() {

            @Override
            public void run() {
                mDropCover.clearViews();
            }
        });
    }
}
