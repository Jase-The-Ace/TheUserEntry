package com.jessicardo.theuserentry;

import com.firebase.client.Firebase;
import com.jessicardo.theuserentry.di.AppModule;
import com.jessicardo.theuserentry.util.HelperUtil;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * Created by Jessicardo.
 */
public class App extends Application {

    private static App sInstance;

    private boolean mIsTablet;

    private ObjectGraph mObjectGraph;

    public App() {
        sInstance = this;
    }

    public static App getInstance() {
        return sInstance;
    }

    public static void injectMembers(Object object) {
        getInstance().mObjectGraph.inject(object);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mObjectGraph = ObjectGraph.create(new AppModule());

        mIsTablet = HelperUtil.isTablet(this);

        Firebase.setAndroidContext(this);

    }

    public boolean isTablet() {
        return mIsTablet;
    }
}
