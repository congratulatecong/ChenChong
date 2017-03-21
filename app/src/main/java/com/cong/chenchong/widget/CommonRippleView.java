
package com.cong.chenchong.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @author huchangqing
 * 仿5.0按下涟漪效果
 */
public class CommonRippleView extends RelativeLayout {

    private final CommonRippleHelper mRippleHelper;

    public CommonRippleView(Context context) {
        this(context, null);
    }

    public CommonRippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRippleHelper = new CommonRippleHelper(this);
    }

    public CommonRippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRippleHelper = new CommonRippleHelper(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mRippleHelper.onRippleTouch(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRippleHelper.onRippleDraw(canvas);
        super.onDraw(canvas);
    }

    @Override
    public boolean performClick() {
        if (mRippleHelper.isDrawRippleDrawing()) {
            mRippleHelper.performClick();
            return true;
        } else {
            return super.performClick();
        }
    }

    public void setRoundRadius(float value) {
        mRippleHelper.setRoundRadius(value);
    }

    public void releaseRipple() {
        mRippleHelper.releaseRipple();
    }
}
