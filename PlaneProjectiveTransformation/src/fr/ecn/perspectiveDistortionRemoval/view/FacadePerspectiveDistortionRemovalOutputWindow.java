package fr.ecn.perspectiveDistortionRemoval.view;

import ij.ImagePlus;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.ecn.perspectiveDistortionRemoval.model.ImageTransform;

public class FacadePerspectiveDistortionRemovalOutputWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The menu bar.
	 */
	protected OutputMenuBarView outputMenuBarView;
	
	/**
	 * The output image.
	 */
	protected ImageTransform imageTransform;

	/**
	 * The main control panel.
	 */
	protected JPanel controlPanel;
		
	public FacadePerspectiveDistortionRemovalOutputWindow(ImageTransform it, ResourceBundle stringValues){
		// Main window declaration
		super(stringValues.getString(Constants.OUTPUT_WINDOW_TITLE_PROPERTY));		
		
		
		//Create a control panel
		this.setControlPanel(new JPanel(new BorderLayout()));
		this.add(getControlPanel());
		
		//Menu Bar declaration
		this.outputMenuBarView = new OutputMenuBarView(stringValues);
		this.controlPanel.add(this.outputMenuBarView, BorderLayout.NORTH);
		
		this.imageTransform = it;
		
		this.controlPanel.add(new ImageOutputPanel(this.imageTransform.getOutImage()), BorderLayout.CENTER);
		
		// Output window properties
		// Dispose on close
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setSize(this.imageTransform.getOutImage().getWidth(), this.outputMenuBarView.getSize().height+this.imageTransform.getOutImage().getHeight());
		this.setVisible(true);
		
	}

	
	public OutputMenuBarView getOutputMenuBarView(){
		return this.outputMenuBarView;
	}

	
	public JPanel getControlPanel() {
		return controlPanel;
	}
	

	public void setControlPanel(JPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	public ImagePlus getImagePlus(){
		return this.imageTransform.getOutImage();
	}

	public void resetImageTransform(){
		this.imageTransform.resetImageTransform();
	}
}
