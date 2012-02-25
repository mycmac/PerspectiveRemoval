package fr.ecn.perspectiveDistortionRemoval.view;

import ij.ImagePlus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;

public class ImagePanel extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ImagePlus image;
	
	protected Vector<Point> pointsList;
		
	public ImagePanel(ImagePlus in){
		super();
		this.image = in;
		this.pointsList = new Vector<Point>();
		this.addMouseListener(this);
	}
	
	public ImagePanel(){
		super();
		this.image = null;
		this.pointsList = new Vector<Point>();
		this.addMouseListener(this);
	}
	
	
	public void paintComponent(Graphics g) {
		
		if(this.image != null){
			// Draw image if not null
			g.drawImage(this.image.getImage(), 0, 0, (int) (this.image.getWidth()), (int) (this.image.getHeight()), this);
			
			//draw facade boundaries onto image in red
			g.setColor(Color.RED);
			Point p1 = new Point();
			
			if(this.pointsList != null && this.pointsList.size()>0){
				p1 = this.pointsList.firstElement();
				// draw a red line between to facade corners
				for(Point p2 : this.pointsList){
					g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
					p1 = p2;
					
				}
				// If the selected point is the last facade point, close the facade boundaries. 
				if(this.pointsList.size() == 4){
					g.drawLine((int)this.pointsList.firstElement().getX(), (int)this.pointsList.firstElement().getY(), (int)this.pointsList.lastElement().getX(), (int)this.pointsList.lastElement().getY());
				}
			}
			
		}else{
			BufferedImage img = new BufferedImage(1,1,BufferedImage.TYPE_3BYTE_BGR);
			img.setRGB(0, 0, Color.gray.getRGB());
			g.drawImage(img, 0, 0, 1, 1, this);
		}
	}

	public void setImagePlus(ImagePlus in){
		this.image = in;
	}
	
	public Vector<Point> getPointsList(){
		return this.pointsList;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// get the selected point on click
		Point p = e.getPoint(); 
		
		//If there is already 4 points in the list
		// restart a new list because a facade 
		//is defined only by 4 points
		if(this.pointsList.size() == 4){
			this.pointsList = new Vector<Point>();
		}
		
		this.pointsList.add(p);

		this.repaint();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
