package com.cong.chenchong.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;
import com.cong.chenchong.util.ToastHelper;
import com.cong.chenchong.widget.RippleView;

public class RippleActivity extends SlidingActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Button btnRipple = (Button) findViewById(R.id.btn_ripple);
        btnRipple.setOnClickListener(this);

        RippleView layoutRipple = (RippleView) findViewById(R.id.layout_ripple);
        layoutRipple.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ripple:
                ToastHelper.toast("RippleEffect");
                break;
            case R.id.layout_ripple:
                ToastHelper.toast("RippleEffect");
                break;

            default:
                break;
        }
    }
}
