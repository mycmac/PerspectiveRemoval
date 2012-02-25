package fr.ecn.perspectiveDistortionRemoval.model;

import ij.ImagePlus;

import java.util.Observable;



/**
 * Class containing all the image informations.
 */
public class ImageModel extends Observable{

	
	protected ImagePlus inImage;
	

	public ImageModel(){
		this.inImage = new ImagePlus();
		setChanged();
		notifyObservers();
	}

	/**
	 * Return the image name.
	 * @return
	 */
	public String getImageName() {
		return this.inImage.getShortTitle();
	}


	public void setInImage(ImagePlus in) {
		this.inImage = in;
		setChanged();
		notifyObservers();
	}

	public ImagePlus getInImage(){
		return this.inImage;
	}

	public void setEmptyImage(){
		this.inImage = null;
		setChanged();
		notifyObservers();
	}

	public boolean isEmpty() {
		if(this.inImage == null){
			return true;
		}else{
			return false;	
		}
	}



}
