package fr.ecn.perspectiveDistortionRemoval.controller;


import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import fr.ecn.perspectiveDistortionRemoval.model.Facade;
import fr.ecn.perspectiveDistortionRemoval.model.HomographyCalculation;
import fr.ecn.perspectiveDistortionRemoval.model.ImageModel;
import fr.ecn.perspectiveDistortionRemoval.model.ImageTransform;
import fr.ecn.perspectiveDistortionRemoval.view.Constants;
import fr.ecn.perspectiveDistortionRemoval.view.FacadePerspectiveDistortionRemovalMainWindow;
import fr.ecn.perspectiveDistortionRemoval.view.FacadePerspectiveDistortionRemovalOutputWindow;


public class MainMenuBarRemovePerspectiveController implements ActionListener{

	protected ImageModel imageModel;
	
	protected JMenuItem removePerspective;
	
	protected Component inputComponent;
	
	//protected Component outputComponent;
	
	protected ResourceBundle param;
	
	//public MainMenuBarRemovePerspectiveController(ImageModel imageModel, JMenuItem removePerspective, Component component, Component component2, ResourceBundle param){
	public MainMenuBarRemovePerspectiveController(ImageModel imageModel, JMenuItem removePerspective, Component component, ResourceBundle param){
		this.imageModel = imageModel;
		this.removePerspective = removePerspective;		
		this.inputComponent = component;
		//this.outputComponent = component2;
		this.param = param;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// Corners of the input facade
		Vector<Point> points = ((FacadePerspectiveDistortionRemovalMainWindow)this.inputComponent).getImagePanel().getPointsList();
		
		//input facade
		Facade facade = null;
		
		//output wanted rectangle after perspective removal
		//Facade rectangle = null;
		Vector<Point> rectangle = null;
		
		// Calcul of the homography and inverse homography matrix
		HomographyCalculation ptp = null; 
		
		// the ImagePlus output Image
		ImageTransform pit = null;
		
		if(points.size() == 4){
			try {
				//Create the facade
				facade = new Facade(points);
				// Calcul of the rectangle bounded by the facade 4 corners position
				rectangle = facade.getInnerRectangle();
				
				
				// Homography calculation between the input facade and the output rectangle
				// The two list of point correspond to each other
				ptp = new HomographyCalculation(facade, rectangle);
				
				// Inverse homography calculation and output image without perspective creation
				pit = new ImageTransform(this.imageModel.getInImage(), ptp.getHomography());
			
				//pit.getOutImage().show();
				
				// create the transformed image in the output component with its listeners
				FacadePerspectiveDistortionRemovalOutputWindow outputWindow = new FacadePerspectiveDistortionRemovalOutputWindow(pit,param);
				//OutputWindow
				//File Menu
				//close
				OutputMenuBarCloseFileController outputMenuBarCloseFileController = new OutputMenuBarCloseFileController(outputWindow.getOutputMenuBarView().getFileClose(), outputWindow);
				outputWindow.getOutputMenuBarView().getFileClose().addActionListener(outputMenuBarCloseFileController);
				//Save
				OutputMenuBarSaveFileController outputMenuBarSaveFileController = new OutputMenuBarSaveFileController(outputWindow.getOutputMenuBarView().getFileSave(), outputWindow);
				outputWindow.getOutputMenuBarView().getFileSave().addActionListener(outputMenuBarSaveFileController);
		
				//reset all the calculation needed objects
				facade = null;
				rectangle = null;
				ptp = null;
				pit = null;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			JOptionPane.showMessageDialog(this.inputComponent, this.param.getString(Constants.FACADE_POINTS_NUMBER_WARNING_MESSAGE));
		}
	}
	
	
}
