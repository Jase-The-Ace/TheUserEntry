package com.jessicardo.theuserentry.ui.common.widgets;


import com.jessicardo.theuserentry.ui.common.interfaces.OnCompletionCallback;
import com.jessicardo.theuserentry.ui.common.interfaces.ScrollIntervalListener;
import com.jessicardo.theuserentry.util.viewhelper.ViewHelper;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.HashSet;

public class LockableScrollView extends ScrollView {

    private static final String TAG = LockableScrollView.class.getSimpleName();

    private float xDistance, yDistance, lastX, lastY;

    private int mScrollChangeInterval;

    private ScrollIntervalListener mScrollIntervalListener;

    public LockableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private DisplayMetrics mDm = getResources().getDisplayMetrics();

    private ArrayList<View> mAttachedViews = new ArrayList<>();

    private HashSet<View> mStickyViews = new HashSet<>();

    private int mStickPosition;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                lastX = ev.getX();
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - lastX);
                yDistance += Math.abs(curY - lastY);
                lastX = curX;
                lastY = curY;
                if (xDistance > yDistance) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        View view = getChildAt(getChildCount() - 1);
        int diff = (view.getBottom() - (getHeight() + getScrollY() + view
                .getTop()));

        int adjsutedScrollY = (int) (getScrollY() / mDm.scaledDensity);

        moveAttachedViews();
        /**
         * Use this to know scroll position to pass into {@link #setScrollIntervalListener(ScrollIntervalListener)}
         */
//        Log.e(TAG, "adjustedScrollY:" + adjsutedScrollY);

        if (mScrollIntervalListener != null && adjsutedScrollY >= mScrollChangeInterval) {
            mScrollIntervalListener.afterScrollInterval();
        } else if (mScrollIntervalListener != null) {
            mScrollIntervalListener.beforeScrollInterval();
        }

        if (diff == 0) {
            //"Bottom has been reached"
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        return true;
    }

    private void moveAttachedViews() {
        for (View view : mAttachedViews) {
            if (!mStickyViews.contains(view)
                    || (mStickyViews.contains(view) && canScrollAttached(view))) {
                view.setTranslationY(-getScrollY());
                // call again to correct bug if scrolled really quick
                canScrollAttached(view);
            }
        }
    }

    private boolean canScrollAttached(View view) {
        boolean canScrollAttached = getViewScrolledYPos(view) >= mStickPosition
                || -view.getTranslationY() >= getScrollY();
        if (!canScrollAttached) {
            // used to solve bug where if you scroll fast, the view will go off screen
            view.setY(mStickPosition - 1);
        }
        return canScrollAttached;
    }

    private float getViewScrolledYPos(View view) {
        float futureScrollPos = view.getY();
        return futureScrollPos;
    }

    /**
     * @param view             - view to attach so that it will scroll with the scroll view. NOTE
     *                         that this view must be on top of the scrollview and be inside a
     *                         layout with full height  so it doesn't clip
     * @param shouldStickToTop - whether or not the view should stop scrolling when it reaches Y pos
     *                         0. In order words this view should not go off screen
     */
    public void attachViewToScrollView(View view, boolean shouldStickToTop) {
        attachViewToScrollView(view, shouldStickToTop, 0);
    }

    /**
     * @param view             - view to attach so that it will scroll with the scroll view. NOTE
     *                         that this view must be on top of the scrollview and be inside a
     *                         layout with full height  so it doesn't clip
     * @param shouldStickToTop - whether or not the view should stop scrolling when it reaches Y pos
     *                         0. In order words this view should not go off screen
     * @param stickPosition    default is 0 from the top. specifies how far from the top the view
     *                         should stick
     */
    public void attachViewToScrollView(View view, boolean shouldStickToTop, int stickPosition) {
        mAttachedViews.add(view);
        mStickPosition = stickPosition;
        if (shouldStickToTop) {
            mStickyViews.add(view);
        }

    }

    public void detatchViewFromScrollView(View view) {
        mAttachedViews.remove(view);
    }

    public ScrollIntervalListener getScrollIntervalListener() {
        return mScrollIntervalListener;
    }

    public void setScrollIntervalListener(ScrollIntervalListener scrollIntervalListener) {
        mScrollIntervalListener = scrollIntervalListener;
    }

    public int getScrollChangeInterval() {
        return mScrollChangeInterval;
    }

    public void setScrollChangeInterval(int scrollChangeInterval) {
        mScrollChangeInterval = scrollChangeInterval;
    }

    /**
     * Calculates the bottom y position of the view and sets it as the scroll change interval
     */
    public void setScrollChangeInterval(final View view) {
        ViewHelper.executeAfterViewRendered(view, new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                setScrollChangeInterval(getScrollIntervalForView(view));
            }
        });
    }

    public int getScrollIntervalForView(View view) {
        int adjustedY = (int) (view.getY() / mDm.scaledDensity);
        int adjustedHeight = (int) (view.getHeight() / mDm.scaledDensity);
        return adjustedY + adjustedHeight;
    }
}
