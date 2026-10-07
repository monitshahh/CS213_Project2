package parking;

import util.Date;

/**
 * Represents the date and time associated with a parking activity.
 * A timestamp contains a calendar date, hour, and minute and can
 * be compared chronologically with another timestamp.
 *
 * @author Hiya Savaliya & Monit Shah
 */
public class Timestamp implements Comparable<Timestamp> {

    /** Minimum valid hour in 24-hour time. */
    private static final int MIN_HOUR = 0;

    /** Maximum valid hour in 24-hour time. */
    private static final int MAX_HOUR = 23;

    /** Minimum valid minute. */
    private static final int MIN_MINUTE = 0;

    /** Maximum valid minute. */
    private static final int MAX_MINUTE = 59;

    /** Smallest number requiring two digits. */
    private static final int TWO_DIGIT_START = 10;

    /** Calendar date associated with this timestamp. */
    private Date date;

    /** Hour associated with this timestamp. */
    private byte hour;

    /** Minute associated with this timestamp. */
    private byte minute;

    /**
     * Creates a timestamp with the specified date, hour, and minute.
     *
     * @param date calendar date of the timestamp
     * @param hour hour in 24-hour format
     * @param minute minute of the hour
     */
    public Timestamp(Date date, int hour, int minute) {
        this.date = date;
        this.hour = (byte) hour;
        this.minute = (byte) minute;
    }

    /**
     * Converts a single-digit value to a two-character representation.
     *
     * @param value value to format
     * @return two-character representation
     */
    private String twoDigitNumber(int value) {
        if (value < TWO_DIGIT_START) {
            return "0" + value;
        }

        return "" + value;
    }

    /**
     * Determines whether the time portion of this timestamp is valid.
     *
     * @return true if the hour and minute are valid; false otherwise
     */
    public boolean isValidTime() {
        return hour >= MIN_HOUR
                && hour <= MAX_HOUR
                && minute >= MIN_MINUTE
                && minute <= MAX_MINUTE;
    }

    /**
     * Returns the date associated with this timestamp.
     *
     * @return timestamp date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the hour associated with this timestamp.
     *
     * @return hour in 24-hour format
     */
    public int getHour() {
        return hour;
    }

    /**
     * Returns the minute associated with this timestamp.
     *
     * @return minute of the hour
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Compares this timestamp chronologically with another timestamp.
     *
     * @param otherTimestamp timestamp to compare with
     * @return -1 if this timestamp is earlier, 1 if this timestamp is later,
     *         or 0 if both timestamps are equal
     */
    @Override
    public int compareTo(Timestamp otherTimestamp) {
        int dateComparison = date.compareTo(otherTimestamp.date);

        if (dateComparison < 0) {
            return -1;
        }

        if (dateComparison > 0) {
            return 1;
        }

        if (hour < otherTimestamp.hour) {
            return -1;
        }

        if (hour > otherTimestamp.hour) {
            return 1;
        }

        if (minute < otherTimestamp.minute) {
            return -1;
        }

        if (minute > otherTimestamp.minute) {
            return 1;
        }

        return 0;
    }

    /**
     * Returns this timestamp in YYYY-MM-DD HH:MM format.
     *
     * @return formatted timestamp
     */
    @Override
    public String toString() {
        return date + " "
                + twoDigitNumber(hour) + ":"
                + twoDigitNumber(minute);
    }

    /**
     * Testbed for the Timestamp compareTo() method.
     * Includes three tests returning -1, three tests returning 1,
     * and one test returning 0.
     *
     * @param args command-line arguments; not used
     */
    public static void main(String[] args) {
        Timestamp earlierYear = new Timestamp(new Date(2025, 9, 15), 10, 30);
        Timestamp laterYear = new Timestamp(new Date(2026, 9, 15), 10, 30);
        System.out.println("Test 1: " + earlierYear.compareTo(laterYear));

        Timestamp earlierMonth = new Timestamp(new Date(2026, 8, 15), 10, 30);
        Timestamp laterMonth = new Timestamp(new Date(2026, 9, 15), 10, 30);
        System.out.println("Test 2: " + earlierMonth.compareTo(laterMonth));

        Timestamp earlierTime = new Timestamp(new Date(2026, 9, 15), 9, 45);
        Timestamp laterTime = new Timestamp(new Date(2026, 9, 15), 10, 30);
        System.out.println("Test 3: " + earlierTime.compareTo(laterTime));

        Timestamp newerYear = new Timestamp(new Date(2027, 9, 15), 10, 30);
        Timestamp olderYear = new Timestamp(new Date(2026, 9, 15), 10, 30);
        System.out.println("Test 4: " + newerYear.compareTo(olderYear));

        Timestamp laterDay = new Timestamp(new Date(2026, 9, 16), 10, 30);
        Timestamp earlierDay = new Timestamp(new Date(2026, 9, 15), 10, 30);
        System.out.println("Test 5: " + laterDay.compareTo(earlierDay));

        Timestamp laterMinute = new Timestamp(new Date(2026, 9, 15), 10, 45);
        Timestamp earlierMinute =
                new Timestamp(new Date(2026, 9, 15), 10, 30);
        System.out.println("Test 6: " + laterMinute.compareTo(earlierMinute));

        Timestamp sameTimeOne = new Timestamp(new Date(2026, 9, 15), 10, 30);
        Timestamp sameTimeTwo = new Timestamp(new Date(2026, 9, 15), 10, 30);
        System.out.println("Test 7: " + sameTimeOne.compareTo(sameTimeTwo));
    }
}