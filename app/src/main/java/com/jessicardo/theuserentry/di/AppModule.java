package com.jessicardo.theuserentry.di;


import com.jessicardo.theuserentry.ui.EntryActivity;
import com.jessicardo.theuserentry.ui.common.AbstractBaseActivity;
import com.jessicardo.theuserentry.ui.common.BaseFragment;
import com.jessicardo.theuserentry.ui.common.MainActivity;
import com.jessicardo.theuserentry.ui.common.MainFragment;

import dagger.Module;

// Orders alphabetically in each section
@Module(
        injects = {
                // Controllers
                // Activities
                AbstractBaseActivity.class,
                EntryActivity.class,
                MainActivity.class,
                // Fragments
                BaseFragment.class,
                MainFragment.class,
                // Views
                // Jobs
        }
)
public class AppModule {

    private static final String TAG = AppModule.class.getSimpleName();

}
