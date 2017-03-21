
package com.cong.chenchong.widget;

import com.cong.chenchong.BuildConfig;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author huchangqing
 * target 为16会卡顿
 * 仿5.0按下涟漪效果
 */
public class CommonRippleHelper {

    private int mExpandDurationSlow;

    private int mExpandDurationFast;

    private int mAlphaDuration;

    private static final float ALPHA_TARGET = 0.1f;

    private static final int FPS = 20;

    private final View mHost;

    private float mCenterX;

    private float mCenterXAdd;

    private float mCenterXTarget;

    private float mCenterY;

    private float mRadius;

    private float mRadiusAdd;

    private float mRadiusTarget;

    private float mAlpha;

    private float mAlphaAdd;

    private float mAlphaTarget;

    private final Paint mPaint;

    private boolean mDrawRipple;

    private boolean mClicked;

    private static int screenW;

    private int mWidth;

    private int mHeight;

    private RectF mBound;

    private float mRoundRadius;

    /**防止多次点击*/
    private static long sLastClickTime;

    private static final int EXP_DURATION_FAST = 200;

    private static final int EXP_DURATION_SLOW = 1200;

    public CommonRippleHelper(View v) {
        v.setClickable(true);
        mHost = v;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void onRippleTouch(MotionEvent event) {
        //        System.out.println("onRippleTouch mClicked: " + mClicked);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (mClicked || !mHost.isEnabled()) {
                return;
            }
            //            System.out.println("down");
            if (mHost instanceof ViewGroup) {
                if (interByChild((ViewGroup) mHost, event.getX(), event.getY())) {
                    return;
                }
            }
            mDrawRipple = true;
            mCenterY = event.getY();

            if (screenW == 0) {
                final WindowManager wm = (WindowManager) mHost.getContext().getSystemService(Context.WINDOW_SERVICE);
                screenW = wm.getDefaultDisplay().getWidth();
            }

            mWidth = mHost.getWidth();
            mHeight = mHost.getHeight();
            if (mBound == null) {
                mBound = new RectF(0, 0, mWidth, mHeight);
            }

            final float ratio = (float) mWidth / screenW;

            mExpandDurationSlow = (int) (EXP_DURATION_SLOW * ratio);
            mExpandDurationFast = EXP_DURATION_FAST;
            mAlphaDuration = 200;

            mCenterX = event.getX();
            mCenterXTarget = mWidth >> 1;
            mCenterXAdd = (mCenterXTarget - mCenterX) * FPS / mExpandDurationSlow;

            mRadius = 0;
            final int height = mHeight;
            if (mCenterY >= (height >> 1)) {
                mRadiusTarget = (float) Math.sqrt(Math.pow(mWidth >> 1, 2) + Math.pow(mCenterY, 2));
            } else {
                mRadiusTarget = (float) Math.sqrt(Math.pow(mWidth >> 1, 2) + Math.pow(height - mCenterY, 2));
            }
            mRadiusAdd = (mRadiusTarget - mRadius) * FPS / mExpandDurationSlow;

            mAlpha = 0;
            mAlphaTarget = ALPHA_TARGET;
            mAlphaAdd = ALPHA_TARGET * FPS / mAlphaDuration;
            mHost.invalidate();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            final float x = event.getX();
            final float y = event.getY();
            if (x < 0 || x > mWidth || y < 0 || y > mHeight) {
                quickAnim();
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            quickAnim();
        }
    }

