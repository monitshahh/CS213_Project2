package parking;

/**
 * Represents a sedan with its parking rates.
 *
 * @author Hiya Savaliya & Monit Shah
 */
public class Sedan extends Vehicle {

    /** Parking charge per started hour. */
    private static final int HOURLY_RATE = 2;

    /** Daily parking charge. */
    private static final int DAILY_RATE = 18;

    /**
     * Creates a sedan with the specified license plate.
     *
     * @param plate license plate
     */
    public Sedan(String plate) {
        super(plate);
    }

    /**
     * Calculates this sedan's parking fee.
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
     * @return formatted sedan information
     */
    @Override
    public String toString() {
        return super.toString() + " [Sedan]";
    }
}