
package com.cong.chenchong.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;

public class WaterDropDetailActivity extends SlidingActivity {
    private static final String EXTRA_TITLE = "extra_title";

    public static void startActivity(Activity activity, String title, View view) {
        Intent intent = new Intent(activity, WaterDropDetailActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, "transition_name_picture");
        activity.startActivity(intent, option.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_drop_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra(EXTRA_TITLE));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }
}
