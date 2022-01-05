package test.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import entity.payment.CreditCard;


class ValidateDate {
	private CreditCard creditCard;

	@BeforeEach
	void setUp() throws Exception {
		creditCard = new CreditCard();
	}

	@ParameterizedTest
	@CsvSource({
		"11/12/2000, true",
		"  , false",
		"!= 12, false"
	})
	void test(String date, boolean expect) {
		boolean isValid = creditCard.validateDate(date, false);
		assertEquals(expect, isValid);
	}

}
