package fr.ecn.perspectiveDistortionRemoval.view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class OutputMenuBarView extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu file;
	private JMenuItem fileSave;
	private JMenuItem fileClose;
	
	
	public OutputMenuBarView(ResourceBundle param){
		// File menu
		file = new JMenu(param.getString(Constants.MENU_FILE_NAME));
		
		fileSave = new JMenuItem(param.getString(Constants.MENU_FILE_SAVE));
		fileClose = new JMenuItem(param.getString(Constants.MENU_FILE_CLOSE));
		// File menu
		file.setMnemonic(KeyEvent.VK_F);
		file.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_FILE_NAME_DESCRIPTION));
		this.add(file);


		fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.META_MASK));
		fileSave.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_FILE_SAVE_DESCRIPTION));
		file.add(fileSave);

				
		fileClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.META_MASK));
		fileClose.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_FILE_CLOSE_DESCRIPTION));
		file.add(fileClose);

		
	}
	
	public JMenuItem getFileSave() {
		return fileSave;
	}

	public JMenuItem getFileClose(){
		return fileClose;
	}
	
}
