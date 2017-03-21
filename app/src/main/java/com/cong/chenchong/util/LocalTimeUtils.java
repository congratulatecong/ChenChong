package com.cong.chenchong.util;

import java.util.Calendar;

import android.text.format.DateUtils;

public class LocalTimeUtils {
    private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
    // 北京时间比GMT快8小时
    private static final long EIGHT_HOURS_IN_MILLIS = 1000 * 60 * 60 * 8;

    public static final String[] DayOfWeek = { "周日", "周一", "周二", "周三", "周四",
            "周五", "周六" };

    /**
     * 是否本地时间同一天
     * 
     * @param time1
     * @param time2
     * @return
     */
    public static final boolean isSameLocalDay(long time1, long time2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTimeInMillis(time1);
        c2.setTimeInMillis(time2);
        return c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
                && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
        // return (time1 + EIGHT_HOURS_IN_MILLIS) / DAY_IN_MILLIS == (time2 +
        // EIGHT_HOURS_IN_MILLIS)
        // / DAY_IN_MILLIS;
    }

    public static final boolean isSameLocalDay(Calendar c1, Calendar c2) {
        return c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
                && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
    }

    /**
     * 获得本地时间kk:mm
     * 
     * @param time
     * @return
     */
    public static final String getLocalTimeKKMM(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return String.format("%02d:%02d", c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE));
    }

    public static final String getLocalTimeKKMM(Calendar c) {
        return String.format("%02d:%02d", c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE));
    }

    /**
     * 获得本地日期,包含星期几
     * 
     * @param time
     * @return
     */
    public static final String getLocalDateWithWeekday(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        Calendar cNow = Calendar.getInstance();
        if (cNow.get(Calendar.YEAR) != c.get(Calendar.YEAR)) {
            return String.format("%d-%d-%d, %s", c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH),
                    DayOfWeek[c.get(Calendar.DAY_OF_WEEK) - 1]);
        } else if (cNow.get(Calendar.MONTH) != c.get(Calendar.MONTH)
                || cNow.get(Calendar.DAY_OF_MONTH)
                        - c.get(Calendar.DAY_OF_MONTH) > 1) {
            return String.format("%d-%d, %s", c.get(Calendar.MONTH) + 1,
                    c.get(Calendar.DAY_OF_MONTH),
                    DayOfWeek[c.get(Calendar.DAY_OF_WEEK) - 1]);
        } else if (cNow.get(Calendar.DAY_OF_MONTH)
                - c.get(Calendar.DAY_OF_MONTH) == 1) {
            return "昨天";
        } else {
            return "今天";
        }
    }

    public static final String getLocalDateWithWeekday(Calendar c, Calendar cNow) {
        if (cNow.get(Calendar.YEAR) != c.get(Calendar.YEAR)) {
            return String.format("%d年%d月%d日, %s", c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH),
                    DayOfWeek[c.get(Calendar.DAY_OF_WEEK) - 1]);
        } else if (cNow.get(Calendar.MONTH) != c.get(Calendar.MONTH)
                || cNow.get(Calendar.DAY_OF_MONTH)
                        - c.get(Calendar.DAY_OF_MONTH) > 1) {
            return String.format("%d月%d日, %s", c.get(Calendar.MONTH) + 1,
                    c.get(Calendar.DAY_OF_MONTH),
                    DayOfWeek[c.get(Calendar.DAY_OF_WEEK) - 1]);
        } else if (cNow.get(Calendar.DAY_OF_MONTH)
                - c.get(Calendar.DAY_OF_MONTH) == 1) {
            return "昨天";
        } else {
            return "今天";
        }
    }
    
    public static final String getWeekString(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return DayOfWeek[c.get(Calendar.DAY_OF_WEEK) - 1];
    }
}
