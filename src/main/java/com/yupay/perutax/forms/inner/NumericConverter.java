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

package com.yupay.perutax.forms.inner;

import javafx.util.StringConverter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * A string converter and unary operator that takes
 * a string and remove any non-digit character. Also
 * strips leading and trailing blank characters.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class NumericConverter
        extends StringConverter<String>
        implements UnaryOperator<String> {
    /**
     * The maximum length of the resulting text.
     */
    private final int length;

    /**
     * Default constructor.
     *
     * @param length maximum length.
     */
    public NumericConverter(int length) {
        this.length = length;
    }

    @Override
    @Contract("null->!null")
    public @NotNull String apply(String s) {
        if (s == null) return "";
        var m = s.codePoints()
                .filter(Character::isDigit)
                .mapToObj(Character::toString)
                .collect(Collectors.joining());
        return (length > 0 && m.length() > length)
                ? m.substring(0, length)
                : m;
    }

    @Override
    public String toString(String object) {
        return apply(object);
    }

    @Override
    public String fromString(String string) {
        return apply(string);
    }
}
