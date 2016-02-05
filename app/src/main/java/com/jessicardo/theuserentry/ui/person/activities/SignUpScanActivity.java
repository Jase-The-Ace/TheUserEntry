package com.jessicardo.theuserentry.ui.person.activities;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;

import com.jessicardo.theuserentry.R;
import com.jessicardo.theuserentry.api.controllers.PersonController;
import com.jessicardo.theuserentry.api.models.DriversLicense;
import com.jessicardo.theuserentry.api.models.Person;
import com.jessicardo.theuserentry.model.PersonModel;
import com.jessicardo.theuserentry.ui.common.ScanBaseActivity;
import com.jessicardo.theuserentry.ui.common.interfaces.OnCompletionCallback;
import com.jessicardo.theuserentry.util.DriversLicenseHelper;
import com.jessicardo.theuserentry.util.googlebarcodescanner.BarcodeDetectedListener;
import com.jessicardo.theuserentry.util.googlebarcodescanner.BarcodeGraphic;
import com.jessicardo.theuserentry.util.googlebarcodescanner.camera.CameraSourcePreview;
import com.jessicardo.theuserentry.util.googlebarcodescanner.camera.GraphicOverlay;
import com.jessicardo.theuserentry.util.viewhelper.ViewHelper;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SignUpScanActivity extends ScanBaseActivity {

    protected String TAG = getClass().getSimpleName();

    @Inject
    protected PersonModel mPersonModel;

    @Inject
    protected PersonController mPersonController;

    @InjectView(R.id.torch)
    protected ImageView mTorchButton;

    @InjectView(R.id.dim_view)
    protected View mDimView;

    @InjectView(R.id.bottom_view)
    protected View mBottomView;

    @InjectView(R.id.scan_info_card)
    protected ViewGroup mScanInfoCard;

    @InjectView(R.id.scan_info_header_text)
    protected TextView mScanInfoHeaderText;

    @InjectView(R.id.scan_info_subtitle_text)
    protected TextView mScanInfoSubtitleText;

    @InjectView(R.id.re_scan_button)
    protected ImageView mReScanButton;

    @InjectView(R.id.confirm_scan_button)
    protected ImageView mConfirmScanButton;

    @InjectView(R.id.barcode_view)
    protected ImageView mBarcodeView;

    @InjectView(R.id.scan_line)
    protected View mScanLine;

    @InjectView(R.id.preview)
    protected CameraSourcePreview mPreview;

    @InjectView(R.id.graphic_overlay)
    protected GraphicOverlay<BarcodeGraphic> mGraphicOverlay;

    private int mBottomViewHeight;

    private boolean mDoneScanning;

    private int mActionButtonsLeftRightMargin;

    private DriversLicense mDriversLicense, mScannedLicense;

    protected Toolbar mToolbar;

    private Handler mHandler = new Handler();

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SignUpScanActivity.class);
        // no extras
        return intent;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v("On Create", "On Create ---------->  " + TAG + " <----------  ");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup_scan);

        ButterKnife.inject(this);

        // Delay so we can present the instructions and load page faster
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initialize();
                startCameraSource();
            }
        }, 1000);

        initToolbar();

        initUIWithScanningState();
    }

    protected ImageView getTorchButton() {
        return mTorchButton;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick(R.id.torch)
    public void toggleTorch() {
        toggleTorchFlag();
    }

    @Override
    protected CameraSourcePreview getPreview() {
        return mPreview;
    }

    @Override
    protected GraphicOverlay<BarcodeGraphic> getGraphicOverlay() {
        return mGraphicOverlay;
    }

    @OnClick(R.id.re_scan_button)
    public void rescan() {
        mScanLine.setBackgroundColor(getResources().getColor(R.color.red_orange));
        mGraphicOverlay.clear();

        mScannedLicense = null;
        animateToScanningMode();
    }

    @OnClick(R.id.confirm_scan_button)
    public void done() {
        mDriversLicense = mScannedLicense;

        if (mDriversLicense != null) {
            Person person = Person.newPersonWithLicense(mDriversLicense);
            long id = mPersonModel.savePerson(person);
            person.setId(id);
            mPersonController.savePerson(person);
        }
        onBackPressed();
    }

    @OnClick(R.id.cancel_button)
    public void cancel() {
        onBackPressed();
    }


    @Override
    protected BarcodeDetectedListener getBarcodeDetectedListener() {
        return new BarcodeDetectedListener() {
            @Override
            public void onDetected(Detector.Detections<Barcode> detectionResults,
                    final Barcode item) {
                if (!mDoneScanning && item.driverLicense != null) {
                    mDoneScanning = true;

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mScanLine.setBackgroundColor(Color.GREEN);
                            setDriversLicenseFromResult(item);
                            updateScanCard(mScannedLicense);
                            vibrate();
                            animateToScanCompletedMode();
                        }
                    });
                }
            }
        };
    }


    private void initUIWithScanningState() {
        mActionButtonsLeftRightMargin = getResources()
                .getDimensionPixelSize(R.dimen.scan_action_button_left_right_margin);
        mBottomViewHeight = getResources()
                .getDimensionPixelSize(R.dimen.scan_bottom_card_height);

        ViewHelper.hideView(mScanLine, false);
        mDimView.setBackgroundColor(getResources().getColor(R.color.black));
        ViewHelper.fadeOutView(mDimView, new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                mDimView.setBackgroundColor(getResources().getColor(R.color.black_60pc));
                ViewHelper.crossFadeViews(mScanLine, mBarcodeView);
            }
        }, 20);

        ViewHelper.executeAfterViewRendered(mBottomView, new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                mBottomView.setTranslationY(mBottomView.getHeight());
            }
        });
        ViewHelper.executeAfterViewRendered(mScanInfoCard, new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                mScanInfoCard.setTranslationY(getScanInfoCardTransY());
            }
        });

        ViewHelper.executeAfterViewRendered(mReScanButton, new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                mReScanButton
                        .setTranslationX(-mActionButtonsLeftRightMargin - mReScanButton.getWidth());
            }
        });
        ViewHelper.executeAfterViewRendered(mConfirmScanButton, new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                mConfirmScanButton.setTranslationX(
                        mActionButtonsLeftRightMargin + mConfirmScanButton.getWidth());
            }
        });
    }

    private void animateToScanCompletedMode() {
        ViewHelper.hideView(mTorchButton, true, null, 3);
        ViewHelper.fadeInView(mDimView, new OnCompletionCallback() {
            @Override
            public void onCompletion() {
                mBottomView.animate().translationY(0)
                        .setInterpolator(new DecelerateInterpolator(2f))
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mReScanButton.animate().translationX(0)
                                        .setInterpolator(new DecelerateInterpolator(2f))
                                        .setListener(null).start();
                                mConfirmScanButton.animate().translationX(0)
                                        .setInterpolator(new DecelerateInterpolator(2f))
                                        .setListener(null).start();
                                mScanInfoCard.animate().translationY(0)
                                        .setInterpolator(new DecelerateInterpolator(2f))
                                        .setListener(null).start();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {
                            }
                        }).start();
            }
        }, 3);
    }

    private void animateToScanningMode() {
        mReScanButton.animate().translationX(
                -mActionButtonsLeftRightMargin - mReScanButton.getWidth())
                .setInterpolator(new DecelerateInterpolator(2f)).setListener(null).start();
        mConfirmScanButton.animate().translationX(
                mActionButtonsLeftRightMargin + mConfirmScanButton.getWidth())
                .setInterpolator(new DecelerateInterpolator(2f)).setListener(null).start();
        mScanInfoCard.animate().translationY(getScanInfoCardTransY())
                .setInterpolator(new DecelerateInterpolator(2f))
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewHelper.fadeOutView(mDimView, null, 3);
                        mBottomView.animate().translationY(mBottomView.getHeight())
                                .setInterpolator(new DecelerateInterpolator(2f)).setListener(null)
                                .start();
                        ViewHelper.showView(mTorchButton, true, new OnCompletionCallback() {
                            @Override
                            public void onCompletion() {
                                mDoneScanning = false;
                            }
                        }, 3);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                }).start();
    }

    private int getScanInfoCardTransY() {
        return mScanInfoCard.getHeight() + mBottomView.getHeight();
    }

    private void updateScanCard(DriversLicense driversLicense) {
        mScanInfoHeaderText
                .setText(driversLicense.getFirstName() + " " + driversLicense.getLastName());
        mScanInfoSubtitleText.setText(
                DriversLicenseHelper.getFormattedDriversLicense(this, driversLicense));
    }

    private void setDriversLicenseFromResult(Barcode barcode) {
        DriversLicense driversLicense = new DriversLicense();
        driversLicense.setFirstName(barcode.driverLicense.firstName);
        driversLicense.setLastName(barcode.driverLicense.lastName);
        driversLicense.setLicenseId(barcode.driverLicense.licenseNumber);
        driversLicense.setExpiration(barcode.driverLicense.expiryDate);
        driversLicense.setState(barcode.driverLicense.addressState);
        driversLicense.setStreet(barcode.driverLicense.addressStreet);
        driversLicense.setCity(barcode.driverLicense.addressCity);
        driversLicense.setZipcode(barcode.driverLicense.addressZip);
        driversLicense.setDob(barcode.driverLicense.birthDate);
        mScannedLicense = driversLicense;
    }

    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(getToolbarId());
        ViewHelper.hideViewHeight(mToolbar, false);
        View statusBarView = findViewById(getStatusBarViewId());
        View statusBarTintView = findViewById(getStatusBarTintViewId());

        configureStatusBar(statusBarView, statusBarTintView);

    }

    protected int getContainerId() {
        return R.id.container;
    }

    protected int getToolbarId() {
        return R.id.toolbar;
    }

    protected int getStatusBarViewId() {
        return R.id.status_bar_view;
    }

    protected int getStatusBarTintViewId() {
        return R.id.status_bar_tint_view;
    }

    @Override
    protected int getProgressBarId() {
        return 0;
    }

    protected int getNaviationBarViewId() {
        return R.id.navigation_bar_view;
    }

}
