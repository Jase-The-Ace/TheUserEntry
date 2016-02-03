package com.jessicardo.theuserentry.ui.common.dialogs;


import com.jessicardo.theuserentry.util.HelperUtil;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public class BaseDialogFragment extends DialogFragment {

    private String TAG = this.getClass().getSimpleName();

    private FragmentActivity mTargetActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v("On Create", "On Create ---------->  " + TAG + " <----------  ");
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG + ".Lifecycle.onStart", "OnStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG + ".Lifecycle.onStop", "OnStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.v("On Destroy", "On Destroy ---------->  " + TAG + " <----------  ");
        super.onDestroy();
    }

    public FragmentActivity getTargetActivity() {
        return mTargetActivity;
    }

    public void setTargetActivity(FragmentActivity targetActivity) {
        mTargetActivity = targetActivity;
    }

    public void show() {
        if (mTargetActivity != null) {
            show(mTargetActivity.getSupportFragmentManager(),
                    mTargetActivity.getClass().getSimpleName());
        } else if (getTargetFragment() != null) {
            show(getTargetFragment().getFragmentManager(), getTargetFragment().getTag());
        } else {
            logE("getTargetFragment() must not be null!");
        }
    }

    protected void showError(int stringResourceId) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), stringResourceId, Toast.LENGTH_SHORT).show();
        }
    }

    protected void showError(String error) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        }
    }

    public void logI(String logMessage) {
        HelperUtil.logI(logMessage, this.getClass());
    }

    public void logE(String logMessage) {
        HelperUtil.logE(logMessage, getClass());
    }

    public void logD(String logMessage) {
        HelperUtil.logD(logMessage, this.getClass());
    }

    public void logV(String logMessage) {
        HelperUtil.logV(logMessage, this.getClass());
    }

    public void logW(String logMessage) {
        HelperUtil.logW(logMessage, this.getClass());
    }

    public void logWtf(String logMessage) {
        HelperUtil.logWtf(logMessage, this.getClass());
    }

}



