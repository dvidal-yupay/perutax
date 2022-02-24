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

import java.util.function.Predicate;

/**
 * Function implementation to check RUC number validity.
 * It allows to verify if a RUC number is valid before sending
 * to the webservice, allowing for resource savings.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class RUCValidation implements Predicate<String> {
    /**
     * Factors to use in each position (left to right).
     */
    private static final int[] FACTORS = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};

    @Override
    public boolean test(String s) {
        if (s == null) return false;
        if (s.matches("((1[05-7])|20)\\d{9}")) {
            var digits = s.chars().map(i -> i - 48).toArray();
            var check = 0;
            for (int i = 0, end = digits.length - 1; i < end; i++) {
                check += FACTORS[i] * digits[i];
            }
            check %= 11;
            check %= 10;
            check = 11 - check;
            return check == digits[10];
        } else {
            return false;
        }
    }
}
