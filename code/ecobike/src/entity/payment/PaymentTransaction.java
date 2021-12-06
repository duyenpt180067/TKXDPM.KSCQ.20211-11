package entity.payment;

import entity.rental.RentInfo;

import java.util.Date;

public class PaymentTransaction {

	private int id;

	private String method;

	private String content;

	private Date createAt;

	private int amount;

	private RentInfo rentInfo;

	private CreditCard creditCard;

	/**
	 * Save its object in database
	 */
	public void save() {

	}

}
