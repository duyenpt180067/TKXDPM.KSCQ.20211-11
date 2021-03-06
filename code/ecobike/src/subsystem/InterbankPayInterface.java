package subsystem;

import common.exception.PaymentException;
import common.exception.UnknownException;
import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;

public interface InterbankPayInterface {
    /**
     * Pay order then return payment transaction
     * @param card - credit card used for payment
     * @param amount - the amount to pay
     * @param content - Have format: â€œThe <rental fee/ deposit> for renting <bike type> <bike code> at <dock Id>â€�
     * @return PaymentTransaction if payment is successful
     * @throws PaymentException if responded with pre-defined error code
     * @throws UnknownException if responded with an unknown error code or something goes wrong
     * @throws Exception 
     */
    public abstract PaymentTransaction pay(CreditCard card, int amount, String content) throws PaymentException, UnknownException, Exception;

}
