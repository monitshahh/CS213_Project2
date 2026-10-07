package parking;

import util.List;
import util.Sort;

/**
 * Manages registered vehicles using the generic list.
 *
 * @author Hiya Savaliya, Monit Shah
 */
public class VehicleList extends List<Vehicle> {

    /**
     * Creates an empty list of registered vehicles.
     */
    public VehicleList() {
        super();
    }

    /**
     * Returns the registered vehicle matching the given vehicle.
     *
     * @param vehicle vehicle to search for
     * @return matching registered vehicle, or null if absent
     */
    public Vehicle getVehicle(Vehicle vehicle) {
        int vehicleIndex = indexOf(vehicle);

        if (vehicleIndex < 0) {
            return null;
        }

        return get(vehicleIndex);
    }

    /**
     * Prints registered vehicles in ascending license plate order.
     */
    public void printByPlate() {
        if (isEmpty()) {
            System.out.println(
                    "Vehicle list is empty - no vehicle is registered.");
            return;
        }

        Sort.byPlate(this);

        System.out.println(
                "** List of registered vehicles, "
                        + "ordered by license plate **");

        for (Vehicle vehicle : this) {
            System.out.println(vehicle);
        }

        System.out.println("** end of list **");
    }

    /**
     * Prints parking history ordered by plate and entry timestamp.
     */
    public void printHistory() {
        if (isEmpty()) {
            System.out.println(
                    "Deck list is empty - no deck is open.");
            return;
        }

        Sort.byPlate(this);

        System.out.println(
                "** Parking history for all vehicles, "
                        + "ordered by plate/timestamp **");

        for (Vehicle vehicle : this) {
            if (vehicle.hasHistory()) {
                System.out.println(vehicle);
                Sort.byHistoryTimestamp(vehicle.getHistory());

                for (History record : vehicle.getHistory()) {
                    System.out.println(record);
                }
            }
        }

        System.out.println("** end of parking history **");
    }

    /**
     * Prints registered vehicles and their received notifications,
     * ordered by license plate.
     */
    public void printNotifications() {
        Sort.byPlate(this);

        System.out.println("** Notifications, ordered by plate **");

        for (Vehicle vehicle : this) {
            System.out.println(vehicle);

            if (!vehicle.getNotifications().isEmpty()) {
                System.out.println("    ** notifications ** ");

                for (String message : vehicle.getNotifications()) {
                    System.out.println("    " + message);
                }
            }
        }

        System.out.println("** end of list **");
    }

}