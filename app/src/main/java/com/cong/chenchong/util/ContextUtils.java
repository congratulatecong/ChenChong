package com.cong.chenchong.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

import com.cong.chenchong.R;
import com.cong.chenchong.constant.NetworkType;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public final class ContextUtils {

	public static void showAlertDialog(Context context, String message) {
		Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_name);
		builder.setMessage(message);
		builder.setNeutralButton(android.R.string.ok, null);
		builder.create().show();
	}

    /** 没有网络 */
    public static final int NETWORKTYPE_INVALID = 0;

    /** wap网络 */
    public static final int NETWORKTYPE_WAP = 1;

    /** 2G网络 */
    public static final int NETWORKTYPE_2G = 2;

    /** 3G和3G以上网络，或统称为快速网络 */
    public static final int NETWORKTYPE_3G = 3;

    /** wifi网络 */
    public static final int NETWORKTYPE_WIFI = 4;

    /**
     * 获取网络状态，wifi,wap,2g,3g.
     *
     * @param context 上下文
     * @return int 网络状态 {@link #NETWORKTYPE_2G},{@link #NETWORKTYPE_3G}, *
     *         {@link #NETWORKTYPE_INVALID},{@link #NETWORKTYPE_WAP}*
     *         <p>
     *         {@link #NETWORKTYPE_WIFI}
     */
    public static int getDetailNetWorkType(Context context) {
        int netWorkType = NETWORKTYPE_INVALID;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                netWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();

                netWorkType = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G : NETWORKTYPE_2G) : NETWORKTYPE_WAP;
            }
        } else {
            netWorkType = NETWORKTYPE_INVALID;
        }
        return netWorkType;
    }

    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    /**
     * 初略判别网络状态
     *
     * @param context
     * @return 网络状态
     */
    public static int getNetworkState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null && networkInfo.isConnected()) {
            return NetworkType.MOBILE;
        }
        networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null && networkInfo.isConnected()) {
            return NetworkType.WIFI;
        }
        return NetworkType.NONE;
    }

    /**
     * 自定义Toast
     *
     * @param iconResId 图片
     * @param tips 提示文字
     */
    public static void showTips(Context context, int iconResId, String tips) {
        TipsToast tipsToast;
        tipsToast = TipsToast.makeText(context, tips, TipsToast.LENGTH_SHORT);
        tipsToast.show();
        tipsToast.setIcon(iconResId);
        tipsToast.setText(tips);
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int identifierId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        // 获取NavigationBar的高度
        return resources.getDimensionPixelSize(identifierId);
    }

    @SuppressLint("NewApi")
    public static boolean checkDeviceHasNavigationBar(Context context) {
        // 通过判断设备是否有菜单键、返回键以及Home键盘（不是NavigationBars上的虚拟键而是实际的物理键）来确定是否有无NavigationBar
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        return !hasMenuKey && !hasBackKey && !hasHomeKey;
    }

}
