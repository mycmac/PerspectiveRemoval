package tests;
import org.ejml.simple.SimpleMatrix;
public class SmallTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		double[][] vA = {
						{2, 0, 0},
						{0, 1, 0},
						{0, -1, 2},
						};

		SimpleMatrix H = new SimpleMatrix(vA);

		System.out.println(H.toString());
		System.out.println(H.invert().toString());
		
		double[][] pers = {
							{0, 1, 0, 2},
							{0, 0, 1, 1},
							{1, 1, 1, 1},
						};
		SimpleMatrix persMatrix = new SimpleMatrix(pers);
	
		System.out.println(H.invert().mult(persMatrix));
		
		}

	

}
