package com.jessicardo.theuserentry.ui.common;

import com.jessicardo.theuserentry.App;
import com.jessicardo.theuserentry.R;
import com.jessicardo.theuserentry.ui.common.interfaces.OnFragmentBackPress;
import com.jessicardo.theuserentry.util.CustomTypefaceSpan;
import com.jessicardo.theuserentry.util.FontType;
import com.jessicardo.theuserentry.util.HelperUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Abstract BaseActivity that contains all the helper methods we need, all activities will subclass
 * this
 */
public abstract class AbstractBaseActivity extends AppCompatActivity {

    protected String TAG = getClass().getSimpleName();

    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("On Create", "On Create ---------->  " + TAG + " <----------  ");
        App.injectMembers(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        Log.v("On Resume", "On Resume ---------->  " + TAG + " <----------  ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
        Log.v("On Pause", "On Pause ---------->  " + TAG + " <----------  ");
    }

    @Override
    public void onDestroy() {
        Log.v("On Destroy", "On Destroy ---------->  " + TAG + " <----------  ");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("On Stop", "On Stop ---------->  " + TAG + " <----------  ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("On SaveInstanceState",
                "On SaveInstanceState ---------->  " + TAG + " <----------  ");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        boolean shouldCallSuper = callFragmentBackListener();
        if (shouldCallSuper) {
            super.onBackPressed();
        }
    }

    /**
     * Used to handle "onBackPress()" in fragments and override default functionality
     *
     * @return bool whether or not we should proceed to calling the activity's onBackPress
     */
    public boolean callFragmentBackListener() {
        boolean shouldCallSuper = true;
        if (HelperUtil.isEmpty(getSupportFragmentManager().getFragments())) {
            return shouldCallSuper;
        }
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof OnFragmentBackPress) {
                OnFragmentBackPress frag = (OnFragmentBackPress) fragment;
                if (shouldCallSuper) {
                    shouldCallSuper = frag.onFragmentBackPressed();
                } else {
                    frag.onFragmentBackPressed();
                }
            }
        }
        return shouldCallSuper;
    }

    //region Toolbar / Status Bar stuffs

    /**
     * This adds an extra view to extend the action bar height and take into consideration the
     * status bar. Additional logic was included to fix up appearance in KitKat.
     */
    protected void configureStatusBar(View statusBarView, View statusBarTintView) {
        ViewGroup.LayoutParams lp = statusBarView.getLayoutParams();
        lp.height = getStatusBarHeight();

        RelativeLayout.LayoutParams lpR = (RelativeLayout.LayoutParams) statusBarTintView
                .getLayoutParams();
        lpR.height = getStatusBarHeight();

        statusBarView.setLayoutParams(lp);
        statusBarTintView.setLayoutParams(lpR);

        if (HelperUtil.isKitKat()) {
            statusBarTintView.setVisibility(View.VISIBLE);
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            statusBarView.setVisibility(View.GONE);
        }

    }


    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(getToolbarId());
        View statusBarView = findViewById(getStatusBarViewId());
        View statusBarTintView = findViewById(getStatusBarTintViewId());

        configureStatusBar(statusBarView, statusBarTintView);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    protected int getStatusBarHeight() {
        int height = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    protected int getNavigationBarHeight() {
        int height = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }
    //endregion

    //region Helpers
    public boolean isPostLollipop() {
        return HelperUtil.isPostLollipop();
    }

    public void logI(String logMessage) {
        HelperUtil.logI(logMessage, this.getClass());
    }

    public void logE(String logMessage) {
        HelperUtil.logE(logMessage, this.getClass());
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

    protected void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            inputManager
                    .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @SuppressLint("NewApi")
    public void startActivityWithSharedElements(Intent intent, List<View> viewsMap) {
        boolean sharedTransition = !HelperUtil.isEmpty(viewsMap);
        if (sharedTransition && isPostLollipop()) {
            List<Pair<View, String>> pairs = new ArrayList<Pair<View, String>>();
            for (View view : viewsMap) {
                Pair<View, String> pair = Pair.create(view, view.getTransitionName());
                pairs.add(pair);
            }
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, pairs.toArray(new Pair[pairs.size()]));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    protected void showError(int stringResourceId) {
        Toast.makeText(this, stringResourceId, Toast.LENGTH_SHORT).show();
    }

    protected void showError(String error) {
        showError(error, Toast.LENGTH_SHORT);
    }

    protected void showError(String error, int length) {
        Toast.makeText(this, error, length).show();
    }

    /**
     * Replace the current fragment with the selected fragment. Add it to a backstack if the
     * backstack is specified.
     *
     * @param fragment      - Fragment to show
     * @param backStackName - Add it to the backstack
     * @param viewsMap      - list of views for the shared transition
     */
    @SuppressLint("NewApi")
    public void replaceFragment(Fragment fragment, String backStackName, int containerId,
            List<View> viewsMap) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        boolean sharedTransition = !HelperUtil.isEmpty(viewsMap);

        ft.replace(containerId, fragment);
        if (backStackName != null) {
            ft.addToBackStack(backStackName);
        }
        if (sharedTransition && isPostLollipop()) {
            for (View view : viewsMap) {
                ft.addSharedElement(view, view.getTransitionName());
            }
        } else {
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        ft.commit();
    }

    /**
     * Replace the current fragment with the selected fragment. Add it to a backstack if the
     * backstack is specified.
     *
     * @param fragment      - Fragment to show
     * @param backStackName - Add it to the backstack
     */
    public void replaceFragment(Fragment fragment, String backStackName, int containerId) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(containerId, fragment);
        if (backStackName != null) {
            ft.addToBackStack(backStackName);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    public void showLoader() {
        if (getProgressBarId() == 0) {
            return;
        }
        ProgressBar progressBar = (ProgressBar) findViewById(getProgressBarId());
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoader() {
        if (getProgressBarId() == 0) {
            return;
        }
        ProgressBar progressBar = (ProgressBar) findViewById(getProgressBarId());
        progressBar.setVisibility(View.GONE);
    }

    public void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        vibrator.vibrate(500);
    }

    //endregion

    //region Abstract Methods
    protected abstract int getContainerId();

    protected abstract int getToolbarId();

    protected abstract int getStatusBarViewId();

    protected abstract int getNaviationBarViewId();

    protected abstract int getStatusBarTintViewId();

    protected abstract int getProgressBarId();

    protected abstract Fragment getFragment();
    //endregion

// region ActionBar methods

    protected void setActionBarTitle(int title) {
        setActionBarTitle(getString(title));
    }

    protected void setActionBarTitle(String title) {
        Spannable sb = getFontedActionBarTitle(title);
        getSupportActionBar().setTitle(sb);
    }

    protected Spannable getFontedActionBarTitle(int title) {
        return getFontedActionBarTitle(getString(title));
    }

    protected Spannable getFontedActionBarTitle(String title) {
        Spannable sb = new SpannableString(title);
        setSpannableTextSize(sb,
                getResources().getDimensionPixelSize(R.dimen.action_bar_title_text_size));
        setSpannableTypeFace(sb, FontType.ROBOTOSLAB_REGULAR.getTypeface());
        return sb;
    }

    protected void setActionBarSubtitle(int subtitle) {
        setActionBarSubtitle(getString(subtitle));
    }

    protected void setActionBarSubtitle(String subtitle) {
        Spannable sb = getFontedActionBarSubtitle(subtitle);
        getSupportActionBar().setSubtitle(sb);
    }

    protected void setActionBarSubtitle(int subtitle, int textSize) {
        Spannable sb = getFontedActionBarSubtitle(getResources().getString(subtitle), textSize);
        getSupportActionBar().setSubtitle(sb);
    }

    protected Spannable getFontedActionBarSubtitle(int subtitle) {
        return getFontedActionBarSubtitle(getString(subtitle));
    }

    protected Spannable getFontedActionBarSubtitle(String subtitle) {
        return getFontedActionBarSubtitle(subtitle, R.dimen.action_bar_subtitle_text_size);
    }

    protected Spannable getFontedActionBarSubtitle(String subtitle, int textSize) {
        Spannable sb = new SpannableString(subtitle);
        setSpannableTextSize(sb,
                getResources().getDimensionPixelSize(textSize));
        setSpannableTypeFace(sb, FontType.ROBOTOSLAB_REGULAR.getTypeface());
        setSpannableTextColor(sb, getResources().getColor(R.color.white_75pc));
        return sb;
    }

    protected void setSpannableTextSize(Spannable sb, int pixelSize) {
        sb.setSpan(new AbsoluteSizeSpan(pixelSize), 0, sb.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    protected void setSpannableTypeFace(Spannable sb, Typeface tf) {
        sb.setSpan(new CustomTypefaceSpan("", tf), 0,
                sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    protected void setSpannableTextColor(Spannable sb, int color) {
        sb.setSpan(new ForegroundColorSpan(color), 0, sb.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

//endregion

    /**
     * Helper to add fragment index when launching an activity or dialog with request codes, so
     * onActivityResult can call the right fragment's onActivityResult method
     *
     * @param fragment    - Fragment we're calling from
     * @param requestCode - Request Code
     */
    public int getFragmentRequestCode(Fragment fragment, int requestCode) {
        int fragmentIndex = fragment.getFragmentManager().getFragments().indexOf(fragment);
        if ((requestCode & 0xffff0000) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        }
        return ((fragmentIndex + 1) << 16) + (requestCode & 0xffff);
    }

}
