package parking;

/**
 * Represents a van with its parking rates.
 *
 * @author Hiya Savaliya & Monit Shah
 */
public class Van extends Vehicle {

    /** Parking charge per started hour. */
    private static final int HOURLY_RATE = 3;

    /** Daily parking charge. */
    private static final int DAILY_RATE = 25;

    /**
     * Creates a van with the specified license plate.
     *
     * @param plate license plate
     */
    public Van(String plate) {
        super(plate);
    }

    /**
     * Calculates this van's parking fee.
     *
     * @param deck parking deck
     * @param enter entering timestamp
     * @param exit exiting timestamp
     * @return total parking fee
     */
    @Override
    public int parkingFee(Deck deck, Timestamp enter, Timestamp exit) {
        return calculateFee(deck, enter, exit, HOURLY_RATE, DAILY_RATE);
    }

    /**
     * Returns the license plate and vehicle type.
     *
     * @return formatted van information
     */
    @Override
    public String toString() {
        return super.toString() + " [Van]";
    }
}