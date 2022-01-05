package subsystem;

import common.exception.PaymentException;
import common.exception.UnknownException;
import entity.payment.CreditCard;

public interface InterbankBalanceInterface {
	/**
    *
    * @param card -  credit card, of which balance is inquired
    * @return
    * @throws PaymentException if responded with pre-defined error code
    * @throws UnknownException if responded with an unknown error code or something goes wrong
    */
   public abstract String getBalance(CreditCard card) throws PaymentException, UnknownException;
}
