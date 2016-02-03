package com.jessicardo.theuserentry.jobs;

import com.jessicardo.theuserentry.JError;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import android.util.Log;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class BaseJob extends Job {

    @Inject
    protected EventBus mEventBus;

    private JError mError;

    // Use subclass name for TAG, not "BaseJob"
    protected String TAG = this.getClass().getSimpleName();

    protected BaseJob(Params params) {
        super(params);
    }

    @Override
    public void onAdded() {
        // subclass
    }

    @Override
    public void onRun() throws Throwable {
        // subclass
    }

    @Override
    protected void onCancel() {
        // subclass
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        Log.e(TAG + ".Error", "", throwable);
        mError = new JError(throwable.getMessage());
        // TODO: Check error Type to figure out if we should retry or not
        return false;
    }

    public JError getError() {
        return mError;
    }

    public void setError(JError error) {
        mError = error;
    }

    public EventBus getEventBus() {
        return mEventBus;
    }
}
