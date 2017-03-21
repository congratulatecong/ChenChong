
package com.cong.chenchong.base;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    private SharedPreferences mSharedPreferences;

    public MySharedPreferences(Context context, String name) {
        mSharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public boolean putBoolean(String key, boolean value) {
        return mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    public boolean putInt(String key, int value) {
        return mSharedPreferences.edit().putInt(key, value).commit();
    }

    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    public boolean putString(String key, String value) {
        return mSharedPreferences.edit().putString(key, value).commit();
    }

}
