package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

import common.exception.InvalidCardException;
import common.exception.InvalidFormInputException;
import common.exception.PaymentException;
import common.exception.UnknownException;
import common.exception.UnrecognizedException;
import entity.payment.PaymentTransaction;
import subsystem.InterbankInterface;
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
	private InterbankInterface interbank;
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
	
	public Map<String, String> payOrder(int amount, String contents, String cardNumber, String cardHolderName,
			String expirationDate, String securityCode) throws UnknownException, Exception {
		Map<String, String> result = new Hashtable<String, String>();
		result.put("RESULT", "PAYMENT FAILED!");
		try {
			this.card = new CreditCard(cardNumber, cardHolderName, Integer.parseInt(securityCode),
					getExpirationDate(expirationDate));
			LOGGER.info("create card"+this.card.toString());
			

			this.interbank = new InterbankSubsystem();
			PaymentTransaction transaction = interbank.payOrder(card, amount, contents);

			result.put("RESULT", "PAYMENT SUCCESSFUL!");
			result.put("MESSAGE", "You have succesffully paid the order!");
		} catch (PaymentException | UnknownError ex) {
			result.put("MESSAGE", ex.getMessage());
		}
		return result;
	}

	/**
	 * @param info - card information submited by user
	 * @return 1 if all fields are filled with valid input
	 * @throws InvalidFormInputException if any required field is empty or input is invalid
	 */
	public boolean validatePaymentForm(CreditCard info) throws InvalidFormInputException {
		return true;
	}
	
	/***
	 * validate date or expiration date
	 * @param date
	 * @return
	 */
	public boolean validateDate(String date, boolean isExpirationDate) {
    	if(date == null || date.trim().isEmpty() ) {
    		return false;
    	}
    	else {
    		if (date.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})"))
    			if(isExpirationDate) {
    				String[] eDate = date.split("/");
    				String pattern = "dd-MM-yyyy";
    				String now = new SimpleDateFormat(pattern).format(new Date());
    				String[] eNow = now.split("-");
					if ((eDate[2].compareTo(eNow[2]) < 0) 
							|| ((eDate[2].compareTo(eNow[2]) == 0) && (eDate[1].compareTo(eNow[1]) < 0))
							|| ((eDate[2].compareTo(eNow[2]) == 0) && (eDate[1].compareTo(eNow[1]) == 0) && (eDate[0].compareTo(eNow[0]) < 0))) {
						return false;
					}
					else {
						return true;
					}
    			}
    			else return true;
    		else
    		   return false;
    	}
    }
	
	/***
	 * validate card number
	 * @param cardNumber
	 * @return
	 */
	public boolean validateCardNumber(String cardNumber) {
    	if(cardNumber == null || cardNumber.trim().isEmpty() || cardNumber.length() > 50) {
    		return false;
    	}
    	else {
    		if (cardNumber.matches("([a-z0-9_-]){1,50}"))
    		    return true;
    		else
    		   return false;
    	}
    }
	
	/***
	 * validate name
	 * @param name
	 * @return
	 */
	public boolean validateName(String name) {
    	if(name == null || name.trim().isEmpty() || name.length() > 20) {
    		return false;
    	}
    	else {
    		if (name.matches("([a-z0-9_-]){1,20}"))
    		    return true;
    		else
    		   return false;
    	}
    }
	
	/***
	 * validate cvv number
	 * @param cvv
	 * @return
	 */
	public boolean validateCVVNumber(String cvv) {
		if(cvv == null || cvv.trim().isEmpty() || cvv.length() > 20) {
    		return false;
    	}
    	else {
    		if (cvv.matches("([0-9]){1,20}"))
    		    return true;
    		else
    		   return false;
    	} 
	}
	
	private String getExpirationDate(String date) throws InvalidCardException {
		String[] strs = date.split("/");
		if (strs.length != 2) {
			throw new InvalidCardException();
		}

		String expirationDate = null;
		int month = -1;
		int year = -1;

		try {
			month = Integer.parseInt(strs[0]);
			year = Integer.parseInt(strs[1]);
			if (month < 1 || month > 12 || year < Calendar.getInstance().get(Calendar.YEAR) % 100 || year > 100) {
				throw new InvalidCardException();
			}
			expirationDate = strs[0] + strs[1];

		} catch (Exception ex) {
			throw new InvalidCardException();
		}

		return expirationDate;
	}

}
