package com.jessicardo.theuserentry;

import com.jessicardo.theuserentry.util.PDF417Helper;

import android.test.InstrumentationTestCase;

import java.util.HashMap;

public class PDF417HelperTestCase extends InstrumentationTestCase {


    public void testDriversBarcode1StringParse() {
        String barcodeString = PDF417Helper.getSampleDriversLicenseBarcode1();

        HashMap<String, String> driversLicenseMap = PDF417Helper
                .getDriversLicenseMap(barcodeString);

        String expectedFullName = null;
        String expectedFirstName = "FIRST";
        String expectedLastName = "LASTNAME";
        String expectedLicenseId = "D1234562";
        String expectedState = "CA";
        String expectedStreet = "1234 ANY STREET";
        String expectedCity = "CITY";
        String expectedZipCode = "000000000";
        String expectedCountry = "USA";
        String expectedExpiration = "10-31-2014";

        String actualFullName = driversLicenseMap.get(PDF417Helper.CODE_FULL_NAME);
        String actualFirstName = driversLicenseMap.get(PDF417Helper.CODE_FIRST_NAME);
        String actualLastName = driversLicenseMap.get(PDF417Helper.CODE_LAST_NAME);
        String actualLicenseId = driversLicenseMap.get(PDF417Helper.CODE_LICENSE_ID);
        String actualState = driversLicenseMap.get(PDF417Helper.CODE_STATE);
        String actualStreet = driversLicenseMap.get(PDF417Helper.CODE_STREET);
        String actualCity = driversLicenseMap.get(PDF417Helper.CODE_CITY);
        String actualZipCode = driversLicenseMap.get(PDF417Helper.CODE_ZIPCODE);
        String actualCountry = driversLicenseMap.get(PDF417Helper.CODE_COUNTRY);
        String actualExpiration = driversLicenseMap.get(PDF417Helper.CODE_EXPIRATION_DATE);

        assertEquals(expectedFullName, actualFullName);
        assertEquals(expectedFirstName, actualFirstName);
        assertEquals(expectedLastName, actualLastName);
        assertEquals(expectedLicenseId, actualLicenseId);
        assertEquals(expectedState, actualState);
        assertEquals(expectedStreet, actualStreet);
        assertEquals(expectedCity, actualCity);
        assertEquals(expectedZipCode, actualZipCode);
        assertEquals(expectedCountry, actualCountry);
        assertEquals(expectedExpiration, actualExpiration);

    }

    public void testDriversBarcode2StringParse() {
        String barcodeString = PDF417Helper.getSampleDriversLicenseBarcode2();

        HashMap<String, String> driversLicenseMap = PDF417Helper
                .getDriversLicenseMap(barcodeString);

        String expectedFullName = "SAMPLE,MONTANA,IDCARD1";
        String expectedFirstName = null;
        String expectedLastName = null;
        String expectedLicenseId = "0300119554115";
        String expectedState = "MT";
        String expectedStreet = "ANYWHERE IN";
        String expectedCity = "HELENA";
        String expectedZipCode = "59601";
        String expectedCountry = null;
        String expectedExpiration = "03-15-2006";

        String actualFullName = driversLicenseMap.get(PDF417Helper.CODE_FULL_NAME);
        String actualFirstName = driversLicenseMap.get(PDF417Helper.CODE_FIRST_NAME);
        String actualLastName = driversLicenseMap.get(PDF417Helper.CODE_LAST_NAME);
        String actualLicenseId = driversLicenseMap.get(PDF417Helper.CODE_LICENSE_ID);
        String actualState = driversLicenseMap.get(PDF417Helper.CODE_STATE);
        String actualStreet = driversLicenseMap.get(PDF417Helper.CODE_STREET);
        String actualCity = driversLicenseMap.get(PDF417Helper.CODE_CITY);
        String actualZipCode = driversLicenseMap.get(PDF417Helper.CODE_ZIPCODE);
        String actualCountry = driversLicenseMap.get(PDF417Helper.CODE_COUNTRY);
        String actualExpiration = driversLicenseMap.get(PDF417Helper.CODE_EXPIRATION_DATE);

        assertEquals(expectedFullName, actualFullName);
        assertEquals(expectedFirstName, actualFirstName);
        assertEquals(expectedLastName, actualLastName);
        assertEquals(expectedLicenseId, actualLicenseId);
        assertEquals(expectedState, actualState);
        assertEquals(expectedStreet, actualStreet);
        assertEquals(expectedCity, actualCity);
        assertEquals(expectedZipCode, actualZipCode);
        assertEquals(expectedCountry, actualCountry);
        assertEquals(expectedExpiration, actualExpiration);
    }
}
