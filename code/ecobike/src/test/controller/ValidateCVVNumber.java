//package test.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//
//import controller.PaymentController;
//
//class ValidateCVVNumber {
//	private PaymentController paymentController;
//
//	@BeforeEach
//	void setUp() throws Exception {
//		paymentController = new PaymentController();
//	}
//
//	@ParameterizedTest
//	@CsvSource({
//		"2021, true",
//		"a13, false",
//		"  , false",
//		"!= 12, false"
//	})
//	void test(String cvvNumber, boolean expect) {
//		boolean isValid = paymentController.validateCVVNumber(cvvNumber);
//		assertEquals(expect, isValid);
//	}
//
//}
