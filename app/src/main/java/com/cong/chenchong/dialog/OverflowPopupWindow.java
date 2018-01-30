package com.cong.chenchong.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.cong.chenchong.R;
import com.cong.chenchong.base.ApplicationContextHolder;

public class OverflowPopupWindow extends PopupWindow {

    private static final int WIDTH = SizeUtils.dp2px(150);
    private static final int HEIGHT = SizeUtils.dp2px(40);

    private OnOperateClickListener onOperateClickListener;
    private TextView tvOperateDelete;

    private OnOperateClickListener getOnOperateClickListener() {
        return onOperateClickListener;
    }

    private void setOnOperateClickListener(OnOperateClickListener onOperateClickListener) {
        this.onOperateClickListener = onOperateClickListener;
    }

    private void setMenuItemText(String text) {
        tvOperateDelete.setText(text);
    }

    private OverflowPopupWindow() {
        initPopupWindow();
    }

    private void initPopupWindow() {
        View contentView = LayoutInflater.from(ApplicationContextHolder.getContext())
                .inflate(R.layout.layout_overflow_popup_window, null);
        setContentView(contentView);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setTouchable(true);
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.PopupWindowAnim);
        setBackgroundDrawable(contentView.getBackground());

        initView(contentView);
    }

    private void initView(View contentView) {
        tvOperateDelete = (TextView) contentView.findViewById(R.id.tv_operate_delete);
        tvOperateDelete.setOnClickListener(v -> {
            if (isShowing()) dismiss();
            getOnOperateClickListener().onClick(v);
        });
    }

    public void show(View view) {
        if (isShowing()) {
            dismiss();
        } else {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            showAtLocation(view, 0, location[0], location[1] + view.getHeight());
        }
    }

    public static class PopupWindowBuilder {
        private OverflowPopupWindow popupWindow;
        private static PopupWindowBuilder instance;

        private PopupWindowBuilder() {
            popupWindow = new OverflowPopupWindow();
        }

        public static PopupWindowBuilder getInstance() {
            if (instance == null) {
                synchronized (PopupWindowBuilder.class) {
                    instance = new PopupWindowBuilder();
                }
            }
            return instance;
        }

        public PopupWindowBuilder setMenuItemText(String text) {
            popupWindow.setMenuItemText(text);
            return this;
        }

        public PopupWindowBuilder setOnOperateClickListener(OnOperateClickListener onOperateClickListener) {
            popupWindow.setOnOperateClickListener(onOperateClickListener);
            return this;
        }

        public void show(View view) {
            popupWindow.show(view);
        }

    }

    public interface OnOperateClickListener {
        void onClick(View view);
    }

}
