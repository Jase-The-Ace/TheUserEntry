package com.jessicardo.theuserentry.util;

import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author jesse Created this class to try and parse drivers license PDF417 barcode data.
 */

public class PDF417Helper {

    public static final String TAG = PDF417Helper.class.getSimpleName();

    public static String CODE_FULL_NAME = "DAA";

    public static String CODE_LICENSE_ID = "DAQ";

    public static String CODE_FIRST_NAME = "DAC";

    public static String CODE_FIRST_NAME_1 = "DCT";

    public static String CODE_LAST_NAME = "DCS";

    public static String CODE_STATE = "DAJ";

    public static String CODE_STREET = "DAG";

    public static String CODE_CITY = "DAI";

    public static String CODE_ZIPCODE = "DAK";

    public static String CODE_COUNTRY = "DCG";

    public static String CODE_EXPIRATION_DATE = "DBA";

    public static String CODE_DATE_OF_BIRTH = "DBB";

    private static List<String> CODES = Arrays.asList(CODE_FULL_NAME,
            CODE_FIRST_NAME + "|" + CODE_FIRST_NAME_1,
            CODE_LAST_NAME,
            CODE_LICENSE_ID,
            CODE_STATE,
            CODE_STREET,
            CODE_CITY,
            CODE_COUNTRY,
            CODE_ZIPCODE,
            CODE_EXPIRATION_DATE,
            CODE_DATE_OF_BIRTH
    );

    public static String getSampleDriversLicenseBarcode1() {
        return "@\n"
                + "ANSI 636014040002DL00410477ZC05180089DLDAQD1234562 XYXYXYXYXYXYXYXYX\n"
                + "DCSLASTNAMEXYXYXYXYXYXYXYXYXXYXYXYXYXYXYXYX\n"
                + "DDEU\n"
                + "DACFIRSTXYXYXYXYXYXYXYXYXXYXYXYXYXYXYXYXXYX\n"
                + "DDFU\n"
                + "DADXYXYXYXYXYXYXYXYXXYXYXYXYXYXYXYXXYXYXYXY\n"
                + "DDGU\n"
                + "DCAA XYXY\n"
                + "DCBNONEY1XY1XY1\n"
                + "DCDNONEX\n"
                + "DBD10312009\n"
                + "DBB10311977\n"
                + "DBA10312014\n"
                + "DBC1\n"
                + "DAU068 IN\n"
                + "DAYBRO\n"
                + "DAG1234 ANY STREET XY1XY1XY1XY1XY1XY1X\n"
                + "DAICITY XY1XY1XY1XY1XY1\n"
                + "DAJCA\n"
                + "DAK000000000  \n"
                + "DCF00/00/0000NNNAN/ANFD/YY X\n"
                + "DCGUSA\n"
                + "DCUSUFIX\n"
                + "DAW150\n"
                + "DAZBLK XY1XY1XY\n"
                + "DCKXY1XY1XY1XY1XY1XY1XY1XY1X\n"
                + "DDAF\n"
                + "DDBMMDDCCYY\n"
                + "DDD1\n"
                + "ZCZCAY\n"
                + "ZCBCORR LENS\n"
                + "ZCCBRN\n"
                + "ZCDXYX\n"
                + "ZCEXYXYXYXYXYXYXY\n"
                + "ZCFXY1XY1XY1XY1XY1XY1XYXYXYXYXYXYXY";
    }

    public static String getSampleDriversLicenseBarcode2() {
        return "@\n"
                + "ANSI 6360080102DL00390207ZM02460112DLDAQ0300119554115\n"
                + "DAASAMPLE,MONTANA,IDCARD1\n"
                + "DAGANYWHERE IN\n"
                + "DAIHELENA\n"
                + "DAJMT\n"
                + "DAK59601      \n"
                + "DAR    \n"
                + "DAS          \n"
                + "DAT     \n"
                + "DAU505\n"
                + "DAW145\n"
                + "DAYBLU\n"
                + "DAZBRO\n"
                + "DBA20060315\n"
                + "DBB19550315\n"
                + "DBC2\n"
                + "DBD20020401\n"
                + "DBH\n"
                + "DBJ00003378 ZMZMA05  \n"
                + "ZMB\n"
                + "ZMC705051\n"
                + "ZMD\n"
                + "ZME\n"
                + "ZMF\n"
                + "ZMG\n"
                + "ZMH\n"
                + "ZMI\n"
                + "ZMJ\n"
                + "ZMK\n"
                + "ZML\n"
                + "ZMM\n"
                + "ZMN\n"
                + "ZMO\n"
                + "ZMP\n"
                + "ZMQ\n"
                + "ZMR\n"
                + "ZMS\n"
                + "ZMT\n"
                + "ZMU\n"
                + "ZMV\n"
                + "ZMW\n"
                + "ZMX\n"
                + "ZMY";
    }

    public static String getSampleDriversLicenseBarcode3() {
        return "@\n"
                + "    ANSI 636001030102DL00410258ZN02990070DLDCAD\n"
                + "    DCBB\n"
                + "    DCDNONE\n"
                + "    DBA08222018\n"
                + "    DCSPEREZ\n"
                + "    DCTJONATHAN\n"
                + "    DBD03312015\n"
                + "    DBB08221989\n"
                + "    DBC1\n"
                + "    DAYBRO\n"
                + "    DAU508\n"
                + "    DAG1506 OVERING ST 2C\n"
                + "    DAIBRONX\n"
                + "    DAJNY\n"
                + "    DAK104610000\n"
                + "    DAQ117009576\n"
                + "    DCFECPMD3CB13\n"
                + "    DCGUSA\n"
                + "ZNZNAPEREZ@JONATHAN\n"
                + "    ZNB1N8+,8A>)f,$o0GC]SlqHhH4%_6H6R^NdFQ>WBU'";
    }

    public static HashMap<String, String> getDriversLicenseMap(String driversLicenseBarcodeData) {
        HashMap<String, String> driversLicenseMap = new HashMap<>();

        String[] lines = driversLicenseBarcodeData.split("\n");
        for (String line : lines) {
            for (String codeMain : CODES) {
                String[] similarCodeNames = codeMain.split("\\|");
                for (String code : similarCodeNames) {
                    if (line.contains(code)) {
                        String value = line.split(code)[1].trim();
                        value = value.split("XY")[0].trim();
                        if (isDateValue(code)) {
                            value = getFormattedDate(value);
                        }
                        if (code.equalsIgnoreCase(CODE_ZIPCODE) && value != null
                                && value.length() >= 5) {
                            value = value.substring(0, 5);
                        }
                        driversLicenseMap.put(code, value);
                    }
                }
            }
        }
        return driversLicenseMap;
    }

    public static String getFormattedDate(String value) {
        try {
            String first4 = value.substring(0, 4);
            int num = Integer.parseInt(first4);
            // format to mm-dd-yyyy
            if (num > 1900) {
                // year is at start
                value = value.substring(4, 6) + "-"
                        + value.substring(6) + "-" + value.substring(0, 4);
            } else {
                // year is at end
                value = value.substring(0, 2) + "-" + value.substring(2, 4)
                        + "-"
                        + value.substring(4);
            }
        } catch (NumberFormatException e) {
            Log.e(TAG, "unable to format date:" + e, e);
        }
        return value;
    }

    private static boolean isDateValue(String code) {
        return code.equals(CODE_EXPIRATION_DATE)
                || code.equals(CODE_DATE_OF_BIRTH);
    }

}
