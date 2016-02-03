package com.jessicardo.theuserentry.ui.person.activities;


import com.jessicardo.theuserentry.ui.common.AbstractContainerActivity;
import com.jessicardo.theuserentry.ui.person.fragments.PersonDetailFragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class PersonDetailActivity extends AbstractContainerActivity {

    private static final String PARAMS_ID = "params_id";

    private static final String PARAMS_CONTACT_IMAGEVIEW_TRANSITION_NAME
            = "params_contact_imageview_transition_name";

    public static Intent newIntent(Context context) {
        return newIntent(context, null, "");
    }

    public static Intent newIntent(Context context, String id,
            String contactImageViewTransitionName) {
        Intent intent = new Intent(context, PersonDetailActivity.class);
        intent.putExtra(PARAMS_ID, id);
        intent.putExtra(PARAMS_CONTACT_IMAGEVIEW_TRANSITION_NAME,
                contactImageViewTransitionName);
        return intent;
    }

    @Override
    protected Fragment getFragment() {
        String id = getIntent().getStringExtra(PARAMS_ID);
        String contactImageViewTransitionName = getIntent()
                .getStringExtra(PARAMS_CONTACT_IMAGEVIEW_TRANSITION_NAME);

        return PersonDetailFragment.newInstance(id, contactImageViewTransitionName);
    }

}
