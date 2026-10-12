package parking;

import util.Date;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests hourly and daily parking fees for vans.
 *
 * @author Hiya Savaliya, Monit Shah
 */
public class VanTest {

    /** Test case 1: one hour and one minute costs six dollars. */
    @Test
    public void hourlyParkingFee() {
        Van vehicle = new Van("V11-AAA");
        Deck deck = new Deck(1, Location.CLARK, Hour.HR5, 6);
        Timestamp enter =
                new Timestamp(new Date(2026, 3, 9), 10, 0);
        Timestamp exit =
                new Timestamp(new Date(2026, 3, 9), 11, 1);
        int expectedOutput = 6;
        int actualOutput = vehicle.parkingFee(deck, enter, exit);

        assertEquals(expectedOutput, actualOutput);
    }

    /** Test case 2: six hours and one minute costs twenty-five dollars. */
    @Test
    public void dailyParkingFee() {
        Van vehicle = new Van("V11-AAA");
        Deck deck = new Deck(1, Location.CLARK, Hour.HR5, 6);
        Timestamp enter =
                new Timestamp(new Date(2026, 3, 9), 10, 0);
        Timestamp exit =
                new Timestamp(new Date(2026, 3, 9), 16, 1);
        int expectedOutput = 25;
        int actualOutput = vehicle.parkingFee(deck, enter, exit);

        assertEquals(expectedOutput, actualOutput);
    }
}