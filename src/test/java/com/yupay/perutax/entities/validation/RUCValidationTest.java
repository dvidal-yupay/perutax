/*
 *  Perutax - Taxation software for Peru.
 *     Copyright (C) 2021-2022  Ingenieria Informatica Yupay SACS
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
