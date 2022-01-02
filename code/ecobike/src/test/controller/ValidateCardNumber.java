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
//class ValidateCardNumber {
//	private PaymentController paymentController;
//
//	@BeforeEach
//	void setUp() throws Exception {
//		paymentController = new PaymentController();
//	}
//
//	@ParameterizedTest
//	@CsvSource({
//		"kscq1_group11_2021, true",
//		"  , false",
//		"!= 12, false"
//	})
//	void test(String cardNumber, boolean expect) {
//		boolean isValid = paymentController.validateCardNumber(cardNumber);
//		assertEquals(expect, isValid);
//	}
//
//}
