package com.cong.chenchong.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.cong.chenchong.base.ApplicationContextHolder;

import rx.android.schedulers.AndroidSchedulers;

@SuppressWarnings("unused")
public class SnackbarHelper {
    private static class SnackbarHelperHandler {
        public static final SnackbarHelper instance = new SnackbarHelper();
    }

    private SnackbarHelper() {
    }

    public static void toast(@NonNull Activity activity, int resourceId) {
        View view = activity.findViewById(android.R.id.content);
        toast(view, resourceId, Snackbar.LENGTH_SHORT);
    }

    public static void toast(@NonNull Activity activity, String message) {
        View view = activity.findViewById(android.R.id.content);
        toast(view, message, Snackbar.LENGTH_SHORT);
    }

    public static void toast(@NonNull Fragment fragment, int resourceId) {
        Activity activity = fragment.getActivity();
        if (activity != null) {
            toast(activity, resourceId);
        }
    }

    public static void toast(@NonNull Fragment fragment, String message) {
        Activity activity = fragment.getActivity();
        if (activity != null) {
            toast(activity, message);
        }
    }

    public static void toast(@NonNull View view, int resourceId) {
        toast(view, resourceId, Snackbar.LENGTH_SHORT);
    }

    public static void toast(@NonNull View view, int resourceId, int duration) {
        Context context = ApplicationContextHolder.getContext();
        if (context == null) {
            return;
        }

        String message = context.getString(resourceId);
        toast(view, message, duration);
    }

    public static void toast(@NonNull View view, String message) {
        toast(view, message, Toast.LENGTH_SHORT);
    }

    public static void toast(@NonNull View view, String message, int duration) {
        AndroidSchedulers.mainThread().createWorker().schedule(() -> SnackbarHelperHandler.instance.showToast(view, message, duration));
    }

    private void showToast(@NonNull View view, String message, int duration) {
        if (message == null) {
            return;
        }

        Context context = ApplicationContextHolder.getContext();
        if (context == null) {
            return;
        }

        Snackbar.make(view, message, duration).show();
    }
}