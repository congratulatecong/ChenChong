package com.cong.chenchong.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;
import com.cong.chenchong.widget.RippleView;

public class RippleActivity extends SlidingActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);

        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText(getIntent().getStringExtra("title"));

        Button btnRipple = (Button) findViewById(R.id.btn_ripple);
        btnRipple.setOnClickListener(this);

        RippleView layoutRipple = (RippleView) findViewById(R.id.layout_ripple);
        layoutRipple.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ripple:
                Toast.makeText(this, "RippleEffect", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_ripple:
                Toast.makeText(this, "RippleEffect", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
}
