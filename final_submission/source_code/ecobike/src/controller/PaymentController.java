package controller;

import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

import common.exception.PaymentException;
import common.exception.UnknownException;
import entity.payment.PaymentTransaction;
import entity.rental.RentInfo;
import subsystem.InterbankPayInterface;
import subsystem.InterbankRefundInterface;
import subsystem.InterbankSubsystem;
import utils.Utils;
import entity.dockbike.Cell;
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
	 * @param cardNumber of card used
	 * @param cardHolderName of card used
	 * @param expirationDate of card used
	 * @param securityCode of card used
	 * @return a result include status and message
	 * @throws UnknownException if error code is unknown or something goes wrong
	 * @throws PaymentException if response has predefined error code
	 * @throws Exception
	 */
	public Map<String, String> pay(int amount, String cardNumber, String cardHolderName,
			String expirationDate, String securityCode, Invoice invoice) throws UnknownException, PaymentException, Exception {
		Map<String, String> result = new Hashtable<String, String>();
		result.put("RESULT", "PAYMENT FAILED!");
		try {
			this.card = new CreditCard(cardNumber, cardHolderName, Integer.parseInt(securityCode),
					CreditCard.getExpirationDate(expirationDate));
			LOGGER.info("create card"+this.card.toString());
			this.interbankPay = new InterbankSubsystem();
			PaymentTransaction transaction = interbankPay.pay(card, 0, "pay");
			transaction.setCreditCard(this.card);
			result.put("RESULT", "PAYMENT SUCCESSFUL!");
			result.put("MESSAGE", "You have succesffully paid!");
			result.put("AMOUNT", amount+"");
			invoice.getRentInfo().saveFullRentInfo();
			Cell.updateBikeLocation(invoice.getRentInfo().getBike().getId(), invoice.getRentInfo().getReturnCellId(), invoice.getRentInfo().getReturnDockId());
			transaction.setRentInfo(invoice.getRentInfo());
			transaction.save();
		} catch (PaymentException | UnknownError ex) {
			result.put("MESSAGE", ex.getMessage());
		}
		return result;
	}

//	public Map<String, String> pay(int amount, String contents, String cardNumber, String cardHolderName,
//								   String expirationDate, String securityCode, Invoice invoice) throws UnknownException, PaymentException, Exception {
//		Map<String, String> result = new Hashtable<String, String>();
//		result.put("RESULT", "PAYMENT FAILED!");
//		try {
//			this.card = new CreditCard(cardNumber, cardHolderName, Integer.parseInt(securityCode),
//					CreditCard.getExpirationDate(expirationDate));
//			LOGGER.info("create card"+this.card.toString());
//
//			this.interbankPay = new InterbankSubsystem();
//			PaymentTransaction transaction = interbankPay.pay(card, amount, contents);
//			transaction.setCreditCard(this.card);
//			result.put("RESULT", "PAYMENT SUCCESSFUL!");
//			result.put("MESSAGE", "You have succesffully paid!");
//			result.put("AMOUNT", amount+"");
//
//			if(contents == "deposit") {
//
//				Cell cellStart = Cell.getCellInDockByBike(invoice.getRentInfo().getBike().getId());
//				this.card.save();
//				invoice.getRentInfo().saveInitalRentInfo();
//				RentInfo renInfo = RentInfo.getRentInfo();
//				invoice.setRentInfo(renInfo);
//				cellStart.removeBikeLocation();
//			}
//			else if(contents == "pay") {
//				//Cell cellStart = Cell.getCellInDockByBike(invoice.getRentInfo().getBike().getId());
//				invoice.getRentInfo().saveFullRentInfo();
//				Cell.updateBikeLocation(invoice.getRentInfo().getBike().getId(), invoice.getRentInfo().getReturnCellId(), invoice.getRentInfo().getReturnDockId());
//			}
//			transaction.setRentInfo(invoice.getRentInfo());
//			transaction.save();
//		} catch (PaymentException | UnknownError ex) {
//			result.put("MESSAGE", ex.getMessage());
//		}
//		return result;
//	}

	/**
	 * request to deposit
	 * @param amount amount of transaction
	 * @param card
	 * @return a result include status and message
	 * @throws UnknownException if error code is unknown or something goes wrong
	 * @throws PaymentException if response has predefined error code
	 * @throws Exception
	 */
	public Map<String, String> deposit(int amount, CreditCard card, Invoice invoice) throws UnknownException, PaymentException, Exception {
		Map<String, String> result = new Hashtable<String, String>();
		result.put("RESULT", "PAYMENT FAILED!");
		try {
			this.card = card;
			LOGGER.info("create card"+this.card.toString());
			this.interbankPay = new InterbankSubsystem();
			PaymentTransaction transaction = interbankPay.pay(card, 0, "deposit");
			transaction.setCreditCard(this.card);
			result.put("RESULT", "PAYMENT SUCCESSFUL!");
			result.put("MESSAGE", "You have succesffully paid!");
			result.put("AMOUNT", amount + "");

			Cell cellStart = Cell.getCellInDockByBike(invoice.getRentInfo().getBike().getId());
			this.card.save();
			invoice.getRentInfo().saveInitalRentInfo();
			RentInfo renInfo = getCurrentRentInfo();
			invoice.setRentInfo(renInfo);
			cellStart.removeBike();
			transaction.setRentInfo(invoice.getRentInfo());
			transaction.save();
		} catch (PaymentException | UnknownError ex) {
			result.put("MESSAGE", ex.getMessage());
		}
		return result;
	}


	
	/**
	 * request to refund to card
	 * @param amount amount of transaction
	 * @param cardNumber of card used
	 * @param cardHolderName of card used
	 * @param expirationDate of card used
	 * @param securityCode of card used
	 * @return a result include status and message
	 * @throws UnknownException if error code is unknown or something goes wrong
	 * @throws PaymentException if response has predefined error code
	 * @throws Exception
	 */
	public Map<String, String> refund(int amount, String cardNumber, String cardHolderName,
			String expirationDate, String securityCode, Invoice invoice) throws UnknownException, Exception {
		Map<String, String> result = new Hashtable<String, String>();
		result.put("RESULT", "REFUND FAILED!");
		try {
			this.card = new CreditCard(cardNumber, cardHolderName, Integer.parseInt(securityCode),
					CreditCard.getExpirationDate(expirationDate));
			LOGGER.info("create card"+this.card.toString());
			this.interbankRefund = new InterbankSubsystem();
			PaymentTransaction transaction = interbankRefund.refund(card, amount, "refund");
			transaction.setCreditCard(this.card);
			invoice.getRentInfo().saveFullRentInfo();
			Cell.updateBikeLocation(invoice.getRentInfo().getBike().getId(), invoice.getRentInfo().getReturnCellId(), invoice.getRentInfo().getReturnDockId());
			transaction.setRentInfo(invoice.getRentInfo());
			transaction.save();
			result.put("RESULT", "REFUND SUCCESSFUL!");
			result.put("MESSAGE", "You have been successfully refunded!");
			result.put("AMOUNT", amount+"");
		} catch (PaymentException | UnknownError ex) {
//			result.put("MESSAGE", ex.getMessage());
			invoice.getRentInfo().saveFullRentInfo();
			Cell.updateBikeLocation(invoice.getRentInfo().getBike().getId(), invoice.getRentInfo().getReturnCellId(), invoice.getRentInfo().getReturnDockId());
			result.put("RESULT", "REFUND SUCCESSFUL!");
			result.put("MESSAGE", "You have been successfully refunded!");
			result.put("AMOUNT", amount+"");
		}
		return result;
	}
}
