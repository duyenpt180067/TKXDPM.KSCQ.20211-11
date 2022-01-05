package test.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import entity.payment.CreditCard;

class ValidateCVVNumber {
	private CreditCard creditCard;

	@BeforeEach
	void setUp() throws Exception {
		creditCard = new CreditCard();
	}

	@ParameterizedTest
	@CsvSource({
		"2021, true",
		"a13, false",
		"  , false",
		"!= 12, false"
	})
	void test(String cvvNumber, boolean expect) {
		boolean isValid = creditCard.validateCVVNumber(cvvNumber);
		assertEquals(expect, isValid);
	}

}
