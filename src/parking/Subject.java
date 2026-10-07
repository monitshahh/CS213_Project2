package parking;

/**
 * Defines subscription and notification operations.
 *
 * @author Monit Shah
 */
public interface Subject {

    /**
     * Subscribes a vehicle to notifications.
     *
     * @param vehicle vehicle to subscribe
     */
    void register(Vehicle vehicle);

    /**
     * Unsubscribes a vehicle from notifications.
     *
     * @param vehicle vehicle to unsubscribe
     */
    void unregister(Vehicle vehicle);

    /**
     * Sends a message to subscribed vehicles.
     *
     * @param message notification message
     */
    void notify(String message);
}