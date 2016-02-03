package com.jessicardo.theuserentry.ui.common.dialogs;

import com.jessicardo.theuserentry.R;
import com.jessicardo.theuserentry.util.DateHelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * DatePicker starts month from ZERO. But input date and output date will represent the actual date
 * So all is offset by 1 w/e incremently or decremently
 */
public class DatePickerDialogFragment extends BaseDialogFragment {

    private static final String TAG = DatePickerDialogFragment.class.getSimpleName();

    public static final int RESULT_CODE_SET_BUTTON = 1;

    /**
     * Format: YYYY-DD-MM
     */
    public static final String PARAM_SELECTED_DATE = "PARAM_SELECTED_DATE";

    public static final String PARAMS_TITLE_TEXT = "params_title_text";

    public static final String PARAM_MIN_YEARS = "PARAM_MIN_YEARS";

    public static final String PARAM_MAX_YEARS = "PARAM_MAX_YEARS";
    public static final String PARAM_START_AT_MAX_DATE = "PARAM_START_AT_MAX_DATE";

    public static final String PARAM_INVALID_DATE_MESSAGE = "param_invalid_date_message";

    private final static int INVALID_YEARS = 999;

    private View mRootView;

    @InjectView(R.id.date_picker)
    protected DatePicker mDatePicker;

    @InjectView(R.id.dialog_title)
    protected TextView mDialogTitle;

    private int mYear;

    private int mMonth;

    private int mDay;

    private int mMinYears;

    private int mMaxYears;

    private String mTitle;

    private Calendar mSelectedDate;

    private Calendar mMinDate;

    private Calendar mMaxDate;

    private String mErrorMessage;

    private boolean mStartAtMaxDate;

    /**
     * @param fragment    - Target Fragment
     * @param requestCode - Target Fragment's request code
     * @param title       - Dialog's Title
     */
    public static DatePickerDialogFragment newInstance(Fragment fragment, int requestCode,
            String title) {
        return newInstance(fragment, requestCode, title, null);
    }

    /**
     * @param fragment     - Target Fragment
     * @param requestCode  - Target Fragment's request code
     * @param title        - Dialog's Title
     * @param selectedDate - Selected date
     */
    public static DatePickerDialogFragment newInstance(Fragment fragment, int requestCode,
            String title, String selectedDate) {
        return newInstance(fragment, requestCode, title, selectedDate, INVALID_YEARS,
                INVALID_YEARS);
    }

    /**
     * @param fragment    - Target Fragment
     * @param requestCode - Target Fragment's request code
     * @param title       - Dialog's Title
     * @param minYears    - minimum year range for calendar
     * @param maxYears    - maximum year range for calendar
     */
    public static DatePickerDialogFragment newInstance(Fragment fragment, int requestCode,
            String title, Integer minYears, Integer maxYears) {
        return newInstance(fragment, requestCode, title, null, minYears, maxYears);
    }

    /**
     * @param fragment     - Target Fragment
     * @param requestCode  - Target Fragment's request code
     * @param title        - Dialog's Title
     * @param selectedDate - Selected date
     * @param minYears     - minimum year range for calendar
     * @param maxYears     - maximum year range for calendar
     */
    public static DatePickerDialogFragment newInstance(Fragment fragment, int requestCode,
            String title, String selectedDate, Integer minYears,
            Integer maxYears) {
        return newInstance(fragment, requestCode, title, selectedDate, minYears, maxYears, null);
    }

    /**
     * @param fragment     - Target Fragment
     * @param requestCode  - Target Fragment's request code
     * @param title        - Dialog's Title
     * @param selectedDate - Selected date
     * @param minYears     - minimum year range for calendar
     * @param maxYears     - maximum year range for calendar
     */
    public static DatePickerDialogFragment newInstance(Fragment fragment, int requestCode,
            String title, String selectedDate, Integer minYears,
            Integer maxYears, boolean startAtMaxDate) {
        return newInstance(fragment, requestCode, title, selectedDate, minYears, maxYears, null,
                startAtMaxDate);
    }

    /**
     * @param fragment     - Target Fragment
     * @param requestCode  - Target Fragment's request code
     * @param title        - Dialog's Title
     * @param selectedDate - Selected date
     * @param minYears     - minimum year range for calendar
     * @param maxYears     - maximum year range for calendar
     */
    public static DatePickerDialogFragment newInstance(Fragment fragment, int requestCode,
            String title, String selectedDate, Integer minYears,
            Integer maxYears, String errorMessage) {

        return newInstance(fragment, requestCode, title, selectedDate, minYears, maxYears,
                errorMessage, false);
    }

