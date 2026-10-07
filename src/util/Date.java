package util;

import java.util.Calendar;

/**
 * Represents a calendar date used by parking activity timestamps.
 * A date stores a year, month, and day and provides operations
 * for validating and comparing calendar dates.
 *
 * @author Hiya Savaliya & Monit Shah
 */
public class Date implements Comparable<Date> {

    /** Number used for the first leap-year divisibility rule. */
    private static final int QUADRENNIAL = 4;

    /** Number used for the century leap-year divisibility rule. */
    private static final int CENTENNIAL = 100;

    /** Number used for the four-century leap-year divisibility rule. */
    private static final int QUATERCENTENNIAL = 400;

    /** January represented as a one-based month number. */
    private static final int JAN = Calendar.JANUARY + 1;

    /** February represented as a one-based month number. */
    private static final int FEB = Calendar.FEBRUARY + 1;

    /** March represented as a one-based month number. */
    private static final int MAR = Calendar.MARCH + 1;

    /** April represented as a one-based month number. */
    private static final int APR = Calendar.APRIL + 1;

    /** May represented as a one-based month number. */
    private static final int MAY = Calendar.MAY + 1;

    /** June represented as a one-based month number. */
    private static final int JUN = Calendar.JUNE + 1;

    /** July represented as a one-based month number. */
    private static final int JUL = Calendar.JULY + 1;

    /** August represented as a one-based month number. */
    private static final int AUG = Calendar.AUGUST + 1;

    /** September represented as a one-based month number. */
    private static final int SEP = Calendar.SEPTEMBER + 1;

    /** October represented as a one-based month number. */
    private static final int OCT = Calendar.OCTOBER + 1;

    /** November represented as a one-based month number. */
    private static final int NOV = Calendar.NOVEMBER + 1;

    /** December represented as a one-based month number. */
    private static final int DEC = Calendar.DECEMBER + 1;

    /** Number of days in a 31-day month. */
    private static final int DAYS_31 = 31;

    /** Number of days in a 30-day month. */
    private static final int DAYS_30 = 30;

    /** Number of days in February during a non-leap year. */
    private static final int FEB_DAYS = 28;

    /** Number of days in February during a leap year. */
    private static final int FEB_LEAP_DAYS = 29;

    /** Smallest number requiring two digits. */
    private static final int TWO_DIGIT_START = 10;

    /** Year of this date. */
    private int year;

    /** Month of this date. */
    private int month;

    /** Day of this date. */
    private int day;

    /**
     * Creates a date with the specified year, month, and day.
     *
     * @param year the year of the date
     * @param month the month of the date
     * @param day the day of the date
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Determines the maximum number of days allowed for this date's month.
     *
     * @return number of days in the month, or 0 for an invalid month
     */
    private int daysAllowedInMonth() {
        if (month == FEB) {
            return isLeapYear() ? FEB_LEAP_DAYS : FEB_DAYS;
        }

        if (month == APR || month == JUN || month == SEP || month == NOV) {
            return DAYS_30;
        }

        if (month >= JAN && month <= DEC) {
            return DAYS_31;
        }

        return 0;
    }

    /**
     * Determines whether this date's year is a leap year.
     *
     * @return true if the year is a leap year; false otherwise
     */
    private boolean isLeapYear() {
        if (year % QUADRENNIAL != 0) {
            return false;
        }

        if (year % CENTENNIAL != 0) {
            return true;
        }

        return year % QUATERCENTENNIAL == 0;
    }

    /**
     * Converts a single-digit number to a two-character representation.
     *
     * @param value number to format
     * @return two-character representation of the number
     */
    private String twoDigitNumber(int value) {
        if (value < TWO_DIGIT_START) {
            return "0" + value;
        }

        return "" + value;
    }

    /**
     * Determines whether this date represents a valid calendar date.
     *
     * @return true if the date is valid; false otherwise
     */
    public boolean isValid() {
        if (month < JAN || month > DEC || day < 1) {
            return false;
        }

        int maximumDay = daysAllowedInMonth();

        return day <= maximumDay;
    }

    /**
     * Returns the year of this date.
     *
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the month of this date.
     *
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns the day of this date.
     *
     * @return day
     */
    public int getDay() {
        return day;
    }

    /**
     * Compares this date with another date chronologically.
     *
     * @param otherDate the date to compare with
     * @return -1 if this date is earlier, 1 if this date is later,
     *         or 0 if the dates are equal
     */
    @Override
    public int compareTo(Date otherDate) {
        if (year < otherDate.year) {
            return -1;
        }

        if (year > otherDate.year) {
            return 1;
        }

        if (month < otherDate.month) {
            return -1;
        }

        if (month > otherDate.month) {
            return 1;
        }

        if (day < otherDate.day) {
            return -1;
        }

        if (day > otherDate.day) {
            return 1;
        }

        return 0;
    }

    /**
     * Determines whether another object represents the same date.
     *
     * @param object the object to compare with this date
     * @return true if both objects represent the same date; false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) object;

        return year == otherDate.year
                && month == otherDate.month
                && day == otherDate.day;
    }

    /**
     * Returns this date in YYYY-MM-DD format.
     *
     * @return formatted date
     */
    @Override
    public String toString() {
        return year + "-"
                + twoDigitNumber(month) + "-"
                + twoDigitNumber(day);
    }

    /**
     * Testbed for the Date class.
     * Executes four invalid-date tests and two valid-date tests.
     *
     * @param args command-line arguments; not used
     */
    public static void main(String[] args) {
        Date invalidMonth = new Date(2026, 13, 10);
        System.out.println("Test 1: " + invalidMonth
                + " is valid: " + invalidMonth.isValid());

        Date invalidAprilDay = new Date(2026, 4, 31);
        System.out.println("Test 2: " + invalidAprilDay
                + " is valid: " + invalidAprilDay.isValid());

        Date invalidFebruary = new Date(2026, 2, 29);
        System.out.println("Test 3: " + invalidFebruary
                + " is valid: " + invalidFebruary.isValid());

        Date invalidDay = new Date(2026, 6, 0);
        System.out.println("Test 4: " + invalidDay
                + " is valid: " + invalidDay.isValid());

        Date validRegularDate = new Date(2026, 9, 28);
        System.out.println("Test 5: " + validRegularDate
                + " is valid: " + validRegularDate.isValid());

        Date validLeapDate = new Date(2028, 2, 29);
        System.out.println("Test 6: " + validLeapDate
                + " is valid: " + validLeapDate.isValid());
    }
}