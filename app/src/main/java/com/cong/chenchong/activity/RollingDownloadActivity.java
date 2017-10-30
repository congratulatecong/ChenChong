package com.cong.chenchong.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;
import com.cong.chenchong.widget.RollingDownload.RollingDownloadView;
import com.cong.chenchong.widget.RollingDownload.RollingDownloadViewGroup;

import java.util.Timer;
import java.util.TimerTask;

public class RollingDownloadActivity extends SlidingActivity implements RollingDownloadView.OnProgressStateChangeListener {
    //模拟进度的计时器
    private Timer timer;
    private int progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rolling_download);
        final RollingDownloadViewGroup vgRollingDownload = (RollingDownloadViewGroup) findViewById(R.id.vg_rolling_download);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!vgRollingDownload.isStop()) {
                            if (vgRollingDownload.getStatus() == RollingDownloadView.Status.Load) {
                                progress++;
                                vgRollingDownload.setProgress(progress);
                            }
                        }
                    }
                });
            }
        }, 0, 500);
        vgRollingDownload.setOnProgressStateChangeListener(this);
    }

    @Override
    public void onSuccess() {
        timer.cancel();
        Toast.makeText(this, "下载完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        progress = 0;
    }

    @Override
    public void onContinue() {
    }

    @Override
    public void onStopDownload() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
