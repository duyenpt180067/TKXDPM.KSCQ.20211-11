package view.handler;

import java.io.IOException;
import java.util.Hashtable;

import controller.BaseController;
import javafx.scene.control.ButtonBar;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import view.handler.home.HomeScreenHandler;

public class BaseScreenHandler extends FXMLScreenHandler {

	protected Scene scene;
	protected BaseScreenHandler prev;
	protected final Stage stage;
	protected HomeScreenHandler homeScreenHandler;
	protected Hashtable<String, String> messages;
	protected BaseController bController;
	

	protected BaseScreenHandler(String screenPath) throws IOException {
		super(screenPath);
		
		this.stage = new Stage();
	}
	public  BaseScreenHandler(Stage stage, String screenPath) throws IOException {
		super(screenPath);
		this.stage = stage;
	}
	
	public void show() {
		if (this.scene == null) {
			this.scene = new Scene(this.content);
		}
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	public void setPreviousScreen(BaseScreenHandler prev) {
		this.prev = prev;
	}

	public BaseScreenHandler getPreviousScreen() {
		return null;
	}

	

	public void setBController(BaseController bController) {
		this.bController = bController;
	}

	public BaseController getBController() {
		return this.bController;
	}

	public void setHomeScreen(HomeScreenHandler homeScreen) {

	}
	
	
//	tham
	

	public void showDialog(String message, String title){
		Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle(title);
		dialog.setContentText(message);
		dialog.setHeight(100);
		dialog.setWidth(500);
		ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(type);
		dialog.showAndWait();
	}

	public void setScreenTitle(String string) {
		this.stage.setTitle(string);
	}


	public void setPrev(BaseScreenHandler prev) {
		this.prev = prev;
	}

	public void setHomeScreenHandler(HomeScreenHandler homeScreenHandler) {
		this.homeScreenHandler = homeScreenHandler;
	}

	public void setbController(BaseController bController) {
		this.bController = bController;
	}

	public Stage getStage() {
		return stage;
	}

	public BaseController getbController() {
		return bController;
	}

	

}
