package com.cong.chenchong.widget.RollingDownload;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cong.chenchong.R;

public class RollingDownloadViewGroup extends RelativeLayout {

    private RollingDownloadView viewRollingDownload;
    private ImageView ivCancel;
    private ImageView ivControl;
    private Drawable cancelIcon;
    private Drawable continueIcon;
    private Drawable stopIcon;
    //状态以及显示百分比的字体大小
    private int statusSize;
    //状态的颜色
    private int statusColor;

    private int loadPointColor;
    //整个背景的颜色
    private int bgColor;
    //进度的颜色
    private int progressColor;
    //背景收缩的时间
    private int collectSpeed;
    //背景收缩后中间的load转一圈需要的时间
    private int collectRotateSpeed;
    //收缩后背景展开的时间
    private int expandSpeed;
    //loading状态下右边的loading每一次转动时增加的角度
    private int rightLoadingSpeed;
    //左边运动的几个点走一次需要的时间
    private int leftLoadingSpeed;

    public RollingDownloadViewGroup(Context context) {
        this(context, null);
    }

    public RollingDownloadViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollingDownloadViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        View.inflate(context, R.layout.layout_rolling_download, this);

        viewRollingDownload = (RollingDownloadView) findViewById(R.id.view_rolling_download);
        RollingDownloadView.ArgusParams params = new RollingDownloadView.ArgusParams();
        params.statusSize = statusSize;
        params.statusColor = statusColor;
        params.loadPointColor = loadPointColor;
        params.bgColor = bgColor;
        params.progressColor = progressColor;
        params.collectSpeed = collectSpeed;
        params.collectRotateSpeed = collectRotateSpeed;
        params.expandSpeed = expandSpeed;
        params.rightLoadingSpeed = rightLoadingSpeed;
        params.leftLoadingSpeed = leftLoadingSpeed;
        viewRollingDownload.setArgus(params);

        ivCancel = (ImageView) findViewById(R.id.iv_cancel);
        ivControl = (ImageView) findViewById(R.id.iv_control);
        ivCancel.setImageDrawable(cancelIcon);
        ivControl.setImageDrawable(stopIcon);
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
                    ivControl.setImageDrawable(stopIcon);
                } else {
                    viewRollingDownload.setStop(true);
                    ivControl.setImageDrawable(continueIcon);
                }
            }
        });

    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RollingDownloadView);
        try {
            cancelIcon = array.getDrawable(R.styleable.RollingDownloadView_cancel_icon);
            continueIcon = array.getDrawable(R.styleable.RollingDownloadView_continue_icon);
            stopIcon = array.getDrawable(R.styleable.RollingDownloadView_stop_icon);
            if (cancelIcon == null || continueIcon == null || stopIcon == null) {
                throw new IllegalArgumentException("your cancel_icon or stop_icon or continue_icon is null");
            }
            statusSize = sp2px(15);
            statusSize = (int) array.getDimension(R.styleable.RollingDownloadView_status_text_size, statusSize);
            statusColor = Color.WHITE;
            statusColor = array.getColor(R.styleable.RollingDownloadView_status_text_color, statusColor);
            loadPointColor = Color.WHITE;
            loadPointColor = array.getColor(R.styleable.RollingDownloadView_load_point_color, loadPointColor);
            bgColor = Color.parseColor("#00CC99");
            bgColor = array.getColor(R.styleable.RollingDownloadView_bg_color, bgColor);
            progressColor = Color.parseColor("#4400CC99");
            progressColor = array.getColor(R.styleable.RollingDownloadView_progress_color, progressColor);
            collectSpeed = 800;
            collectSpeed = array.getInt(R.styleable.RollingDownloadView_collect_speed, collectSpeed);
            collectRotateSpeed = 1500;
            collectRotateSpeed = array.getInt(R.styleable.RollingDownloadView_collect_rotate_speed, collectRotateSpeed);
            expandSpeed = 1000;
            expandSpeed = array.getInt(R.styleable.RollingDownloadView_expand_speed, expandSpeed);
            rightLoadingSpeed = 7;
            rightLoadingSpeed = array.getInt(R.styleable.RollingDownloadView_right_loading_speed, rightLoadingSpeed);
            leftLoadingSpeed = 2000;
            leftLoadingSpeed = array.getInt(R.styleable.RollingDownloadView_left_loading_speed, leftLoadingSpeed);
        } finally {
            array.recycle();
        }
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

    private int sp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
    }
}
