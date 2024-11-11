package edu.pitt.cs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatIntegrationTest {

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	RentACat r; // Object to test
	Cat c1; // First cat object
	Cat c2; // Second cat object
	Cat c3; // Third cat object

	ByteArrayOutputStream out; // Output stream for testing system output
	PrintStream stdout; // Print stream to hold the original stdout stream
	String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n") for use in assertEquals

	@Before
	public void setUp() throws Exception {
		// INITIALIZE THE TEST FIXTURE

		// 1. Create a new RentACat object and assign to r using a call to RentACat.createInstance(InstanceType).
		r = RentACat.createInstance(InstanceType.IMPL);
		

		// 2. Create a Cat with ID 1 and name "Jennyanydots", assign to c1 using a call to Cat.createInstance(InstanceType, int, String).
		c1 = Cat.createInstance(InstanceType.IMPL, 1, "Jennyanydots");
		r.addCat(c1);

		// 3. Create a Cat with ID 2 and name "Old Deuteronomy", assign to c2 using a call to Cat.createInstance(InstanceType, int, String).
		c2 = Cat.createInstance(InstanceType.IMPL, 2, "Old Deuteronomy");
		r.addCat(c2);
		// 4. Create a Cat with ID 3 and name "Mistoffelees", assign to c3 using a call to Cat.createInstance(InstanceType, int, String).
		c3 = Cat.createInstance(InstanceType.IMPL, 3, "Mistoffelees");
		r.addCat(c3);
		
		// 5. Redirect system output from stdout to the "out" stream
		// First, make a back up of System.out (which is the stdout to the console)
		stdout = System.out;
		// Second, update System.out to the PrintStream created from "out"
		out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
	}

	@After
	public void tearDown() throws Exception {
		// Restore System.out to the original stdout
		System.setOut(stdout);

		// Not necessary strictly speaking since the references will be overwritten in
		// the next setUp call anyway and Java has automatic garbage collection.
		r = null;
		c1 = null;
		c2 = null;
		c3 = null;
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is null.
	 *                 System output is "Invalid cat ID." + newline.
	 * </pre>
	 * 
	 * Hint: You will need to use Java reflection to invoke the private getCat(int)
	 * method. efer to the Unit Testing Part 1 lecture and the textbook appendix 
	 * hapter on using reflection on how to do this.  Please use r.getClass() to get
	 * the class object of r instead of hardcoding it as RentACatImpl.
	 */
	@Test
	public void testGetCatNullNumCats0() throws Exception {
		Method getcatmethod = RentACatImpl.class.getDeclaredMethod("getCat", int.class);
		getcatmethod.setAccessible(true);
		Object ret = getcatmethod.invoke(r, 2);
		assertNull(ret);
		assertEquals("Invalid cat ID." + newline, out.toString());
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is not null.
	 *                 Returned cat has an ID of 2.
	 * </pre>
	 * 
	 * Hint: You will need to use Java reflection to invoke the private getCat(int)
	 * method. efer to the Unit Testing Part 1 lecture and the textbook appendix 
	 * hapter on using reflection on how to do this.  Please use r.getClass() to get
	 * the class object of r instead of hardcoding it as RentACatImpl.
	 */
	@Test
	public void testGetCatNumCats3() throws Exception {
		Method getcatmethod = RentACatImpl.class.getDeclaredMethod("getCat", int.class);
		getcatmethod.setAccessible(true);
		Object ret = getcatmethod.invoke(r, 2);
		assertNotNull(ret);
		assertEquals(c2, ret);
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "".
	 * </pre>
	 */
	@Test
	public void testListCatsNumCats0() {
		String g = r.listCats();
		assertEquals("", g);
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
	 *                 Deuteronomy\nID 3. Mistoffelees\n".
	 * </pre>
	 */
	@Test
	public void testListCatsNumCats3() {
		String g = r.listCats();
		assertEquals("ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n", g);
	}

	/**
	 * Test case for boolean renameCat(int id, String name).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call renameCat(2, "Garfield").
	 * Postconditions: Return value is false.
	 *                 c2 is not renamed to "Garfield".
	 *                 System output is "Invalid cat ID." + newline.
	 * </pre>
	 */
	@Test
	public void testRenameFailureNumCats0() {
		boolean ret = r.renameCat(2, "Garfield");
		assertFalse(ret);
		assertEquals("Invalid cat ID." + newline, out.toString());
		
	}

	/**
	 * Test case for boolean renameCat(int id, String name).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call renameCat(2, "Garfield").
	 * Postconditions: Return value is true.
	 *                 c2 is renamed to "Garfield".
	 * </pre>
	 */
	@Test
	public void testRenameNumCat3() {
		

		boolean ret = r.renameCat(2, "Garfield");
		assertTrue(ret);
		assertEquals("Garfield", c2.getName());
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is true.
	 *                 c2 is rented as a result of the execution steps.
	 *                 System output is "Old Deuteronomy has been rented." + newline
	 * </pre>
	 */
	@Test
	public void testRentCatNumCats3() {
		boolean ret = r.rentCat(2);
		assertTrue(ret);
		assertEquals("Old Deuteronomy has been rented." + newline, out.toString());
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 *                 c2 stays rented.
	 *                 System output is "Sorry, Old Deuteronomy is not here!" + newline
	 * </pre>
	 */
	@Test
	public void testRentCatFailureNumCats3() {
		r.rentCat(2);
		boolean ret = r.rentCat(2);
		assertFalse(ret);
		assertEquals("Sorry, Old Deuteronomy is not here!" + newline, out.toString());
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is true.
	 *                 c2 is returned as a result of the execution steps.
	 *                 System output is "Welcome back, Old Deuteronomy!" + newline
	 * </pre>
	 */
	@Test
	public void testReturnCatNumCats3() {
		r.rentCat(2);
		boolean ret = r.returnCat(2);
		assertTrue(ret);
		assertEquals("Welcome back, Old Deuteronomy!" + newline, out.toString());
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is false.
	 *                 c2 stays not rented.
	 *                 System output is "Old Deuteronomy is already here!" + newline
	 * </pre>
	 */
	@Test
	public void testReturnFailureCatNumCats3() {
		boolean ret = r.returnCat(2);
		assertFalse(ret);
		assertEquals("Old Deuteronomy is already here!" + newline, out.toString());
	}

}