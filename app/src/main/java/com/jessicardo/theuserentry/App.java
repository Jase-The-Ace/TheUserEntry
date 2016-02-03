package com.jessicardo.theuserentry;

import com.jessicardo.theuserentry.util.HelperUtil;

import android.app.Application;

/**
 * Created by Jessicardo.
 */
public class App extends Application {

    private static App sInstance;

    private boolean mIsTablet;

    public App() {
        sInstance = this;
    }

    public static App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mIsTablet = HelperUtil.isTablet(this);

    }

    public boolean isTablet() {
        return mIsTablet;
    }
}
