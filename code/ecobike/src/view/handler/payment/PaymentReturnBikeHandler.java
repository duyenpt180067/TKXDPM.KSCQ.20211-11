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
		String contentsPay = "pay";
		String contentsRefund = "refund";
		setBController(new PaymentController());
		PaymentController ctrl = (PaymentController) getBController();
		int amount = invoice.getRentInfo().getDepositAmount() - invoice.getAmount();
		Map<String, String> response;
		if(amount < 0) {
			response = ctrl.pay(0, contentsPay, cardNumber.getText(), cardHolder.getText(),
					expirationDate.getText(), securityCode.getText(), this.invoice);
		}
		else {
			response = ctrl.refund(amount, contentsRefund, cardNumber.getText(), cardHolder.getText(),
					expirationDate.getText(), securityCode.getText(), this.invoice);
		}
		BaseScreenHandler resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH, response.get("RESULT"), response.get("MESSAGE"), response.get("AMOUNT"));
		resultScreen.setPreviousScreen(this);
		resultScreen.setHomeScreenHandler(homeScreenHandler);
		resultScreen.setScreenTitle("Result Screen");
		resultScreen.show();
	}
	
	
	
	public PaymentController getBController() {
		return (PaymentController) super.getBController();
	}

}
