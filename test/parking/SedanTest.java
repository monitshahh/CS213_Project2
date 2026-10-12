package parking;

import util.Date;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests hourly and daily parking fees for sedans.
 *
 * @author Hiya Savaliya, Monit Shah
 */
public class SedanTest {

    /** Test case 1: one hour and one minute costs four dollars. */
    @Test
    public void hourlyParkingFee() {
        Sedan vehicle = new Sedan("S11-AAA");
        Deck deck = new Deck(1, Location.CLARK, Hour.HR5, 6);
        Timestamp enter =
                new Timestamp(new Date(2026, 3, 9), 10, 0);
        Timestamp exit =
                new Timestamp(new Date(2026, 3, 9), 11, 1);
        int expectedOutput = 4;
        int actualOutput = vehicle.parkingFee(deck, enter, exit);

        assertEquals(expectedOutput, actualOutput);
    }

    /** Test case 2: six hours and one minute costs eighteen dollars. */
    @Test
    public void dailyParkingFee() {
        Sedan vehicle = new Sedan("S11-AAA");
        Deck deck = new Deck(1, Location.CLARK, Hour.HR5, 6);
        Timestamp enter =
                new Timestamp(new Date(2026, 3, 9), 10, 0);
        Timestamp exit =
                new Timestamp(new Date(2026, 3, 9), 16, 1);
        int expectedOutput = 18;
        int actualOutput = vehicle.parkingFee(deck, enter, exit);

        assertEquals(expectedOutput, actualOutput);
    }
}