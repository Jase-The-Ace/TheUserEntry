package com.jessicardo.theuserentry.ui.common;

import com.jessicardo.theuserentry.R;
import com.jessicardo.theuserentry.model.PersonModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

public class MainFragment extends BaseFragment {

    private View mView;

    @Inject
    protected PersonModel mPersonModel;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        // no args
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // no args
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);


        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateActionBar();

    }

    private void updateActionBar() {
        setActionBarTitle(R.string.home);
    }

}
