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
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;

/**
 * Text formatter that enforces only upper case input.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class UpperCaseFormatter extends TextFormatter<String> {
    /**
     * Default constructor that relies on
     * {@link TextFormatter#TextFormatter(StringConverter, Object, UnaryOperator)}
     * to create a new instance with {@link UpperCaseConverter} and that transforms
     * every single input character into upper case.
     */
    public UpperCaseFormatter() {
        super(new UpperCaseConverter(), "", c -> {
            c.setText(c.getText().toUpperCase());
            return c;
        });
    }
}
