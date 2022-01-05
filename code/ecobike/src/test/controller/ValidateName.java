package test.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import entity.payment.CreditCard;

class ValidateName {
	private CreditCard creditCard;

	@BeforeEach
	void setUp() throws Exception {
		creditCard = new CreditCard();
	}

	@ParameterizedTest
	@CsvSource({
		"1/asd, false",
		"  , false",
		"ptduyen, true"
	})
	void test(String name, boolean expect) {
		boolean isValid = creditCard.validateName(name);
		assertEquals(expect, isValid);
	}

}
