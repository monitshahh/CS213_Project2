package parking;
import util.List;
import java.util.Calendar;

/**
 * Represents a registered vehicle in the parking management system.
 * Stores its license plate, parking history, and notification messages
 *
 * @author Hiya Savaliya & Monit Shah
 */
public abstract class Vehicle implements Observer {

    /** License plate that uniquely identifies this vehicle. */
    private String plate;

    /** Completed parking activities for this vehicle. */
    private List<History> history;

    /** System notification messages received by this vehicle. */
    private List<String> notifications;

    /** Number of minutes in one hour. */
    private static final int MINUTES_PER_HOUR = 60;

    /** Maximum parking hours charged at the hourly rate. */
    private static final int MAX_HOURLY_HOURS = 6;

    /** Converts month numbers 1–12 to Calendar's range 0–11. */
    private static final int CALENDAR_MONTH_OFFSET = 1;

    /** Duration above which the daily rate applies. */
    private static final int DAILY_RATE_THRESHOLD = MAX_HOURLY_HOURS * MINUTES_PER_HOUR;

    /**
     * Creates a vehicle with the specified license plate.
     *
     * @param plate license plate of the vehicle
     */
    public Vehicle(String plate) {
        this.plate = plate;
        this.history = new List<History>();
        notifications = new List<String>();
    }

    /**
     * Returns the license plate of this vehicle.
     *
     * @return license plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Appends a completed parking activity to the history list.
     *
     * @param parking completed parking activity
     * @param deck deck where the activity occurred
     */
    public void addParking(Parking parking, Deck deck) {
        history.add(new History(parking, deck.getNumber()));
    }

    /**
     * Determines whether this vehicle has parking history.
     *
     * @return true if parking history exists; false otherwise
     */
    public boolean hasHistory() {
        return !history.isEmpty();
    }

    /**
     * Returns this vehicle's completed parking history.
     *
     * @return list of completed parking records
     */
    public List<History> getHistory() {
        return history;
    }

    /**
     * Stores a received system notification.
     *
     * @param message notification message
     */
    @Override
    public void update(String message) {
        notifications.add(message);
    }

    /**
     * Returns the notification messages received by this vehicle.
     *
     * @return list of received messages
     */
    public List<String> getNotifications() {
        return notifications;
    }

    /**
     * Calculates the parking fee for this vehicle type.
     *
     * @param deck parking deck
     * @param enter entering timestamp
     * @param exit exiting timestamp
     * @return total parking fee
     */
    public abstract int parkingFee(Deck deck, Timestamp enter, Timestamp exit);

    /**
     * Calculates the parking fee using the specified vehicle rates.
     *
     * @param deck deck containing the parking activity
     * @param enter entering timestamp
     * @param exit exiting timestamp
     * @param hourlyRate charge per started hour
     * @param dailyRate charge per day
     * @return total parking fee
     */
    protected int calculateFee(Deck deck, Timestamp enter, Timestamp exit,
                               int hourlyRate, int dailyRate) {
        int days = daysBetween(enter, exit);
        int exitMinutes = exit.getHour() * MINUTES_PER_HOUR
                + exit.getMinute();

        if (days == 0) {
            int enterMinutes = enter.getHour() * MINUTES_PER_HOUR
                    + enter.getMinute();

            return dailyFee(exitMinutes - enterMinutes,
                    hourlyRate, dailyRate);
        }

        int lastDayMinutes = exitMinutes
                - deck.getHour().getOpeningMinutes();

        return days * dailyRate
                + dailyFee(lastDayMinutes, hourlyRate, dailyRate);
    }

    /**
     * Calculates the charge for a duration within one day.
     *
     * @param minutes parking duration in minutes
     * @param hourlyRate charge per started hour
     * @param dailyRate charge when the duration exceeds six hours
     * @return charge for the duration
     */
    private int dailyFee(int minutes, int hourlyRate, int dailyRate) {
        if (minutes > DAILY_RATE_THRESHOLD) {
            return dailyRate;
        }

        int hours = minutes / MINUTES_PER_HOUR;

        if (minutes % MINUTES_PER_HOUR != 0) {
            hours++;
        }

        return hours * hourlyRate;
    }

    /**
     * Counts calendar days between two valid parking timestamps.
     *
     * @param enter entering timestamp
     * @param exit exiting timestamp
     * @return difference between their calendar dates
     */
    private int daysBetween(Timestamp enter, Timestamp exit) {
        Calendar current = Calendar.getInstance();
        current.clear();
        current.set(enter.getDate().getYear(),
                enter.getDate().getMonth() - CALENDAR_MONTH_OFFSET,
                enter.getDate().getDay());

        Calendar end = Calendar.getInstance();
        end.clear();
        end.set(exit.getDate().getYear(),
                exit.getDate().getMonth() - CALENDAR_MONTH_OFFSET,
                exit.getDate().getDay());

        int days = 0;

        while (current.before(end)) {
            current.add(Calendar.DAY_OF_MONTH, 1);
            days++;
        }

        return days;
    }

    /**
     * Determines whether this vehicle has the same
     * license plate as another vehicle.
     *
     * @param object object to compare
     * @return true if the license plates are equal;
     *         false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof Vehicle)) {
            return false;
        }

        Vehicle otherVehicle = (Vehicle) object;

        return plate.equalsIgnoreCase(otherVehicle.plate);
    }

    /**
     * Returns the license plate of this vehicle.
     *
     * @return license plate
     */
    @Override
    public String toString() {
        return plate;
    }
}