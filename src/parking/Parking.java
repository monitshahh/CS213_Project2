package parking;

/**
 * Represents one parking activity for a vehicle.
 * A parking activity records the vehicle along with
 * its entry and exit timestamps.
 *
 * @author Hiya Savaliya & Monit Shah
 */
public class Parking {

    /** Vehicle associated with this parking activity. */
    private Vehicle vehicle;

    /** Timestamp when the vehicle entered the deck. */
    private Timestamp enter;

    /** Timestamp when the vehicle exited the deck. */
    private Timestamp exit;

    /**
     * Creates a parking activity when a vehicle enters a deck.
     *
     * @param vehicle vehicle being parked
     * @param enter entering timestamp
     */
    public Parking(Vehicle vehicle, Timestamp enter) {
        this.vehicle = vehicle;
        this.enter = enter;
        this.exit = null;
    }

    /**
     * Returns the vehicle associated with this activity.
     *
     * @return vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Returns the entering timestamp.
     *
     * @return entering timestamp
     */
    public Timestamp getEnter() {
        return enter;
    }

    /**
     * Returns the exiting timestamp.
     *
     * @return exiting timestamp, or null if the vehicle
     *         has not exited
     */
    public Timestamp getExit() {
        return exit;
    }

    /**
     * Records the exiting timestamp.
     *
     * @param exit exiting timestamp
     */
    public void setExit(Timestamp exit) {
        this.exit = exit;
    }

    /**
     * Determines whether this parking activity is completed.
     *
     * @return true if an exit timestamp exists;
     *         false otherwise
     */
    public boolean isCompleted() {
        return exit != null;
    }

    /**
     * Returns the formatted parking activity.
     *
     * @return parking activity as text
     */
    @Override
    public String toString() {
        return vehicle
                + " [entered: " + enter + "]"
                + "[exited: " + exit + "]";
    }
}