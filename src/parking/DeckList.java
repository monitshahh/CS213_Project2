package parking;

import util.List;
import util.Sort;

/**
 * Manages parking decks using the generic list.
 *
 * @author Hiya Savaliya, Monit Shah
 */
public class DeckList extends List<Deck> {

    /**
     * Creates an empty list of parking decks.
     */
    public DeckList() {
        super();
    }

    /**
     * Adds a new deck or reopens an existing deck.
     *
     * @param deck deck to open
     */
    public void open(Deck deck) {
        Deck existingDeck = getDeck(deck);

        if (existingDeck != null) {
            existingDeck.openDeck();
            return;
        }

        add(deck);
    }

    /**
     * Closes an existing deck without removing it.
     *
     * @param deck deck to close
     */
    public void close(Deck deck) {
        Deck existingDeck = getDeck(deck);

        if (existingDeck != null) {
            existingDeck.closeDeck();
        }
    }

    /**
     * Returns the existing deck matching the specified deck.
     *
     * @param deck deck to search for
     * @return matching deck, or null if absent
     */
    public Deck getDeck(Deck deck) {
        int deckIndex = indexOf(deck);

        if (deckIndex < 0) {
            return null;
        }

        return get(deckIndex);
    }

    /**
     * Returns the deck with the specified number.
     *
     * @param deckNumber deck identification number
     * @return matching deck, or null if absent
     */
    public Deck getDeck(int deckNumber) {
        for (Deck deck : this) {
            if (deck.getNumber() == deckNumber) {
                return deck;
            }
        }

        return null;
    }

    /**
     * Finds the deck where the specified vehicle is parked.
     *
     * @param vehicle vehicle to search for
     * @return deck containing the vehicle, or null if absent
     */
    public Deck findVehicleDeck(Vehicle vehicle) {
        for (Deck deck : this) {
            if (deck.contains(vehicle)) {
                return deck;
            }
        }

        return null;
    }

    /**
     * Determines whether any deck is currently open.
     *
     * @return true if at least one deck is open
     */
    private boolean hasOpenDeck() {
        for (Deck deck : this) {
            if (deck.isOpen()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Prints open decks ordered by county and deck number.
     */
    public void printByLocation() {
        if (!hasOpenDeck()) {
            System.out.println(
                    "Deck list is empty - no deck is open.");
            return;
        }

        Sort.byLocation(this);

        System.out.println(
                "** List of decks, ordered by county/deck number **");

        for (Deck deck : this) {
            if (deck.isOpen()) {
                System.out.println(deck);
            }
        }

        System.out.println("** end of list **");
    }

    /**
     * Prints the vehicles in a deck ordered by license plate.
     *
     * @param deck deck whose vehicles will be printed
     */
    public void printVehicles(Deck deck) {
        List<Parking> parkings = new List<Parking>();

        for (int index = 0; index < deck.getNumParked(); index++) {
            Parking parking = deck.getParkingAt(index);

            if (parking != null) {
                parkings.add(parking);
            }
        }

        Sort.byParkingPlate(parkings);

        System.out.println(
                "** List of vehicles in Deck# "
                        + deck.getNumber()
                        + ", ordered by plate **");

        for (Parking parking : parkings) {
            System.out.println(parking);
        }

        System.out.println("** end of list **");
    }

    /**
     * Prints collected parking fees ordered by deck number.
     */
    public void printFees() {
        if (isEmpty()) {
            System.out.println("Deck list is empty - no deck is open.");
            return;
        }

        Sort.byDeckNumber(this);

        System.out.println("** Parking fees, ordered by deck number **");

        for (Deck deck : this) {
            if (deck.getFeesCollected() > 0) {
                System.out.println("Deck#" + deck.getNumber());
                System.out.println("    ** Fees collected: $ "
                        + deck.getFeesCollected());
            }
        }

        System.out.println("** end of list **");
    }
}