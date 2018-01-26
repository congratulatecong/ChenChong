package com.cong.chenchong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cong.chenchong.R;

import java.util.ArrayList;
import java.util.List;

public class ActionSheetDialog extends Dialog {

    private static final int DEFAULT_TITLE_SIZE = 16;
    private static final int DEFAULT_OPTION_SIZE = 16;

    private LinearLayout layoutOptionArea;
    private TextView tvDialogTitle;
    private View viewTitleLine;
    private OnDialogDismissListener dismissListener;

    private ActionSheetDialog(Context context) {
        super(context, R.style.FullScreenDialog);
        setContentView(R.layout.layout_action_sheet_dialog);
        initView();
    }

    private void initView() {
        tvDialogTitle = (TextView) findViewById(R.id.tv_dialog_title);
        viewTitleLine = findViewById(R.id.view_title_line);
        layoutOptionArea = (LinearLayout) findViewById(R.id.layout_option_area);
        findViewById(R.id.tv_cancel_btn).setOnClickListener(v -> dismiss());

        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.ActionSheetAnim);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (dismissListener != null) {
            dismissListener.onDismiss();
        }
    }

    private void setOnDialogDismissListener(OnDialogDismissListener listener) {
        this.dismissListener = listener;
    }

    public interface OnOptionClickListener {
        void onOptionClick();
    }

    public interface OnDialogDismissListener {
        void onDismiss();
    }

    public static class Builder {
        private DialogParameter dialogParameter;
        private Context context;

        public Builder(Context context) {
            dialogParameter = new DialogParameter();
            this.context = context;
        }

        public Builder setTitle(String title, int color) {
            dialogParameter.title = title;
            dialogParameter.titleColor = color;
            return this;
        }

        public Builder addOption(int textId, int colorId, OnOptionClickListener listener) {
            dialogParameter.options.add(new Option(textId, colorId, listener));
            return this;
        }

        public Builder setDialogDismissListener(OnDialogDismissListener listener) {
            dialogParameter.dismissListener = listener;
            return this;
        }

        public ActionSheetDialog create() {
            final ActionSheetDialog dialog = new ActionSheetDialog(context);
            final ViewGroup.LayoutParams dividerLineParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            int height = context.getResources().getDimensionPixelSize(R.dimen.action_sheet_item_h);
            ViewGroup.LayoutParams optionParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

            if (dialogParameter.title.isEmpty()) {
                dialog.tvDialogTitle.setVisibility(View.GONE);
                dialog.viewTitleLine.setVisibility(View.GONE);
            } else {
                dialog.tvDialogTitle.setText(dialogParameter.title);
                dialog.tvDialogTitle.setTextColor(dialogParameter.titleColor);
                dialog.tvDialogTitle.setTextSize(dialogParameter.titleSize);
                dialog.tvDialogTitle.setVisibility(View.VISIBLE);
                dialog.viewTitleLine.setVisibility(View.VISIBLE);
            }

            if (dialogParameter.options.size() == 0) {
                dialog.layoutOptionArea.setVisibility(View.GONE);
            } else {
                for (int i = 0; i < dialogParameter.options.size(); i++) {
                    final Option option = dialogParameter.options.get(i);
                    final TextView tvOption = new TextView(context);
                    tvOption.setText(context.getString(option.getTextId()));
                    tvOption.setGravity(Gravity.CENTER);
                    tvOption.setTextSize(dialogParameter.optionTextSize);
                    tvOption.setTextColor(ContextCompat.getColor(context, option.getColorId()));
                    tvOption.setBackgroundResource(R.drawable.bg_list_item);
                    dialog.layoutOptionArea.addView(tvOption, optionParams);

                    tvOption.setOnClickListener(v -> {
                        dialog.dismiss();
                        if (option.getListener() != null) {
                            option.getListener().onOptionClick();
                        }
                    });

                    if (i != dialogParameter.options.size() - 1) {
                        View divider = new View(context);
                        divider.setBackgroundResource(R.color.common_divider_line);
                        dialog.layoutOptionArea.addView(divider, dividerLineParams);
                    }
                }
            }

            dialog.setOnDialogDismissListener(dialogParameter.dismissListener);
            return dialog;
        }
    }

    private static class DialogParameter {
        int titleSize;
        int optionTextSize;
        String title;
        int titleColor;
        boolean cancelable;
        List<Option> options;
        OnDialogDismissListener dismissListener;

        DialogParameter() {
            title = "";
            titleColor = Color.BLACK;
            cancelable = true;
            options = new ArrayList<>();
            titleSize = DEFAULT_TITLE_SIZE;
            optionTextSize = DEFAULT_OPTION_SIZE;
        }
    }

    private static class Option {
        private int textId;
        private int colorId;
        private ActionSheetDialog.OnOptionClickListener listener;

        Option(int textId, int colorId, ActionSheetDialog.OnOptionClickListener listener) {
            this.textId = textId;
            this.colorId = colorId;
            this.listener = listener;
        }

        int getTextId() {
            return textId;
        }

        void setTextId(int textId) {
            this.textId = textId;
        }

        int getColorId() {
            return colorId;
        }

        void setColorId(int colorId) {
            this.colorId = colorId;
        }

        ActionSheetDialog.OnOptionClickListener getListener() {
            return listener;
        }

        void setListener(ActionSheetDialog.OnOptionClickListener listener) {
            this.listener = listener;
        }
    }

}