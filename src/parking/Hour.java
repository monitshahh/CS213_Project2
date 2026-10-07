package parking;

/**
 * Represents the supported operating-hour schedules
 * for parking decks.
 *
 * @author Hiya Savaliya
 */
public enum Hour {

    /**
     * Operating schedule from 5:00 to 21:30.
     */
    HR5(5, 0, 21, 30),

    /**
     * Operating schedule from 6:30 to 18:30.
     */
    HR6(6, 30, 18, 30),

    /**
     * Operating schedule from 7:00 to 18:00.
     */
    HR7(7, 0, 18, 0);

    /**
     * Number of minutes in one hour.
     */
    private static final int MINUTES_PER_HOUR = 60;

    /**
     * Smallest number requiring two digits.
     */
    private static final int TWO_DIGIT_START = 10;

    /**
     * Opening hour of this operating schedule.
     */
    private final int openingHour;

    /**
     * Opening minute of this operating schedule.
     */
    private final int openingMinute;

    /**
     * Closing hour of this operating schedule.
     */
    private final int closingHour;

    /**
     * Closing minute of this operating schedule.
     */
    private final int closingMinute;

    /**
     * Creates an operating-hour schedule.
     *
     * @param openingHour   opening hour
     * @param openingMinute opening minute
     * @param closingHour   closing hour
     * @param closingMinute closing minute
     */
    Hour(int openingHour, int openingMinute,
         int closingHour, int closingMinute) {

        this.openingHour = openingHour;
        this.openingMinute = openingMinute;
        this.closingHour = closingHour;
        this.closingMinute = closingMinute;
    }

    /**
     * Formats an hour and minute for display.
     *
     * @param hour   hour value
     * @param minute minute value
     * @return formatted time
     */
    private String formatTime(int hour, int minute) {
        String minuteText;

        if (minute < TWO_DIGIT_START) {
            minuteText = "0" + minute;
        } else {
            minuteText = "" + minute;
        }

        return hour + ":" + minuteText;
    }

    /**
     * Finds an operating-hour schedule from its code.
     * The code comparison is not case-sensitive.
     *
     * @param hourCode operating-hour code
     * @return matching Hour value, or null if the code is invalid
     */
    public static Hour findHour(String hourCode) {
        if (hourCode == null) {
            return null;
        }

        for (Hour currentHour : Hour.values()) {
            if (currentHour.name().equalsIgnoreCase(hourCode)) {
                return currentHour;
            }
        }

        return null;
    }

    /**
     * Determines whether a timestamp occurs within
     * this operating-hour schedule.
     *
     * @param timestamp timestamp to check
     * @return true if the time is within operating hours;
     * false otherwise
     */
    public boolean isWithinOperatingHours(Timestamp timestamp) {
        int activityMinutes =
                timestamp.getHour() * MINUTES_PER_HOUR
                        + timestamp.getMinute();

        int openingMinutes =
                openingHour * MINUTES_PER_HOUR
                        + openingMinute;

        int closingMinutes =
                closingHour * MINUTES_PER_HOUR
                        + closingMinute;

        return activityMinutes >= openingMinutes
                && activityMinutes <= closingMinutes;
    }

    /**
     * Returns the opening time as minutes after midnight.
     *
     * @return opening time in minutes
     */
    public int getOpeningMinutes() {
        return openingHour * MINUTES_PER_HOUR + openingMinute;
    }

    /**
     * Returns the operating hours in display format.
     *
     * @return formatted operating-hour range
     */
    @Override
    public String toString() {
        return formatTime(openingHour, openingMinute)
                + " ~ "
                + formatTime(closingHour, closingMinute);
    }
}
