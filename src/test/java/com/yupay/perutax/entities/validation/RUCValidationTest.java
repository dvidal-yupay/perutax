package com.yupay.perutax.entities.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests the function to locally validate RUC numbers.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
class RUCValidationTest {
    @Test
    void testRUC() {
        var validator = new RUCValidation();
        Assertions.assertTrue(validator.test("20604427909"));
        Assertions.assertTrue(validator.test("10700968133"));
        Assertions.assertFalse(validator.test("10700968132"));
        Assertions.assertFalse(validator.test("107009468133"));
        Assertions.assertFalse(validator.test("1068133"));
        Assertions.assertFalse(validator.test("10 68133"));
        Assertions.assertFalse(validator.test("10700968133 "));
        Assertions.assertFalse(validator.test(" 10700968133 "));
        Assertions.assertFalse(validator.test(null));
        Assertions.assertFalse(validator.test(""));
    }
}
