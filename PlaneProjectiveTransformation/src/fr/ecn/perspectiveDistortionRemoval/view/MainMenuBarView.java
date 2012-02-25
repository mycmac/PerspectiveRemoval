package fr.ecn.perspectiveDistortionRemoval.view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MainMenuBarView extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu file;
	private JMenuItem fileOpen;
	private JMenuItem fileClose;
	private JMenuItem quit;
	
	private JMenu facade;
	private JMenuItem removePerspective;
	
	private JMenu help;
	private JMenuItem helpContents;
	
	
	public MainMenuBarView(ResourceBundle param){
		// File menu
		file = new JMenu(param.getString(Constants.MENU_FILE_NAME));
		fileOpen = new JMenuItem(param.getString(Constants.MENU_FILE_OPEN));
		fileClose = new JMenuItem(param.getString(Constants.MENU_FILE_CLOSE));
		quit = new JMenuItem(param.getString(Constants.MENU_FILE_APP_QUIT));

		// Facade menu
		facade = new JMenu(param.getString(Constants.MENU_FACADE_NAME));
		removePerspective = new JMenuItem(param.getString(Constants.MENU_FACADE_REMOVEPERSPECTIVE));
		
		//Help menu
		help = new JMenu(param.getString(Constants.MENU_HELP_NAME));
		helpContents = new JMenuItem(param.getString(Constants.MENU_HELP_CONTENTS_NAME));
		
		// File menu
		file.setMnemonic(KeyEvent.VK_F);
		file.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_FILE_NAME_DESCRIPTION));
		this.add(file);

		fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.META_MASK));
		fileOpen.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_FILE_OPEN_DESCRIPTION));
		file.add(fileOpen);
				
		fileClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.META_MASK));
		fileClose.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_FILE_CLOSE_DESCRIPTION));
		file.add(fileClose);

		file.addSeparator();
		
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.META_MASK));
		quit.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_FILE_APP_QUIT_DESCRIPTION));
		file.add(quit);
		
		//facade menu
		facade.setMnemonic(KeyEvent.VK_A);
		facade.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_FACADE_NAME_DESCRIPTION));
		this.add(facade);

		removePerspective.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.META_MASK));
		removePerspective.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_FACADE_REMOVEPERSPECTIVE_DESCRIPTION));
		facade.add(removePerspective);
		
		//help menu
		help.setMnemonic(KeyEvent.VK_H);
		help.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_HELP_NAME_DESCRIPTION));
		this.add(help);

		helpContents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.META_MASK));
		helpContents.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_HELP_CONTENTS_NAME_DESCRIPTION));
		help.add(helpContents);
		
		//disables the Unnecessary MenuItem
		fileClose.setEnabled(false);
		
	}
	
	public JMenuItem getFileOpen() {
		return fileOpen;
	}

	public JMenuItem getFileClose(){
		return fileClose;
	}
	
	public JMenuItem getQuit(){
		return quit;
	}
	
	public JMenuItem getRemovePerspective(){
		return removePerspective;
	}
	
	public JMenuItem getHelpContents(){
		return helpContents;
	}
}
