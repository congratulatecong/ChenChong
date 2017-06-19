package com.cong.chenchong.widget.RollingDownload;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cong.chenchong.R;

public class RollingDownloadViewGroup extends RelativeLayout {

    private RollingDownloadView viewRollingDownload;
    private ImageView ivCancel;
    private ImageView ivControl;

    public RollingDownloadViewGroup(Context context) {
        this(context, null);
    }

    public RollingDownloadViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollingDownloadViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.layout_rolling_download, this);

        viewRollingDownload = (RollingDownloadView) findViewById(R.id.view_rolling_download);
        ivCancel = (ImageView) findViewById(R.id.iv_cancel);
        ivControl = (ImageView) findViewById(R.id.iv_control);
        ivCancel.setImageResource(R.drawable.ic_cancel);
        ivControl.setImageResource(R.drawable.ic_stop);
        ivCancel.setVisibility(View.GONE);
        ivControl.setVisibility(View.GONE);
        ivCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRollingDownload.setCancel();
                ivCancel.setVisibility(View.GONE);
                ivControl.setVisibility(View.GONE);
            }
        });
        ivControl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStop()) {
                    viewRollingDownload.setStop(false);
                    ivControl.setImageResource(R.drawable.ic_stop);
                } else {
                    viewRollingDownload.setStop(true);
                    ivControl.setImageResource(R.drawable.ic_play);
                }
            }
        });

    }

    public boolean isStop() {
        return viewRollingDownload.isStop();
    }

    public void setProgress(int progress) {
        viewRollingDownload.setProgress(progress);

        boolean isShow = progress > 0 && progress < 100;
        ivControl.setVisibility(isShow ? View.VISIBLE : View.GONE);
        ivCancel.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void setOnProgressStateChangeListener(RollingDownloadView.OnProgressStateChangeListener onProgressStateChangeListener) {
        viewRollingDownload.setOnProgressStateChangeListener(onProgressStateChangeListener);
    }

    public RollingDownloadView.Status getStatus() {
        return viewRollingDownload.getStatus();
    }
}
