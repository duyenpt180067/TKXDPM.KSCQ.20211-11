package controller;

import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

import common.exception.PaymentException;
import common.exception.UnknownException;
import entity.payment.PaymentTransaction;
import subsystem.InterbankPayInterface;
import subsystem.InterbankRefundInterface;
import subsystem.InterbankSubsystem;
import utils.Utils;
import entity.payment.CreditCard;
import entity.payment.Invoice;

/**
 * {@code PaymentController} controls processes relating to payment between user and ecobike system
 * and between ecobike system and Interbank Interface
 */
public class PaymentController extends BaseController{
	
	private static Logger LOGGER = Utils.getLogger(PaymentController.class.getName());
	
	private CreditCard card;
	private InterbankPayInterface interbankPay;
	private InterbankRefundInterface interbankRefund;
	
	
	/**
	 * Forward payment request to InterBank and receive its result as payment transaction
	 * @param creditCard - card used for payment
	 * @param invoice - the invoice containing payment content
	 * @return PaymentTransaction - store the important payment information
	 * @throws PaymentException if response has predefined error code
	 * @throws UnknownException if error code is unknown or something goes wrong
	 */
	public PaymentTransaction processPayment(CreditCard creditCard, Invoice invoice)  {
		
		return null;
	}
	
	/**
	 * request to pay
	 * @param amount amount of transaction
	 * @param contents amount of transaction
	 * @param cardNumber of card used
	 * @param cardHolderName of card used
	 * @param expirationDate of card used
	 * @param securityCode of card used
	 * @return a result include status and message
	 * @throws UnknownException if error code is unknown or something goes wrong
	 * @throws PaymentException if response has predefined error code
	 * @throws Exception
	 */
	public Map<String, String> pay(int amount, String contents, String cardNumber, String cardHolderName,
			String expirationDate, String securityCode) throws UnknownException, PaymentException, Exception {
		Map<String, String> result = new Hashtable<String, String>();
		result.put("RESULT", "PAYMENT FAILED!");
		try {
			this.card = new CreditCard(cardNumber, cardHolderName, Integer.parseInt(securityCode),
					CreditCard.getExpirationDate(expirationDate));
			LOGGER.info("create card"+this.card.toString());
		
			this.interbankPay = new InterbankSubsystem();
			PaymentTransaction transaction = interbankPay.pay(card, amount, contents);

			result.put("RESULT", "PAYMENT SUCCESSFUL!");
			result.put("MESSAGE", "You have succesffully paid!");
			result.put("transaction", transaction.toString());
		} catch (PaymentException | UnknownError ex) {
			result.put("MESSAGE", ex.getMessage());
		}
		return result;
	}
	
	/**
	 * request to refund to card
	 * @param amount amount of transaction
	 * @param contents amount of transaction
	 * @param cardNumber of card used
	 * @param cardHolderName of card used
	 * @param expirationDate of card used
	 * @param securityCode of card used
	 * @return a result include status and message
	 * @throws UnknownException if error code is unknown or something goes wrong
	 * @throws PaymentException if response has predefined error code
	 * @throws Exception
	 */
	public Map<String, String> refund(int amount, String contents, String cardNumber, String cardHolderName,
			String expirationDate, String securityCode) throws UnknownException, Exception {
		Map<String, String> result = new Hashtable<String, String>();
		result.put("RESULT", "REFUND FAILED!");
		try {
			this.card = new CreditCard(cardNumber, cardHolderName, Integer.parseInt(securityCode),
					CreditCard.getExpirationDate(expirationDate));
			LOGGER.info("create card"+this.card.toString());
		
			this.interbankRefund = new InterbankSubsystem();
			PaymentTransaction transaction = interbankRefund.refund(card, amount, contents);

			result.put("RESULT", "REFUND SUCCESSFUL!");
			result.put("MESSAGE", "You have been successfully refunded!");
			result.put("transaction", transaction.toString());
		} catch (PaymentException | UnknownError ex) {
			result.put("MESSAGE", ex.getMessage());
		}
		return result;
	}

}
