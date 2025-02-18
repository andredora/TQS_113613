import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import euromillions.CuponEuromillions;
import euromillions.Dip;

public class CuponEuromillionsTest {

    @Test
    @DisplayName("Test format output of CuponEuromillions")
    public void testFormat() {
        CuponEuromillions cupon = new CuponEuromillions();

        Dip dip1 = new Dip(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2});
        Dip dip2 = new Dip(new int[]{6, 7, 8, 9, 10}, new int[]{3, 4});

        cupon.addDipToCuppon(dip1);
        cupon.addDipToCuppon(dip2);

        String formatted = cupon.format();

        assertFalse(formatted.isEmpty(), "Formatted output should not be empty");
        assertTrue(formatted.contains("Dip #1"), "Formatted output should contain Dip #1");
        assertTrue(formatted.contains("Dip #2"), "Formatted output should contain Dip #2");

        for (int num : dip1.getNumbersColl()) {
            assertTrue(formatted.contains(String.format("%3d", num)), "Formatted output should contain number " + num);
        }
        for (int num : dip2.getNumbersColl()) {
            assertTrue(formatted.contains(String.format("%3d", num)), "Formatted output should contain number " + num);
        }

        for (int star : dip1.getStarsColl()) {
            assertTrue(formatted.contains(String.format("%3d", star)), "Formatted output should contain star " + star);
        }
        for (int star : dip2.getStarsColl()) {
            assertTrue(formatted.contains(String.format("%3d", star)), "Formatted output should contain star " + star);
        }
    }
}
