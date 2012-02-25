package fr.ecn.perspectiveDistortionRemoval.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class MainMenuBarQuitController implements ActionListener{

	private JFrame component;

	/**
	 */
	public MainMenuBarQuitController(JFrame component){
		this.component = component;
	}

	public void actionPerformed(ActionEvent arg0) {
		this.component.dispose();
	}
}
