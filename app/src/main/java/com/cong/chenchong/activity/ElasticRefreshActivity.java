package com.cong.chenchong.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;
import com.cong.chenchong.util.ToastHelper;
import com.cong.chenchong.widget.ElasticScrollView;
import com.cong.chenchong.widget.ElasticScrollView.OverScrollListener;
import com.cong.chenchong.widget.ElasticScrollView.OverScrollTinyListener;

public class ElasticRefreshActivity extends SlidingActivity implements OverScrollListener, OverScrollTinyListener {

    /**
     * 初始化填充值，控制图片被隐藏的边缘值
     */
    private static final int PADDING = -100;

    private ImageView mHeaderImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elastic_refresh);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        mHeaderImage = (ImageView) findViewById(R.id.image);

        ElasticScrollView scrollView = (ElasticScrollView) findViewById(R.id.layout);

        scrollView.setOverScrollListener(this);
        scrollView.setOverScrollTinyListener(this);

        // 隐藏图片的初始边界
        // 保证下拉能出现放大图片的效果
        scrollLoosen();
    }

    public void scrollDistance(int tinyDistance, int totalDistance) {
        if (totalDistance > 0 || tinyDistance == 0
                || mHeaderImage.getPaddingBottom() == 0) {
            return;
        }
        int padding = PADDING - totalDistance / 2;
        if (padding > 0) {
            padding = 0;
        }
        mHeaderImage.setPadding(padding, 0, padding, padding);
    }

    public void scrollLoosen() {
        int padding = PADDING;
        mHeaderImage.setPadding(padding, 0, padding, padding);

        // 滑动处理松开时回调
    }

    public void headerScroll() {
        ToastHelper.toast("开始刷新");

        // 处理下拉超过一定临界值时 的回调
    }

    public void footerScroll() {
        // 处理上拉超过一定临界值时 的回调
    }

}
