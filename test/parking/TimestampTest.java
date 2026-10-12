package parking;

import util.Date;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests timestamp comparisons using expected and actual results.
 *
 * @author Hiya Savaliya, Monit Shah
 */
public class TimestampTest {

    /** Test case 1: an earlier year returns negative one. */
    @Test
    public void earlierYear() {
        Timestamp first =
                new Timestamp(new Date(2025, 9, 15), 10, 30);
        Timestamp second =
                new Timestamp(new Date(2026, 9, 15), 10, 30);
        int expectedOutput = -1;
        int actualOutput = first.compareTo(second);

        assertEquals(expectedOutput, actualOutput);
    }

    /** Test case 2: an earlier month returns negative one. */
    @Test
    public void earlierMonth() {
        Timestamp first =
                new Timestamp(new Date(2026, 8, 15), 10, 30);
        Timestamp second =
                new Timestamp(new Date(2026, 9, 15), 10, 30);
        int expectedOutput = -1;
        int actualOutput = first.compareTo(second);

        assertEquals(expectedOutput, actualOutput);
    }

    /** Test case 3: an earlier time returns negative one. */
    @Test
    public void earlierTime() {
        Timestamp first =
                new Timestamp(new Date(2026, 9, 15), 9, 45);
        Timestamp second =
                new Timestamp(new Date(2026, 9, 15), 10, 30);
        int expectedOutput = -1;
        int actualOutput = first.compareTo(second);

        assertEquals(expectedOutput, actualOutput);
    }

    /** Test case 4: a later year returns positive one. */
    @Test
    public void laterYear() {
        Timestamp first =
                new Timestamp(new Date(2027, 9, 15), 10, 30);
        Timestamp second =
                new Timestamp(new Date(2026, 9, 15), 10, 30);
        int expectedOutput = 1;
        int actualOutput = first.compareTo(second);

        assertEquals(expectedOutput, actualOutput);
    }

    /** Test case 5: a later day returns positive one. */
    @Test
    public void laterDay() {
        Timestamp first =
                new Timestamp(new Date(2026, 9, 16), 10, 30);
        Timestamp second =
                new Timestamp(new Date(2026, 9, 15), 10, 30);
        int expectedOutput = 1;
        int actualOutput = first.compareTo(second);

        assertEquals(expectedOutput, actualOutput);
    }

    /** Test case 6: a later minute returns positive one. */
    @Test
    public void laterMinute() {
        Timestamp first =
                new Timestamp(new Date(2026, 9, 15), 10, 45);
        Timestamp second =
                new Timestamp(new Date(2026, 9, 15), 10, 30);
        int expectedOutput = 1;
        int actualOutput = first.compareTo(second);

        assertEquals(expectedOutput, actualOutput);
    }

    /** Test case 7: identical timestamps return zero. */
    @Test
    public void equalTimestamps() {
        Timestamp first =
                new Timestamp(new Date(2026, 9, 15), 10, 30);
        Timestamp second =
                new Timestamp(new Date(2026, 9, 15), 10, 30);
        int expectedOutput = 0;
        int actualOutput = first.compareTo(second);

        assertEquals(expectedOutput, actualOutput);
    }
}