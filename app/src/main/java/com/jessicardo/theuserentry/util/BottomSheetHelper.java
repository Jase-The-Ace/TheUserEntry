package com.jessicardo.theuserentry.util;

import com.cocosw.bottomsheet.BottomSheet;
import com.jessicardo.theuserentry.R;

import android.app.Activity;


public class BottomSheetHelper {

    public static final int MENU_BUTTON_ID_ADD_MANUAL = 100;

    public static final int MENU_BUTTON_ID_ADD_SCANNING = 200;

    public static BottomSheet.Builder getAddPersonBottomSheet(Activity activity) {
        BottomSheet.Builder bottomSheet = new BottomSheet.Builder(activity);

        bottomSheet.title(R.string.input_type);

        bottomSheet.sheet(MENU_BUTTON_ID_ADD_MANUAL,
                R.drawable.ic_border_color_black_48dp,
                R.string.enter_manually);
        bottomSheet.sheet(MENU_BUTTON_ID_ADD_SCANNING,
                R.drawable.ic_credit_card_black_48dp,
                R.string.scan_drivers_license);

        return bottomSheet;
    }

}
