package com.jessicardo.theuserentry.util.googlebarcodescanner;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * @author Jesse A.
 */
public interface BarcodeDetectedListener {

    public void onDetected(Detector.Detections<Barcode> detectionResults, Barcode item);

}
