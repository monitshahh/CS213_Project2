package parking;
import util.List;

/**
 * Represents a parking deck in the parking management system.
 * A deck maintains its location, operating hours, parking
 * activities, capacity, and operating status.
 *
 * @author Hiya Savaliya, Monit Shah
 */
public class Deck {

    /** Value returned when a vehicle is not found in the deck. */
    private static final int NOTFOUND = -1;

    /** Maximum capacity allowed for any parking deck. */
    public static final int MAXCAPACITY = 6;

    /** Identification number of this deck. */
    private int number;

    /** Location of this deck. */
    private Location location;

    /** Operating-hour schedule of this deck. */
    private Hour hour;

    /** Array containing current parking activities. */
    private List<Parking> parkings;

    /** Number of vehicles currently parked in this deck. */
    private int numParked;

    /** Operating status of this deck. */
    private boolean open;

    /** Maximum number of vehicles allowed in this deck. */
    private int capacity;

    /** Total parking fees collected by this deck. */
    private int feesCollected;

    /**
     * Creates an open parking deck with the specified information.
     *
     * @param number deck identification number
     * @param location location of the deck
     * @param hour operating-hour schedule
     * @param capacity maximum number of vehicles allowed
     */
    public Deck(int number, Location location,
                Hour hour, int capacity) {

        this.number = number;
        this.location = location;
        this.hour = hour;
        parkings = new List<Parking>();
        this.capacity = capacity;
        feesCollected = 0;
        this.numParked = 0;
        this.open = true;
    }

    /**
     * Finds the parking activity for a specified vehicle.
     *
     * @param vehicle vehicle to search for
     * @return index of the parking activity,
     *         or NOTFOUND if not found
     */
    private int find(Vehicle vehicle) {
        for (int index = 0; index < parkings.size(); index++) {
            if (parkings.get(index).getVehicle().equals(vehicle)) {
                return index;
            }
        }

        return NOTFOUND;
    }

    /**
     * Adds a parking activity to this deck.
     *
     * @param parking parking activity to add
     */
    public void enter(Parking parking) {
        if (numParked < capacity) {
            parkings.add(parking);
            numParked = parkings.size();
        }
    }

    /**
     * Removes a parking activity from this deck.
     *
     * @param parking parking activity to remove
     */
    public void exit(Parking parking) {
        int parkingIndex = find(parking.getVehicle());

        if (parkingIndex == NOTFOUND) {
            return;
        }

        parkings.remove(parkings.get(parkingIndex));
        numParked = parkings.size();
    }

    /**
     * Returns the parking activity for a specified vehicle.
     *
     * @param vehicle vehicle to search for
     * @return matching parking activity, or null if not found
     */
    public Parking getParking(Vehicle vehicle) {
        int parkingIndex = find(vehicle);

        if (parkingIndex == NOTFOUND) {
            return null;
        }

        return parkings.get(parkingIndex);
    }

    /**
     * Returns a parking activity at the specified position.
     *
     * @param index position in the parking array
     * @return parking activity, or null if the index is invalid
     */
    public Parking getParkingAt(int index) {
        if (index < 0 || index >= numParked) {
            return null;
        }

        return parkings.get(index);
    }

    /**
     * Swaps two parking activities in the parking array.
     *
     * @param first first array index
     * @param second second array index
     */
    public void swapParking(int first, int second) {
        Parking temporaryParking = parkings.get(first);
        parkings.set(first, parkings.get(second));
        parkings.set(second, temporaryParking);
    }

    /**
     * Determines whether a specified vehicle is currently
     * parked in this deck.
     *
     * @param vehicle vehicle to search for
     * @return true if the vehicle is currently parked;
     *         false otherwise
     */
    public boolean contains(Vehicle vehicle) {
        return find(vehicle) != NOTFOUND;
    }

    /**
     * Returns the deck identification number.
     *
     * @return deck number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns the location of this deck.
     *
     * @return deck location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns the operating-hour schedule of this deck.
     *
     * @return operating-hour schedule
     */
    public Hour getHour() {
        return hour;
    }

    /**
     * Returns the maximum parking capacity of this deck.
     *
     * @return deck capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the number of vehicles currently parked.
     *
     * @return number of parked vehicles
     */
    public int getNumParked() {
        return numParked;
    }

    /**
     * Determines whether this deck is currently open.
     *
     * @return true if open; false otherwise
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Marks this deck as open.
     */
    public void openDeck() {
        open = true;
    }

    /**
     * Marks this deck as closed.
     */
    public void closeDeck() {
        open = false;
    }

    /**
     * Determines whether this deck has reached
     * its maximum parking capacity.
     *
     * @return true if full; false otherwise
     */
    public boolean isFull() {
        return numParked >= capacity;
    }

    /**
     * Adds a parking fee to this deck's collected total.
     *
     * @param fee parking fee collected
     */
    public void collectFee(int fee) {
        feesCollected += fee;
    }

    /**
     * Returns the total parking fees collected.
     *
     * @return collected fees
     */
    public int getFeesCollected() {
        return feesCollected;
    }

    /**
     * Determines whether this deck has the same identification
     * number as another deck.
     *
     * @param object object to compare with this deck
     * @return true if the deck numbers are equal;
     *         false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof Deck)) {
            return false;
        }

        Deck otherDeck = (Deck) object;

        return number == otherDeck.number;
    }

    /**
     * Returns a textual representation of this parking deck.
     *
     * @return formatted deck information
     */
    @Override
    public String toString() {
        String status;

        if (open) {
            status = "open";
        } else {
            status = "closed";
        }

        String vehicleText;

        if (numParked == 1) {
            vehicleText = "vehicle";
        } else {
            vehicleText = "vehicles";
        }

        return "Deck#" + number
                + "@" + location
                + "[" + status + " " + hour + "]"
                + " [capacity " + capacity + "]"
                + " [" + numParked + " " + vehicleText + "]"
                + " [" + location.getCounty() + "]";
    }
}