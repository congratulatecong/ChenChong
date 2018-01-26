
package com.cong.chenchong.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.cong.chenchong.R;
import com.cong.chenchong.dialog.Effectstype;
import com.cong.chenchong.dialog.NiftyDialogBuilder;
import com.cong.chenchong.global.SlidingActivity;

public class EffectDialogActivity extends SlidingActivity {

    private Effectstype effect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_effect);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

    }

    public void dialogShow(View v) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);

        switch (v.getId()) {
            case R.id.fadein:
                effect = Effectstype.Fadein;
                break;
            case R.id.slideright:
                effect = Effectstype.Slideright;
                break;
            case R.id.slideleft:
                effect = Effectstype.Slideleft;
                break;
            case R.id.slidetop:
                effect = Effectstype.Slidetop;
                break;
            case R.id.slideBottom:
                effect = Effectstype.SlideBottom;
                break;
            case R.id.newspager:
                effect = Effectstype.Newspager;
                break;
            case R.id.fall:
                effect = Effectstype.Fall;
                break;
            case R.id.sidefall:
                effect = Effectstype.Sidefill;
                break;
            case R.id.fliph:
                effect = Effectstype.Fliph;
                break;
            case R.id.flipv:
                effect = Effectstype.Flipv;
                break;
            case R.id.rotatebottom:
                effect = Effectstype.RotateBottom;
                break;
            case R.id.rotateleft:
                effect = Effectstype.RotateLeft;
                break;
            case R.id.slit:
                effect = Effectstype.Slit;
                break;
            case R.id.shake:
                effect = Effectstype.Shake;
                break;
        }

        dialogBuilder.withTitle("Custom Dialog")
                .withTitleColor("#FFFFFF")
                .withDividerColor("#11000000")
                .withMessage("This is a custom dialog.")
                .withMessageColor("#FFFFFF")
                .withIcon(getResources().getDrawable(R.drawable.icon_github))
                .isCanceledOnTouchOutside(true)
                .withDuration(800)
                .withEffect(effect)
                .withButton1Text("OK")
                .withButton2Text("Cancel")
                .setCustomView(R.layout.custom_view, v.getContext())
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "I'm btn1", Toast.LENGTH_SHORT).show();
                    }
                }).setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "I'm btn2", Toast.LENGTH_SHORT).show();
                dialogBuilder.dismiss();
            }
        }).show();

    }
}
