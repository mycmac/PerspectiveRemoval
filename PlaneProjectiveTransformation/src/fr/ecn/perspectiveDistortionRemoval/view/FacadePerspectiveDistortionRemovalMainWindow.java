package fr.ecn.perspectiveDistortionRemoval.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FacadePerspectiveDistortionRemovalMainWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The menu bar.
	 */
	protected MainMenuBarView menuBarView;
	
	/**
	 * The image panel.
	 */
	protected ImagePanel imagePanel;

	/**
	 * The main control panel.
	 */
	protected JPanel controlPanel;
		
	public FacadePerspectiveDistortionRemovalMainWindow(ResourceBundle stringValues){
		// Main window declaration
		super(stringValues.getString(Constants.MAIN_WINDOW_TITLE_PROPERTY));		
		
		Dimension dim = new Dimension(400, 50);
		
		//Create a control panel
		this.setControlPanel(new JPanel(new BorderLayout()));
		this.add(getControlPanel());
		
		//Menu Bar declaration
		this.menuBarView = new MainMenuBarView(stringValues);
		this.controlPanel.add(this.menuBarView, BorderLayout.NORTH);
		
		this.imagePanel = new ImagePanel();
		
		this.controlPanel.add(new JScrollPane(this.imagePanel), BorderLayout.CENTER);
		
		// Main window properties
		// Exit on close
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set size
		this.setSize(dim.width,dim.height);
		// Set visible
		this.setVisible(true);

	}
	
	
	public MainMenuBarView getMenuBarView(){
		return this.menuBarView;
	}

	
	public JPanel getControlPanel() {
		return controlPanel;
	}
	
	
	public ImagePanel getImagePanel(){
		return this.imagePanel;
	}

	public void setControlPanel(JPanel controlPanel) {
		this.controlPanel = controlPanel;
	}


}
