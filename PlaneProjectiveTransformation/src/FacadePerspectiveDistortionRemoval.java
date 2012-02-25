import java.util.Locale;
import java.util.ResourceBundle;

import fr.ecn.perspectiveDistortionRemoval.controller.MainMenuBarCloseFileController;
import fr.ecn.perspectiveDistortionRemoval.controller.MainMenuBarHelpController;
import fr.ecn.perspectiveDistortionRemoval.controller.MainMenuBarOpenFileController;
import fr.ecn.perspectiveDistortionRemoval.controller.MainMenuBarQuitController;
import fr.ecn.perspectiveDistortionRemoval.controller.MainMenuBarRemovePerspectiveController;
import fr.ecn.perspectiveDistortionRemoval.model.ImageModel;
import fr.ecn.perspectiveDistortionRemoval.view.FacadePerspectiveDistortionRemovalMainWindow;



public class FacadePerspectiveDistortionRemoval {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Manadge locale
		Locale currentLocale = new Locale("en");
		ResourceBundle stringValues = ResourceBundle.getBundle("stringvalues", currentLocale);

		if(args.length !=0){
			if(args.length == 1){
				String language = new String(args[0]);
				currentLocale = new Locale(language);		
				stringValues = ResourceBundle.getBundle("stringvalues", currentLocale);
			}else{
				String language = new String(args[0]);
				String country = new String(args[1]);
				currentLocale = new Locale(language, country);		
				stringValues = ResourceBundle.getBundle("stringvalues", currentLocale);
			}
		}
		
		//create the model
		//the image
		ImageModel imageModel = new ImageModel();
		
		//create the view
		// Main window declaration
		FacadePerspectiveDistortionRemovalMainWindow mainWindow = new FacadePerspectiveDistortionRemovalMainWindow(stringValues);
		
		//add Controllers and Listeners
		//Main Window
		//File Menu 
		//open 
		MainMenuBarOpenFileController menuBarOpenController = new MainMenuBarOpenFileController(imageModel, mainWindow.getMenuBarView().getFileOpen(), mainWindow);
		mainWindow.getMenuBarView().getFileOpen().addActionListener(menuBarOpenController);																																
		imageModel.addObserver(menuBarOpenController);
		
		//close
		MainMenuBarCloseFileController menuBarCloseController = new MainMenuBarCloseFileController(imageModel, mainWindow.getMenuBarView().getFileClose(), mainWindow);
		mainWindow.getMenuBarView().getFileClose().addActionListener(menuBarCloseController);																																
		imageModel.addObserver(menuBarCloseController);
		
		//quit
		MainMenuBarQuitController menuBarQuitController = new MainMenuBarQuitController(mainWindow);
		mainWindow.getMenuBarView().getQuit().addActionListener(menuBarQuitController);
		
		//Facade Menu
		//MainMenuBarRemovePerspectiveController menuBarRemovePerspectiveController = new MainMenuBarRemovePerspectiveController(imageModel, mainWindow.getMenuBarView().getRemovePerspective(), mainWindow, outputWindow, stringValues);
		MainMenuBarRemovePerspectiveController menuBarRemovePerspectiveController = new MainMenuBarRemovePerspectiveController(imageModel, mainWindow.getMenuBarView().getRemovePerspective(), mainWindow, stringValues);
		mainWindow.getMenuBarView().getRemovePerspective().addActionListener(menuBarRemovePerspectiveController);
		
		//Help Menu
		MainMenuBarHelpController menuBarHelpController = new MainMenuBarHelpController(mainWindow.getMenuBarView().getHelpContents(), mainWindow, stringValues);
		mainWindow.getMenuBarView().getHelpContents().addActionListener(menuBarHelpController);
		
	}

}
