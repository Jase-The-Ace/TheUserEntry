package com.jessicardo.theuserentry.util;

import android.os.Build;

public class DeviceUtil {

    public static boolean isGalaxyS4() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (manufacturer.equals("samsung")) {
            if (model.equals("GT-I9500") || model.equals("GT-I9506") || model.equals("GT-I9515")
                    || model.equals("SHV-E300K/S/L")) {
                //model numbers obtained from https://en.wikipedia.org/wiki/Samsung_Galaxy_S4
                return true;
            }
        }
        return false;
    }
    public static boolean isSamsung() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (manufacturer.equals("samsung")) {
                return true;
        }
        return false;
    }

    public static boolean isMotoG() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (manufacturer.equals("motorola")) {
            if (model.equals("XT1032") || model.equals("XT1034") || model.equals("XT1045")
                    || model.equals("XT1040") || model.equals("XT1039")) {
                //model numbers obtained from https://en.wikipedia.org/wiki/Moto_G_%281st_generation%29
                return true;
            }
        }
        return false;
    }

    public static boolean isHtcVle() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (manufacturer.equals("HTC")) {
            if (model.equals("HTC VLE_U")) {
                return true;
            }
        }
        return false;
    }
    public static boolean isHtc() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (manufacturer.equals("HTC")) {
                return true;
        }
        return false;
    }
}
