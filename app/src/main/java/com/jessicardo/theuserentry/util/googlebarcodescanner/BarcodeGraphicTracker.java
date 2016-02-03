/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jessicardo.theuserentry.util.googlebarcodescanner;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

import com.jessicardo.theuserentry.util.PDF417Helper;
import com.jessicardo.theuserentry.util.googlebarcodescanner.camera.GraphicOverlay;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * Generic tracker which is used for tracking or reading a barcode (and can really be used for any
 * type of item).  This is used to receive newly detected items, add a graphical representation to
 * an overlay, update the graphics as the item changes, and remove the graphics when the item goes
 * away.
 */
public class BarcodeGraphicTracker extends Tracker<Barcode> {

    private GraphicOverlay<BarcodeGraphic> mOverlay;

    private BarcodeGraphic mGraphic;

    private BarcodeDetectedListener mBarcodeDetectedListener;

    public BarcodeGraphicTracker(GraphicOverlay<BarcodeGraphic> overlay, BarcodeGraphic graphic,
            BarcodeDetectedListener barcodeDetectedListener) {
        mOverlay = overlay;
        mGraphic = graphic;
        mBarcodeDetectedListener = barcodeDetectedListener;
    }

    /**
     * Start tracking the detected item instance within the item overlay.
     */
    @Override
    public void onNewItem(int id, Barcode item) {
        mGraphic.setId(id);
    }

    /**
     * Update the position/characteristics of the item within the overlay.
     */
    @Override
    public void onUpdate(Detector.Detections<Barcode> detectionResults, Barcode item) {
        if (mBarcodeDetectedListener != null) {
            // This means it was a driver's license but the scan sdk didn't process it.
            if (item.rawValue.startsWith("@") && item.driverLicense == null) {
                item.driverLicense = getProcessedDriversLicense(item.rawValue);
            } else if (item.driverLicense != null) {
                // change date from mmddyyyy to mm-dd-yyyy
                item.driverLicense.birthDate = PDF417Helper
                        .getFormattedDate(item.driverLicense.birthDate);
                item.driverLicense.expiryDate = PDF417Helper
                        .getFormattedDate(item.driverLicense.expiryDate);
            }

            if (item.driverLicense != null && item.driverLicense.addressZip != null
                    && item.driverLicense.addressZip.length() >= 5) {
                item.driverLicense.addressZip = item.driverLicense.addressZip.substring(0, 5);
            }
            mBarcodeDetectedListener.onDetected(detectionResults, item);
        }
        mOverlay.add(mGraphic);
        mGraphic.updateItem(item);
    }

    private Barcode.DriverLicense getProcessedDriversLicense(String rawValue) {
        Barcode.DriverLicense processedDl = new Barcode.DriverLicense();

        HashMap<String, String> driversLicenseMap = PDF417Helper
                .getDriversLicenseMap(rawValue);

        String fullName =
                TextUtils
                        .isEmpty(driversLicenseMap.get(PDF417Helper.CODE_FULL_NAME)) ? ""
                        : driversLicenseMap.get(PDF417Helper.CODE_FULL_NAME);
        String firstName =
                TextUtils.isEmpty(driversLicenseMap.get(PDF417Helper.CODE_FIRST_NAME)) ? ""
                        : driversLicenseMap.get(PDF417Helper.CODE_FIRST_NAME);
        if (TextUtils.isEmpty(firstName)) {
            firstName = TextUtils.isEmpty(driversLicenseMap.get(PDF417Helper.CODE_FIRST_NAME_1))
                    ? ""
                    : driversLicenseMap.get(PDF417Helper.CODE_FIRST_NAME_1);
        }
        String lastName =
                TextUtils.isEmpty(driversLicenseMap.get(PDF417Helper.CODE_LAST_NAME)) ? ""
                        : driversLicenseMap.get(PDF417Helper.CODE_LAST_NAME);
        String licenseId =
                TextUtils.isEmpty(driversLicenseMap.get(PDF417Helper.CODE_LICENSE_ID)) ? ""
                        : driversLicenseMap.get(PDF417Helper.CODE_LICENSE_ID);
        String state = TextUtils.isEmpty(driversLicenseMap.get(PDF417Helper.CODE_STATE))
                ? "" : driversLicenseMap.get(PDF417Helper.CODE_STATE);

        String street =
                TextUtils.isEmpty(driversLicenseMap.get(PDF417Helper.CODE_STREET))
                        ? "" : driversLicenseMap.get(PDF417Helper.CODE_STREET);
        String city =
                TextUtils.isEmpty(driversLicenseMap.get(PDF417Helper.CODE_CITY))
                        ? "" : driversLicenseMap.get(PDF417Helper.CODE_CITY);

        String zipCode =
                TextUtils.isEmpty(driversLicenseMap.get(PDF417Helper.CODE_ZIPCODE))
                        ? "" : driversLicenseMap.get(PDF417Helper.CODE_ZIPCODE);

        String expiration =
                TextUtils.isEmpty(driversLicenseMap.get(PDF417Helper.CODE_EXPIRATION_DATE))
                        ? "" : driversLicenseMap.get(PDF417Helper.CODE_EXPIRATION_DATE);

        String dob =
                TextUtils.isEmpty(driversLicenseMap.get(PDF417Helper.CODE_DATE_OF_BIRTH))
                        ? "" : driversLicenseMap.get(PDF417Helper.CODE_DATE_OF_BIRTH);

        if (!TextUtils.isEmpty(fullName) && TextUtils.isEmpty(firstName) || TextUtils
                .isEmpty(lastName)) {
            String[] names = fullName.split(",");
            firstName = "";
            lastName = fullName.split(",")[0];
            for (int i = 1; i < names.length; i++) {
                firstName = firstName + fullName.split(",")[i] + " ";
            }
            firstName = firstName.trim();
        }

        processedDl.firstName = firstName;
        processedDl.lastName = lastName;
        processedDl.licenseNumber = licenseId;
        processedDl.expiryDate = expiration;
        processedDl.addressState = state;
        processedDl.addressStreet = street;
        processedDl.addressCity = city;
        processedDl.addressZip = zipCode;
        processedDl.birthDate = dob;

        return processedDl;
    }

    /**
     * Hide the graphic when the corresponding object was not detected.  This can happen for
     * intermediate frames temporarily, for example if the object was momentarily blocked from
     * view.
     */
    @Override
    public void onMissing(Detector.Detections<Barcode> detectionResults) {
        mOverlay.remove(mGraphic);
    }

    /**
     * Called when the item is assumed to be gone for good. Remove the graphic annotation from the
     * overlay.
     */
    @Override
    public void onDone() {
        mOverlay.remove(mGraphic);
    }
}
