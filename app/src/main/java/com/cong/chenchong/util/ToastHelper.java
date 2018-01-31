package com.cong.chenchong.util;

import android.content.Context;
import android.widget.Toast;

import com.cong.chenchong.base.ApplicationContextHolder;

import rx.android.schedulers.AndroidSchedulers;

@SuppressWarnings("unused")
public class ToastHelper {
    private Toast toast = null;

    private static class ToastHelperHandler {
        public static final ToastHelper instance = new ToastHelper();
    }

    private ToastHelper() {
    }

    public static void toast(int resourceId) {
        toast(resourceId, Toast.LENGTH_SHORT);
    }

    public static void toast(int resourceId, int duration) {
        Context context = ApplicationContextHolder.getContext();
        if (context == null) {
            return;
        }

        String message = context.getString(resourceId);
        toast(message, duration);
    }

    public static void toast(String message) {
        toast(message, Toast.LENGTH_SHORT);
    }

    public static void toast(String message, int duration) {
        AndroidSchedulers.mainThread().createWorker().schedule(() -> ToastHelperHandler.instance.showToast(message, duration));
    }

    private void showToast(String message, int duration) {
        if (message == null) {
            return;
        }

        Context context = ApplicationContextHolder.getContext();
        if (context == null) {
            return;
        }

        if (toast == null) {
            toast = Toast.makeText(context, message, duration);
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        toast.show();
    }
}