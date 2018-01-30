package com.cong.chenchong.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationContextHolder.init(this);
        Utils.init(this);
    }
}
