package com.jessicardo.theuserentry.ui.common;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Jessicardo.
 */
public class MainActivity extends AbstractContainerActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected Fragment getFragment() {
        return MainFragment.newInstance();
    }
}
