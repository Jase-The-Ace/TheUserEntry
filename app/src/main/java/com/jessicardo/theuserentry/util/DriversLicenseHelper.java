package com.jessicardo.theuserentry.util;

import com.jessicardo.theuserentry.R;
import com.jessicardo.theuserentry.api.models.DriversLicense;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.Calendar;

public class DriversLicenseHelper {

    public static Spannable getFormattedDriversLicense(Context context,
            DriversLicense driversLicense) {
        String driverLicenseFormat = "MM-dd-yyyy";
        Calendar expireDateCal = DateHelper
                .getCalendarDateFromFormattedString(driversLicense.getExpiration(),
                        driverLicenseFormat);
        // offset so that if it expired today, it won't say expired until tommorrow.
        expireDateCal.add(Calendar.DAY_OF_MONTH, 1);
        boolean expired = expireDateCal.before(Calendar.getInstance());
        String expireText = context.getString(R.string.expires);
        if (expired) {
            expireText = context.getString(R.string.expired);
        }

        String expiresText = expireText + ": " + driversLicense.getExpiration();
        String subtitle =
                context.getString(R.string.id).toUpperCase() + ": " + driversLicense.getLicenseId()
                        +
                        "\n" +
                        context.getString(R.string.address) + ": " + driversLicense.getStreet()
                        + "\n"
                        + driversLicense.getCity() + ", " + driversLicense.getState() + " "
                        + driversLicense.getZipcode() +
                        "\n"
                        + expiresText;

        Spannable spannableString = new SpannableString(subtitle);
        if (expired) {
            int indexOfExpires = subtitle.indexOf(expiresText);
            spannableString
                    .setSpan(new ForegroundColorSpan(
                                    context.getResources().getColor(R.color.carmine)),
                            indexOfExpires,
                            indexOfExpires + expiresText.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

}
