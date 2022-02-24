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

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The fixed decimal converter parses/formats a decimal with such many
 * decimal places as required by a scale factor. Will use rounding mode
 * {@link RoundingMode#HALF_UP}
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class FixedDecimalConverter extends StringConverter<BigDecimal> {
    /**
     * The scale expected on from/to String results.
     */
    private final int scale;

    /**
     * Default constructor.
     *
     * @param scale the scale on fromString an toString results.
     */
    public FixedDecimalConverter(int scale) {
        this.scale = scale;
    }

    @Override
    public String toString(BigDecimal object) {
        return object == null
                ? ""
                : scale < 1 ? "%.0f".formatted(object)
                : ("%." + scale + "f").formatted(object);
    }

    @Override
    public BigDecimal fromString(String string) {
        if (string == null || string.isBlank())
            return null;
        if (string.matches("[+-]?\\d*\\.?\\d*"))
            return new BigDecimal(string).setScale(scale, RoundingMode.HALF_UP);
        if (string.matches("[+-]?\\."))
            return BigDecimal.ZERO.setScale(scale, RoundingMode.HALF_UP);
        return null;
    }
}
