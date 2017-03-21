package com.cong.chenchong.helper;

import com.cong.chenchong.R;

import android.content.Context;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PageIndicator extends LinearLayout implements OnPageChangeListener {
    @SuppressWarnings("unused")
    private static final String TAG = "PageIndicator";

    public ImageView[] mViews;

    private PageVirtualPositionComputer mComputer;

    private static PageVirtualPositionComputer sDefaultComputer = new PageVirtualPositionComputer() {

        @Override
        public int getVirtualPosition(int position) {
            return position;
        }
    };

    public PageIndicator(Context context) {
        this(context, null);
    }

    public PageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
    }

    public void init(Context context, int count) {
        init(context, count, R.drawable.emotion_dot_focused, R.drawable.emotion_dot_normal);
    }

    public void init(Context context, int count, int selectedImageRid, int unselectedImageRid) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 0, 5, 0);
        mViews = new ImageView[count];
        for (int i = 0; i < count; ++i) {
            mViews[i] = new ImageView(context);
            mViews[i].setBackgroundResource(unselectedImageRid);
            mViews[i].setImageDrawable(context.getResources().getDrawable(selectedImageRid).mutate());
            mViews[i].getDrawable().setAlpha(0);
            addView(mViews[i], params);
            if (mFilterEnd) {
                if (i == 0 || i == count - 1) {
                    mViews[i].setVisibility(View.GONE);
                }
            }
        }
        mViews[0].getDrawable().setAlpha(255);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mComputer == null) {
            mComputer = sDefaultComputer;
        }
        position = mComputer.getVirtualPosition(position);
        if (position >= 0 && position < mViews.length) {
            mViews[position].getDrawable().setAlpha((int) (255 - 255 * (positionOffset)));
        }
        int nextPosition = mComputer.getVirtualPosition(position + 1);
        if (nextPosition >= 0 && nextPosition < mViews.length) {
            mViews[nextPosition].getDrawable().setAlpha((int) (255 * positionOffset));
        }
    }

    @Override
    public void onPageSelected(int page) {
        //Bug 66178 - b1013 【信息】信息会话或新建信息界面点击表情面板，表情面板最底端页数导航第一点没有高亮显示
        //首次进入滑动之前，需要高亮显示当前位置
        if (mComputer == null) {
            mComputer = sDefaultComputer;
        }
        int position = page;
        position = mComputer.getVirtualPosition(position);
        if (position >= 0 && position < mViews.length) {
            mViews[position].getDrawable().setAlpha(255);
        }
    }

    public static interface PageVirtualPositionComputer {
        int getVirtualPosition(int position);
    }

    public void setPageVirtualPositionComputer(PageVirtualPositionComputer computer) {
        mComputer = computer;
    }

    private boolean mFilterEnd = false;
    public void setFilterEnd(boolean filter) {
        mFilterEnd = filter;
    }
}
