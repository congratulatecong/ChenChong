package com.cong.chenchong.base;

import android.app.Application;
import android.content.Context;

@SuppressWarnings("unused")
public class ApplicationContextHolder {
    private static Context applicationContext;

    public static void init(Context context) {
        applicationContext = context;
    }

    public static Context getContext() {
        if (applicationContext == null) {
            applicationContext = getContextByReflect();
        }
        return applicationContext;
    }

    private static Context getContextByReflect() {
        try {
            Application application = (Application) Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication")
                    .invoke(null, (Object[]) null);
            if (application != null) {
                return application;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Application application = (Application) Class.forName("android.app.AppGlobals")
                    .getMethod("getInitialApplication")
                    .invoke(null, (Object[]) null);
            if (application != null) {
                return application;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
