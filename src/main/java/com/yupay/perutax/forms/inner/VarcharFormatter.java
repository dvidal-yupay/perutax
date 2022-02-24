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
 * A text formatter to enforce user inputs
 * for a varchar database field, so the user
 * input will be striped up to a maximum length,
 * and then upper cased.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class VarcharFormatter
        extends TextFormatter<String> {
    /**
     * Default constructor that takes a maximum
     * length and upper case flag.
     *
     * @param length maximum length allowed.
     * @param upper  upper case flag. If true, every input
     *               will be upper cased.
     */
    public VarcharFormatter(int length, boolean upper) {
        super(
                new VarcharConverter(length, upper),
                "",
                c -> {
                    var nTxt = c.getControlNewText().strip();
                    if (nTxt.isBlank()) return c;
                    if (nTxt.length() > length) return null;
                    if (upper) c.setText(c.getText().toUpperCase());
                    return c;
                });
    }
}
