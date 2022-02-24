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
 * A formatter for a fixed decimal, stating a scale.
 * The numbers will be rounded with {@link  java.math.RoundingMode#HALF_UP}
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class FixedDecimalFormatter extends TextFormatter<BigDecimal> {
    /**
     * Default constructor.
     *
     * @param scale a scale for number parsing or formatting.
     */
    public FixedDecimalFormatter(int scale) {
        super(new FixedDecimalConverter(scale), null, c -> {
            var str = c.getControlNewText().strip();
            if (str.isBlank()) return c;
            if (str.matches("[-+]?\\d*\\.?\\d*")) return c;
            return null;
        });
    }
}
