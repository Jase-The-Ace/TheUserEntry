package com.jessicardo.theuserentry.ui.common;


import com.jessicardo.theuserentry.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Container Activity that holds a default container for Fragments with a ToolBar
 */
public abstract class AbstractContainerActivity extends AbstractBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_fragment_container);

        initToolbar();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(getContainerId(), getFragment())
                    .commit();
        }
    }

    @Override
    protected int getContainerId() {
        return R.id.container;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected int getStatusBarViewId() {
        return R.id.status_bar_view;
    }

    @Override
    protected int getNaviationBarViewId() {
        return R.id.navigation_bar_view;
    }

    @Override
    protected int getStatusBarTintViewId() {
        return R.id.status_bar_tint_view;
    }

    @Override
    protected int getProgressBarId() {
        return R.id.progress_bar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: Try getting fragment via ID or Tag
        if (getSupportFragmentManager().getFragments().size() > 0) {
            Fragment fragment = getSupportFragmentManager().getFragments().get(0);
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
