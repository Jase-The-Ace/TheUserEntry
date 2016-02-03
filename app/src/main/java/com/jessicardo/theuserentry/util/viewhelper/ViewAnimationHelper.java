package com.jessicardo.theuserentry.util.viewhelper;


import com.jessicardo.theuserentry.App;
import com.jessicardo.theuserentry.R;
import com.jessicardo.theuserentry.ui.common.interfaces.OnCompletionCallback;
import com.jessicardo.theuserentry.util.HelperUtil;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import io.codetail.animation.RevealAnimator;
import io.codetail.animation.SupportAnimator;

/**
 * @author Jesse A.
 */
// default modifier so that other classes cannot see this class or it's methods.
class ViewAnimationHelper {

    private static final int sAnimationDuration = 250;

    private static final String TAG = ViewAnimationHelper.class.getSimpleName();

    /**
     * @param callback - code to set the view visible
     */
    static void animateShow(View view, final OnCompletionCallback callback,
            final OnCompletionCallback completionCallback) {
        animateShow(view, callback, completionCallback, 1.0f);
    }

    /**
     * @param callback           - code to set the view visible
     * @param durationMultiplier - value to be multiplied to {@link com.jessicardo.theuserentry.util.viewhelper.ViewAnimationHelper#sAnimationDuration}
     */
    @SuppressLint("NewApi")
    static void animateShow(View view, final OnCompletionCallback callback,
            final OnCompletionCallback completionCallback, float durationMultiplier) {
        // get the center for the clipping circle
        int cx = (view.getWidth()) / 2;
        int cy = (view.getHeight()) / 2;
        animateShow(view, callback, completionCallback, durationMultiplier, cx, cy);
    }

    /**
     * @param callback           - code to set the view visible
     * @param durationMultiplier - value to be multiplied to {@link com.jessicardo.theuserentry.util.viewhelper.ViewAnimationHelper#sAnimationDuration}
     */
    @SuppressLint("NewApi")
    static void animateShow(View view, final OnCompletionCallback callback,
            final OnCompletionCallback completionCallback, float durationMultiplier, int cx,
            int cy) {
        try {
            if (cx == -1 || cy == -1) {
                cx = (view.getWidth()) / 2;
                cy = (view.getHeight()) / 2;
            }
            // get the final radius for the clipping circle
            int finalRadius = Math.max(view.getWidth(), view.getHeight());
            // use reveal effect if API > 21
            if (HelperUtil.isPostLollipop()) {

                // create the animator for this view (the start radius is zero)
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

                anim.setInterpolator(new AccelerateInterpolator(2f));

                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        // make the view visible when animation starts
                        callback.onCompletion();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (completionCallback != null) {
                            completionCallback.onCompletion();
                        }
                    }
                });

                anim.setDuration((long) (sAnimationDuration * durationMultiplier));

