
package com.cong.chenchong.base;

import com.cong.chenchong.activity.RobotActivity;

import android.content.Context;

public class SPConfigManager {

    private MySharedPreferences mSharedPreferences;

    public SPConfigManager(Context context) {
        mSharedPreferences = new MySharedPreferences(context, "chenchong_sp");
    }

    public void setFirstRun(boolean isFirstRun) {
        mSharedPreferences.putBoolean("is_first_run", isFirstRun);
    }

    public boolean isFirstRun() {
        return mSharedPreferences.getBoolean("is_first_run", true);
    }

    public void setChangeSizeGuaidShow(boolean isGuaidShow) {
        mSharedPreferences.putBoolean("is_guaid_show", isGuaidShow);
    }

    public boolean isChangeSizeGuaidShow() {
        return mSharedPreferences.getBoolean("is_guaid_show", true);
    }

    public void setFontSize(int fontSize) {
        mSharedPreferences.putInt("font_size", fontSize);
    }

    public int getFontSize() {
        return mSharedPreferences.getInt("font_size", RobotActivity.TxtSizeDefault);
    }

    public void setPopupMenuShow(boolean isPopupMenuShow) {
        mSharedPreferences.putBoolean("is_popup_menu_show", isPopupMenuShow);
    }

    public boolean isPopupMenuShow() {
        return mSharedPreferences.getBoolean("is_popup_menu_show", true);
    }
}
