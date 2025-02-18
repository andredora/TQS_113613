/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package euromillions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import sets.SetOfNumbers;

/**
 *
 * @author ico0
 */
public class DipTest {

    private Dip dip;

    @BeforeEach
    public void setUp() {
        dip = new Dip(new int[] { 10, 20, 30, 40, 50 }, new int[] { 1, 2 });
    }

    @AfterEach
    public void tearDown() {
        dip = null;
    }

    @Test
    public void testConstructorFromBadArrays() {
        // todo: instantiating a dip passing invalid arrays should raise an exception
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[] { 10, 20, 30, 40 }, new int[] { 1, 2 }));
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[] { 10, 20, 30, 40, 50, 60 }, new int[] { 1, 2 }));
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[] { 10, 20, 30, 40, 50 }, new int[] { 1 }));
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[] { 10, 20, 30, 40, 50 }, new int[] { 1, 2, 3 }));
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[] { 10, 20, 30, 40, 40 }, new int[] { 1, 2 }));
    }

    @Test
    @DisplayName("pretty format of a dip")
    public void testPrettyFormat() {

        // note: correct the implementation, not the test ;)
        String result = dip.format();
        assertEquals("N[ 10 20 30 40 50] S[  1  2]", result, "format as string: formatted string not as expected. ");
    }

    @Test
    @DisplayName("Test multiple cases for Dip.equals")
    public void testEquals() {
        Dip dipA = new Dip(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2});
        Dip dipB = new Dip(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2});
        Dip dipC = new Dip(new int[]{6, 7, 8, 9, 10}, new int[]{1, 2});
        Dip dipD = new Dip(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4});

        assertEquals(dipA, dipB, "Dips with the same numbers and stars should be equal");
        assertNotEquals(dipA, dipC, "Dips with different numbers should not be equal");
        assertNotEquals(dipA, dipD, "Dips with different stars should not be equal");
        assertNotEquals(dipA, null, "A Dip should not be equal to null");
        assertNotEquals(dipA, "some string", "A Dip should not be equal to an object of a different class");
        assertEquals(dipA, dipA, "A Dip should be equal to itself");
    }
}
