
package com.cong.chenchong.util;

import java.util.List;

public final class CommonUtil {

    /**
     * List判空
     *
     * @param list
     * @return
     */
    public static <T> boolean isListEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * same as TextUtils.isEmpty(str);
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