                // start the animation
                anim.start();
            } else if (view.getParent() != null &&
                    view.getParent() instanceof RevealAnimator) {
                // this means that we want the support reveal animation
                SupportAnimator anim =
                        io.codetail.animation.ViewAnimationUtils
                                .createCircularReveal(view, cx, cy, 0, finalRadius);
                anim.setInterpolator(new AccelerateInterpolator(2f));

                anim.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {
                        // make the view visible when animation starts
                        callback.onCompletion();
                    }

                    @Override
                    public void onAnimationEnd() {
                        if (completionCallback != null) {
                            completionCallback.onCompletion();
                        }
                    }

                    @Override
                    public void onAnimationCancel() {

                    }

                    @Override
                    public void onAnimationRepeat() {

                    }
                });
                anim.setDuration((int) (sAnimationDuration * durationMultiplier));
                anim.start();

            } else {
                // just do a fade animation
                doXmlAnimation(R.anim.fade_in, view, callback, false, completionCallback,
                        durationMultiplier);
            }
        } catch (Exception e) {
            Log.e(TAG, "exception doing animation e:" + e, e);
        }
    }

    static void animateTransition(View view) {
        final TransitionDrawable background = (TransitionDrawable) view.getBackground();
        background.startTransition(sAnimationDuration * 2);
    }

    static void animateTransitionReverse(View view) {
        final TransitionDrawable background = (TransitionDrawable) view.getBackground();
        background.reverseTransition(sAnimationDuration * 2);
    }

    static void animateColorChange(final View view, Integer colorFrom, Integer colorTo,
            final OnCompletionCallback completionCallback) {
        ValueAnimator colorAnimation = ValueAnimator
                .ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((Integer) animator.getAnimatedValue());
            }


        });
        colorAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (completionCallback != null) {
                    completionCallback.onCompletion();
                }
            }
        });
        colorAnimation.start();
    }

    /**
     * @param callback - code to hide the view
     */
    static void animateHide(View view, final OnCompletionCallback callback,
            final OnCompletionCallback completionCallback) {
        animateHide(view, callback, completionCallback, 1.0f);
    }

    /**
     * @param callback - code to hide the view
     */
    @SuppressLint("NewApi")
    static void animateHide(View view, final OnCompletionCallback callback,
            final OnCompletionCallback completionCallback, float durationMultiplier) {
        // get the center for the clipping circle
        int cx = (view.getWidth()) / 2;
        int cy = (view.getHeight()) / 2;
        animateHide(view, callback, completionCallback, durationMultiplier, cx, cy);
    }

    static void animateHideLeft(View view, final OnCompletionCallback callback,
            final OnCompletionCallback completionCallback, float durationMultiplier) {
        int cx = 0;
        int cy = (view.getHeight()) / 2;
        animateHide(view, callback, completionCallback, durationMultiplier, cx, cy);
    }

    /**
     * @param callback - code to hide the view
     */
    @SuppressLint("NewApi")
    static void animateHide(View view, final OnCompletionCallback callback,
            final OnCompletionCallback completionCallback, float durationMultiplier, int cx,
            int cy) {
        try {
            if (cx == -1 || cy == -1) {
                cx = (view.getWidth()) / 2;
                cy = (view.getHeight()) / 2;
            }
            // get the initial radius for the clipping circle
            int initialRadius = view.getWidth();
            // use reveal effect if API > 21
            if (HelperUtil.isPostLollipop()) {
                // create the animation (the final radius is zero)
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

                anim.setInterpolator(new DecelerateInterpolator(2f));

                // make the view invisible when the animation is done
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (callback != null) {
                            callback.onCompletion();
                        }
                        if (completionCallback != null) {
                            completionCallback.onCompletion();
                        }
                    }
                });
                anim.setDuration((long) (sAnimationDuration * durationMultiplier));

                // start the animation
                anim.start();
            } else if (view.getParent() != null &&
                    view.getParent() instanceof RevealAnimator) {
                // this means that we want the support reveal animation
                SupportAnimator anim =
                        io.codetail.animation.ViewAnimationUtils
                                .createCircularReveal(view, cx, cy, initialRadius, 0);
                anim.setInterpolator(new DecelerateInterpolator(2f));

                // make the view invisible when the animation is done
                anim.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {
                        if (callback != null) {
                            callback.onCompletion();
                        }
                        if (completionCallback != null) {
                            completionCallback.onCompletion();
                        }
                    }

                    @Override
                    public void onAnimationCancel() {

                    }

                    @Override
                    public void onAnimationRepeat() {

                    }
                });
                anim.setDuration((int) (sAnimationDuration * durationMultiplier));
                anim.start();
            } else {
                // just do a fade animation
                doXmlAnimation(R.anim.fade_out, view, callback, true, completionCallback,
                        durationMultiplier);
            }
        } catch (Exception e) {
            Log.e(TAG, "exception doing animation e:" + e, e);
        }
    }

    /**
     * @param callback - code to slide down the view by it's height distance
     */
    static void slideDown(View view, final OnCompletionCallback callback,
            final boolean callbackAfterAnimation, OnCompletionCallback completionCallback) {
        float fromYValue = 0;
        float toYValue = 1.0f;
        float fromXValue = 0;
        float toXValue = 0;
        doCodeAnimation(fromXValue, toXValue, fromYValue, toYValue, view, callback,
                callbackAfterAnimation, completionCallback);
    }

    /**
     * @param callback - code to slide up the view by it's height distance
     */
    static void slideUp(View view, final OnCompletionCallback callback,
            final boolean callbackAfterAnimation, OnCompletionCallback completionCallback) {
        float fromYValue = 1.0f;
        float toYValue = 0;
        float fromXValue = 0;
        float toXValue = 0;
        doCodeAnimation(fromXValue, toXValue, fromYValue, toYValue, view, callback,
                callbackAfterAnimation, completionCallback);
    }

    /**
     * @param callback - code to slide up the view by it's width distance
     */
    static void slideLeft(View view, final OnCompletionCallback callback,
            final boolean callbackAfterAnimation, OnCompletionCallback completionCallback) {
        float fromYValue = 0;
        float toYValue = 0;
        float fromXValue = 1.0f;
        float toXValue = 0;
        doCodeAnimation(fromXValue, toXValue, fromYValue, toYValue, view, callback,
                callbackAfterAnimation, completionCallback);
    }

    /**
     * @param callback - code to slide up the view by it's width distance
     */
    static void slideRight(View view, final OnCompletionCallback callback,
            final boolean callbackAfterAnimation, OnCompletionCallback completionCallback) {
        float fromYValue = 0;
        float toYValue = 0;
        float fromXValue = 0;
        float toXValue = 1.0f;
        doCodeAnimation(fromXValue, toXValue, fromYValue, toYValue, view, callback,
                callbackAfterAnimation, completionCallback);
    }

    private static void doCodeAnimation(float fromXValue, float toXValue, float fromYValue,
            float toYValue, final View view, final OnCompletionCallback animationSpecificCallback,
            final boolean callbackAfterAnimation, final OnCompletionCallback completionCallback) {

        TranslateAnimation translateAnimation = new TranslateAnimation(
                callbackAfterAnimation ? Animation.RELATIVE_TO_PARENT : Animation.RELATIVE_TO_SELF,
                fromXValue,
                Animation.RELATIVE_TO_PARENT,
                toXValue,
                callbackAfterAnimation ? Animation.RELATIVE_TO_PARENT : Animation.RELATIVE_TO_SELF,
                fromYValue,
                Animation.RELATIVE_TO_PARENT,
                toYValue);
        translateAnimation.setDuration(sAnimationDuration);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (!callbackAfterAnimation && animationSpecificCallback != null) {
                    animationSpecificCallback.onCompletion();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (callbackAfterAnimation && animationSpecificCallback != null) {
                    animationSpecificCallback.onCompletion();
                }
                if (completionCallback != null) {
                    completionCallback.onCompletion();
                }
                animation.reset();
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(translateAnimation);
    }

    /**
     * @param animResId                 - resId of the animation
     * @param view                      - view to perform the animation on
     * @param animationSpecificCallback - call back to hide or show view depending on the kind of
     *                                  aniumation. Some animation require the view to be hidden
     *                                  after, some require the view to be showed on animation
     *                                  start
     * @param callbackAfterAnimation    - determines when to call the animationSpecificCallback,
     *                                  either when animation starts(false) or when it ends(true)
     * @param completionCallback        - callback to be invoked after animation ends
     */
    static void doXmlAnimation(int animResId, View view,
            final OnCompletionCallback animationSpecificCallback,
            final boolean callbackAfterAnimation, final OnCompletionCallback completionCallback) {
        doXmlAnimation(animResId, view, animationSpecificCallback, callbackAfterAnimation,
                completionCallback, 1.0f);
    }

    /**
     * @param animResId                 - resId of the animation
     * @param view                      - view to perform the animation on
     * @param animationSpecificCallback - call back to hide or show view depending on the kind of
     *                                  aniumation. Some animation require the view to be hidden
     *                                  after, some require the view to be showed on animation
     *                                  start
     * @param callbackAfterAnimation    - determines when to call the animationSpecificCallback,
     *                                  either when animation starts(false) or when it ends(true)
     * @param completionCallback        - callback to be invoked after animation ends
     * @param durationMultiplier        - value to be multiplied to {@link com.jessicardo.theuserentry.util.viewhelper.ViewAnimationHelper#sAnimationDuration}
     */
    static void doXmlAnimation(int animResId,
            View view,
            final OnCompletionCallback animationSpecificCallback,
            final boolean callbackAfterAnimation,
            final OnCompletionCallback completionCallback,
            float durationMultiplier) {
        Animation anim = AnimationUtils
                .loadAnimation(App.getInstance().getApplicationContext(),
                        animResId);
        anim.setDuration((long) (sAnimationDuration * durationMultiplier));
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (!callbackAfterAnimation && animationSpecificCallback != null) {
                    animationSpecificCallback.onCompletion();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (callbackAfterAnimation && animationSpecificCallback != null) {
                    animationSpecificCallback.onCompletion();
                }
                if (completionCallback != null) {
                    completionCallback.onCompletion();
                }
            }

        });
        view.startAnimation(anim);
    }

}