    /**
     * @param fragment     - Target Fragment
     * @param requestCode  - Target Fragment's request code
     * @param title        - Dialog's Title
     * @param selectedDate - Selected date
     * @param minYears     - minimum year range for calendar
     * @param maxYears     - maximum year range for calendar
     */
    public static DatePickerDialogFragment newInstance(Fragment fragment, int requestCode,
            String title, String selectedDate, Integer minYears,
            Integer maxYears, String errorMessage, boolean startAtMaxDate) {
        DatePickerDialogFragment frag = new DatePickerDialogFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_TITLE_TEXT, title);
        args.putString(PARAM_SELECTED_DATE, selectedDate);
        if (minYears != null) {
            args.putInt(PARAM_MIN_YEARS, minYears);
        }
        if (maxYears != null) {
            args.putInt(PARAM_MAX_YEARS, maxYears);
        }
        args.putString(PARAM_INVALID_DATE_MESSAGE, errorMessage);
        args.putBoolean(PARAM_START_AT_MAX_DATE, startAtMaxDate);
        frag.setArguments(args);
        frag.setTargetFragment(fragment, requestCode);

        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        String selectedDateString = null;
        if (extras != null) {
            mTitle = extras.getString(PARAMS_TITLE_TEXT);
            selectedDateString = extras.getString(PARAM_SELECTED_DATE);
            mMinYears = extras.getInt(PARAM_MIN_YEARS, INVALID_YEARS);
            mMaxYears = extras.getInt(PARAM_MAX_YEARS, INVALID_YEARS);
            mErrorMessage = extras.getString(PARAM_INVALID_DATE_MESSAGE);
            mStartAtMaxDate = extras.getBoolean(PARAM_START_AT_MAX_DATE);
        }

        if (TextUtils.isEmpty(mErrorMessage)) {
            mErrorMessage = getString(R.string.out_of_range_date_message);
        }
        if (!TextUtils.isEmpty(selectedDateString)) {
            mSelectedDate = DateHelper.getCalendarDateFromFormattedString(selectedDateString);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_date_picker_dialog, container, false);

        ButterKnife.inject(this, mRootView);

        prepareDialog();

        return mRootView;
    }

    /**
     * Set text and edit views of the dialog
     */
    private void prepareDialog() {
        mDialogTitle.setText(mTitle);
        try {
            calculateMaxDate();
            calculateMinDate();
        } catch (Exception e) {
            Log.e(TAG, "error calculating max or min date :" + e, e);
        } finally {
            displayDefaultDate();
        }
    }

    @OnClick(R.id.dialog_set_button)
    protected void set() {
        mMonth = mDatePicker.getMonth();
        mDay = mDatePicker.getDayOfMonth();
        mYear = mDatePicker.getYear();
        mSelectedDate.set(Calendar.YEAR, mYear);
        mSelectedDate.set(Calendar.MONTH, mMonth);
        mSelectedDate.set(Calendar.DAY_OF_MONTH, mDay);
        String selectedDate = DateHelper.getShortFormattedDate(mSelectedDate.getTime());

        if ((mMaxDate != null && mSelectedDate.getTimeInMillis() > mMaxDate.getTimeInMillis())
                || (mMinDate != null && mSelectedDate.getTimeInMillis() < mMinDate
                .getTimeInMillis())) {
            showError(mErrorMessage);
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(PARAM_SELECTED_DATE, selectedDate);
        getTargetFragment().onActivityResult(
                getTargetRequestCode(),
                RESULT_CODE_SET_BUTTON, intent);
        dismiss();
    }

    @OnClick(R.id.dialog_cancel_button)
    protected void cancel() {
        dismiss();
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),
                        FragmentActivity.RESULT_CANCELED,
                        null);
    }

    /**
     * Calculate the closet date from Today.
     */
    private void calculateMaxDate() {
        logE("mMinYears:" + mMinYears);
        if (mMinYears != INVALID_YEARS) {
            mMaxDate = Calendar.getInstance();
            mMaxDate.add(Calendar.YEAR, -mMinYears);
            if (mMinYears == 0) {
                mMaxDate.add(Calendar.MINUTE, 1);
            }
            //setMaxDate is the latest date it can go back, setMinDate, is the EARLIEST
            mDatePicker.setMaxDate(mMaxDate.getTimeInMillis());
        }
    }

    /**
     * Calculate the furthest date from Today
     */
    private void calculateMinDate() {
        logE("mMaxYears:" + mMaxYears);
        if (mMaxYears != INVALID_YEARS) {
            mMinDate = Calendar.getInstance();
            mMinDate.add(Calendar.YEAR, -mMaxYears);
            if (mMaxYears == 0) {
                mMinDate.add(Calendar.MINUTE, -1);
            }
            mDatePicker.setMinDate(mMinDate.getTimeInMillis());
        }
    }

    private void displayDefaultDate() {
        //0 is January, offset by 1, so every month is deducted by 1
        //get current date time with Calendar()

        if (mSelectedDate == null && mMaxDate != null && mMaxDate.getTimeInMillis() < Calendar
                .getInstance().getTimeInMillis()) {
            mSelectedDate = (Calendar) mMaxDate.clone();
        }
        if (mSelectedDate == null && mMinDate != null && !mStartAtMaxDate) {
            mSelectedDate = (Calendar) mMinDate.clone();
        }
        if (mSelectedDate == null) {
            mSelectedDate = Calendar.getInstance();
        }

        mMonth = mSelectedDate.get(Calendar.MONTH);
        mYear = mSelectedDate.get(Calendar.YEAR);
        mDay = mSelectedDate.get(Calendar.DAY_OF_MONTH);

        mDatePicker.updateDate(mYear, mMonth, mDay);
        mDatePicker.invalidate();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), FragmentActivity.RESULT_CANCELED,
                        null);
    }

}



