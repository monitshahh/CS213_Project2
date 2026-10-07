package parking;

/**
 * Defines how a user receives a notification.
 *
 * @author Monit Shah
 */

public interface Observer {

    /**
     * Receives a system notification message.
     *
     * @param message notification message
     */
    void update(String message);
}
