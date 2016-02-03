package com.jessicardo.theuserentry.ui.common.widgets;

import com.jessicardo.theuserentry.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FooterProgressView extends RelativeLayout {

    private static final String TAG = FooterProgressView.class.getSimpleName();

    @InjectView(R.id.progress_bar)
    protected ProgressBar mProgressBar;

    public FooterProgressView(Context context) {
        this(context, null);
    }

    public FooterProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterProgressView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.row_footer_progress_view, this);

        setRootViewAttributes();

        ButterKnife.inject(this);
    }

    private void setRootViewAttributes() {
        int height = (int) getResources().getDimension(R.dimen.footer_progress_view_height);
        setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                height));
    }

    public void toggleLoader(boolean show) {
        int visibility = show ? View.VISIBLE : View.INVISIBLE;
        mProgressBar.setVisibility(visibility);
    }

}
