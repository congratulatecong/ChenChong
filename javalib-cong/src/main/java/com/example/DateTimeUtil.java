package com.example;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by chenchong-ms on 2016/9/12.
 */
public class DateTimeUtil {

    public static boolean isJavaTimeStamp(long time) {
        return time > 10000000000L;
    }

    public static long convertToJavaTimeStamp(long time) {
        return isJavaTimeStamp(time) ? time : time * 1000L;
    }

    public static String formatTime(long time, long currentTime) {
        if (time != 0L && currentTime != 0L) {
            time = convertToJavaTimeStamp(time);
            currentTime = convertToJavaTimeStamp(currentTime);
            if (currentTime >= time) {
                int startCalendar1 = Math.max(1, (int) ((currentTime - time) / 1000L));
                return startCalendar1 <= 60 ? "刚刚" : (startCalendar1 <= 3600 && startCalendar1 > 60 ? (int) Math.max(1.0D, Math.floor((double) (startCalendar1 / 60))) + "分钟前" : (startCalendar1 < 86400 ? (int) Math.floor((double) (startCalendar1 / 3600)) + "小时前" : ""));
            } else {
                Calendar startCalendar = Calendar.getInstance();
                startCalendar.setTimeInMillis(time);
                Calendar nowCalendar = Calendar.getInstance();
                nowCalendar.setTimeInMillis(currentTime);
                String format = "M月dd日HH:mm";
                if (startCalendar.get(1) == nowCalendar.get(1) && startCalendar.get(2) == nowCalendar.get(2)) {
                    int df = startCalendar.get(6) - nowCalendar.get(6);
                    if (df == 0) {
                        format = "今天HH:mm";
                    } else if (df == 1) {
                        format = "明天HH:mm";
                    }
                }

                SimpleDateFormat df1 = new SimpleDateFormat(format, Locale.getDefault());
                return df1.format(Long.valueOf(time));
            }
        } else {
            return "";
        }
    }

    public static String formatTime(long time) {
        return formatTime(time, System.currentTimeMillis());
    }

}
