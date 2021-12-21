package test.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controller.PaymentController;

class ValidateDate {
	private PaymentController paymentController;

	@BeforeEach
	void setUp() throws Exception {
		paymentController = new PaymentController();
	}

	@ParameterizedTest
	@CsvSource({
		"11/12/2000, true",
		"  , false",
		"!= 12, false"
	})
	void test(String date, boolean expect) {
		boolean isValid = paymentController.validateDate(date, false);
		assertEquals(expect, isValid);
	}

}