    private boolean interByChild(ViewGroup vg, float x, float y) {
        final int childCount = vg.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                final View v = vg.getChildAt(i);
                if (v.getVisibility() != View.VISIBLE) {
                    continue;
                }
                if (v instanceof ViewGroup) {
                    if (x < v.getRight() && x > v.getLeft() && y < v.getBottom() && y > v.getTop()) {
                        if (!interByChild((ViewGroup) v, x - v.getLeft(), y - v.getTop())) {
                            continue;
                        } else {
                            return true;
                        }
                    } else {
                        continue;
                    }
                }
                if (v.isClickable() && x < v.getRight() && x > v.getLeft() && y < v.getBottom() && y > v.getTop()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setRoundRadius(float value) {
        mRoundRadius = value;
    }

    private void quickAnim() {
        if (!mDrawRipple) {
            return;
        }
        if (mAlphaAdd > 0) {
            mAlpha = ALPHA_TARGET;
            mAlphaAdd = -mAlphaAdd;
            mAlphaTarget = 0;

            mCenterXAdd = mCenterXAdd * mExpandDurationSlow / mExpandDurationFast;
            mRadiusAdd = mRadiusAdd * mExpandDurationSlow / mExpandDurationFast;
        }
    }

    public void onRippleDraw(Canvas canvas) {
        //        System.out.println("mDrawRipple:" + mDrawRipple + ", mAlpha:" + mAlpha + ", mAlphaAdd:" + mAlphaAdd
        // + ", mAlphaTarget:" + mAlphaTarget + ", mRadius:" + mRadius + ", mRadiusAdd:" + mRadiusAdd
        //                + ", mCenterX:" + mCenterX + ", mCenterXAdd:" + mCenterXAdd);
        if (mDrawRipple) {
            // TextView 如果设置Gravity为非LEFT、TOP并且singleLine为true，会导致Ripple显示不出来，原因是canvas的matrix被修改了
            canvas.save();
            final int scrollX = mHost.getScrollX();
            final int scrollY = mHost.getScrollY();
            if (scrollX != 0 || scrollY != 0) {
                canvas.translate(scrollX, scrollY);
            }
            //画背景
            mPaint.setColor(0);
            mPaint.setAlpha((int) (mAlpha * 255));
            if (mRoundRadius == 0) {
                canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
            } else {
                canvas.drawRoundRect(mBound, mRoundRadius, mRoundRadius, mPaint);
            }
            mAlpha += mAlphaAdd;
            if (mAlphaAdd < 0) {
                if (mAlpha < mAlphaTarget) {
                    mAlpha = mAlphaTarget;
                    mDrawRipple = false;
                    if (mClicked) {
                        // 部分机型(三星I9082)popwindow在onDraw中调用performClick会崩溃
                        mHost.post(new Runnable() {

                            @Override
                            public void run() {
                                checkListItemClick();
                                mHost.performClick();
                                //                                System.out.println("mHost.performClick()");
                            }
                        });
                        mClicked = false;
                    }
                }
            } else {
                if (mAlpha > mAlphaTarget) {
                    mAlpha = mAlphaTarget;
                }
            }

            // 画圆
            if (mAlphaTarget == ALPHA_TARGET) {
                mPaint.setAlpha((int) (ALPHA_TARGET * 255));
            }
            if (mRoundRadius != 0 && mRadius >= mRadiusTarget - mRoundRadius) {
                canvas.drawRoundRect(mBound, mRoundRadius, mRoundRadius, mPaint);
            } else {
                canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
            }
            mCenterX += mCenterXAdd;
            if (mCenterXAdd < 0) {
                if (mCenterX < mCenterXTarget) {
                    mCenterX = mCenterXTarget;
                }
            } else {
                if (mCenterX > mCenterXTarget) {
                    mCenterX = mCenterXTarget;
                }
            }
            mRadius += mRadiusAdd;
            if (mRadius > mRadiusTarget) {
                mRadius = mRadiusTarget;
            }
            canvas.restore();
            mHost.invalidate();
        }
    }

    // 判断是否处于ListView中，由于消费了触摸事件，ListView的OnItemClick事件不会被触发，要手动触发
    private void checkListItemClick() {
        try {
            final View parent = (View) mHost.getParent();
            if (parent instanceof AdapterView) {
                final AdapterView<?> listview = (AdapterView<?>) parent;
                final int position = listview.getPositionForView(mHost);
                final OnItemClickListener l = listview.getOnItemClickListener();
                if (l != null) {
                    l.onItemClick(listview, mHost, position, listview.getItemIdAtPosition(position));
                }
            }
        } catch (final Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    public boolean isDrawRippleDrawing() {
        return mDrawRipple;
    }

    public void performClick() {
        final long time = System.currentTimeMillis();
        if (Math.abs(time - sLastClickTime) > EXP_DURATION_FAST) {
            sLastClickTime = time;
            mClicked = true;
            //            System.out.println("performClick");
        }
    }

    public void clearRipple() {
        mDrawRipple = false;
    }

    public void releaseRipple() {
        quickAnim();
    }
}
