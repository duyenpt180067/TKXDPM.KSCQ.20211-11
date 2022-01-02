package view.handler.payment;

import java.io.IOException;
import java.util.Map;

import common.exception.UnknownException;
import controller.PaymentController;
import entity.payment.Invoice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Configs;
import view.handler.BaseScreenHandler;

public class PaymentRentBikeHandler extends BaseScreenHandler{
	
private Invoice invoice;
	
	@FXML 
	Text type;
	
	@FXML
	Text cost;
	
	@FXML
	Text deposit;
	
	@FXML
	Button btnConfirmPayment;
	
	public PaymentRentBikeHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
		super(stage, screenPath);
		this.invoice = invoice;

		cardNumber.setText("kscq1_group11_2021");
		cardHolder.setText("Group 11");
		expirationDate.setText("11/25");
		btnConfirmPayment.setOnMouseClicked(e -> {
			try {
				confirmToPayOrder();
//				((PaymentController) getBController()).emptyCart();
			} catch (Exception exp) {
				System.out.println(exp);
			}
		});
	}
	
	public void display() {
		
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
	
	public void confirmToPayOrder() throws UnknownException, Exception {
		String contents = "pay order";
		setBController(new PaymentController());
		PaymentController ctrl = (PaymentController) getBController();
		Map<String, String> response = ctrl.payOrder(invoice.getAmount(), contents, cardNumber.getText(), cardHolder.getText(),
				expirationDate.getText(), securityCode.getText());
//		Map<String, String> response = ctrl.payOrder(5, contents, "kscq1_group11_2021", "Group 11",
//				"11/25", "298");
		if(response.get("RESULT").toLowerCase().contains("fail")) {
			BaseScreenHandler resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH, response.get("RESULT"), response.get("MESSAGE") );
			resultScreen.showDialog(response.get("MESSAGE"),response.get("RESULT"));
		}
		else {
			BaseScreenHandler resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH, response, invoice);
			resultScreen.setPreviousScreen(this);
			resultScreen.setHomeScreenHandler(homeScreenHandler);
			resultScreen.setScreenTitle("Result Screen");
			resultScreen.show();
		}
		
	}


}
