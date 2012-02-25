package fr.ecn.perspectiveDistortionRemoval.controller;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import fr.ecn.perspectiveDistortionRemoval.view.FacadePerspectiveDistortionRemovalOutputWindow;


public class OutputMenuBarCloseFileController implements ActionListener{
	
	protected JMenuItem fileClose;
	
	protected Component component;
	
	public OutputMenuBarCloseFileController(JMenuItem fileClose, Component component){
		
		this.fileClose = fileClose;
		
		this.component = component;
		

	}
	

	public void actionPerformed(ActionEvent arg0) {
		 
         ((FacadePerspectiveDistortionRemovalOutputWindow)this.component).dispose();
         
         // Remove the memorized transformed image
         ((FacadePerspectiveDistortionRemovalOutputWindow)this.component).resetImageTransform();

       	 
	}
	
	
}
