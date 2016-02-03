package com.jessicardo.theuserentry.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {

    private static final String TAG = DateHelper.class.getSimpleName();

    public static String dayOfWeekForDate(String date) {
        return getNewFormattedDate(date, "EEE");
    }

    /**
     * @param date - formatted date ex: 2013-08-21T17:48:10-04:00
     * @return the day represented in String format, ex: 21
     */
    public static String getDayForDate(String date) {
        return getNewFormattedDate(date, "d");
    }

    /**
     * @param date - formatted date ex: 2013-08-21T17:48:10-04:00
     * @return the day represented in int or 0 if an exception occured.ex: 21
     */
    public static int getDayOfMonthIntValue(String date) {
        try {
            return Integer.parseInt(getDayForDate(date));
        } catch (NumberFormatException e) {
            Log.w(TAG, "NumberFormat issue ", e);
            return 0;
        }
    }

    /**
     * @param date - formatted date ex: 2013-08-21T17:48:10-04:00
     * @return the month represented in int or 0 if an exception occured.ex: 10
     */
    public static int getMonthForDateIntValue(String date) {
        try {
            return Integer.parseInt(getNewFormattedDate(date, "MM"));
        } catch (NumberFormatException e) {
            Log.w(TAG, "NumberFormat issue ", e);
            return 0;
        }

    }

    /**
     * @param date - formatted date ex: 2013-08-21T17:48:10-04:00
     * @return the month represented in String format, ex:Jan
     */
    public static String getMonthForDate(String date) {
        return getNewFormattedDate(date, "MMM");
    }

    public static String getNewFormattedDate(String fullDate,
            String format) {
        Date date;
        try {
            long epochDate = Long.parseLong(fullDate + "000");
            date = new Date(epochDate);
        } catch (NumberFormatException e) {
            Log.w(TAG, "NumberFormat issue ", e);
            date = getCalendarDateFromFormattedString(fullDate).getTime();
        }
        SimpleDateFormat sdFormat = new SimpleDateFormat(format, Locale.getDefault());
        return sdFormat.format(date);
    }

    public static String getNewFormattedDate(Date date,
            String format) {
        SimpleDateFormat sdFormat = new SimpleDateFormat(format, Locale.getDefault());
        return sdFormat.format(date);
    }

    /**
     * uses format : yyyy-MM-dd
     */
    public static Calendar getCalendarDateFromFormattedString(String dateString) {
        return getCalendarDateFromFormattedString(dateString, "yyyy-MM-dd");
    }

    /**
     * uses format : yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     */
    public static Calendar getCalendarDateFromFullFormattedString(String dateString) {
        return getCalendarDateFromFormattedString(dateString, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }

    public static Calendar getCalendarDateFromFormattedString(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar date = Calendar.getInstance();
        try {
            date.setTime(dateFormat.parse(dateString));
        } catch (ParseException e) {
            Log.wtf(TAG, "Failed to parse Date: " + dateString, e);
        } catch (Exception e) {
            Log.wtf(TAG, "exception paring Date: " + dateString, e);
        }
        return date;
    }


    /**
     * @return -ex: 2013-03-25
     */
    public static String getShortFormattedDate(Date date) {
        String format = "yyyy-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        String fullDate = dateFormat.format(date);
        return fullDate;
    }

    /**
     * @param initialDate -ex: 2013-03-25
     * @param dateToQuery -ex: 2013-03-25
     */
    public static boolean isDateBefore(String initialDate, String dateToQuery) {
        int val = 0;
        if (initialDate != null && dateToQuery != null) {
            val = initialDate.compareTo(dateToQuery);
        }
        return val < 0;
    }

    /**
     * @param initialDate -ex: 2013-03-25
     * @param dateToQuery -ex: 2013-03-25
     */
    public static boolean isDateAfter(String initialDate, String dateToQuery) {
        int val = 0;
        if (initialDate != null && dateToQuery != null) {
            val = initialDate.compareTo(dateToQuery);
        }
        return val > 0;
    }

    /**
     * @param formattedDate -ex: 2013-03-25T04:30:02+0000
     * @return -ex: 2013-03-25
     */
    public static String getShortFormattedDate(String formattedDate) {
        return formattedDate.substring(0, 10);
    }

    public static boolean isDateToday(String dateString) {
        Calendar date = Calendar.getInstance();
        String todaysDate = getFormattedDate((date.getTime()));
        return isDayEqual(dateString, todaysDate);
    }

    public static boolean isDayEqual(String dateString, String otherDateString) {
        return (getCleanDisplayDateFromFullDate(dateString)
                .equals(getCleanDisplayDateFromFullDate(otherDateString)));
    }

    public static boolean isDateToday(Calendar selectedDateObject) {
        Calendar today = Calendar.getInstance();
        return isDayEqual(selectedDateObject, today);
    }

    public static boolean isDayEqual(Calendar selectedDateObject, Calendar otherDateObject) {
        if (selectedDateObject.get(Calendar.MONTH) == otherDateObject.get(Calendar.MONTH)
                && selectedDateObject.get(Calendar.DAY_OF_MONTH) == otherDateObject
                .get(Calendar.DAY_OF_MONTH)
                && selectedDateObject.get(Calendar.YEAR) == otherDateObject
                .get(Calendar.YEAR)) {
            return true;
        }
        return false;
    }

    /**
     * Helper method to get Formatted Date for the API
     *
     * @param date - date to format
     * @return ex: 2013-03-25T04:30:02+00:00
     */
    public static String getFormattedDate(Date date) {
        String format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        return getFormattedDate(date, format);
    }

    /**admin
     * Helper method to get Formatted Date for the API
     *
     * @param date - date to format
     * @return ex: 2013-03-25T04:30:02+00:00
     */
    public static String getFormattedDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        String fullDate = dateFormat.format(date);
        return fullDate;
    }

    /**
     * Get UTC from Local date
     *
     * @param date - local date
     * @return - UTC Formated date i.e: 2013-03-25T05:00:00.000Z
     */
    public static String getUTCFromLocalDate(Date date) {
        String format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcDate = dateFormat.format(date);
        return utcDate;
    }

    /**
     * Get UTC from Local Formatted String date
     *
     * @param dateString - Date formatted with timezone
     */
    public static String getUTCFromLocalDate(String dateString) {
        String format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        Calendar date = Calendar.getInstance();
        try {
            date.setTime(dateFormat.parse(dateString));
        } catch (ParseException e) {
            Log.wtf(TAG, "Failed to parse Date: " + dateString, e);
        }
        String utcDate = getUTCFromLocalDate(date.getTime());
        return utcDate;
    }

    /**
     * Get UTC from Local Formatted String date
     *
     * @param dateString - Date formatted with timezone
     */
    public static String getLocalDateFromUTCDate(String dateString) {
        String localDate = "";
        String utcFormat = "yyyy-MM-dd'T'HH:mm:ss";
        String localFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat utcDateFormat = new SimpleDateFormat(utcFormat,
                Locale.getDefault());
        SimpleDateFormat localDateFormat = new SimpleDateFormat(localFormat,
                Locale.getDefault());
        utcDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        dateString = fixIfEpochDate(dateString);
        try {
            date = utcDateFormat.parse(dateString);
        } catch (ParseException e) {
            Log.wtf(TAG, "Failed to parse Date: " + dateString, e);
        }

        if (date != null) {
            localDate = localDateFormat.format(date);
        }
        return localDate;
    }

    public static String getCleanDisplayDateFromFullDate(
            String formattedFullDate) {
        if (formattedFullDate != null) {
            String[] brokenDate = formattedFullDate.split("-");
            if (brokenDate.length > 2) {
                String day = brokenDate[1];
                String month = brokenDate[2].substring(0, 2);
                String year = brokenDate[0].substring(2);

                return String.format("%s/%s/%s", day, month, year);
            }
        }
        return "";
    }

    /**
     * @param formattedDate - ex: 2013-03-25T04:30:02+0000
     * @return Calendar object representing the formattedDate
     */
    public static Calendar getDateFromFormattedDate(String formattedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        if (formattedDate != null) {
            try {
                calendar.setTime(dateFormat.parse(formattedDate));
            } catch (ParseException e) {
                Log.wtf(TAG, "Failed to parse Date: " + formattedDate, e);
            }
        }
        return calendar;
    }

    /**
     * @param weekStartDate - the first day of the week range you want to be displayed
     * @return formatted date in format ex: Jan. 19th
     */
    public static String getNavBarWeekDateFormat(Date weekStartDate) {
        if (weekStartDate != null) {
            String format = "MMM d";
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            String fullDate = dateFormat.format(weekStartDate);

            String numberToCheck = fullDate.substring(fullDate.length() - 1,
                    fullDate.length());

            // will be a whitespace if it is a single digit so we are safe - it
            // will
            // be truncated when concatenated.
            String prevNumberToCheck = fullDate.substring(
                    fullDate.length() - 2, fullDate.length() - 1);

            int day;
            try {
                day = Integer.parseInt((prevNumberToCheck + numberToCheck)
                        .trim());
            } catch (NumberFormatException e) {
                Log.w(TAG, "NumberFormat issue ", e);
                day = 0;
                return "";
            }

            fullDate = fullDate + getNumberSuffix(day);

            return fullDate;
        }
        return "";
    }

    /**
     * @return formatted date in format ex: January 2013
     */
    public static String getNavBarCleanMonth(Date date) {
        if (date != null) {
            String format = "MMMM yyyy";
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            String fullDate = dateFormat.format(date);

            return fullDate;
        }
        return "";
    }

    /**
     * @param formattedDate - ex: 2013-03-25T04:30:02+0000
     * @return ex: January 20th, 2013
     */
    public static String getOrdinalFormattedDate(String formattedDate) {
        Calendar calendar = getCalendarDateFromFullFormattedString(formattedDate);
        return getOrdinalFormattedDate(calendar);
    }

    /**
     * @return @return ex: January 20th, 2013
     */
    public static String getOrdinalFormattedDate(Calendar calendar) {
        if (calendar != null) {
            String format = "MMMM";
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            String fullDate = dateFormat.format(calendar.getTime());

            fullDate = fullDate + " " + getNumberWithSuffix(calendar.get(Calendar.DAY_OF_MONTH))
                    + ", " + calendar.get(Calendar.YEAR);

            return fullDate;
        }
        return "";
    }

    /**
     * @return @return ex: Jan 20th, 2013
     */
    public static String getOrdinalFormattedShortDate(Calendar calendar) {
        if (calendar != null) {
            String format = "MMM";
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            String fullDate = dateFormat.format(calendar.getTime());

            fullDate = fullDate + " " + getNumberWithSuffix(calendar.get(Calendar.DAY_OF_MONTH))
                    + ", " + calendar.get(Calendar.YEAR);

            return fullDate;
        }
        return "";
    }

    /**
     * Get first date of the week relative to specified date
     *
     * @param currentDate - date used to determine week range.
     * @return - returns a Calendar representing the first day of the week
     */
    public static Calendar getWeekStartDate(Calendar currentDate) {
        Calendar offsetedDate = (Calendar) currentDate.clone();
        int firstDayOfWeekOffset = offsetedDate.get(Calendar.DAY_OF_WEEK)
                - offsetedDate.getFirstDayOfWeek();
        offsetedDate.add(Calendar.DATE, -firstDayOfWeekOffset);
        return offsetedDate;
    }

    /**
     * Get last date of the week relative to specified date
     *
     * @param currentDate - date used to determine week range.
     * @return - returns a Calendar representing the first day of the week
     */
    public static Calendar getWeekEndDate(Calendar currentDate) {
        Calendar offsetedDate = (Calendar) currentDate.clone();
        int lastDayOfWeekOffset = (offsetedDate.getFirstDayOfWeek() + 6)
                - currentDate.get(Calendar.DAY_OF_WEEK);
        offsetedDate.add(Calendar.DATE, lastDayOfWeekOffset);
        return offsetedDate;
    }

    /**
     * Get first date of the week relative to specified date with week offset
     *
     * @param currentDate    - date used to determine week range.
     * @param numWeeksOffset - # of weeks to offset
     * @return - returns a Calendar representing the first day of the week
     */
    public static Calendar getWeekStartDate(Calendar currentDate,
            int numWeeksOffset) {
        Calendar offsetedDate = getWeekStartDate(currentDate);
        zeroTimeForDate(offsetedDate);
        offsetedDate.add(Calendar.DATE, numWeeksOffset * 7);
        return offsetedDate;
    }

    /**
     * Get date from 7 days of currentDate
     */
    public static Calendar getLastWeeksDate(Calendar currentDate, int numWeeksOffset) {
        Calendar offsetedDate = (Calendar) currentDate.clone();
        zeroTimeForDate(offsetedDate);
        offsetedDate.add(Calendar.DATE, -6);
        offsetedDate.add(Calendar.DATE, numWeeksOffset * 7);
        return offsetedDate;
    }

    /**
     * Get with week offset
     */
    public static Calendar getDateWithWeekOffset(Calendar currentDate, int numWeeksOffset) {
        Calendar offsetedDate = (Calendar) currentDate.clone();
        zeroTimeForDate(offsetedDate);
        offsetedDate.add(Calendar.DATE, numWeeksOffset * 7);
        return offsetedDate;
    }

    /**
     * Helper method to set hour, minutes and seconds to 0
     */
    public static void zeroTimeForDate(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Get last date of the week relative to specified date with week offset
     *
     * @param currentDate    - date used to determine week range.
     * @param numWeeksOffset - # of weeks to offset
     * @return - returns a Calendar representing the first day of the week
     */
    public static Calendar getWeekEndDate(Calendar currentDate,
            int numWeeksOffset) {
        Calendar offsetedDate = getWeekEndDate(currentDate);
        zeroTimeForDate(offsetedDate);
        offsetedDate.add(Calendar.DATE, numWeeksOffset * 7);
        return offsetedDate;
    }

    public static Calendar getFirstDayOfMonthFromRelativeDate(
            Calendar currentDate, int numMonthsOffset) {
        Calendar offsetedDate = (Calendar) currentDate.clone();
        offsetedDate.add(Calendar.MONTH, numMonthsOffset);
        offsetedDate.set(Calendar.DAY_OF_MONTH,
                offsetedDate.getActualMinimum(Calendar.DAY_OF_MONTH));
        return offsetedDate;
    }

    public static Calendar getLastDayOfMonthFromRelativeDate(
            Calendar currentDate, int numMonthsOffset) {
        Calendar offsetedDate = (Calendar) currentDate.clone();
        offsetedDate.add(Calendar.MONTH, numMonthsOffset);
        offsetedDate.set(Calendar.DAY_OF_MONTH,
                offsetedDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        return offsetedDate;
    }

    /**
     * @param utcDateTime - formatted UTC date ex: 2013-08-21T17:48:10
     * @return the time of the date in a format : ex: 5:48 pm
     */
    public static String getLocalTimeFromUTCDate(String utcDateTime) {
        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
        String timeFormat = "h:mm a";
        SimpleDateFormat utcDateFormat = new SimpleDateFormat(dateFormat);
        SimpleDateFormat localDateFormat = new SimpleDateFormat(timeFormat);
        Date date = null;
        String time = "";
        TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
        utcDateFormat.setTimeZone(utcTimeZone);
        if (utcDateTime != null) {
            try {
                date = utcDateFormat.parse(utcDateTime);
            } catch (ParseException e) {
                Log.wtf(TAG, "Failed to parse Date: " + utcDateTime, e);
            }
        }

        if (date != null) {
            time = localDateFormat.format(date);
        }
        return time;
    }

    /**
     * Get the beginning of the day in UTC in relation to the user's local time
     *
     * @param date - short formatted date: i.e.: 2013-10-31
     * @return - Full formatted UTC date: i.e.: 2013-10-31T04:00:00.000Z (example for edt beginning
     * of day)
     */
    public static String getStartUTCDateTimeFromLocalDate(String date) {
        Calendar localDate = getCalendarDateFromFormattedString(date);
        zeroTimeForDate(localDate);
        String utcDate = getUTCFromLocalDate(localDate.getTime());
        return utcDate;
    }

    /**
     * Get the beginning of the day in UTC in relation to the user's local time
     *
     * @param date - short formatted date: i.e.: 2013-10-31
     * @return - Full formatted UTC date: i.e.: 2013-11-01T04:00:00.000Z (example for edt beginning
     * of day)
     */
    public static String getEndUTCDateTimeFromLocalDate(String date) {
        Calendar localDate = getCalendarDateFromFormattedString(date);
        zeroTimeForDate(localDate);
        localDate.add(Calendar.DATE, 1);
        String utcDate = getUTCFromLocalDate(localDate.getTime());
        return utcDate;
    }

    /**
     * add "st" to 1 or "nd" to 2 etc.
     *
     * @return 1st or 2nd etc.
     */
    private static String getNumberWithSuffix(int n) {
        return n + getNumberSuffix(n);
    }

    /**
     * add "st" to 1 or "nd" to 2 etc.
     *
     * @return st or nd etc.
     */
    private static String getNumberSuffix(int n) {
        if (n >= 1 && n <= 31) {
            if (n >= 11 && n <= 13) {
                return "th";
            }
            switch (n % 10) {
                case 1:
                    return "st";
                case 2:
                    return "nd";
                case 3:
                    return "rd";
                default:
                    return "th";
            }
        } else {
            return "";
        }
    }

    /**
     * returns the number of days between specified date and current date (today)
     *
     * @param startDate - full formatted date: i.e.: 2013-10-31T04:00:00.000Z
     * @return - number of days between current day and specified day or -1000 if startDate is null
     */
    public static int getDaysBetweenDateAndToday(String startDate) {
        int numDays = -1000;
        if (startDate != null) {
            Calendar today = Calendar.getInstance();
            String endDate = getFormattedDate(today.getTime());
            numDays = getNumDaysBetweenDates(startDate, endDate);
        }
        return numDays;
    }

    /**
     * returns the number of days between specified date and current date (today)
     *
     * @param startDate - full formatted date: i.e.: 2013-10-31T04:00:00.000Z
     * @return - number of months between current day and specified day or -1000 if startDate is
     * null
     */
    public static int getMonthsBetweenDateAndToday(String startDate) {
        int numMonths = -1000;
        if (startDate != null) {
            Calendar today = Calendar.getInstance();
            String endDate = getFormattedDate(today.getTime());
            numMonths = getNumMonthsBetweenDates(startDate, endDate);
        }
        return numMonths;
    }

    public static int getDaysBetweenDateAndToday(Date startDate) {
        Calendar today = Calendar.getInstance();
        return getNumDaysBetweenDates(startDate, today.getTime());
    }

    public static int getMonthsBetweenDateAndToday(Calendar calendar) {
        Calendar today = Calendar.getInstance();
        return getNumMonthsBetweenDates(calendar, today);
    }

    /**
     * Get Num days between dates
     *
     * @param startDateString - full ISO 8601 date format i.e: 2013-11-03T00:45:54+00:00
     * @param endDateString   - full ISO 8601 date format i.e: 2013-11-03T00:45:54+00:00
     */
    public static int getNumDaysBetweenDates(String startDateString, String endDateString) {
        startDateString = fixIfEpochDate(startDateString);
        endDateString = fixIfEpochDate(endDateString);
        Date startDate = getCalendarDateFromFormattedString(startDateString).getTime();
        Date endDate = getCalendarDateFromFormattedString(endDateString).getTime();
        return getNumDaysBetweenDates(startDate, endDate);
    }

    /**
     * Get Num months between dates
     *
     * @param startDateString - full ISO 8601 date format i.e: 2013-11-03T00:45:54+00:00
     * @param endDateString   - full ISO 8601 date format i.e: 2013-11-03T00:45:54+00:00
     */
    public static int getNumMonthsBetweenDates(String startDateString, String endDateString) {
        startDateString = fixIfEpochDate(startDateString);
        endDateString = fixIfEpochDate(endDateString);
        Calendar startDate = getCalendarDateFromFormattedString(startDateString);
        Calendar endDate = getCalendarDateFromFormattedString(endDateString);
        return getNumMonthsBetweenDates(startDate, endDate);
    }

    public static int getNumDaysBetweenDates(Date startDate, Date endDate) {
        return (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static int getNumMonthsBetweenDates(Calendar startDate, Calendar endDate) {
        int diffYear = endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH);
        return diffMonth;
    }

    private static String fixIfEpochDate(String startDate) {
        try {
            long epochDate = Long.parseLong(startDate.trim() + "000");
            Log.e(TAG, "epoch date to convert" + epochDate);
            Date date = new Date(epochDate);
            startDate = getFormattedDate(date);
            Log.e(TAG, "converted epoch date to:" + startDate);
        } catch (NumberFormatException e) {
            Log.w(TAG, "NumberFormat issue ", e);
        }
        return startDate;
    }

    /**
     * Gets a time string
     *
     * @param totalMinutes - number minutes
     * @return - time in format: x Hr(s) y Min
     */
    public static String getHourMinFromMinutes(int totalMinutes) {
        String time = "";
        int minutes = totalMinutes % 60;
        int hours = (totalMinutes - minutes) / 60;
        if (hours == 1) {
            time = hours + " Hr ";
        } else if (hours > 1) {
            time = hours + " Hrs ";
        }
        time += minutes + " Min";
        return time;
    }

    /**
     * Convert special case dates in mm-dd-YYYY to YYYY-mm-dd
     */
    public static String convertDateToIso(String date) {
        String pattern = "(\\d{2})-(\\d{2})-(\\d{4})";

        // Prefixes the Month with a 0, i.e: 8-12-2014 will become 2014-08-12
        // Days are already prefixed with 0
        String singleDigitPattern = "(\\d{1})-(\\d{2})-(\\d{4})";
        String fixedDate = date;
        if (date.matches(singleDigitPattern)) {
            fixedDate = "0" + date;
        }

        if (fixedDate.matches(pattern)) {
            return fixedDate.replaceAll(pattern, "$3-$1-$2");
        }
        return date;
    }
}
