package com.jessicardo.theuserentry.util;

import com.jessicardo.theuserentry.R;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;

import java.util.List;

public class HelperUtil {

    public static <T> String getTAG(Class<T> clazz) {
        return clazz.getSimpleName();
    }

    public static <T> void logI(String logMessage, Class<T> clazz) {
        String tag = getTAG(clazz);
        Log.i(tag, logMessage);
    }

    public static <T> void logE(String logMessage, Class<T> clazz) {
        String tag = getTAG(clazz);
        Log.e(tag, logMessage);
    }

    public static <T> void logD(String logMessage, Class<T> clazz) {
        String tag = getTAG(clazz);
        Log.d(tag, logMessage);
    }

    public static <T> void logV(String logMessage, Class<T> clazz) {
        String tag = getTAG(clazz);
        Log.v(tag, logMessage);
    }

    public static <T> void logW(String logMessage, Class<T> clazz) {
        String tag = getTAG(clazz);
        Log.w(tag, logMessage);
    }

    public static <T> void logWtf(String logMessage, Class<T> clazz) {
        String tag = getTAG(clazz);
        Log.wtf(tag, logMessage);
    }

    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.isTablet);
    }

    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static boolean isEmpty(List array) {
        return array == null || array.size() == 0;
    }

    /**
     * Check if device is online or not
     *
     * @return true if it has access to Internet, false otherwise
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static boolean isPostLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isKitKat() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT;
    }

}
