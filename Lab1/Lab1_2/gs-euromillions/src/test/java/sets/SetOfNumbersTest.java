package sets;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class SetOfNumbersTest {
	private SetOfNumbers setA;
	private SetOfNumbers setB;
	private SetOfNumbers setC;
	private SetOfNumbers setD;

	/**
	 * create some sets for testing
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		setA = new SetOfNumbers();
		setB = SetOfNumbers.fromArray(new int[] { 10, 20, 30, 40, 50, 60 });

		setC = new SetOfNumbers();
		for (int i = 5; i < 50; i++) {
			setC.add(i * 10);
		}
		setD = SetOfNumbers.fromArray(new int[] { 30, 40, 50, 60, 10, 20 });
	}

	@AfterEach
	public void tearDown() throws Exception {
		setA = setB = setC = setD = null;
	}

	@Test
	@DisplayName("add valid numbers to the set")
	public void testAdd() {

		setA.add(99);
		assertTrue(setA.contains(99), "add: added element not found in set.");
		assertEquals(1, setA.size());
		setA.add(11);
		assertTrue(setA.contains(11), "add: added element not found in set.");
		assertEquals(2, setA.size(), "add: elements count not as expected.");
	}

	@Test
	@DisplayName("two non overlaping sets report no intersection")
	public void testIntersectForNoIntersection() {
		assertFalse(setA.intersects(setB), "no intersection was reported as existing");

	}

	@Test
	@DisplayName("two sets with intersection report it")
	public void testIntersectsForIntersectionNotEmpty() {
		assertTrue(setB.intersects(setC), "failed to find existing intersection");
		assertTrue(setD.intersects(setB), "failed to find existing intersection");
	}

	@Test
	public void testContains() {
		assertTrue(setB.contains(10), "contains: expected value not found");
		assertTrue(setB.contains(60), "contains: expected value not found");
		assertFalse(setB.contains(-1), "contains: non existing value reported found");
		assertFalse(setB.contains(90), "contains: non existing value reported found");
	}

	@Test
	public void testNoDuplicates() {
		assertThrows(IllegalArgumentException.class, () -> setB.add(20)); // duplicate, must fail with an exception
	}

	@Test
	@DisplayName("subtract removes elements that exist in both sets")
	public void testSubtract() {
		SetOfNumbers mainSet = SetOfNumbers.fromArray(new int[] { 1, 2, 3, 4, 5 });
		SetOfNumbers subset = SetOfNumbers.fromArray(new int[] { 2, 4 });

		mainSet.subtract(subset);

		assertFalse(mainSet.contains(2), "Element 2 should be removed");
		assertFalse(mainSet.contains(4), "Element 4 should be removed");

		assertTrue(mainSet.contains(1), "Element 1 should still be present");
		assertTrue(mainSet.contains(3), "Element 3 should still be present");
		assertTrue(mainSet.contains(5), "Element 5 should still be present");

		assertEquals(3, mainSet.size(), "Size should be 3 after subtraction");
	}

	@Test
	@DisplayName("Testing equals")
	public void testEqualsSameContent() {
		SetOfNumbers setA = SetOfNumbers.fromArray(new int[] { 1, 2, 3 });
		SetOfNumbers setB = SetOfNumbers.fromArray(new int[] { 1, 2, 3 });
		SetOfNumbers setC = SetOfNumbers.fromArray(new int[] { 1, 2, 4 });

    
		assertEquals(setA, setB, "Sets with the same elements should be equal");
		assertEquals(setB, setB, "A set should be equal to itself");
		assertNotEquals(setA, setC, "Sets with different elements should not be equal");
		assertNotEquals(setB, null, "A set should not be equal to null");
        assertNotEquals(setB, "some string", "A set should not be equal to an object of a different class");

	}
}
