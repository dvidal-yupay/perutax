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
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.text.ParseException;

import static com.yupay.perutax.forms.FormUtils.DF_RATE;
import static java.math.RoundingMode.HALF_UP;

/**
 * String converter implementation to parse and format 0.00% values.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class RateDecimalConverter extends StringConverter<BigDecimal> {
    @Override
    @Contract("null->!null")
    @NotNull
    public String toString(@Nullable BigDecimal object) {
        return object == null
                ? ""
                : DF_RATE.format(object);
    }

    @Override
    @Contract("null->null")
    @Nullable
    public BigDecimal fromString(@Nullable String string) {
        if (string == null || string.isBlank()) return null;
        try {
            var txt = string.strip();
            if (!txt.endsWith("%")) txt += "%";
            return DF_RATE.parse(txt) instanceof BigDecimal bd
                    ? bd : BigDecimal.ZERO.setScale(4, HALF_UP);
        } catch (ParseException e) {
            return null;
        }
    }
}
