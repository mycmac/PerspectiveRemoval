package fr.ecn.perspectiveDistortionRemoval.model;

import java.awt.Point;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ExecutionException;



/**
 * This class contain a list of Points defining the "corners" of a facade onto an image.
 * A facade is composed of exactly 4 points with 3 points non-collinear (warning non-tested).
 * @author mservieres
 *
 */
public class Facade{
	
	/**
	 * Facades corners.
	 */
	protected Vector<Point> pointsList;
	
	/**
	 * Facade constructor. Exactly 4 points are mandatory to define a facade.
	 * @param list Vector of Points
	 */
	public Facade(Vector<Point> list) throws ExecutionException{
		
		if(list.size() != 4){
			throw new ExecutionException(new Throwable("Exact number of facade points should be 4"));
		}

		pointsList = new Vector<Point>();
		for(Point p : list){
			pointsList.add(new Point(p));
		}
		
		if(are3PointsCollinear()) throw new ExecutionException(new Throwable("3 points among the 4 facade points are collinear."));
		
		//the point list should be sorted starting from (xmin, y) and then clockwise.
		pointsList = sort(pointsList);

	}

	public Facade(){
		this.pointsList = new Vector<Point>();
	}
	
	public boolean containsFourPoints(){
		if(this.pointsList.size() == 4){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @return the _corners
	 */
	public Vector<Point> getFacadePointsList() {
		return pointsList;
	}

	/**
	 * @param _corners the _corners to set
	 */
	public void set_corners(Vector<Point> _corners) {
		this.pointsList = _corners;
	}
	
	/**
	 * Get the bounding Rectangle of the current Facade.
	 * @return a Facade containing 4 points.
	 * @throws ExecutionException 
	 */
	public Facade getBoundingRectangle() throws ExecutionException{
		
		// x and y min and max coordinates
		int xmin = this.pointsList.get(0).x;
		int xmax = this.pointsList.get(0).x;
		int ymin = this.pointsList.get(0).y;
		int ymax = this.pointsList.get(0).y;
		
		for(Point p : this.pointsList){
			if(p.x < xmin) xmin = p.x;
			if(p.x > xmax) xmax = p.x;
			if(p.y < ymin) ymin = p.y;
			if(p.y > ymax) ymax = p.y;
		}
		
		// The 4 points list corresponding to the bounding rectangle
		Vector<Point> recList = new Vector<Point>();
		recList.add(new Point(xmin, ymin));
		recList.add(new Point(xmax, ymin));
		recList.add(new Point(xmax, ymax));
		recList.add(new Point(xmin, ymax));
		
		return new Facade(recList);
	}
	
	
	/**
	 * Get the inner  rectangle of the current facade.
	 * @return
	 * @throws ExecutionException 
	 */
	public Vector<Point> getInnerRectangle() throws ExecutionException{
		
		TreeSet<Integer> x = new TreeSet<Integer>();
		TreeSet<Integer> y = new TreeSet<Integer>();
		
		for(Point p : this.pointsList){
			x.add(p.x);
			y.add(p.y);
		}
		
		Object[] xtab = x.toArray();
		Object[] ytab = y.toArray();
		
		Vector<Point> recList = new Vector<Point>();
		
		int xrecmin=0;
		int xrecmax=0;
		int yrecmin=0;
		int yrecmax=0;
		
		// Some of the 4 points can have the same x or y
		if(xtab.length == 4 || xtab.length == 3){
			xrecmin = (Integer)xtab[1];
			xrecmax = (Integer)xtab[2];
		}else if(xtab.length == 2){
			xrecmin = (Integer)xtab[0];
			xrecmax = (Integer)xtab[1];
		}else{
			new Exception("Facade corners position problem");
		}

		if(ytab.length == 4 || ytab.length == 3){
			yrecmin = (Integer)ytab[1];
			yrecmax = (Integer)ytab[2];
		}else if(ytab.length == 2){
			yrecmin = (Integer)ytab[0];
			yrecmax = (Integer)ytab[1];
		}else{
			new Exception("Facade corners position problem");
		}

		
		recList.add(new Point(xrecmin, yrecmin));
		recList.add(new Point(xrecmax, yrecmin));
		recList.add(new Point(xrecmax, yrecmax));
		recList.add(new Point(xrecmin, yrecmax));

		
		return recList;
		//return new Facade(recList);
		
	}
	
	/** 
	 * Number of points defining the facade.
	 * @return
	 */
	public int numberOfPoint(){
		return this.pointsList.size();
	}
	
	/**
	 * Test if within 4 points from input perspective facade there is
	 * at least 3 non-collinear points. 
	 * @return true if 3 points are collinear
	 */
	public boolean are3PointsCollinear(){
		// boolean true if 3 points are collinear, false otherwise
		boolean is3collinear = false;
		
		// Test the collinearity the 4 possibles groups of 3 points within the 4 facade points : P0, P1, P2, P3 
		// The tested groups are 
		// P0, P1, P2
		// P0, P1, P3
		// P0, P2, P3
		// P1, P2, P3
		is3collinear = is3collinear || areCollinear(this.pointsList.get(0), this.pointsList.get(1), this.pointsList.get(2));
		is3collinear = is3collinear || areCollinear(this.pointsList.get(0), this.pointsList.get(1), this.pointsList.get(3));
		is3collinear = is3collinear || areCollinear(this.pointsList.get(0), this.pointsList.get(2), this.pointsList.get(3));
		is3collinear = is3collinear || areCollinear(this.pointsList.get(1), this.pointsList.get(2), this.pointsList.get(3));
		
		return is3collinear;
	}
	
	/**
	 * Test if 3 point are collinear.
	 * @param P0
	 * @param P1
	 * @param P2
	 * @return
	 */
	private boolean areCollinear(Point P0, Point P1, Point P2) {
		
		int calcul = (P1.x-P0.x)*(P2.y-P0.y)-(P1.y-P0.y)*(P2.x-P0.x);
		
		if(calcul == 0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Print the list of points defining a Facade.
	 */
	public String toString(){
		String out = new String();
		for(Point p : this.pointsList){
			out = out + "("+p.x+","+p.y+")\n";
		}
		return out;
		
	}
	
	/**
	 * Sort point list starting from (xmin, ymin) and then clockwise.
	 * @param list
	 */
	private static Vector<Point> sort(Vector<Point> list){
		
		
		// list to help to sort
		//TreeMap<Double, Point> pointsWithAngle = new TreeMap<Double, Point>(); 
		
		// result list with sorted points.
		Vector<Point> sortedList = new Vector<Point>();
		
		
		//The 4 points
		Point P0 = new Point();
		Point P1 = new Point();
		Point P2 = new Point();
		Point P3 = new Point();
		
		double xBary = 0;
		double yBary = 0;
		int cpt = 0;
		//caculate the barycenter coordinates
		for(Point p : list){
			xBary = xBary + p.x;
			yBary = yBary + p.y;
			cpt++;
		}
		xBary = xBary/cpt;
		yBary = yBary/cpt;
		
		//Sort the 4 points
		for(Point p : list){
			if(p.x <= xBary && p.y <= yBary) P0 = new Point(p);
			if(p.x >= xBary && p.y <= yBary) P1 = new Point(p);
			if(p.x >= xBary && p.y >= yBary) P2 = new Point(p);
			if(p.x <= xBary && p.y >= yBary) P3 = new Point(p);
		}
		
		sortedList.add(P0);
		sortedList.add(P1);
		sortedList.add(P2);
		sortedList.add(P3);
		
		return sortedList; 
	}
	
}

