package fr.ecn.perspectiveDistortionRemoval.controller;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import fr.ecn.perspectiveDistortionRemoval.view.Constants;


public class MainMenuBarHelpController implements ActionListener{
	
	protected JMenuItem help;
	
	protected Component component;
	
	protected ResourceBundle param;
	
	public MainMenuBarHelpController(JMenuItem help, Component component, ResourceBundle param){
		this.help = help;		
		this.component = component;
		this.param = param;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		JOptionPane.showMessageDialog(this.component, this.param.getString(Constants.HELP_MESSAGE));
	}
	
	
}
