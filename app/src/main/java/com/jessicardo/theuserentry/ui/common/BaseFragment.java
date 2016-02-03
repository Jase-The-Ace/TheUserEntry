package com.jessicardo.theuserentry.ui.common;

import com.jessicardo.theuserentry.ui.LifecycleListener;
import com.jessicardo.theuserentry.ui.LifecycleProvider;
import com.jessicardo.theuserentry.ui.common.interfaces.OnFragmentBackPress;
import com.jessicardo.theuserentry.util.HelperUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class BaseFragment extends Fragment implements LifecycleProvider, OnFragmentBackPress {

    private String TAG = this.getClass().getSimpleName();

    @Inject
    protected EventBus mEventBus;

    private CopyOnWriteArraySet<LifecycleListener> mLifecycleListeners
            = new CopyOnWriteArraySet<LifecycleListener>();

    public BaseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG + ".Lifecycle.onCreate", " ---------->  " + TAG + " <----------  ");
        super.onCreate(savedInstanceState);
    }

    public AbstractBaseActivity getBaseActivity() {
        return (AbstractBaseActivity) getActivity();
    }

    @Override
    public void onAttach(Activity activity) {
        // needs to be called or else the onPrepareOptionsMenu will not be
        // called.
        setHasOptionsMenu(true);
        super.onAttach(activity);
    }

    /**
     * Gets called before Activity's onBackPress()
     *
     * @return - True if we should call the onBackPress in the Activity, false to completely
     * override it
     */
    public boolean onFragmentBackPressed() {
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        for (LifecycleListener listener : mLifecycleListeners) {
            listener.onStart();
        }
        Log.v(TAG + ".Lifecycle.onStart", "OnStart");
        try {
            // Throws exception if Fragment doesn't contain onEvent method
            mEventBus.register(this);
        } catch (Throwable t) {
            // Uncomment for debugging
            // Log.d(this.getClass().getSimpleName(), "EventBus Failed to Register: ", t);
        }
    }

    @Override
    public void onStop() {
        for (LifecycleListener listener : mLifecycleListeners) {
            listener.onStop();
        }
        Log.v(TAG + ".Lifecycle.onStop", "OnStop");
        super.onStop();
        try {
            mEventBus.unregister(this);
        } catch (Throwable t) {
            // Uncomment for debugging
            // Log.v(TAG, "EventBus Failed to Unregister: ", t);
        }
    }

    @Override
    public void onDestroy() {
        String tag = HelperUtil.getTAG(this.getClass());
        Log.v(TAG + ".Lifecycle.onDestroy", " ---------->  " + tag + " <----------  ");
        super.onDestroy();
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

    public void vibrate() {
        if (getBaseActivity() != null) {
            getBaseActivity().vibrate();
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

    protected Toolbar getToolbar() {
        return ((AbstractContainerActivity) getActivity()).getToolbar();
    }

    /**
     * Replace the current fragment with the selected fragment. Add it to a backstack if the
     * backstack is specified.
     *
     * @param fragment      - Fragment to show
     * @param backStackName - Add it to the backstack
     */
    public void replaceFragment(Fragment fragment, String backStackName, int containerId) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(containerId, fragment);
        if (backStackName != null) {
            ft.addToBackStack(backStackName);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    @SuppressLint("NewApi")
    public void replaceFragmentInActivity(Fragment fragment, String backStackName,
            List<View> viewsMap) {
        if (isPostLollipop()) {

            setSharedElementReturnTransition(
                    TransitionInflater.from(getActivity())
                            .inflateTransition(android.R.transition.move));

            fragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity())
                    .inflateTransition(android.R.transition.move));
        }

        if (getActivity() instanceof AbstractBaseActivity) {
            AbstractBaseActivity activity = (AbstractBaseActivity) getActivity();
            activity.replaceFragment(fragment, backStackName, activity.getContainerId(),
                    viewsMap);
        }
    }

    public boolean isPostLollipop() {
        return HelperUtil.isPostLollipop();
    }

    @Override
    public void registerLifcycleListener(LifecycleListener listener) {
        mLifecycleListeners.add(listener);
    }

    @Override
    public void unregisterLifcycleListener(LifecycleListener listener) {
        mLifecycleListeners.remove(listener);
    }

    protected void hideKeyboard() {
        hideKeyboard(getActivity().getCurrentFocus());
    }

    protected void hideKeyboard(View view) {
        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            view.clearFocus();
            inputManager
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void hideLoader() {
        if (getBaseActivity() != null) {
            getBaseActivity().hideLoader();
        }
    }

    protected void showLoader() {
        getBaseActivity().showLoader();
    }

    // region ActionBar methods

    protected void setActionBarTitle(int title) {
        setActionBarTitle(getString(title));
    }

    protected void setActionBarTitle(String title) {
        if (getActivity() != null) {
            ((AbstractBaseActivity) getActivity()).setActionBarTitle(title);
        }
    }

    protected Spannable getFontedActionBarTitle(int title) {
        return getFontedActionBarTitle(getString(title));
    }

    protected Spannable getFontedActionBarTitle(String title) {
        Spannable sb = new SpannableString(title);
        if (getActivity() != null) {
            sb = ((AbstractBaseActivity) getActivity()).getFontedActionBarTitle(title);
        }
        return sb;
    }

    protected void setActionBarSubtitle(int subtitle) {
        setActionBarSubtitle(getString(subtitle));
    }

    protected void setActionBarSubtitle(String subtitle) {
        if (getActivity() != null) {
            ((AbstractBaseActivity) getActivity()).setActionBarSubtitle(subtitle);
        }
    }

    protected void setActionBarSubtitle(int subtitle, int textSize) {
        Spannable sb = getFontedActionBarSubtitle(getResources().getString(subtitle), textSize);
        if (getActivity() != null) {
            ((AbstractBaseActivity) getActivity()).setActionBarSubtitle(subtitle, textSize);
        }
    }

    protected Spannable getFontedActionBarSubtitle(int subtitle) {
        return getFontedActionBarSubtitle(getString(subtitle));
    }

    protected Spannable getFontedActionBarSubtitle(String subtitle) {
        Spannable sb = new SpannableString(subtitle);
        if (getActivity() != null) {
            sb = ((AbstractBaseActivity) getActivity()).getFontedActionBarSubtitle(subtitle);
        }
        return sb;
    }

    protected Spannable getFontedActionBarSubtitle(String subtitle, int textSize) {
        Spannable sb = new SpannableString(subtitle);
        if (getActivity() != null) {
            sb = ((AbstractBaseActivity) getActivity())
                    .getFontedActionBarSubtitle(subtitle, textSize);
        }
        return sb;
    }

    protected void setSpannableTextSize(Spannable sb, int pixelSize) {
        if (getActivity() != null) {
            ((AbstractBaseActivity) getActivity()).setSpannableTextSize(sb, pixelSize);
        }
    }

    protected void setSpannableTypeFace(Spannable sb, Typeface tf) {
        if (getActivity() != null) {
            ((AbstractBaseActivity) getActivity()).setSpannableTypeFace(sb, tf);
        }
    }

    protected void setSpannableTextColor(Spannable sb, int color) {
        if (getActivity() != null) {
            ((AbstractBaseActivity) getActivity()).setSpannableTextColor(sb, color);
        }
    }
    //endregion
}
