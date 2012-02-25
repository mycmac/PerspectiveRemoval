package fr.ecn.perspectiveDistortionRemoval.controller;


import ij.ImagePlus;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import fr.ecn.perspectiveDistortionRemoval.model.ImageModel;
import fr.ecn.perspectiveDistortionRemoval.view.FacadePerspectiveDistortionRemovalMainWindow;
import fr.ecn.perspectiveDistortionRemoval.view.ImageFilter;


public class MainMenuBarOpenFileController implements Observer, ActionListener{
	
	protected ImageModel imageModel;
	
	protected JMenuItem fileOpen;
	
	protected Component component;
	
	public MainMenuBarOpenFileController(ImageModel imageModel, JMenuItem fileOpen, Component component){
		this.imageModel = imageModel;
		
		this.fileOpen = fileOpen;
		
		this.component = component;
		

	}
	
	public void update(Observable arg0, Object arg1) {
		if(!((ImageModel)arg0).isEmpty()){
			this.fileOpen.setEnabled(false);
		}else{
			this.fileOpen.setEnabled(true);
		}
		
	}

	public void actionPerformed(ActionEvent arg0) {
		//create a file chooser
		JFileChooser fileChooser = new JFileChooser();
		//use an image file filter
		ImageFilter imageFilter = new ImageFilter();
		fileChooser.addChoosableFileFilter(imageFilter);
		//Show it.
        int returnVal = fileChooser.showDialog(this.component,"Open");

        //Process the results.
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            ImagePlus ip = new ImagePlus(file.getAbsolutePath());
            
            this.imageModel.setInImage(ip);
            
            //ImagePanel imagePanel = new ImagePanel(ip); 
            
            //((FacadePerspectiveDistortionRemovalMainWindow)this.component).getControlPanel().add(new JScrollPane(imagePanel),BorderLayout.CENTER);
            ((FacadePerspectiveDistortionRemovalMainWindow)this.component).getImagePanel().setImagePlus(ip);
            
            // Get the screen size
    		Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();		
    		if(ip.getWidth()<dimScreen.width && ip.getHeight() < dimScreen.height){
    			((FacadePerspectiveDistortionRemovalMainWindow)this.component).setSize(ip.getWidth(), ip.getHeight());
    			//((FacadePerspectiveDistortionRemovalMainWindow)this.component).getImagePanel().setSize(ip.getWidth(), ip.getHeight());
    		}else{
    			((FacadePerspectiveDistortionRemovalMainWindow)this.component).setSize(dimScreen);
    			//((FacadePerspectiveDistortionRemovalMainWindow)this.component).getImagePanel().setSize(dimScreen);
    		}
            //((FacadePerspectiveDistortionRemovalMainWindow)this.component).setVisible(true);
    		((FacadePerspectiveDistortionRemovalMainWindow)this.component).repaint();
    		
        }        
        
        //Reset the file chooser for the next time it's shown.
        fileChooser.setSelectedFile(null);
		
	}
	
	
}
