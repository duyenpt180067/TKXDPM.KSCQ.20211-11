package test.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import entity.payment.CreditCard;

class ValidateCardNumber {
	private CreditCard creditCard;

	@BeforeEach
	void setUp() throws Exception {
		creditCard = new CreditCard();
	}

	@ParameterizedTest
	@CsvSource({
		"kscq1_group11_2021, true",
		"  , false",
		"!= 12, false"
	})
	void test(String cardNumber, boolean expect) {
		boolean isValid = creditCard.validateCardNumber(cardNumber);
		assertEquals(expect, isValid);
	}

}
