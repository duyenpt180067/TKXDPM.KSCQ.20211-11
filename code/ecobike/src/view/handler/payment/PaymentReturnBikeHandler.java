package view.handler.payment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import common.exception.UnknownException;
import controller.PaymentController;
import controller.ReturnBikeController;
import entity.dockbike.Bike;
import entity.payment.Invoice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Configs;
import view.handler.BaseScreenHandler;

public class PaymentReturnBikeHandler extends BaseScreenHandler  {
	
	private Invoice invoice;
	
	@FXML 
	Text type;
	
	@FXML
	Text multiple;
	
	@FXML
	Text time;
	
	@FXML
	Text amount;
	
	@FXML
	Button btnConfirmPayment;
	
	public PaymentReturnBikeHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
		super(stage, screenPath);
		this.invoice = invoice;
		this.setScreenTitle("Return Bike Screen");
		cardNumber.setText("kscq1_group11_2021");
		cardHolder.setText("Group 11");
		expirationDate.setText("11/25");
//		btnConfirmPayment.setOnMouseClicked(e -> {
//			try {
//				confirmToPayOrder();
//			} catch (Exception exp) {
//				System.out.println(exp);
//			}
//		});
	}
	
	public void display() {
//		display invoice of rent info
		Map<String, String> infoMap = invoice.getReturnInfo();
		type.setText(infoMap.get("TYPE"));
		
		multiple.setText(infoMap.get("MULTI"));
		time.setText(infoMap.get("TIME"));
		amount.setText(infoMap.get("AMOUNT"));
		
//get transaction and display info of card		
	}
	
	@FXML 
	ChoiceBox<String> bankName;
	
	@FXML
	TextField cardNumber;
	
	@FXML
	TextField cardHolder;
	
	@FXML
	TextField expirationDate;
	
	@FXML
	TextField securityCode;
	
	@FXML
	public void confirmToPayOrder() throws UnknownException, Exception {
		String contents = "pay order";
		setBController(new PaymentController());
		PaymentController ctrl = (PaymentController) getBController();
		Map<String, String> response = ctrl.payOrder(0, contents, cardNumber.getText(), cardHolder.getText(),
				expirationDate.getText(), securityCode.getText());

		BaseScreenHandler resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH, response.get("RESULT"), response.get("MESSAGE") );
		resultScreen.setPreviousScreen(this);
		resultScreen.setHomeScreenHandler(homeScreenHandler);
		resultScreen.setScreenTitle("Result Screen");
		resultScreen.show();
		
	}
	
	public PaymentController getBController() {
		return (PaymentController) super.getBController();
	}

}
