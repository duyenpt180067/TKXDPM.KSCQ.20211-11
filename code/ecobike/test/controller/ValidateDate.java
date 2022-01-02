package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateDate {
	private PaymentController payController;

	@BeforeEach
	void setUp() throws Exception {
		payController = new PayController();
	}

	@ParameterizedTest
	@CsvSource({
		"11/12/2000, true",
		"  , false",
		"!= 12, false"
	})
	void test(String date, boolean expect) {
		boolean isValid = payController.validateDate(date);
		assertEquals(expect, isValid);
	}

}
