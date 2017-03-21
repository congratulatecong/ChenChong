
package com.cong.chenchong.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * @author chenchong-ms 解决编辑框打开表情面板之后再切换到系统输入法时表情面板不消失的问题
 */
public class EditTextBase extends AppCompatEditText {

    public interface OnCloseOtherViewsListerner {
        void onCloseOtherViews(EditTextBase etb);
    }

    private OnCloseOtherViewsListerner onCloseOtherViewsListerner;

    public void setOnCloseOtherViewsListerner(OnCloseOtherViewsListerner onCloseOtherViewsListerner) {
        this.onCloseOtherViewsListerner = onCloseOtherViewsListerner;
    }

    public EditTextBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EditTextBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextBase(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        onCloseOtherViewsListerner.onCloseOtherViews(this);
        return super.onTouchEvent(event);
    }

}
