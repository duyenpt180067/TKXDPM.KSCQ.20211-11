package controller;

import common.exception.InvalidFormInputException;
import common.exception.PaymentException;
import common.exception.UnknownException;
import entity.payment.PaymentTransaction;
import entity.payment.CreditCard;
import entity.payment.Invoice;

/**
 * {@code PaymentController} controls processes relating to payment between user and ecobike system
 * and between ecobike system and Interbank Interface
 */
public class PaymentController extends BaseController{
	/**
	 * Forward payment request to InterBank and receive its result as payment transaction
	 * @param creditCard - card used for payment
	 * @param invoice - the invoice containing payment content
	 * @return PaymentTransaction - store the important payment information
	 * @throws PaymentException if response has predefined error code
	 * @throws UnknownException if error code is unknown or something goes wrong
	 */
	public PaymentTransaction processPayment(CreditCard creditCard, Invoice invoice) throws PaymentException, UnknownException {
		return null;
	}

	/**
	 * @param info - card information submited by user
	 * @return 1 if all fields are filled with valid input
	 * @throws InvalidFormInputException if any required field is empty or input is invalid
	 */
	public int validatePaymentForm(CreditCard info) throws InvalidFormInputException {
		return 0;
	}
}
