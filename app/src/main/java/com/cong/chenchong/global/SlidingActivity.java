package com.cong.chenchong.global;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.cong.chenchong.R;
import com.cong.chenchong.widget.swipeBackLayout.BGASwipeBackLayout;

public abstract class SlidingActivity extends ImmersionActivity implements BGASwipeBackLayout.PanelSlideListener {
    protected BGASwipeBackLayout mSwipeBackLayout;

    /**
     * 初始化滑动返回
     */
    private void initSwipeBackFinish() {
        if (isSupportSwipeBack()) {
            mSwipeBackLayout = new BGASwipeBackLayout(this);
            mSwipeBackLayout.attachToActivity(this);
            mSwipeBackLayout.setPanelSlideListener(this);
            // 设置滑动返回是否可用。默认值为 true
            mSwipeBackLayout.setSwipeBackEnable(true);
            // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
            mSwipeBackLayout.setIsOnlyTrackingLeftEdge(false);
            // 设置是否是微信滑动返回样式。默认值为 true「如果需要启用微信滑动返回样式，必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this)」
            mSwipeBackLayout.setIsWeChatStyle(false);
            // 设置是否显示滑动返回的阴影效果。默认值为 true
            mSwipeBackLayout.setIsNeedShowShadow(true);
            // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
            mSwipeBackLayout.setIsShadowAlphaGradient(true);
        }
    }

    /**
     * 是否支持滑动返回。默认支持，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    protected boolean isSupportSwipeBack() {
        return true;
    }

    private boolean isSupportSlidingOpen(Intent intent) {
        try {
            if (intent.getComponent() != null &&
                    SlidingActivity.class.isAssignableFrom(
                            Class.forName(intent.getComponent().getClassName()))) {
                return true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 动态设置滑动返回是否可用。
     *
     * @param swipeBackEnable
     */
    protected void setSwipeBackEnable(boolean swipeBackEnable) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setSwipeBackEnable(swipeBackEnable);
        }
    }

    @Override
    public void onPanelClosed(View view) {
    }

    @Override
    public void onPanelOpened(View view) {
        swipeBackward();
    }

    @Override
    public void onPanelSlide(View view, float v) {
    }

    @Override
    public View getFitWindowView() {
        return ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initSwipeBackFinish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * 回到上一个Activity，并销毁当前Activity
     */
    @Override
    public void finish() {
        super.finish();
        executeBackwardAnim();
    }

    public void openActivity(Class<?> pClass, Bundle pBundle, int requestCode) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (requestCode == 0) {
            startActivity(intent);
        } else {
            startActivityForResult(intent, requestCode);
        }
    }

    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null, 0);
    }

    /**
     * 滑动返回上一个Activity，并销毁当前Activity
     */
    public void swipeBackward() {
        finish();
        executeSwipeBackAnim();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (isSupportSlidingOpen(intent)) {
            executeForwardAnim();
        }
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
        if (isSupportSlidingOpen(intent)) {
            executeForwardAnim();
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (isSupportSlidingOpen(intent)) {
            executeForwardAnim();
        }
    }

    /**
     * 执行跳转到下一个Activity的动画
     */
    public void executeForwardAnim() {
        //TODO:背景设置透明，R.anim.activity_forward_exit无效
        overridePendingTransition(R.anim.activity_forward_enter, R.anim.activity_forward_exit);
    }

    public void executeBackwardAnim() {
        //TODO:背景设置透明，R.anim.activity_backward_enter无效
        overridePendingTransition(R.anim.activity_backward_enter, R.anim.activity_backward_exit);
    }

    public void executeSwipeBackAnim() {
        overridePendingTransition(R.anim.activity_swipeback_enter, R.anim.activity_swipeback_exit);
    }

}
