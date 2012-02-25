package fr.ecn.perspectiveDistortionRemoval.controller;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;

import fr.ecn.perspectiveDistortionRemoval.model.ImageModel;
import fr.ecn.perspectiveDistortionRemoval.view.FacadePerspectiveDistortionRemovalMainWindow;


public class MainMenuBarCloseFileController implements Observer, ActionListener{
	
	protected ImageModel imageModel;
	
	protected JMenuItem fileClose;
	
	protected Component component;
	
	public MainMenuBarCloseFileController(ImageModel imageModel, JMenuItem fileClose, Component component){
		this.imageModel = imageModel;
		
		this.fileClose = fileClose;
		
		this.component = component;
		

	}
	
	public void update(Observable arg0, Object arg1) {
		if(!((ImageModel)arg0).isEmpty()){
			this.fileClose.setEnabled(true);
		}else{
			this.fileClose.setEnabled(false);
		}
		
	}

	public void actionPerformed(ActionEvent arg0) {
		 
         this.imageModel.setEmptyImage();  
         
         // create an empty window
         //((FacadePerspectiveDistortionRemovalMainWindow)this.component).getControlPanel().add(new JPanel(),BorderLayout.CENTER);
         ((FacadePerspectiveDistortionRemovalMainWindow)this.component).getImagePanel().setImagePlus(null);
         Dimension dim = new Dimension(400, 50);
         ((FacadePerspectiveDistortionRemovalMainWindow)this.component).setSize(dim.width,dim.height); 
 		 // Set visible
         //((FacadePerspectiveDistortionRemovalMainWindow)this.component).setVisible(true);
         ((FacadePerspectiveDistortionRemovalMainWindow)this.component).repaint();
		 
	}
	
	
}
