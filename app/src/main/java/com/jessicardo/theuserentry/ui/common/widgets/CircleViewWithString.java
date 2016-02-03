package com.jessicardo.theuserentry.ui.common.widgets;

import com.jessicardo.theuserentry.R;
import com.jessicardo.theuserentry.util.FontType;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * The default size of the circles are adjusted for the main menu, which means big circle is 44dp
 * and small one is 16dp. If you would like to use both of them, give "wrap_content" for height and
 * weight. If you want to use a big circle with the size of greater or less than 44dp, note that it
 * will not sync with the small circle.
 */
public class CircleViewWithString extends RelativeLayout {

    private static final String TAG = CircleViewWithString.class.getSimpleName();

    @InjectView(R.id.big_circle)
    protected TextView mBigCircleTextView;

    @InjectView(R.id.small_circle)
    protected TextView mSmallCircleTextView;

    private ShapeDrawable mShapeDrawableForBigCircle;

    private ShapeDrawable mShapeDrawableForSmallCircle;

    public CircleViewWithString(Context context) {
        super(context);
        init();
    }

    public CircleViewWithString(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleViewWithString(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.circle_view_with_string, this);
        setRootViewAttributes();
        ButterKnife.inject(this);
        drawBigCircle();
        drawSmallCircle();
    }

    private void setRootViewAttributes() {
        setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    /**
     * drawBigCircle is a private method that gets called as soon as the view is instantiated.
     */
    private void drawBigCircle() {
        mShapeDrawableForBigCircle = new ShapeDrawable(new OvalShape());
        mBigCircleTextView.setBackgroundDrawable(mShapeDrawableForBigCircle);
        mShapeDrawableForBigCircle.getPaint().setColor(getResources().getColor(R.color.web_orange));
        mBigCircleTextView.setTextColor(getResources().getColor(R.color.white));
        mBigCircleTextView.setVisibility(VISIBLE);
        mBigCircleTextView.setTypeface(FontType.ROBOTOSLAB_BOLD.getTypeface());
    }

    /**
     * @param text is what will be shown on the big circle. Make sure it's not more than one
     *             letter.
     */
    public void setBigCircleText(String text) {
        mBigCircleTextView.setText(text.toUpperCase());
    }

    public void setBigCircleTypeFace(Typeface tf) {
        mBigCircleTextView.setTypeface(tf);
    }

    public void setSmallCircleTypeFace(Typeface tf) {
        mSmallCircleTextView.setTypeface(tf);
    }

    /**
     * * @param textColor is to change color of the text on the circle.
     */

    public void setBigCircleTextColor(int textColor) {
        mBigCircleTextView.setTextColor(textColor);
    }

    /**
     * @param backgroundColor provide the color in the form of getResources().getColor(R.color.flamingo).
     */

    public void setBigCircleBackgroundColor(int backgroundColor) {
        mShapeDrawableForBigCircle.getPaint().setColor(backgroundColor);
    }

    /**
     * * @param textSize (only for Big circle) if you want to use default value is 20sp. Unit is
     * TypedValue.COMPLEX_UNIT_PX
     */
    public void setBigCircleTextSize(float textSize) {
        mBigCircleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    /**
     * small circle is invisible by default. If you want to show small circle, call the method
     * "showSmallCircle"
     */
    private void drawSmallCircle() {
        mShapeDrawableForSmallCircle = new ShapeDrawable(new OvalShape());
        mSmallCircleTextView.setBackgroundDrawable(mShapeDrawableForSmallCircle);
        mShapeDrawableForSmallCircle.getPaint().setColor(getResources().getColor(R.color.thunderbird));
        mSmallCircleTextView.setTextColor(getResources().getColor(R.color.white));
        mSmallCircleTextView.setTypeface(FontType.ROBOTOSLAB_BOLD.getTypeface());
    }

    public void setSmallCircleTextColor(int textColor) {
        mSmallCircleTextView.setTextColor(getResources().getColor(textColor));
    }

    public void setSmallCircleText(String text) {
        mSmallCircleTextView.setText(text);
    }

    public void setSmallCircleBackgroundColor(int backgroundColor) {
        mShapeDrawableForSmallCircle.getPaint().setColor(getResources().getColor(backgroundColor));
    }

    public void setBigCircleSize(int widthHeight) {
        LayoutParams lp = (LayoutParams) mBigCircleTextView.getLayoutParams();
        lp.width = widthHeight;
        lp.height = widthHeight;
        mBigCircleTextView.setLayoutParams(lp);
    }

    /**
     * It shows small circle that was previously invisible.
     */
    public void showSmallCircle() {
        mSmallCircleTextView.setVisibility(VISIBLE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int min = Math.min(h, w);
        setBigCircleSize(min);
    }
}