package com.jessicardo.theuserentry.ui.person.widgets;

import com.jessicardo.theuserentry.R;
import com.jessicardo.theuserentry.ui.common.interfaces.ListItemSelectedListener;
import com.jessicardo.theuserentry.ui.common.widgets.CircleViewWithString;
import com.jessicardo.theuserentry.ui.person.viewmodel.PersonInfo;
import com.jessicardo.theuserentry.util.HelperUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PersonListRow extends RelativeLayout {

    private static final String TAG = PersonListRow.class.getSimpleName();

    @InjectView(R.id.circle_view)
    protected CircleViewWithString mCircleView;

    @InjectView(R.id.line1_textview)
    protected TextView mLine1TextView;

    @InjectView(R.id.line2_textview)
    protected TextView mLine2TextView;

    private int mPosition;

    private ListItemSelectedListener mDeleteListener;

    public PersonListRow(Context context) {
        this(context, null);
    }

    public PersonListRow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonListRow(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.row_contact_list, this);

        setRootViewAttributes();

        ButterKnife.inject(this);

        if (!isInEditMode()) {
            resetUI();
        }
    }

    private void resetUI() {
        mLine1TextView.setText("");
    }

    public void updateUI(PersonInfo data, int position) {
        mPosition = position;
        setTransitionNames(position);

        resetUI();
        mLine1TextView.setText(data.getLine1());
        mLine2TextView.setText(data.getLine2());
        String circleText = data.getLine1();
        if (TextUtils.isEmpty(circleText)) {
            circleText = "?";
        }
        mCircleView.setBigCircleText(circleText.substring(0, 1));
    }

    @OnClick(R.id.delete_icon)
    protected void deletePersonTap() {
        if (mDeleteListener != null) {
            mDeleteListener.onListItemSelected(mPosition, this);
        }
    }

    @SuppressLint("NewApi")
    private void setTransitionNames(int position) {
        if (!HelperUtil.isPostLollipop()) {
            return;
        }
        mCircleView.setTransitionName("mCircleView" + position);
    }

    private void setRootViewAttributes() {
        int padding = (int) getResources().getDimension(R.dimen.person_list_row_padding);
        setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER_VERTICAL);
        setPadding(0, padding, 0, padding);
        setBackgroundDrawable(
                getResources().getDrawable(R.drawable.bg_transparent_with_touch_state));
    }

    public void setDeleteListener(
            ListItemSelectedListener deleteListener) {
        mDeleteListener = deleteListener;
    }
}
