
package com.cong.chenchong.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class CommonBtn extends AppCompatButton {

    public CommonBtn(Context context) {
        this(context, null);
    }

    public CommonBtn(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonBtn(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // setBackgroundResource(R.drawable.img_solid_bg);
        // setGravity(Gravity.CENTER);
    }

}
