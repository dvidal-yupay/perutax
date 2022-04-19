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

import java.math.BigDecimal;

/**
 * Decimal text formatter to format values masked as #,##0.00
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class AmountDecimalFormatter extends TextFormatter<BigDecimal> {

    /**
     * Empty default constructor.
     */
    public AmountDecimalFormatter() {
        super(new AmountDecimalConverter(), null,
                c -> {
                    var txt = c.getControlNewText().strip();
                    if (txt.isBlank()) return c;
                    if (txt.matches("[-+]?\\d*\\.?\\d{0,2}")) return c;
                    return null;
                });
    }
}
