package com.jessicardo.theuserentry.di;


import com.jessicardo.theuserentry.db.DbHelper;
import com.jessicardo.theuserentry.dbgen.DaoSession;
import com.jessicardo.theuserentry.ui.EntryActivity;
import com.jessicardo.theuserentry.ui.common.AbstractBaseActivity;
import com.jessicardo.theuserentry.ui.common.BaseFragment;
import com.jessicardo.theuserentry.ui.common.MainActivity;
import com.jessicardo.theuserentry.ui.common.MainFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

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

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    DaoSession provideDaoSession(DbHelper dbHelper) {
        return dbHelper.getDaoSession();
    }

}
