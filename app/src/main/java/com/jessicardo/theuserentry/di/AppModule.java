package com.jessicardo.theuserentry.di;


import com.firebase.client.Firebase;
import com.jessicardo.theuserentry.App;
import com.jessicardo.theuserentry.Constants;
import com.jessicardo.theuserentry.api.controllers.FirebaseController;
import com.jessicardo.theuserentry.api.controllers.PersonController;
import com.jessicardo.theuserentry.db.DbHelper;
import com.jessicardo.theuserentry.dbgen.DaoSession;
import com.jessicardo.theuserentry.jobs.BaseJob;
import com.jessicardo.theuserentry.jobs.CreatePersonsJob;
import com.jessicardo.theuserentry.jobs.DeletePersonsJob;
import com.jessicardo.theuserentry.jobs.FetchPersonsJob;
import com.jessicardo.theuserentry.jobs.MyJobManager;
import com.jessicardo.theuserentry.ui.EntryActivity;
import com.jessicardo.theuserentry.ui.common.AbstractBaseActivity;
import com.jessicardo.theuserentry.ui.common.BaseFragment;
import com.jessicardo.theuserentry.ui.common.MainActivity;
import com.jessicardo.theuserentry.ui.common.MainFragment;
import com.jessicardo.theuserentry.ui.person.activities.PersonDetailActivity;
import com.jessicardo.theuserentry.ui.person.fragments.PersonDetailFragment;
import com.path.android.jobqueue.JobManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

// Orders alphabetically in each section
@Module(
        injects = {
                // Controllers
                FirebaseController.class,
                PersonController.class,
                // Activities
                AbstractBaseActivity.class,
                EntryActivity.class,
                MainActivity.class,
                PersonDetailActivity.class,
                // Fragments
                BaseFragment.class,
                MainFragment.class,
                PersonDetailFragment.class,
                // Views
                // Jobs
                BaseJob.class,
                CreatePersonsJob.class,
                DeletePersonsJob.class,
                FetchPersonsJob.class,
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

    @Provides
    @Singleton
    JobManager provideJobManager() {
        return new MyJobManager(App.getInstance());
    }

    @Provides
    @Singleton
    Firebase provideFirebase() {
        return new Firebase("https://" + Constants.FIREBASE_PROJECT_NAME_STAGING + ".firebaseio.com/");
    }
}
