package util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests date validation using expected and actual results.
 *
 * @author Hiya Savaliya, Monit Shah
 */
public class DateTest {

    /** Test case 1: rejects a month greater than twelve. */
    @Test
    public void invalidMonth() {
        Date date = new Date(2026, 13, 10);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();

        assertEquals(expectedOutput, actualOutput);
    }

    /** Test case 2: rejects April thirty-first. */
    @Test
    public void invalidAprilDay() {
        Date date = new Date(2026, 4, 31);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();

        assertEquals(expectedOutput, actualOutput);
    }

    /** Test case 3: rejects February twenty-ninth in a non-leap year. */
    @Test
    public void invalidFebruary() {
        Date date = new Date(2026, 2, 29);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();

        assertEquals(expectedOutput, actualOutput);
    }

    /** Test case 4: rejects day zero. */
    @Test
    public void invalidDay() {
        Date date = new Date(2026, 6, 0);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();

        assertEquals(expectedOutput, actualOutput);
    }

    /** Test case 5: accepts a regular calendar date. */
    @Test
    public void validRegularDate() {
        Date date = new Date(2026, 9, 28);
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();

        assertEquals(expectedOutput, actualOutput);
    }

    /** Test case 6: accepts February twenty-ninth in a leap year. */
    @Test
    public void validLeapDate() {
        Date date = new Date(2028, 2, 29);
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();

        assertEquals(expectedOutput, actualOutput);
    }
}