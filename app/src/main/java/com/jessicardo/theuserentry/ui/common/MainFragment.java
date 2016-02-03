package com.jessicardo.theuserentry.ui.common;

import com.cocosw.bottomsheet.BottomSheet;
import com.jessicardo.theuserentry.App;
import com.jessicardo.theuserentry.R;
import com.jessicardo.theuserentry.api.controllers.PersonController;
import com.jessicardo.theuserentry.api.models.Person;
import com.jessicardo.theuserentry.events.FetchedPersonsEvent;
import com.jessicardo.theuserentry.model.PersonModel;
import com.jessicardo.theuserentry.ui.common.interfaces.ListItemSelectedListener;
import com.jessicardo.theuserentry.ui.common.interfaces.OnCompletionCallback;
import com.jessicardo.theuserentry.ui.person.activities.PersonDetailActivity;
import com.jessicardo.theuserentry.ui.person.activities.SignUpScanActivity;
import com.jessicardo.theuserentry.ui.person.adapters.PersonListAdapter;
import com.jessicardo.theuserentry.ui.person.fragments.PersonDetailFragment;
import com.jessicardo.theuserentry.ui.person.viewmodel.PersonInfo;
import com.jessicardo.theuserentry.util.BottomSheetHelper;
import com.jessicardo.theuserentry.util.FontType;
import com.jessicardo.theuserentry.util.HelperUtil;
import com.jessicardo.theuserentry.util.SafeAsyncTask;
import com.jessicardo.theuserentry.util.recyclerview.SlideInOutBottomItemAnimator;
import com.jessicardo.theuserentry.util.viewhelper.ViewHelper;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainFragment extends BaseFragment {

    private View mView;

    private PersonListAdapter mPersonListAdapter;

    private LinearLayoutManager mLayoutManager;

    private ArrayList<PersonInfo> mData = new ArrayList<>();

    @Inject
    protected PersonController mPersonController;

    @Inject
    protected PersonModel mPersonModel;

    @InjectView(R.id.add)
    protected ImageView mAddButton;

    @InjectView(R.id.title)
    protected TextView mTitle;


    @InjectView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    private boolean mClicked;

    private Handler mHandler = new Handler();

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
        App.injectMembers(this);

        if (getArguments() != null) {
            // no args
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.inject(this, mView);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new SlideInOutBottomItemAnimator(mRecyclerView));

        mPersonListAdapter = new PersonListAdapter(mData);

        mRecyclerView.setAdapter(mPersonListAdapter);

        setListeners();

        fetchData();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateActionBar();
        loadData();

        mClicked = false;
    }

    private void updateActionBar() {
        setActionBarTitle(R.string.home);
        ViewHelper.animateSetViewColor(getToolbar(),
                getResources().getColor(R.color.color_primary));
    }

    private void setListeners() {
        mPersonListAdapter.setOnItemSelectedListener(new ListItemSelectedListener() {
            @SuppressLint("NewApi")
            @Override
            public void onListItemSelected(int position, View view) {
                if (mClicked) {
                    return;
                }
                mClicked = true;

                PersonInfo contact = mData.get(position);

                View circleView = null;

                circleView = view.findViewById(R.id.circle_view);

                List<View> viewMap = new ArrayList<>();
                viewMap.add(circleView);

                String imageViewTransitionName = "";
                if (isPostLollipop()) {
                    imageViewTransitionName = circleView.getTransitionName();
                }

                PersonDetailFragment fragment = PersonDetailFragment.newInstance(
                        String.valueOf(contact.getId()),
                        imageViewTransitionName);
                replaceFragmentInActivity(fragment, PersonDetailFragment.class.getSimpleName(),
                        viewMap);
            }
        });

        mPersonListAdapter.setDeleteListener(new ListItemSelectedListener() {
            @Override
            public void onListItemSelected(int position, View view) {
                PersonInfo data = mData.remove(position);
                mPersonListAdapter.notifyItemRemoved(position);
                mPersonModel.deleteById(String.valueOf(data.getId()));
                mPersonController.deletePerson(String.valueOf(data.getId()));

                updateTitle();

                // update positions in views
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPersonListAdapter.notifyDataSetChanged();
                    }
                }, 500);
            }
        });
    }

    @OnClick(R.id.add)
    protected void showBottomSheet() {
        BottomSheet.Builder bottomSheet = BottomSheetHelper.getAddPersonBottomSheet(getActivity());
        bottomSheet.typeface(FontType.ROBOTOSLAB_REGULAR.getTypeface());
        bottomSheet.listener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == BottomSheetHelper.MENU_BUTTON_ID_ADD_MANUAL) {
                    launchAddPersonActivity();
                } else if (which == BottomSheetHelper.MENU_BUTTON_ID_ADD_SCANNING) {
                    launchScanActivity();
                }

            }
        });
        bottomSheet.show();
    }

    private void launchScanActivity() {
        startActivity(SignUpScanActivity.newIntent(getActivity()));
    }

    protected void launchAddPersonActivity() {
        startActivity(PersonDetailActivity.newIntent(getActivity()));
    }

    private void loadData() {
        new SafeAsyncTask<List<Person>>() {

            @Override
            protected List<Person> safeDoInBackground(Void... params) {
                List<Person> persons = mPersonModel.loadAll();
                return persons;
            }

            @Override
            protected void safeOnPostExecute(List<Person> persons) {
                int oldDataSize = mData.size();
                mData.clear();
                if (persons != null && persons.size() > 0) {
                    for (Person person : persons) {
                        mData.add(new PersonInfo(person.getFirstName() + " " + person.getLastName(),
                                "Zipcode: " + person.getZipCode(), person.getId()));
                    }
                    // Local persons are loaded first, if we found persons, hide loader, otherwise it's waiting for API to finish Requesting
                    hideLoader();
                }
                updateTitle();
                mPersonListAdapter
                        .notifyItemRangeInserted(oldDataSize, mData.size() - oldDataSize);
            }
        }.execute();
    }

    private void updateTitle() {
        if (HelperUtil.isEmpty(mData)) {
            mTitle.setText(R.string.no_list_of_persons);
            animateAddButtonToMiddle();
        } else {
            mTitle.setText(R.string.list_of_persons);
            animateAddButtonToBottom();
        }
    }

    private void animateAddButtonToBottom() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAddButton.animate().translationY(0)
                        .setInterpolator(new DecelerateInterpolator(2f))
                        .setListener(null).start();
            }
        }, 500);

    }

    private void animateAddButtonToMiddle() {
        ViewHelper.executeAfterViewRendered(mRecyclerView, new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                mAddButton.animate().translationY(-mRecyclerView.getHeight() / 2)
                        .setInterpolator(new DecelerateInterpolator(2f))
                        .setListener(null).start();
            }
        });
    }

    private void fetchData() {
        showLoader();
        mPersonController.fetchPersons();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEventMainThread(FetchedPersonsEvent event) {
        if (event.isSuccess()) {
            loadData();
        } else {
            showError(event.getErrorMsg());
        }
        hideLoader();
    }


}
