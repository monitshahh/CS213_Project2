package parking;

/**
 * Stores one completed parking activity and its deck number.
 *
 * @author Hiya Savaliya, Monit Shah
 */
public class History {

    /** Completed parking activity. */
    private Parking parking;

    /** Identification number of the deck used. */
    private int deckNumber;

    /**
     * Creates a completed parking-history record.
     *
     * @param parking completed parking activity
     * @param deckNumber identification number of the deck
     */
    public History(Parking parking, int deckNumber) {
        this.parking = parking;
        this.deckNumber = deckNumber;
    }

    /**
     * Returns the completed parking activity.
     *
     * @return parking activity
     */
    public Parking getParking() {
        return parking;
    }

    /**
     * Returns the formatted parking-history record.
     *
     * @return entry deck, entry timestamp, and exit timestamp
     */
    @Override
    public String toString() {
        return "     [entered Deck# " + deckNumber
                + ": " + parking.getEnter()
                + "][exited: " + parking.getExit() + "]";
    }
}