package com.jessicardo.theuserentry.util;


import com.jessicardo.theuserentry.ui.LifecycleListener;
import com.jessicardo.theuserentry.ui.LifecycleProvider;

import android.os.AsyncTask;
import android.util.Log;


abstract public class SafeAsyncTask<Result> extends AsyncTask<Void, Void, Result>
        implements LifecycleListener {

    private LifecycleProvider mLifecycleProvider;

    private Throwable mRunException;

    @Override
    final protected Result doInBackground(Void... params) {
        try {
            return safeDoInBackground(params);
        } catch (Throwable t) {
            mRunException = t;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        final LifecycleProvider provider = mLifecycleProvider;
        cleanup();
        if (mRunException == null) {
            safeOnPostExecute(result);
        } else {
            if (provider != null) {
                Log.e(provider.getClass().getSimpleName(),
                        "Task RuntimeException: " + mRunException);
            }
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        cleanup();
    }

    @Override
    public void onStart() {
        //no-op
    }

    @Override
    public void onStop() {
        cancel(false);
        cleanup();
    }

    private void cleanup() {
        if (mLifecycleProvider != null) {
            mLifecycleProvider.unregisterLifcycleListener(this);
            mLifecycleProvider = null;
        }
    }

    protected abstract Result safeDoInBackground(Void[] params);

    protected abstract void safeOnPostExecute(Result result);

}
