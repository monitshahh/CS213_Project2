package util;
import parking.Vehicle;
import parking.Deck;
import parking.Parking;
import parking.History;

/**
 * Provides static methods for sorting lists of objects.
 *
 * @author Monit Shah
 */
public class Sort {

    /**
     * Sorts vehicles in ascending order by license plate.
     *
     * @param vehicles list of vehicles to sort
     */
    public static void byPlate(List<Vehicle> vehicles) {
        for (int current = 0; current < vehicles.size() - 1;
             current++) {
            int smallest = current;

            for (int search = current + 1;
                 search < vehicles.size(); search++) {
                String searchPlate = vehicles.get(search).getPlate();
                String smallestPlate =
                        vehicles.get(smallest).getPlate();

                if (searchPlate.compareToIgnoreCase(smallestPlate) < 0) {
                    smallest = search;
                }
            }

            if (smallest != current) {
                Vehicle temporaryVehicle = vehicles.get(current);
                vehicles.set(current, vehicles.get(smallest));
                vehicles.set(smallest, temporaryVehicle);
            }
        }
    }
    /**
     * Sorts decks by county and then by deck number.
     *
     * @param decks list of decks to sort
     */
    public static void byLocation(List<Deck> decks) {
        for (int current = 1; current < decks.size(); current++) {
            Deck selected = decks.get(current);
            int previous = current - 1;

            while (previous >= 0) {
                Deck preceding = decks.get(previous);
                int comparison = preceding.getLocation().getCounty()
                        .compareToIgnoreCase(
                                selected.getLocation().getCounty());

                if (comparison < 0 || (comparison == 0
                        && preceding.getNumber() <= selected.getNumber())) {
                    break;
                }

                decks.set(previous + 1, preceding);
                previous--;
            }

            decks.set(previous + 1, selected);
        }
    }

    /**
     * Sorts parking activities by vehicle license plate.
     *
     * @param parkings list of parking activities to sort
     */
    public static void byParkingPlate(List<Parking> parkings) {
        for (int current = 1; current < parkings.size(); current++) {
            Parking selected = parkings.get(current);
            int previous = current - 1;

            while (previous >= 0
                    && parkings.get(previous).getVehicle().getPlate()
                    .compareToIgnoreCase(
                            selected.getVehicle().getPlate()) > 0) {
                parkings.set(previous + 1, parkings.get(previous));
                previous--;
            }

            parkings.set(previous + 1, selected);
        }
    }

    /**
     * Sorts parking history by entry timestamp in ascending order.
     *
     * @param history parking records to sort
     */
    public static void byHistoryTimestamp(List<History> history) {
        for (int current = 1; current < history.size(); current++) {
            History selected = history.get(current);
            int previous = current - 1;

            while (previous >= 0
                    && history.get(previous).getParking().getEnter()
                    .compareTo(selected.getParking().getEnter()) > 0) {
                history.set(previous + 1, history.get(previous));
                previous--;
            }

            history.set(previous + 1, selected);
        }
    }

    /**
     * Sorts decks by identification number in ascending order.
     *
     * @param decks list of decks to sort
     */
    public static void byDeckNumber(List<Deck> decks) {
        for (int current = 1; current < decks.size(); current++) {
            Deck selected = decks.get(current);
            int previous = current - 1;

            while (previous >= 0 && decks.get(previous).getNumber() > selected.getNumber()) {
                decks.set(previous + 1, decks.get(previous));
                previous--;
            }

            decks.set(previous + 1, selected);
        }
    }

}