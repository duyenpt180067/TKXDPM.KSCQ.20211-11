package view.handler.payment;

import java.io.IOException;
import java.util.Map;

import entity.payment.Invoice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
//import entity.payment.Invoice;
import javafx.stage.Stage;
import utils.Configs;
import view.handler.BaseScreenHandler;
import view.handler.home.HomeScreenHandler;

/**
 * Define the behaviour of ResultScreen
 */
public class ResultScreenHandler extends BaseScreenHandler{
	
	@FXML Text titleResult;
	 
	@FXML Text contentResult;
	
	@FXML Text amountResult;
	 
//	@FXML Text account;
//	 
//	@FXML Text bank;
//	 
//	@FXML Text service;
//	 
//	 
//	@FXML Text content;
	 
	@FXML Button homeBackBtn;

	/**
	 * Display ResultScreen notifying user the result got from InterBank
	 * @param mode - either "success" or "fail"
	 */
	public void display(String mode) {

	}

	public ResultScreenHandler(Stage stage, String screenPath, String status, String message, String amount) throws IOException {
		super(stage, screenPath);
		titleResult.setText(status);
		contentResult.setText(message);
		amountResult.setText(amount);
	}
	public ResultScreenHandler(Stage stage, String screenPath, Map<String, String> response, Invoice invoice) throws IOException {
		super(stage, screenPath);
		titleResult.setText(response.get(""));
		contentResult.setText(response.get(""));
	}
	
	@FXML
	void backToHome(MouseEvent event) throws IOException {
		HomeScreenHandler homeHandler = new HomeScreenHandler(stage, Configs.HOME_PATH);
	    homeHandler.setScreenTitle("Home Screen");
	    homeHandler.show();
	}
}
