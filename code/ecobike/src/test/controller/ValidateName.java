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
//class ValidateName {
//	private PaymentController paymentController;
//
//	@BeforeEach
//	void setUp() throws Exception {
//		paymentController = new PaymentController();
//	}
//
//	@ParameterizedTest
//	@CsvSource({
//		"1/asd, false",
//		"  , false",
//		"ptduyen, true"
//	})
//	void test(String name, boolean expect) {
//		boolean isValid = paymentController.validateName(name);
//		assertEquals(expect, isValid);
//	}
//
//}
