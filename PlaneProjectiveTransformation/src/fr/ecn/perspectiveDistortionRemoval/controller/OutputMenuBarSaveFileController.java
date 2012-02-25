package fr.ecn.perspectiveDistortionRemoval.controller;


import ij.io.FileSaver;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import fr.ecn.perspectiveDistortionRemoval.view.FacadePerspectiveDistortionRemovalOutputWindow;
import fr.ecn.perspectiveDistortionRemoval.view.ImageFilter;


public class OutputMenuBarSaveFileController implements ActionListener{
	
	protected JMenuItem fileSave;
	
	protected Component component;
	
	public OutputMenuBarSaveFileController(JMenuItem fileSave, Component component){
		
		this.fileSave = fileSave;
		
		this.component = component;
		

	}
	

	public void actionPerformed(ActionEvent arg0) {
		//create a file chooser
		JFileChooser fileChooser = new JFileChooser();
		//use an image file filter
		ImageFilter imageFilter = new ImageFilter();
		fileChooser.addChoosableFileFilter(imageFilter);
		//Show it.
        int returnVal = fileChooser.showSaveDialog(this.component);

        //Process the results.
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            
            System.out.println(file.getName()+" "+ImageFilter.getExtension(file)+" "+file.getPath());
            //Save the file
            FileSaver fileSaver = new FileSaver(((FacadePerspectiveDistortionRemovalOutputWindow)this.component).getImagePlus());
            	
            if(ImageFilter.getExtension(file).equals("tiff") || ImageFilter.getExtension(file).equals("tif")){
            	fileSaver.saveAsTiff(file.getPath());
            }else if(ImageFilter.getExtension(file).equals("gif")){
            	fileSaver.saveAsGif(file.getPath());
            }else if(ImageFilter.getExtension(file).equals("png")){
            	fileSaver.saveAsPng(file.getPath());	
            }else{
            	// default save as jpeg
            	fileSaver.saveAsJpeg(file.getPath());
            }
            
            // Remove the memorized transformed image
            ((FacadePerspectiveDistortionRemovalOutputWindow)this.component).resetImageTransform();
    		
        }        
        
        //Reset the file chooser for the next time it's shown.
        fileChooser.setSelectedFile(null);
		
	}
	
	
}
