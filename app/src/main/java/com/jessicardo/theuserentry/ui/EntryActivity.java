package com.jessicardo.theuserentry.ui;

import com.jessicardo.theuserentry.R;
import com.jessicardo.theuserentry.ui.common.AbstractBaseActivity;
import com.jessicardo.theuserentry.ui.common.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

public class EntryActivity extends AbstractBaseActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        Intent launchIntent = new Intent();

        //placeholder
        boolean hasSeenWalkthrough = true;
        if (hasSeenWalkthrough) {
            launchIntent = MainActivity.newIntent(getApplicationContext());
        } else {
            //TODO:
        }

        final Intent finalLaunchIntent = launchIntent;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(finalLaunchIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, 1000);
    }

    //region overrides
    @Override
    protected int getContainerId() {
        return 0; // no fragment container
    }

    @Override
    protected int getToolbarId() {
        return 0; // no toolbar
    }

    @Override
    protected int getStatusBarViewId() {
        return 0; // no toolbar
    }

    @Override
    protected int getStatusBarTintViewId() {
        return 0; // no status bar
    }

    @Override
    protected int getNaviationBarViewId() {
        return 0; // no status bar
    }

    @Override
    protected Fragment getFragment() {
        return null; // no fragment
    }

    @Override
    protected int getProgressBarId() {
        return 0;
    }

    //endregion
}
