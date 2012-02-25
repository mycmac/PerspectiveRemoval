package fr.ecn.perspectiveDistortionRemoval.view;

import ij.ImagePlus;

import java.awt.Graphics;

import javax.swing.JPanel;

public class ImageOutputPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ImagePlus image;
	
		
	public ImageOutputPanel(ImagePlus in){
		super();
		this.image = in;
	}
	
	
	
	public void paintComponent(Graphics g) {	
		g.drawImage(this.image.getImage(), 0, 0, (int) (this.image.getWidth()), (int) (this.image.getHeight()), this);
	}


}
