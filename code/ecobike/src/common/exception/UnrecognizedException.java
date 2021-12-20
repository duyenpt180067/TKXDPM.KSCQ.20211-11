package common.exception;

public class UnrecognizedException extends PaymentException {
	public UnrecognizedException() {
		super("ERROR: Something went wrowng!");
	}
}
