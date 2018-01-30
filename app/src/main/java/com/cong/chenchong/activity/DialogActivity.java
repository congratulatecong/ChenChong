
package com.cong.chenchong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.cong.chenchong.R;
import com.cong.chenchong.dialog.ActionSheetDialog;
import com.cong.chenchong.dialog.OverflowPopupWindow;
import com.cong.chenchong.global.SlidingActivity;

public class DialogActivity extends SlidingActivity implements OnClickListener {

    private Button btnEffectDialog;
    private Button btnActionSheetDialog;
    private Button btnPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        btnEffectDialog = (Button) findViewById(R.id.btn_effect_dialog);
        btnEffectDialog.setOnClickListener(this);
        btnActionSheetDialog = (Button) findViewById(R.id.btn_action_sheet_dialog);
        btnActionSheetDialog.setOnClickListener(this);
        btnPopupWindow = (Button) findViewById(R.id.btn_popup_window);
        btnPopupWindow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_effect_dialog:
                intent.setClass(this, EffectDialogActivity.class);
                intent.putExtra("title", btnEffectDialog.getText().toString());
                startActivity(intent);
                break;

            case R.id.btn_action_sheet_dialog:
                ActionSheetDialog.Builder builder = new ActionSheetDialog.Builder(this);
                builder.setTitle(btnActionSheetDialog.getText().toString(),
                        ContextCompat.getColor(this, android.R.color.black))
                        .addOption(R.string.action_sheet_dialog_item1, android.R.color.holo_red_light, () ->
                                Toast.makeText(this, "Item1", Toast.LENGTH_SHORT).show())
                        .addOption(R.string.action_sheet_dialog_item2, android.R.color.holo_orange_light, () ->
                                Toast.makeText(this, "Item2", Toast.LENGTH_SHORT).show())
                        .addOption(R.string.action_sheet_dialog_item3, android.R.color.holo_blue_light, () ->
                                Toast.makeText(this, "Item3", Toast.LENGTH_SHORT).show())
                        .setDialogDismissListener(() -> Toast.makeText(this, "dialog dismiss", Toast.LENGTH_SHORT).show())
                        .create().show();
                break;
            case R.id.btn_popup_window:
                OverflowPopupWindow.PopupWindowBuilder.getInstance()
                        .setMenuItemText("Popup Window")
                        .setOnOperateClickListener(view -> Toast.makeText(this, "popup window", Toast.LENGTH_SHORT).show())
                        .show(btnPopupWindow);

            default:
                break;
        }
    }
}
