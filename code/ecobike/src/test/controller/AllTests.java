package test.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ValidateExpirationDate.class, ValidateDate.class, ValidateCardNumber.class, ValidateCVVNumber.class, ValidateName.class})
public class AllTests {

}
