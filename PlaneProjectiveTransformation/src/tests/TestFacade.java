package tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.ecn.perspectiveDistortionRemoval.model.Facade;


public class TestFacade {

	Vector<Point> pointList1;
	Vector<Point> pointList2;
	Vector<Point> collinearPoints;
	Facade incomplete;
	Facade test;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
			pointList1 = new Vector<Point>();
			pointList1.add(new Point(10, 10));
			pointList1.add(new Point(15, 30));
			pointList1.add(new Point(30, 20));
			
			pointList2 = new Vector<Point>(pointList1);
			pointList2.add(new Point(20, 0));
			
			collinearPoints = new Vector<Point>();
			collinearPoints.add(new Point(10,10));
			collinearPoints.add(new Point(20,20));
			collinearPoints.add(new Point(30,30));
			collinearPoints.add(new Point(20,30));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test 
	public void testIncompleteFacade() {
		try {
			incomplete = new Facade(pointList1);
			fail("Should raise an ExecutionException");
		} catch (ExecutionException e) {
			assertTrue(e.getMessage(), true);
			//e.printStackTrace();
		}
	}
	
	@Test 
	public void testSort() throws ExecutionException {
		test = new Facade(pointList2);
		
		assertEquals(test.getFacadePointsList().get(0), new Point(10,10));
		assertEquals(test.getFacadePointsList().get(1), new Point(20,0));
		assertEquals(test.getFacadePointsList().get(2), new Point(30,20));
		assertEquals(test.getFacadePointsList().get(3), new Point(15,30));
	}
	
	@Test
	public void testGetBoundingRectangle() throws ExecutionException {		
		
		// test facade quelconque
		test = new Facade(pointList2);
		
		Facade rectangle = test.getBoundingRectangle();
		
		assertEquals(rectangle.getFacadePointsList().get(0), new Point(10,0));
		assertEquals(rectangle.getFacadePointsList().get(1), new Point(30,0));
		assertEquals(rectangle.getFacadePointsList().get(2), new Point(30,30));
		assertEquals(rectangle.getFacadePointsList().get(3), new Point(10,30));
		
		// test si la facade est déja un rectangle
		Facade rectangle2 = (new Facade(rectangle.getFacadePointsList())).getBoundingRectangle();
		
		assertEquals(rectangle2.getFacadePointsList().get(0), new Point(10,0));
		assertEquals(rectangle2.getFacadePointsList().get(1), new Point(30,0));
		assertEquals(rectangle2.getFacadePointsList().get(2), new Point(30,30));
		assertEquals(rectangle2.getFacadePointsList().get(3), new Point(10,30));
	}

	@Test
	public void testGetInnerRectangle() throws ExecutionException{
		// test facade quelconque
		test = new Facade(pointList2);

		Vector<Point> rectangle = test.getInnerRectangle();

		assertEquals(rectangle.get(0), new Point(15,10));
		assertEquals(rectangle.get(1), new Point(20,10));
		assertEquals(rectangle.get(2), new Point(20,20));
		assertEquals(rectangle.get(3), new Point(15,20));

		// test si la facade est déja un rectangle
		Vector<Point> rectangle2 = (new Facade(rectangle)).getInnerRectangle();

		assertEquals(rectangle2.get(0), new Point(15,10));
		assertEquals(rectangle2.get(1), new Point(20,10));
		assertEquals(rectangle2.get(2), new Point(20,20));
		assertEquals(rectangle2.get(3), new Point(15,20));	}

	@Test
	public void testAre3PointsCollinear(){
		try {
			test = new Facade(collinearPoints);
			fail("Should raise an ExecutionException");
		} catch (ExecutionException e) {
			assertTrue(e.getMessage(), true);
			//e.printStackTrace();
		}
		
	}

}
