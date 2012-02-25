package fr.ecn.perspectiveDistortionRemoval.model;

import java.awt.Point;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import org.ejml.simple.SimpleMatrix;



/**
 * Algorithme and explanation in Multiple View Geometry p88-89-90
 * 
 * If we have a set of corresponding points xi and xi', we can calculate the homography matrix 3x3 H such as  
 * xi'= Hxi with H 
 * 			h1 h2 h3
 * 		H = h4 h5 h6 
 * 			h7 h8 h9
 * 
 * To carry out the calculation (at a scale factor), 4 corresponding points are necessary between the 2 images
 * with at least 3 non-collinear points.
 * Point (xi,yi) and (xi', yi') are directly measured coordinates into the input (perspective) image and the output (redressed) image. 
 * In this case, for each point, this equation can be written as : xi' x Hxi = 0 where x is a cross product. This can also be written as : 
 * xi 		yi 		1 		0 		0 		0 		−xixi' 	-yixi'	-xi' 	x h =   0
 * 	0		0		0		xi		yi		1		-xiyi'	-yiyi'	-yi'
 *  
 * With several correspondances, it is a classic linear system :   
 * Ah = 0 where A is 2nX9 with n the number of points in correspondence and h = [h1, h2, h3, h4, h5, h6, h7, h8, h9]T 
 *  
 *  Following algorithm from Multiple View Geometry p91 :
 *  Obtain the SVD of A. The unit singular vector corresponding to the smallest singular value is the solution of h. 
 * 	If A = UDV^T with D diagonal with positive diagonal entries arranged in descending order down the diagonal, then h is the last column of V.
 *  
 * @author mservieres
 *
 */


public class HomographyCalculation {

	protected Facade perspectiveFacade = null;
	protected Vector<Point> rectangleWantedFacade = null;
	protected SimpleMatrix homography = null;
	

	
	public HomographyCalculation(Facade in, Vector<Point> out) throws Exception{
		this.perspectiveFacade = in ;
		this.rectangleWantedFacade = out ;
		
		/*System.out.println("Perspective facade :\n");
		for(Point p : perspectiveFacade.getFacadePointsList()){
			System.out.println(p.toString());
		}*/
		
		// create the system Ah = 0 to solve to get the homography matrix h
		// Facade and rectangle have 4 points sorted clockwise from point with xmin
		
		this.homography = calculateHomography(perspectiveFacade, rectangleWantedFacade);
		
		//System.out.println("Homography matrix \n"+homography.toString());
		
		
	}
	
	/**
	 * @return the perspectiveFacade
	 */
	public Facade getPerspectiveFacade() {
		return perspectiveFacade;
	}


	/**
	 * @return the rectangle
	 */
	public Vector<Point> getRectangle() {
		return rectangleWantedFacade;
	}


	/**
	 * @return the homography
	 */
	public SimpleMatrix getHomography() {
		return homography;
	}

	

	/**
	 * Return the H 3x3 matrix from h 9x1 matrix null space of A. 
	 * Ah=0 
	 * following algorithm from Mutiple View Geometry p91 :
	 * Obtain the SVD of A. The unit singular vector corresponding to the smallest singular value is the solution of h. 
	 * If A = UDV^T with D diagonal with positive diagonal entries arranged in descending order down the diagonal, then h is the last column of V.
	 * out = H x in
	 * @param in
	 * @param out
	 * @return
	 */
	private SimpleMatrix calculateHomography(Facade in, Vector<Point> out) throws ExecutionException{
		SimpleMatrix A = new SimpleMatrix(in.numberOfPoint()*2,9);
		int j = 0;
		
		for(int i = 0; i<in.numberOfPoint(); i++){
			//A.insertIntoThis(j, 0, AForTwoCorrespondingPoints(in.getFacadePointsList().get(i).x, in.getFacadePointsList().get(i).y, out.getFacadePointsList().get(i).x, out.getFacadePointsList().get(i).y));
			A.insertIntoThis(j, 0, AForTwoCorrespondingPoints(in.getFacadePointsList().get(i).x, in.getFacadePointsList().get(i).y, out.get(i).x, out.get(i).y));
			j = j+2;	
		}
		
		//Print the A matrix
		//System.out.println("A\n"+A.toString());
		
		//Calculate SVD and get last column of V for h value
		SimpleMatrix V = A.svd().getV();
		SimpleMatrix h = V.extractVector(false, V.numCols()-1);
		
		// test to verify h is a null space of A i.e. Ah = 0
		double mult = A.mult(h).normF();
		
		if(mult > Math.pow(10, -3)){
			throw new ExecutionException("The calculated h is not a null space of A", new Throwable("Ah != 0"));
		}
		
		h.reshape(3, 3);
		
		//System.out.println("H\n"+h.toString());
		
		return h;
	}
		
	
	/**
	 * Return the 2 row of the A matrix for two points of correspondance.
	 * @param xiprime
	 * @param yiprime
	 * @param xi
	 * @param yi
	 * @return
	 */
	private static SimpleMatrix AForTwoCorrespondingPoints(int xi, int yi, int xiprime, int yiprime) {
		double[][] vA = {
				{xi, yi, 1, 0, 0, 0, -xi*xiprime, -yi*xiprime, -xiprime},
				{0, 0, 0, xi, yi, 1, -xi*yiprime, -yi*yiprime, -yiprime},
				};
		
		return new SimpleMatrix(vA);		
	}
	

	
}
