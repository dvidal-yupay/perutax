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

import javafx.scene.control.TextFormatter;

/**
 * A text formatter that allows numeric only characters.
 * Will allow a maximum length.
 * Example: {@link new NumericFormatter(10);} will allow
 * the user to enter up to 10 numeric characters, any blank
 * space, or character other than digit, will be ignored.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class NumericFormatter extends TextFormatter<String> {
    /**
     * The default constructor.
     *
     * @param length maximum length of resulting text.
     */
    public NumericFormatter(int length) {
        super(
                new NumericConverter(length),
                "",
                c -> {
                    var m = c.getControlNewText().strip();
                    //If control text will be blank, do nothing
                    if (m.isBlank()) return c;
                    //If any character is not a digit, cancel editing.
                    if (m.codePoints()
                            .anyMatch(i -> !Character.isDigit(i)))
                        return null;
                    //If new control text is longer than length, cancel editing.
                    //Also, if max length is 0 or less, then should ignore it.
                    if (length > 0 && m.length() > length) return null;
                    //If all is OK, continue with edition.
                    return c;
                }
        );
    }
}
