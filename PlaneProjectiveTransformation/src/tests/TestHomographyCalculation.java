package tests;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.Vector;

import org.ejml.simple.SimpleMatrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.ecn.perspectiveDistortionRemoval.model.Facade;
import fr.ecn.perspectiveDistortionRemoval.model.HomographyCalculation;
import fr.ecn.perspectiveDistortionRemoval.model.ImageTransform;


/**
 * Test du calcul d'homographie en faisant correspondre
 * (x',y')T = H (x,y)
 * (x,y)->(x',y')
 * (0,0)->(0,0)
 * (1,0)->(1,0)
 * (0,1)->(0,1)
 * (2,1)->(1,1)
 * @author mservieres
 *
 */
public class TestHomographyCalculation {

	Vector<Point> persList;
	Vector<Point> rectList;

	Facade pers;
	//Facade rect;
	
	HomographyCalculation pt;
	
	SimpleMatrix A;
	
	SimpleMatrix vA;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		persList = new Vector<Point>();
		persList.add(new Point(0,0));
		persList.add(new Point(1,0));
		persList.add(new Point(0,1));
		persList.add(new Point(2,1));
		pers = new Facade(persList);
		
		//rectList = pers.getInnerRectangle();
		rectList = new Vector<Point>();
		rectList.add(new Point(0,0));
		rectList.add(new Point(1,0));
		rectList.add(new Point(1,1));
		rectList.add(new Point(0,1));
		//rect = new Facade(rectList);
		
		//Matrix column fill with [1,2,3,4,5]T
		A = new SimpleMatrix(5,5);
		for(int i=0; i<5; i++){
			for(int j=0; j<5 ; j++){
				A.set(i, j, i);
			}
		}
		vA = A.extractVector(false, A.numCols()-1);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPerspectiveTransform() {
		
		try{
			System.out.println("Rectangle");
			for(Point p : rectList){
				System.out.println(p.toString());
			}
			
			pt = new HomographyCalculation(pers, rectList);		
			
			//Matrix with the wanted rect point 
			//Vector<SimpleMatrix> rectListMatrixPoint = new Vector<SimpleMatrix>();
			
			/*for(Point p : rectList){
			
				double[][] pdouble = new double[3][1];
				pdouble[0][0] = p.getX();
				pdouble[1][0] = p.getY();
				pdouble[2][0] = 1;
			
				rectListMatrixPoint.add(new SimpleMatrix(pdouble));
			}*/
			
			
			//reconstructed point from  H * pers point
			Vector<Point> reconstructedPointVectorPoint = new Vector<Point>();
			
			for(Point persP : pers.getFacadePointsList()){
				reconstructedPointVectorPoint.add(ImageTransform.getHomographyTransformedPointCoordinates(persP, pt.getHomography()));
			}
			
			//System.out.println("H * persPoint");
			for(int i = 0; i<reconstructedPointVectorPoint.size(); i++){
				assertEquals(rectList.get(i), reconstructedPointVectorPoint.get(i));
			}
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}

	}


}
