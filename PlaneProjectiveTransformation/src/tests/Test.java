package tests;
import fr.ecn.perspectiveDistortionRemoval.model.Facade;
import fr.ecn.perspectiveDistortionRemoval.model.HomographyCalculation;
import fr.ecn.perspectiveDistortionRemoval.model.ImageTransform;
import ij.ImagePlus;
import ij.io.FileSaver;

import java.awt.Point;
import java.util.Vector;


/**
 * Test d'homographie inverse
 * @author mservieres
 *
 */
public class Test {
	public static void main(String[] arg){
		
		// use an ImagePlus to process images
		ImagePlus _image = null;
		
		// Plane corners to transform
		Facade _facade = null;
		Vector<Point> _corners = new Vector<Point>();
		
		// target rectangle
		Vector<Point> _rectangle = null;
		
		// Calcul of the homography and inverse homography matrix
		HomographyCalculation ptp = null; 
		
		
		// the ImagePlus output Image
		ImageTransform pit = null;
		
		try {
			_image = new ImagePlus("./ressources/DSC_0056.jpg");
			//_image = new ImagePlus("./ressources/DSCN3616-1.jpg");
			System.out.println(_image.getShortTitle()+" hauteur : "+_image.getHeight()+" largeur : "+_image.getWidth());
			
			/*System.out.println("valeurs des coins de l'image");
			int h = _image.getHeight();
			int w = _image.getWidth();
			int[] inPixelValue = _image.getPixel(0, 0);
			System.out.println("(0,0)"+ inPixelValue[0]+" "+inPixelValue[1]+" "+inPixelValue[2]+" "+inPixelValue[3]);
			inPixelValue = _image.getPixel(w-1, 0);
			System.out.println("("+w+",0)"+ inPixelValue[0]+" "+inPixelValue[1]+" "+inPixelValue[2]+" "+inPixelValue[3]);
			inPixelValue = _image.getPixel(w-1, h-1);
			System.out.println("("+w+","+h+")"+ inPixelValue[0]+" "+inPixelValue[1]+" "+inPixelValue[2]+" "+inPixelValue[3]);
			inPixelValue = _image.getPixel(0, h-1);
			System.out.println("(0,"+h+")"+ inPixelValue[0]+" "+inPixelValue[1]+" "+inPixelValue[2]+" "+inPixelValue[3]);

	*/
			
			
			
			// Test avec 4 coins d'une face arbitraire
			// dans l'ordre P0(xmin) d'abords jusqu'à P2(ymax) et P3
			//Test pour image DSC_0056
			_corners.add(new Point(689,409));
			_corners.add(new Point(280,139));
			_corners.add(new Point(656,11));
			_corners.add(new Point(287,398));
			
			//manque un test pour savoir si 3 points parmis 4 ne sont pas colinéaires
			
			//Test pour image DSCN3616-1 facade de droite
			/*_corners.add(new Point(528,114));
			_corners.add(new Point(1088,262));
			_corners.add(new Point(1110,650));
			_corners.add(new Point(536,668));*/
			
			
			//Test pour image DSCN3616-1 facade de gauche
			/*_corners.add(new Point(124,376));
			_corners.add(new Point(530,256));
			_corners.add(new Point(530,674));
			_corners.add(new Point(124,606));*/
			
			
			_facade = new Facade(_corners);
			
			System.out.println("Facade :\n"+ _facade.toString());
			
			
			// Calcul des 4 coins du rectangle englobé par la facade
			//_rectangle = _facade.getBoundingRectangle();
			_rectangle = _facade.getInnerRectangle();
			System.out.println("Rectangle :\n"+_rectangle.toString());
			
			
			
			
			// Calcul de l'homographie attention, les points entre la facade à redresser
			// et le rectangle cible se correspondent
			ptp = new HomographyCalculation(_facade, _rectangle);
			
			//Verification de la transformation de l'image perspective
			for(int i=0; i<_facade.getFacadePointsList().size(); i++){
				System.out.println("Corner "+_facade.getFacadePointsList().get(i));
				System.out.println("Calculated Transformed corner "+ImageTransform.getHomographyTransformedPointCoordinates(_facade.getFacadePointsList().get(i), ptp.getHomography()).toString());
				System.out.println("Corresponding rect point "+_rectangle.get(i)+"\n");
			}
			
			// Application de l'homographie inverse et obtention de l'image redressée
			// création de l'image de sortie
			pit = new ImageTransform(_image, ptp.getHomography());
		
			pit.getOutImage().show();
			
			//Save out image
			FileSaver fs = new FileSaver(pit.getOutImage());
			fs.saveAsJpeg("./ressources/"+_image.getShortTitle()+"_rectified.jpg");

			// Calcul de l'image transformée
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		
	}
}
