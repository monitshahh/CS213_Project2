package parking;

import util.List;

/**
 * Manages vehicle subscriptions and distributes notifications.
 *
 * @author Monit Shah
 */
public class Notification implements Subject {

    /** Vehicles subscribed to system notifications. */
    private List<Vehicle> vehicles;

    /**
     * Creates an empty subscription list.
     */
    public Notification() {
        vehicles = new List<Vehicle>();
    }

    /**
     * Subscribes a vehicle if it is not already subscribed.
     *
     * @param vehicle vehicle to subscribe
     */
    @Override
    public void register(Vehicle vehicle) {
        if (!vehicles.contains(vehicle)) {
            vehicles.add(vehicle);
        }
    }

    /**
     * Removes a vehicle from the subscription list.
     *
     * @param vehicle vehicle to unsubscribe
     */
    @Override
    public void unregister(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    /**
     * Sends a message to every subscribed vehicle.
     *
     * @param message notification message
     */
    @Override
    public void notify(String message) {
        for (Vehicle vehicle : vehicles) {
            vehicle.update(message);
        }
    }
}