package com.jessicardo.theuserentry.util.viewhelper;

import com.jessicardo.theuserentry.R;
import com.jessicardo.theuserentry.ui.common.interfaces.OnCompletionCallback;
import com.jessicardo.theuserentry.util.HelperUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.text.Spannable;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

/**
 * @author Jesse A.
 */
public class ViewHelper {

    public static final String TAG = ViewHelper.class.getSimpleName();

    /**
     * hide view by setting the height to 0
     *
     * @param view    - view to hide
     * @param animate - whether to animate hiding
     */
    public static void hideViewHeight(final View view, boolean animate) {
        hideViewHeight(view, animate, null);
    }

    /**
     * hide view by setting the height to 0
     *
     * @param view               - view to hide
     * @param animate            - whether to animate hiding
     * @param completionCallback - callback to invoke after the animation is complete, IF animate =
     *                           true
     */
    public static void hideViewHeight(final View view, boolean animate,
            final OnCompletionCallback completionCallback) {
        OnCompletionCallback callback = new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                toggleViewHeight(view, 0);
            }
        };

        if (animate) {
            ViewAnimationHelper.animateHide(view, callback, completionCallback);
        } else {
            callback.onCompletion();
            if (completionCallback != null) {
                completionCallback.onCompletion();
            }
        }
    }

    /**
     * show view by setting the height to WRAP_CONTENT
     *
     * @param view    - view to show
     * @param animate - whether to animate showing
     */
    public static void showViewHeight(final View view, boolean animate) {
        showViewHeight(view, animate, null);
    }

    /**
     * show view by setting the height to WRAP_CONTENT
     *
     * @param view               - view to show
     * @param animate            - whether to animate showing
     * @param completionCallback - callback to invoke after the animation is complete, IF animate =
     *                           true
     */
    public static void showViewHeight(final View view, boolean animate,
            final OnCompletionCallback completionCallback) {
        OnCompletionCallback callback = new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                toggleViewHeight(view, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };

        if (animate) {
            ViewAnimationHelper.animateShow(view, callback, completionCallback);
        } else {
            callback.onCompletion();
            if (completionCallback != null) {
                completionCallback.onCompletion();
            }
        }
    }

    /**
     * show view by setting it's visibility to View.VISIBLE
     *
     * @param view    - view to show
     * @param animate - whether to animate showing
     */
    public static void showView(final View view, boolean animate) {
        showView(view, animate, null);
    }

    /**
     * show view by setting it's visibility to View.VISIBLE
     *
     * @param view               - view to show
     * @param animate            - whether to animate showing
     * @param completionCallback - callback to invoke after the animation is complete, IF animate =
     *                           true
     */
    public static void showView(final View view, boolean animate,
            final OnCompletionCallback completionCallback) {
        showView(view, animate, completionCallback, 1.0f);
    }

    /**
     * show view by setting it's visibility to View.VISIBLE
     *
     * @param view               - view to show
     * @param animate            - whether to animate showing
     * @param completionCallback - callback to invoke after the animation is complete, IF animate =
     *                           true
     * @param durationMultiplier - value to be multiplied to {@link ViewAnimationHelper#sAnimationDuration}
     */
    public static void showView(final View view, boolean animate,
            final OnCompletionCallback completionCallback, float durationMultiplier) {
        showView(view, animate, completionCallback, durationMultiplier, -1, -1);
    }

    /**
     * show view by setting it's visibility to View.VISIBLE
     *
     * @param view               - view to show
     * @param animate            - whether to animate showing
     * @param completionCallback - callback to invoke after the animation is complete, IF animate =
     *                           true
     * @param durationMultiplier - value to be multiplied to {@link ViewAnimationHelper#sAnimationDuration}
     * @param revealX            - provide to specify x location to start reveal animation
     * @param revealY            - provide to specify y location to start reveal animation
     */
    public static void showView(final View view, boolean animate,
            final OnCompletionCallback completionCallback, float durationMultiplier, int revealX,
            int revealY) {
        OnCompletionCallback callback = new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                view.setVisibility(View.VISIBLE);
            }
        };

        if (animate) {
            if (revealX == -1 || revealY == -1) {
                ViewAnimationHelper
                        .animateShow(view, callback, completionCallback, durationMultiplier);
            } else {
                ViewAnimationHelper
                        .animateShow(view, callback, completionCallback, durationMultiplier,
                                revealX,
                                revealY);
            }

        } else {
            callback.onCompletion();
            if (completionCallback != null) {
                completionCallback.onCompletion();
            }
        }
    }

    /**
     * fade in view by setting it's visibility to View.VISIBLE
     *
     * @param view               - view to show
     * @param completionCallback - callback to invoke after the animation is complete, IF animate =
     *                           true
     * @param durationMultiplier - value to be multiplied to {@link ViewAnimationHelper#sAnimationDuration}
     */
    public static void fadeInView(final View view,
            final OnCompletionCallback completionCallback, float durationMultiplier) {
        OnCompletionCallback callback = new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                view.setVisibility(View.VISIBLE);
            }
        };

        ViewAnimationHelper
                .doXmlAnimation(R.anim.fade_in, view, callback, false, completionCallback,
                        durationMultiplier);
    }

    /**
     * hide view by setting it's visibility to View.GONE
     *
     * @param view    - view to hide
     * @param animate - whether to animate hiding
     */
    public static void hideView(final View view, boolean animate) {
        hideView(view, animate, null);
    }

    /**
     * hide view by setting it's visibility to View.GONE
     *
     * @param view               - view to hide
     * @param animate            - whether to animate hiding
     * @param completionCallback - callback to invoke after the animation is complete, IF animate =
     *                           true
     */
    public static void hideView(final View view, boolean animate,
            final OnCompletionCallback completionCallback) {
        hideView(view, animate, completionCallback, 1.0f);
    }

    /**
     * hide view by setting it's visibility to View.GONE
     *
     * @param view               - view to hide
     * @param animate            - whether to animate hiding
     * @param completionCallback - callback to invoke after the animation is complete, IF animate =
     *                           true
     * @param durationMultiplier - value to be multiplied to {@link ViewAnimationHelper#sAnimationDuration}
     */
    public static void hideView(final View view, boolean animate,
            final OnCompletionCallback completionCallback, float durationMultiplier) {
        hideView(view, animate, completionCallback, durationMultiplier, -1, -1);
    }

    /**
     * hide view by setting it's visibility to View.GONE
     *
     * @param view               - view to hide
     * @param animate            - whether to animate hiding
     * @param completionCallback - callback to invoke after the animation is complete, IF animate =
     *                           true
     * @param durationMultiplier - value to be multiplied to {@link ViewAnimationHelper#sAnimationDuration}
     * @param revealX            - provide to specify x location to start unreveal animation
     * @param revealY            - provide to specify y location to start unreveal animation
     */
    public static void hideView(final View view, boolean animate,
            final OnCompletionCallback completionCallback, float durationMultiplier, int revealX,
            int revealY) {
        OnCompletionCallback callback = new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                view.setVisibility(View.INVISIBLE);
            }
        };

        if (animate) {
            if (revealX == -1 || revealY == -1) {
                ViewAnimationHelper
                        .animateHide(view, callback, completionCallback, durationMultiplier);
            } else {
                ViewAnimationHelper
                        .animateHide(view, callback, completionCallback, durationMultiplier,
                                revealX,
                                revealY);
            }
        } else {
            callback.onCompletion();
            if (completionCallback != null) {
                completionCallback.onCompletion();
            }
        }
    }

    /**
     * fade out view by setting it's visibility to View.GONE
     *
     * @param view               - view to hide
     * @param completionCallback - callback to invoke after the animation is complete, IF animate =
     *                           true
     * @param durationMultiplier - value to be multiplied to {@link ViewAnimationHelper#sAnimationDuration}
     */
    public static void fadeOutView(final View view,
            final OnCompletionCallback completionCallback, float durationMultiplier) {
        OnCompletionCallback callback = new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                view.setVisibility(View.INVISIBLE);
            }
        };

        ViewAnimationHelper
                .doXmlAnimation(R.anim.fade_out, view, callback, true, completionCallback,
                        durationMultiplier);
    }

    /**
     * hide view by setting the width to 0
     *
     * @param view    - view to hide
     * @param animate - whether to animate hiding
     */
    public static void hideViewWidth(final View view, boolean animate) {
        hideViewWidth(view, animate, null);
    }

    /**
     * hide view by setting the width to 0
     *
     * @param view               - view to hide
     * @param animate            - whether to animate hiding
     * @param completionCallback - callback to invoke after the animation is complete, IF animate =
     *                           true
     */
    public static void hideViewWidth(final View view, boolean animate,
            final OnCompletionCallback completionCallback) {
        OnCompletionCallback callback = new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                toggleViewWidth(view, 0);
            }
        };

        if (animate) {
            ViewAnimationHelper.animateHide(view, callback, completionCallback);
        } else {
            callback.onCompletion();
            if (completionCallback != null) {
                completionCallback.onCompletion();
            }
        }
    }

    /**
     * show view by setting the width to WRAP_CONTENT
     *
     * @param view    - view to show
     * @param animate - whether to animate showing
     */
    public static void showViewWidth(final View view, boolean animate) {
        showViewWidth(view, animate, null);
    }

    /**
     * show view by setting the width to WRAP_CONTENT
     *
     * @param view               - view to show
     * @param animate            - whether to animate showing
     * @param completionCallback - callback to invoke after the animation is complete, IF animate =
     *                           true
     */
    public static void showViewWidth(final View view, boolean animate,
            final OnCompletionCallback completionCallback) {
        OnCompletionCallback callback = new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                toggleViewWidth(view, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };

        if (animate) {
            ViewAnimationHelper.animateShow(view, callback, completionCallback);
        } else {
            callback.onCompletion();
            if (completionCallback != null) {
                completionCallback.onCompletion();
            }
        }
    }

    public static void crossFadeViews(View viewToShow, View viewToHide) {
        crossFadeViews(viewToShow, viewToHide, null);
    }

    public static void crossFadeViews(View viewToShow, View viewToHide, int multiplier) {
        crossFadeViews(viewToShow, viewToHide, null, multiplier);
    }

    public static void crossFadeViews(View viewToShow, View viewToHide,
            OnCompletionCallback completionCallback) {
        crossFadeViews(viewToShow, viewToHide, completionCallback, 4);
    }

    public static void crossFadeViews(View viewToShow, View viewToHide,
            OnCompletionCallback completionCallback, int multiplier) {
        showView(viewToShow, true, completionCallback, multiplier);
        hideView(viewToHide, true, null, multiplier);
    }

    public static void circularReveal(final View view) {
        circularReveal(view, null);
    }

    public static void circularReveal(final View view,
            final OnCompletionCallback completionCallback) {
        executeAfterViewRendered(view, new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                showView(view, true, completionCallback, 2);
            }
        });
    }

    /**
     * Executes code that has to do with knowing views height, x or y position. Sets an observer and
     * executes callback after view has been layed out
     */
    public static void executeAfterViewRendered(final View view,
            final OnCompletionCallback callback) {
        if (view.getHeight() == 0 || view.getWidth() == 0) {
            ViewTreeObserver vto = view.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    callback.onCompletion();
                    ViewTreeObserver obs = view.getViewTreeObserver();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        obs.removeOnGlobalLayoutListener(this);
                    } else {
                        obs.removeGlobalOnLayoutListener(this);
                    }
                }
            });
        } else {
            if (callback != null) {
                callback.onCompletion();
            }
        }
    }

    public static void circularUnReveal(View view, final OnCompletionCallback completionCallback) {
        hideView(view, true, completionCallback, 2);
    }

    public static void spin(View view) {
        // by xml
        // ViewAnimationHelper.doXmlAnimation(R.anim.rotate, view, null, false, null, 1.5f);
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(500);
        anim.setFillAfter(true);
        anim.setFillEnabled(true);

        view.startAnimation(anim);
    }

    public static void spin(View view, float fromDegrees, float toDegrees) {
        spin(view, fromDegrees, toDegrees, 0, 1);
    }

    public static void spin(View view, float fromDegrees, float toDegrees, int repeatCount,
            int durationMultiplyer) {
        RotateAnimation anim = new RotateAnimation(fromDegrees, toDegrees,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(repeatCount);
        anim.setDuration(250 * durationMultiplyer);
        anim.setFillAfter(true);
        anim.setFillEnabled(true);

        view.startAnimation(anim);
    }

    /**
     * Does a Transition Animation if view's background drawable is an instance of
     * TransitionDrawable
     *
     * @param view - view to do the transition animation on
     */
    public static void startTransitionAnimation(View view) {
        if (view == null || !(view.getBackground() instanceof TransitionDrawable)) {
            Log.e(TAG, "error trying to start transition animation");
            return;
        }
        ViewAnimationHelper.animateTransition(view);
    }

    /**
     * Does a Reverse Transition Animation if view's background drawable is an instance of
     * TransitionDrawable
     *
     * @param view - view to do the transition animation on
     */
    public static void startReverseTransitionAnimation(View view) {
        if (view == null || !(view.getBackground() instanceof TransitionDrawable)) {
            Log.e(TAG, "error trying to start transition animation");
            return;
        }
        ViewAnimationHelper.animateTransitionReverse(view);
    }


    /**
     * Set view color by transitioning from the view's current color to the desired color
     *
     * @param view  - view to perform animation on
     * @param color - end color to see the view's background with
     */
    public static void animateSetViewColor(final View view, Integer color) {
        animateSetViewColor(view, color, null);
    }

    @SuppressLint("NewApi")
    public static Transition getAccelerateExplodeTransition(Context context) {
        Transition explode = TransitionInflater.from(context)
                .inflateTransition(android.R.transition.explode);
        explode.setInterpolator(new AccelerateInterpolator(2f)).setDuration(2500);
        return explode;
    }

    @SuppressLint("NewApi")
    public static Transition getDecelerateExplodeTransition(Context context) {
        Transition explode = TransitionInflater.from(context)
                .inflateTransition(android.R.transition.explode);
        explode.setInterpolator(new DecelerateInterpolator(2f)).setDuration(2500);
        return explode;
    }


    /**
     * Set view color by transitioning from the view's current color to the desired color
     *
     * @param view               - view to perform animation on
     * @param color              - end color to see the view's background with
     * @param completionCallback - callback to invoke after the animation is complete
     */
    public static void animateSetViewColor(final View view, Integer color,
            OnCompletionCallback completionCallback) {
        Drawable background = view.getBackground();
        Integer colorFrom = view.getDrawingCacheBackgroundColor();
        if (background instanceof ColorDrawable) {
            colorFrom = ((ColorDrawable) background).getColor();
        }
        ViewAnimationHelper.animateColorChange(view, colorFrom, color, completionCallback);

    }

    /**
     * slide up view, hide/show by setting view's height, NOTE: View must have wrap content for
     * height
     *
     * @param view - view to animate
     * @param show - true if you want to show the view after animation, false if you want to hide
     *             the view
     */
    public static void slideUpViewHeight(final View view, final boolean show) {
        slideUpViewHeight(view, show, null);
    }

    /**
     * slide up view, hide/show by setting view's height, NOTE: View must have wrap content for
     * height
     *
     * @param view               - view to animate
     * @param show               - true if you want to show the view after animation, false if you
     *                           want to hide the view
     * @param completionCallback - callback to invoke after animation is compelete
     */
    public static void slideUpViewHeight(final View view, final boolean show,
            OnCompletionCallback completionCallback) {
        final OnCompletionCallback callback = getHideShowViewHeightCompletion(view, show);

        ViewAnimationHelper.slideUp(view, callback, show, completionCallback);
    }

    /**
     * slide up view, hide/show by setting view's visibility
     *
     * @param view - view to animate
     * @param show - true if you want to show the view after animation, false if you want to hide
     *             the view
     */
    public static void slideUpView(final View view, final boolean show) {
        slideUpView(view, show, null);
    }

    /**
     * slide up view, hide/show by setting view's visibility
     *
     * @param view               - view to animate
     * @param show               - true if you want to show the view after animation, false if you
     *                           want to hide the view
     * @param completionCallback - callback to be invoked after animation is complete
     */
    public static void slideUpView(final View view, final boolean show,
            OnCompletionCallback completionCallback) {
        final OnCompletionCallback callback = getHideShowViewCompletion(view, show);

        ViewAnimationHelper.slideUp(view, callback, show, completionCallback);
    }

    /**
     * slide down view, hide/show by setting view's height NOTE: View must have wrap content for
     * height
     *
     * @param view - view to animate
     * @param show - true if you want to show the view after animation, false if you want to hide
     *             the view
     */
    public static void slideDownViewHeight(final View view, final boolean show) {
        slideDownViewHeight(view, show, null);
    }

    /**
     * slide down view, hide/show by setting view's height NOTE: View must have wrap content for
     * height
     *
     * @param view               - view to animate
     * @param show               - true if you want to show the view after animation, false if you
     *                           want to hide the view
     * @param completionCallback - callback to be invoked after animation is complete
     */
    public static void slideDownViewHeight(final View view, final boolean show,
            OnCompletionCallback completionCallback) {
        final OnCompletionCallback callback = getHideShowViewHeightCompletion(view, show);

        ViewAnimationHelper.slideDown(view, callback, show, completionCallback);
    }

    /**
     * slide down view, hide/show by setting view's visibility
     *
     * @param view - view to animate
     * @param show - true if you want to show the view after animation, false if you want to hide
     *             the view
     */
    public static void slideDownView(final View view, final boolean show) {
        slideDownView(view, show, null);
    }

    /**
     * slide down view, hide/show by setting view's visibility
     *
     * @param view               - view to animate
     * @param show               - true if you want to show the view after animation, false if you
     *                           want to hide the view
     * @param completionCallback - callback to be invoked after animation is complete
     */
    public static void slideDownView(final View view, final boolean show,
            OnCompletionCallback completionCallback) {
        final OnCompletionCallback callback = getHideShowViewCompletion(view, show);

        ViewAnimationHelper.slideDown(view, callback, show, completionCallback);
    }

    /**
     * slide left view, hide/show by setting view's width NOTE: View must have wrap content for
     * width
     *
     * @param view - view to animate
     * @param show - true if you want to show the view after animation, false if you want to hide
     *             the view
     */
    public static void slideLeftViewWidth(final View view, final boolean show) {
        slideLeftViewWidth(view, show, null);
    }

    /**
     * slide left view, hide/show by setting view's width NOTE: View must have wrap content for
     * width
     *
     * @param view               - view to animate
     * @param show               - true if you want to show the view after animation, false if you
     *                           want to hide the view
     * @param completionCallback - callback to be invoked after animation is complete
     */
    public static void slideLeftViewWidth(final View view, final boolean show,
            OnCompletionCallback completionCallback) {
        final OnCompletionCallback callback = getHideShowViewWidthCompletion(view, show);

        ViewAnimationHelper.slideLeft(view, callback, show, completionCallback);
    }

    /**
     * slide left view, hide/show by setting view's visibility
     *
     * @param view - view to animate
     * @param show - true if you want to show the view after animation, false if you want to hide
     *             the view
     */
    public static void slideLeftView(final View view, final boolean show) {
        slideLeftView(view, show, null);
    }

    /**
     * slide left view, hide/show by setting view's visibility
     *
     * @param view               - view to animate
     * @param show               - true if you want to show the view after animation, false if you
     *                           want to hide the view
     * @param completionCallback - callback to be invoked after animation is complete
     */
    public static void slideLeftView(final View view, final boolean show,
            OnCompletionCallback completionCallback) {
        final OnCompletionCallback callback = getHideShowViewCompletion(view, show);

        ViewAnimationHelper.slideLeft(view, callback, show, completionCallback);
    }

    /**
     * slide right view, hide/show by setting view's width NOTE: View must have wrap content for
     * width
     *
     * @param view - view to animate
     * @param show - true if you want to show the view after animation, false if you want to hide
     *             the view
     */
    public static void slideRightViewWidth(final View view, final boolean show) {
        slideRightViewWidth(view, show, null);
    }

    /**
     * slide right view, hide/show by setting view's width NOTE: View must have wrap content for
     * width
     *
     * @param view               - view to animate
     * @param show               - true if you want to show the view after animation, false if you
     *                           want to hide the view
     * @param completionCallback - callback to be invoked after animation is complete
     */
    public static void slideRightViewWidth(final View view, final boolean show,
            OnCompletionCallback completionCallback) {
        final OnCompletionCallback callback = getHideShowViewWidthCompletion(view, show);

        ViewAnimationHelper.slideRight(view, callback, show, completionCallback);
    }

    /**
     * slide right view, hide/show by setting view's visibility
     *
     * @param view - view to animate
     * @param show - true if you want to show the view after animation, false if you want to hide
     *             the view
     */
    public static void slideRightView(final View view, final boolean show) {
        slideRightView(view, show, null);
    }

    /**
     * slide right view, hide/show by setting view's visibility
     *
     * @param view               - view to animate
     * @param show               - true if you want to show the view after animation, false if you
     *                           want to hide the view
     * @param completionCallback - callback to be invoked after animation is complete
     */
    public static void slideRightView(final View view, final boolean show,
            OnCompletionCallback completionCallback) {
        final OnCompletionCallback callback = getHideShowViewCompletion(view, show);

        ViewAnimationHelper.slideRight(view, callback, show, completionCallback);
    }

    private static OnCompletionCallback getHideShowViewCompletion(final View view,
            final boolean show) {
        return new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                if (show) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    private static OnCompletionCallback getHideShowViewHeightCompletion(final View view,
            final boolean show) {
        return new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                if (show) {
                    toggleViewHeight(view, ViewGroup.LayoutParams.WRAP_CONTENT);
                } else {
                    toggleViewHeight(view, 0);
                }
            }
        };
    }

    private static OnCompletionCallback getHideShowViewWidthCompletion(final View view,
            final boolean show) {
        return new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                if (show) {
                    toggleViewWidth(view, ViewGroup.LayoutParams.WRAP_CONTENT);
                } else {
                    toggleViewWidth(view, 0);
                }
            }
        };
    }

    /**
     * Set the View's height to  int "height" value passed in
     *
     * @param view   - view to hide
     * @param height - value to set view's height with
     */
    public static void toggleViewHeight(View view, int height) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = height;
        view.setLayoutParams(lp);
    }

    /**
     * Set the View's width to  int "width" value passed in
     *
     * @param view  - view to hide
     * @param width - value to set view's width with
     */
    public static void toggleViewWidth(View view, int width) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams lp = view
                .getLayoutParams();
        lp.width = width;
        view.setLayoutParams(lp);
    }


}
