package view.handler.payment;

import java.io.IOException;
import java.util.Map;

import common.exception.UnknownException;
import controller.PaymentController;
import entity.payment.CreditCard;
import entity.payment.Invoice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
	
	public PaymentReturnBikeHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
		super(stage, screenPath);
		this.invoice = invoice;
		CreditCard card = invoice.getRentInfo().getDepositCard();
		this.setScreenTitle("Return Bike Screen");
		cardNumber.setText(card.getCardCode());
		cardHolder.setText(card.getOwner());
		expirationDate.setText("11/25");
	}

	/**
	 * display invoice of rent info
	 */
	public void display() {
		Map<String, String> infoMap = invoice.getReturnInfo();
		type.setText(infoMap.get("TYPE"));
		multiple.setText(infoMap.get("MULTI"));
		time.setText(infoMap.get("TIME"));
		amount.setText(infoMap.get("AMOUNT"));
	}
	
	@FXML
	public void confirmToPayOrder(MouseEvent event) throws UnknownException, Exception {
		setBController(new PaymentController());
		PaymentController ctrl = (PaymentController) getBController();
		int amount = invoice.getRentInfo().getDepositAmount() - invoice.getAmount();
		Map<String, String> response;
		if(amount < 0) {
			response = ctrl.pay(amount, cardNumber.getText(), cardHolder.getText(),
					expirationDate.getText(), securityCode.getText(), this.invoice);
		}
		else {
			response = ctrl.refund(amount, cardNumber.getText(), cardHolder.getText(),
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
