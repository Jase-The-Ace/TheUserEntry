package com.jessicardo.theuserentry.ui.person.fragments;

import com.jessicardo.theuserentry.App;
import com.jessicardo.theuserentry.Constants;
import com.jessicardo.theuserentry.R;
import com.jessicardo.theuserentry.api.controllers.PersonController;
import com.jessicardo.theuserentry.api.models.Person;
import com.jessicardo.theuserentry.model.PersonModel;
import com.jessicardo.theuserentry.ui.common.BaseFragment;
import com.jessicardo.theuserentry.ui.common.dialogs.DatePickerDialogFragment;
import com.jessicardo.theuserentry.ui.common.interfaces.OnCompletionCallback;
import com.jessicardo.theuserentry.ui.common.widgets.CircleViewWithString;
import com.jessicardo.theuserentry.ui.common.widgets.LockableScrollView;
import com.jessicardo.theuserentry.util.DateHelper;
import com.jessicardo.theuserentry.util.SafeAsyncTask;
import com.jessicardo.theuserentry.util.viewhelper.ViewHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class PersonDetailFragment extends BaseFragment {

    private static final String PARAMS_ID = "params_id";

    private static final String PARAMS_CONTACT_IMAGEVIEW_TRANSITION_NAME
            = "params_contact_imageview_transition_name";

    private static String BIRTHDATE_DISPLAY_FORMAT = "MM/dd/yyyy";

    private static String BIRTHDATE_ISO_FORMAT = "yyyy-MM-dd";

    private static final int RC_DOB_DIALOG = 1;

    private View mView;

    private String mId;

    private boolean mUnvealed, mUnvealing;

    private String mContactImageViewTransitionName;

    private Person mPerson;

    @Inject
    protected PersonController mPersonController;

    @Inject
    protected PersonModel mPersonModel;

    @InjectView(R.id.circle_view)
    protected CircleViewWithString mCircleView;

    @InjectView(R.id.first_name_edittext)
    protected EditText mFirstNameEditText;

    @InjectView(R.id.first_name_edittext_container)
    protected TextInputLayout mFirstNameEditTextContainer;

    @InjectView(R.id.last_name_edittext)
    protected EditText mLastNameEditText;

    @InjectView(R.id.last_name_edittext_container)
    protected TextInputLayout mLastNameEditTextContainer;

    @InjectView(R.id.zipcode_edittext)
    protected EditText mZipcodeEditText;

    @InjectView(R.id.zipcode_edittext_container)
    protected TextInputLayout mZipcodeEditTextContainer;

    @InjectView(R.id.dob_edittext)
    protected EditText mDOBEditText;

    @InjectView(R.id.dob_edittext_container)
    protected TextInputLayout mDobEditTextContainer;

    @InjectView(R.id.bottom_layout)
    protected View mBottomLayout;

    @InjectView(R.id.done_button)
    protected Button mDoneButton;

    @InjectView(R.id.scroll_view)
    protected LockableScrollView mScrollView;

    public static PersonDetailFragment newInstance() {
        PersonDetailFragment fragment = new PersonDetailFragment();
        Bundle args = new Bundle();
        // no args
        fragment.setArguments(args);
        return fragment;
    }

    public static PersonDetailFragment newInstance(String id,
            String contactImageViewTransitionName) {
        PersonDetailFragment fragment = new PersonDetailFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_ID, id);
        args.putString(PARAMS_CONTACT_IMAGEVIEW_TRANSITION_NAME, contactImageViewTransitionName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.injectMembers(this);

        if (getArguments() != null) {
            mId = getArguments().getString(PARAMS_ID);
            mContactImageViewTransitionName = getArguments()
                    .getString(PARAMS_CONTACT_IMAGEVIEW_TRANSITION_NAME);
        }
    }

    private void updateActionBar() {
        String title = getString(R.string.add_person);
        if (mId != null) {
            title = getString(R.string.update_person);
        }
        setActionBarTitle(title);
        ViewHelper.animateSetViewColor(getToolbar(),
                getResources().getColor(R.color.material_brown_200));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_add_person, container, false);

        ButterKnife.inject(this, mView);

        setTransitionNames();

        loadData();

        if (mId == null) {
            mDoneButton.setText(R.string.create);
        } else {
            mDoneButton.setText(R.string.update);
        }

        // TODO: figure out why setting this in xml isn't working. Set programmatically since the attrs aren't working.
        mFirstNameEditTextContainer.setHint(getString(R.string.first_name));
        mLastNameEditTextContainer.setHint(getString(R.string.last_name));
        mZipcodeEditTextContainer.setHint(getString(R.string.zipcode));
        mDobEditTextContainer.setHint(getString(R.string.date_of_birth));

        circularReveal();

        setListeners();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateActionBar();
    }

    private void loadData() {
        if (mId == null) {
            mPerson = new Person();
            updateUI();
            return;
        }
        new SafeAsyncTask<Person>() {

            @Override
            protected Person safeDoInBackground(Void... params) {
                Person person = mPersonModel.loadById(mId);
                return person;
            }

            @Override
            protected void safeOnPostExecute(Person person) {
                logE("person:" + person);
                mPerson = person;
                if (mPerson == null) {
                    mPerson = new Person();
                }
                updateUI();
            }
        }.execute();
    }

    private void updateUI() {
        if (mPerson != null) {
            updateTextUI(mPerson.getFirstName(), mPerson.getLastName(), mPerson.getZipCode(),
                    mPerson.getDob());
        }
    }

    private void updateTextUI(String firstName, String lastName, String zipCode, String dob) {
        mFirstNameEditText.setText(firstName);
        mLastNameEditText.setText(lastName);
        mZipcodeEditText.setText(zipCode);

        if (!TextUtils.isEmpty(dob)) {
            Calendar dobCal = DateHelper
                    .getCalendarDateFromFormattedString(dob,
                            BIRTHDATE_ISO_FORMAT);
            mDOBEditText.setText(
                    DateHelper.getFormattedDate(dobCal.getTime(),
                            BIRTHDATE_DISPLAY_FORMAT));
        }
    }

    @OnEditorAction(R.id.last_name_edittext)
    protected boolean lastNameEditor(int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                || actionId == EditorInfo.IME_ACTION_DONE
                || actionId == EditorInfo.IME_ACTION_NEXT) {
            hideKeyboard(mLastNameEditText);
            showDOBDialog();
            return true;
        }
        return false;
    }

    @OnEditorAction(R.id.zipcode_edittext)
    protected boolean zipcodeEditor(int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                || actionId == EditorInfo.IME_ACTION_DONE
                || actionId == EditorInfo.IME_ACTION_NEXT) {
            done();
            return true;
        }
        return false;
    }

    @OnClick(R.id.dob_edittext)
    protected void birthDatePress() {
        showDOBDialog();
    }

    private void showDOBDialog() {
        String formattedDate = getDOBISOFormat();
        if (TextUtils.isEmpty(formattedDate)) {
            Calendar defaultDate = Calendar.getInstance();
            defaultDate.add(Calendar.YEAR, -22);
            formattedDate = DateHelper
                    .getFormattedDate(defaultDate.getTime(), BIRTHDATE_ISO_FORMAT);
        }
        DatePickerDialogFragment dialogFragment = DatePickerDialogFragment
                .newInstance(this, RC_DOB_DIALOG, getString(R.string.date_of_birth),
                        formattedDate,
                        Constants.MINIMUN_AGE_FOR_USAGE, Constants.MAXIMUM_AGE_FOR_USAGE,
                        getString(R.string.user_must_be_over_15));
        dialogFragment.show();
    }


    private String getDOBISOFormat() {
        String formattedDate = null;
        String birthDateText = mDOBEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(birthDateText)) {
            Calendar calendar = DateHelper.getCalendarDateFromFormattedString(birthDateText,
                    BIRTHDATE_DISPLAY_FORMAT);
            formattedDate = DateHelper
                    .getFormattedDate(calendar.getTime(), BIRTHDATE_ISO_FORMAT);
        }
        return formattedDate;
    }

    protected void circularReveal() {
        ViewHelper.hideView(mDoneButton, false);
        ViewHelper.hideView(mBottomLayout, false);
        ViewHelper.executeAfterViewRendered(mBottomLayout, new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                ViewHelper.showView(mBottomLayout, true, new OnCompletionCallback() {
                    @Override
                    public void onCompletion() {
                        ViewHelper.showView(mDoneButton, true, null, 1.5f);
                    }
                }, 3);
            }
        });
    }

    private void circularUnreveal() {
        if (mUnvealing) {
            return;
        }
        mUnvealing = true;
        ViewHelper.hideView(mDoneButton, true, new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                ViewHelper.hideView(mBottomLayout, true, new OnCompletionCallback() {
                    @Override
                    public void onCompletion() {
                        mUnvealed = true;
                        mUnvealing = false;
                        getActivity().onBackPressed();
                    }
                }, 2);
            }
        }, 1.5f);
    }

    private void setListeners() {
        mScrollView.attachViewToScrollView(mCircleView, false);
        mFirstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateCircleText(s.toString());
            }
        });
    }

    @SuppressLint("NewApi")
    @OnClick(R.id.done_button)
    protected void done() {
        hideKeyboard();
        mPerson.setDob(getDOBISOFormat());
        mPerson.setFirstName(mFirstNameEditText.getText().toString().trim());
        mPerson.setLastName(mLastNameEditText.getText().toString().trim());
        mPerson.setZipCode(mZipcodeEditText.getText().toString().trim());

        long id = mPersonModel.savePerson(mPerson);
        mPerson.setId(id);
        mPersonController.savePerson(mPerson);
        getActivity().onBackPressed();
    }

    private void updateCircleText(String fullText) {
        if (TextUtils.isEmpty(fullText)) {
            fullText = "?";
        }
        mCircleView.setBigCircleText(fullText.substring(0, 1));
    }

    @Override
    public boolean onFragmentBackPressed() {
        if (!mUnvealed) {
            circularUnreveal();
        }
        return mUnvealed;
    }

    @SuppressLint("NewApi")
    private void setTransitionNames() {
        if (!isPostLollipop()) {
            return;
        }
        mCircleView.setTransitionName(mContactImageViewTransitionName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data != null ? data.getExtras() : null;
        if (requestCode == RC_DOB_DIALOG) {
            if (resultCode == DatePickerDialogFragment.RESULT_CODE_SET_BUTTON) {
                // Set date
                String selectedDate = extras
                        .getString(DatePickerDialogFragment.PARAM_SELECTED_DATE);
                Calendar birthDateCal = DateHelper
                        .getCalendarDateFromFormattedString(selectedDate,
                                BIRTHDATE_ISO_FORMAT);
                mDOBEditText.setText(
                        DateHelper.getFormattedDate(birthDateCal.getTime(),
                                BIRTHDATE_DISPLAY_FORMAT));
                showSoftKeyboard(mZipcodeEditText);
            } else {
                mZipcodeEditText.requestFocus();
            }
        }
    }

}
