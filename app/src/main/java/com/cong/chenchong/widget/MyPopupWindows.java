package com.cong.chenchong.widget;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

import android.widget.PopupWindow;
import android.app.Activity;
import android.content.Context;

public class MyPopupWindows {
	protected Context mContext;
	protected PopupWindow mWindow;
	protected View mRootView;
	protected Drawable mBackground = null;
	protected WindowManager mWindowManager;

	public MyPopupWindows(Context context) {
		mContext	= context;
		mWindow 	= new PopupWindow(context);
		mWindow.setTouchable(true);
	      mWindow.setFocusable(true);// 保证点击空白的地方popup window 消失
	        mWindow.setOutsideTouchable(true);// 保证点击空白的地方popup window 消失
		//保证点击空白的地方popup window 消失
		mWindow.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});
		mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	}

	protected void preShow(boolean needFocus) {
		if (mRootView == null)
			throw new IllegalStateException("setContentView was not called with a view to display.");
		if (mBackground == null)//保证点击空白的地方popup window 消失
			mWindow.setBackgroundDrawable(new BitmapDrawable());
		else
			mWindow.setBackgroundDrawable(mBackground);

		mWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		mWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		mWindow.setTouchable(true);
		if (needFocus){
		    mWindow.setFocusable(true);//保证点击空白的地方有响应
		}else{
		    mWindow.setFocusable(false);
		}
		mWindow.setOutsideTouchable(true);//保证点击空白的地方popup window 消失

		mWindow.setContentView(mRootView);
	}
	public void setContentView(View root) {
		mRootView = root;
		mWindow.setContentView(root);
	}

	public void setContentView(int layoutResID) {
		LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		setContentView(inflator.inflate(layoutResID, null));
	}

	public void dismiss() {
		if (mContext instanceof Activity 
			&& !((Activity) mContext).isFinishing()
			&& mWindow != null && mWindow.isShowing()) {
			mWindow.dismiss();
        }
	}
}