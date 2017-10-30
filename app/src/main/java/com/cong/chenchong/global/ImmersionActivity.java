package com.cong.chenchong.global;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.cong.chenchong.R;
import com.cong.chenchong.util.StatusBarUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


public abstract class ImmersionActivity extends RxAppCompatActivity {

    private View mFitWindowView;

    abstract public View getFitWindowView();

    protected void setStatusBarColor(int color) {
        StatusBarUtil.setStatusBarTintViewColor(this, color);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //状态栏沉浸
        setFitsSystemWindows(true);
        setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
    }

    protected void setFitsSystemWindows(boolean enabled) {
        if (mFitWindowView == null) {
            mFitWindowView = getFitWindowView();
        }
        if (mFitWindowView != null) {
            mFitWindowView.setFitsSystemWindows(enabled);
        }
    }

    protected void setStatusBarAlpha(float alpha) {
        StatusBarUtil.getSystemBarTintManager(this).setStatusBarAlpha(alpha);
    }

    protected void setStatusBarTransparent(boolean transparent) {
        setStatusBarAlpha(transparent ? 0f : 1f);
    }

}
