package subsystem;

import common.exception.PaymentException;
import common.exception.UnknownException;
import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;

public interface InterbankInterface {
    /**
     * Pay order then return payment transaction
     * @param card - credit card used for payment
     * @param amount - the amount to pay
     * @param content - Have format: “The <rental fee/ deposit> for renting <bike type> <bike code> at <dock Id>”
     * @return PaymentTransaction if payment is successful
     * @throws PaymentException if responded with pre-defined error code
     * @throws UnknownException if responded with an unknown error code or something goes wrong
     */
    public abstract PaymentTransaction payOrder(CreditCard card, int amount, String content) throws PaymentException, UnknownException;

    /**
     * Refun and then return payment transaction
     * @param card - credit card used for payment
     * @param amount - the amount to pay
     * @param content - - Have format: “The refund for renting <bike type> <bike code> at <dock Id>”
     * @return PaymentTransaction if payment is successful
     * @throws PaymentException if responded with pre-defined error code
     * @throws UnknownException if responded with an unknown error code or something goes wrong
     */
    public abstract PaymentTransaction refund(CreditCard card, int amount, String content) throws PaymentException, UnknownException;

    /**
     *
     * @param card -  credit card, of which balance is inquired
     * @return
     * @throws PaymentException if responded with pre-defined error code
     * @throws UnknownException if responded with an unknown error code or something goes wrong
     */
    public abstract String getBalance(CreditCard card) throws PaymentException, UnknownException;
}
