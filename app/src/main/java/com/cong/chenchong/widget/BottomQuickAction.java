
package com.cong.chenchong.widget;

import java.util.ArrayList;
import java.util.List;

import com.cong.chenchong.R;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class BottomQuickAction extends MyPopupWindows {

    private LayoutInflater inflater;

    private OnActionItemClickListener mItemClickListener;

    private OnDismissListener mDismissListener;

    private List<BottomItem> mActionItemList = new ArrayList<BottomItem>();

    private boolean mIsTopRight = true;

    private int mChildPos;

    // public static boolean isFinishing = false;
    // public static boolean isClickAnchor = false;
    private static boolean isClickSelf = false;

    private Context mContext;

    /**
     * @param context
     * @param myselfView 点击的按钮
     * @param anchor 整个底部输入框
     */
    public BottomQuickAction(Context context, final View myselfView, final View anchor) {
        super(context);
        mContext = context;

        mWindow.setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    int x = (int) event.getRawX();
                    int y = (int) event.getRawY();
                    int[] location = new int[2];
                    anchor.getLocationOnScreen(location);
                    int[] locationSelf = new int[2];
                    myselfView.getLocationOnScreen(locationSelf);
                    // 判断点击区域是底部输入框，并且不是自己
                    if (x > location[0] && x < location[0] + anchor.getWidth() && y > location[1] && y < location[1] + anchor.getHeight()) {
                        if (((x > locationSelf[0] && x < locationSelf[0] + myselfView.getWidth() && y > locationSelf[1] && y < locationSelf[1] + myselfView.getHeight()))) {
                            isClickSelf = true;
                            dismiss();
                        } else {
                            isClickSelf = false;
                            dismissNorAnimation();
                        }
                    } else {
                        isClickSelf = false;
                        dismiss();
                    }

                    return true;
                }
                return false;
            }
        });
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setRootViewId(R.layout.bottom_action_menu);
        mChildPos = 0;
    }

    public BottomQuickAction(Context context) {
        super(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setRootViewId(R.layout.bottom_action_menu);
        mChildPos = 0;
    }

    public BottomQuickAction(Context context, boolean isTopRight) {
        super(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setRootViewId(R.layout.bottom_action_menu);
        mChildPos = 0;
        mIsTopRight = isTopRight;
    }

    public void setRootViewId(int id) {
        mRootView = inflater.inflate(id, null);
        mRootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        setContentView(mRootView);
    }

    public void addActionListItems(List<BottomItem> secondPubNumItems) {
        if (secondPubNumItems == null || secondPubNumItems.size() == 0) {
            return;
        }
        for (BottomItem actionItem : secondPubNumItems) {
            addActionItem(actionItem);
        }
    }

    /** 添加item */
    public void addActionItem(BottomItem action) {
        if (action == null) {
            return;
        }
        mActionItemList.add(action);
        String title = action.title;
        View container = inflater.inflate(R.layout.bottom_action_menu_item, null);
        TextView textView = (TextView) container.findViewById(R.id.vertical_popup_title);
        final BottomItem maActionItem = action;
        // container.setBackgroundResource(R.drawable.bottom_action_item_bg);
        if (title != null) {
            textView.setText(title);
        } else {
            textView.setVisibility(View.GONE);
        }

        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissNorAnimation();
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(maActionItem);
                }
            }
        });
        container.setFocusable(true);
        container.setClickable(true);
        ((ViewGroup) mRootView).addView(container, mChildPos);
        mChildPos++;
    }

    public void clear() {
        mChildPos = 0;
        if (mRootView != null) {
            ((ViewGroup) mRootView).removeAllViews();
        }
        if (mActionItemList != null) {
            mActionItemList.clear();
        }
    }

    public void setOnActionItemClickListener(OnActionItemClickListener listener) {
        mItemClickListener = listener;
    }

    /**
     * Show popup mWindow
     */
    public void show(View anchor) {
        if (mActionItemList == null || mActionItemList.size() < 1) {
            return;
        }
        if (isClickSelf) {
            isClickSelf = false;
            return;
        }
        preShow(false);
        // mWindow.setAnimationStyle(R.style.bottom_popmenu_animation);
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);
        mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // mRootView.setAnimation(getAnimationIn());
        mRootView.clearAnimation();
        mRootView.startAnimation(getAnimationIn());

        mWindow.update();
        int rootWidth = mRootView.getMeasuredWidth();
        int rootHeight = mRootView.getMeasuredHeight();
        if (rootHeight < 10) {
            return;
        }
        int screenWidth = mWindowManager.getDefaultDisplay().getWidth();
        int screenHeight = mWindowManager.getDefaultDisplay().getHeight();
        int yPos = location[1] - rootHeight - 3;
        int xPos = location[0] + (anchor.getWidth() - rootWidth) / 2;
        if (xPos > screenWidth - 20) {
            xPos = screenWidth - 20;
        } else if (xPos < 20) {
            xPos = 20;
        }
        mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
        // mWindow.showAsDropDown(anchor);

    }

    private Animation getAnimationIn() {
        // ScaleAnimation sa = new ScaleAnimation(0.5f, 1, 0.5f, 1,
        // Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
        // AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1);
        // AnimationSet animationSet = new AnimationSet(true);
        // animationSet.addAnimation(sa);
        // animationSet.addAnimation(alphaAnimation);
        // animationSet.setDuration(200);
        // animationSet.setInterpolator(new DecelerateInterpolator());
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.bottom_popmenu_animation_in);
        return animation;

    }

    private Animation getAnimationOut() {
        // ScaleAnimation sa = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f,
        // Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.0f);
        // AnimationSet animationSet = new AnimationSet(true);
        // animationSet.addAnimation(sa);
        // animationSet.addAnimation(alphaAnimation);
        // animationSet.addAnimation(ta);
        // animationSet.setDuration(150);
        // animationSet.setInterpolator(new DecelerateInterpolator());
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.bottom_popmenu_animation_out);
        return animation;

    }

    public void setOnDismissListener(BottomQuickAction.OnDismissListener listener) {
        mDismissListener = listener;
    }

    public void dismissNorAnimation() {
        if (mDismissListener != null) {
            mDismissListener.onDismiss();
        }
        if (mWindow != null && mWindow.isShowing()) {
            mWindow.dismiss();
            clear();
        }
    }

    @Override
    public void dismiss() {

        if (mDismissListener != null) {
            mDismissListener.onDismiss();
        }

        // super.dismiss(); // 5.1.5部分4.1.2机器上消失动画会异常，暂时去掉消失动画。
        // clear();
        mRootView.startAnimation(getAnimationOut());

        Animation as = getAnimationOut();
        as.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub
                // isFinishing = true;
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {

                        if (mWindow != null && mWindow.isShowing()) {
                            mWindow.dismiss();
                        }
                    }
                });
                clear();
            }
        });
        mRootView.startAnimation(as);
    }

    public interface OnActionItemClickListener {
        public abstract void onItemClick(BottomItem sourceAction);
    }

    public interface OnDismissListener {
        public abstract void onDismiss();
    }

    public static class BottomItem {
        public String title;

        public Object data;
    }

}
