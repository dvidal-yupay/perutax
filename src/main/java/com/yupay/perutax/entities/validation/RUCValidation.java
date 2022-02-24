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
