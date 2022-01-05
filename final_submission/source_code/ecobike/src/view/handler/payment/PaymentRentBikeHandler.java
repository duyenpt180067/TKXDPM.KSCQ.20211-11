package view.handler.payment;

import java.io.IOException;
import java.util.Map;

import common.exception.InvalidCardException;
import common.exception.PaymentException;
import common.exception.UnknownException;
import controller.PaymentController;
import entity.payment.CreditCard;
import entity.payment.Invoice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import subsystem.interbank.InterbankSubsystemController;
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
		bController = new PaymentController();
		this.invoice = invoice;
		type.setText(invoice.getRentInfo().getBike().getType());
		cost.setText(invoice.getRentInfo().getBike().getPrice()+" VND");
		deposit.setText(invoice.getAmount()+" VND");
		cardNumber.setText("kscq1_group11_2021");
		cardHolder.setText("Group 11");
		expirationDate.setText("11/25");
		btnConfirmPayment.setOnMouseClicked(e -> {
			try {
				confirmToPayOrder(invoice);
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
	
	public void confirmToPayOrder(Invoice invoice) {
		try {
			setBController(new PaymentController());
			PaymentController ctrl = (PaymentController) getBController();
			CreditCard creditCard = new CreditCard(cardNumber.getText(), cardHolder.getText(), Integer.parseInt(securityCode.getText()), CreditCard.getExpirationDate(expirationDate.getText()));
			creditCard.validateCardInfo();
			Map<String, String> response = ctrl.deposit(invoice.getAmount(), creditCard, invoice);
			BaseScreenHandler resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH, response.get("RESULT"), response.get("MESSAGE"), response.get("AMOUNT"));
			resultScreen.setPreviousScreen(this);
			resultScreen.setHomeScreenHandler(homeScreenHandler);
			resultScreen.setScreenTitle("Result Screen");
			resultScreen.show();
		} catch (InvalidCardException e) {
			e.printStackTrace();
		} catch (UnknownException e) {
			e.printStackTrace();
		} catch (PaymentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
