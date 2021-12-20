package entity.payment;

import entity.rental.RentInfo;

import java.util.Date;


public class PaymentTransaction {

	private int id;

	private String method;

	private String content;

	private String createAt;

	private int amount;

	private RentInfo rentInfo;

	private CreditCard creditCard;
	private String errorCode;

	/**
	 * Save its object in database
	 */
	public void save() {

	}
	
	public PaymentTransaction(String errorCode, CreditCard creditCard, String transactionId, String transactionContent,
			int amount, String createdAt) {
		super();
		this.errorCode = errorCode;
		this.creditCard = creditCard;
		this.id = id;
		this.content = transactionContent;
		this.amount = amount;
		this.createAt = createdAt;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public static PaymentTransaction gePaymentTransactionFromRentInfoId() {
		return null;
	}
	
	
}
