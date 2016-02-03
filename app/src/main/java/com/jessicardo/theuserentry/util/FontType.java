package com.jessicardo.theuserentry.util;

import com.jessicardo.theuserentry.App;

import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public enum FontType {

    // This is mapped directly to the attrs.xml.  If changes are made here, it should be made in attrs.xml as well
    BARAMOND("baramond.ttf"),
    BARAMOND_BOLD("baramond_bold.ttf"),
    BARAMOND_BOLD_ITALIC("baramond_bold_italic.ttf"),
    BARAMOND_ITALIC("baramond_italic.ttf"),
    ROBOTOSLAB_BOLD("robotoslab_bold.ttf"),
    ROBOTOSLAB_LIGHT("robotoslab_light.ttf"),
    ROBOTOSLAB_REGULAR("robotoslab_regular.ttf"),
    ROBOTOSLAB_THIN("robotoslab_thin.ttf"),;

    private static Map<String, Typeface> sTypefaceByFilename = new HashMap<>();

    private String mFileName;

    private FontType(String fileName) {
        mFileName = fileName;
    }

    public Typeface getTypeface() {
        if (!sTypefaceByFilename.containsKey(mFileName)) {
            Typeface typeface = Typeface
                    .createFromAsset(App.getInstance().getAssets(), "fonts/" + mFileName);
            sTypefaceByFilename.put(mFileName, typeface);
        }
        return sTypefaceByFilename.get(mFileName);
    }
}
