
package com.cong.chenchong.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class TestLinearLayout extends LinearLayout {


    public TestLinearLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public TestLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public TestLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        // LayoutInflater.from(context).inflate(R.layout.layout_toolbar,
        // this);
        // LayoutInflater.from(context).inflate(R.layout.layout_toolbar,
        // null);
        // addView(LayoutInflater.from(context).inflate(R.layout.layout_toolbar,
        // null));
        // mImageView = (ImageView) findViewById(R.id.iv_test);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("cc", "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.v("cc", "ACTION_DOWN");
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
        // return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        super.onFinishInflate();
    }
}
