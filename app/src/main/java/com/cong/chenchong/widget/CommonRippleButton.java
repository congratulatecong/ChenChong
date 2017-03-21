
package com.cong.chenchong.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * @author huchangqing
 * 仿5.0按下涟漪效果
 */
public class CommonRippleButton extends AppCompatButton {

    private final CommonRippleHelper mRippleHelper;

    public CommonRippleButton(Context context) {
        this(context, null);
    }

    public CommonRippleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRippleHelper = new CommonRippleHelper(this);
    }

    public CommonRippleButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
