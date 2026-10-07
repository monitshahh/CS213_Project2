package parking;

/**
 * Represents a pickup truck with its parking rates.
 *
 * @author Hiya Savaliya & Monit Shah
 */
public class Pickup extends Vehicle {

    /** Parking charge per started hour. */
    private static final int HOURLY_RATE = 4;

    /** Daily parking charge. */
    private static final int DAILY_RATE = 30;

    /**
     * Creates a pickup truck with the specified license plate.
     *
     * @param plate license plate
     */
    public Pickup(String plate) {
        super(plate);
    }

    /**
     * Calculates this pickup truck's parking fee.
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
     * @return formatted pickup information
     */
    @Override
    public String toString() {
        return super.toString() + " [Pickup]";
    }
}