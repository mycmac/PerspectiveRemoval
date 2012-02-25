package fr.ecn.perspectiveDistortionRemoval.model;

import ij.ImagePlus;
import ij.gui.NewImage;

import java.awt.Point;

import org.ejml.alg.dense.mult.MatrixDimensionException;
import org.ejml.simple.SimpleMatrix;

public class ImageTransform {
	
	/**
	 * Input perspective image.
	 */
	protected ImagePlus persImage;
	
	/**
	 * Perspective rectified output image.
	 */
	protected ImagePlus outImage;
	
	/**
	 * Calculated homography.
	 */
	protected SimpleMatrix homography;
	
	/**
	 * Inverse homography. 
	 */
	protected SimpleMatrix homographyInv;
		
	/**
	 * Constructor of ImageTransform.
	 * @param in
	 * @param hInv
	 */
	
	public ImageTransform(ImagePlus in, SimpleMatrix h){
		this.persImage = in;
		this.homography = h;
		this.homographyInv = this.homography.invert();
		this.outImage = calculateRectifiedImage();
		
	}

	/**
	 * @return the outImage
	 */
	public ImagePlus getOutImage() {
		return outImage;
	}

	/** 
	 * For all pixel in the output image, get the corresponding pixels in the input image using inverse homography  to get the out image pixels values.
	 * @return
	 */
	private ImagePlus calculateRectifiedImage(){
		ImagePlus rectifiedImage = NewImage.createRGBImage(this.persImage.getShortTitle()+"Rectified", 
				this.persImage.getWidth(), 
				this.persImage.getHeight(), 
				1, 
				NewImage.FILL_BLACK);
		
		for(int y = 0; y<rectifiedImage.getHeight(); y++){
			for(int x = 0; x<rectifiedImage.getWidth(); x++){
				
				Point inPoint = getHomographyTransformedPointCoordinates(new Point(x,y), this.homographyInv);
				
				if(isWithinBoundaries(inPoint.x,inPoint.y, rectifiedImage.getWidth(), rectifiedImage.getHeight())){
					rectifiedImage.getProcessor().putPixel(x, y, this.persImage.getPixel(inPoint.x, inPoint.y));
				}
				
			}
		}
		
		return rectifiedImage;
	}
		
	/**
	 * Test if a point coordinate (x,y) is in the output imageLimit.
	 * @param x
	 * @param y
	 * @param w image width
	 * @param h image high
	 * @return true if it is within boundaries.
	 */
	private boolean isWithinBoundaries(int x, int y, int w, int h) {
		
		if(x>=0 && x<w && y>=0 && y<h){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Divide all the point coordinate by the last coordinate to get a point into the image with the (x,y,1) form.  
	 * @param p
	 * @return
	 * @throws MatrixDimensionException
	 */
	private static SimpleMatrix normalizePoint(SimpleMatrix p) throws MatrixDimensionException{
		if(!p.isVector()) new MatrixDimensionException("normalize is only for point Matrix 3x1");
		
		double lastCoord = p.get(2,0);
		
		p=p.divide(lastCoord);
		
		return p;
		
		
	}
	
	/**
	 * Apply the H transform onto input points and give back the transform point (using int round coordinate) in the image plane.
	 * @param inPoint
	 * @return outPoint
	 */
	public static Point getHomographyTransformedPointCoordinates(Point inPoint, SimpleMatrix h) 	{
		
		//Get the inPoint into a matrix form
		double[][] inPointD = new double[3][1];
		inPointD[0][0] = inPoint.getX();
		inPointD[1][0] = inPoint.getY();
		inPointD[2][0] = 1;
		SimpleMatrix inPointM = new SimpleMatrix(inPointD);
		
		//Apply the transformation H*inPoint
		SimpleMatrix outPointMatrix = h.mult(inPointM);
		
		//Get the output point coordinate in the image plane
		outPointMatrix = normalizePoint(outPointMatrix);
		

		//Get the outPointMatrix into a Point form
		Point out = new Point((int)Math.round(outPointMatrix.get(0, 0)), (int) Math.round(outPointMatrix.get(1,0)));
		return out;
	}
	
	public void resetImageTransform(){
		this.persImage = null;
		this.homography = null;
		this.homographyInv = null;
		this.outImage = null;
	}
}
