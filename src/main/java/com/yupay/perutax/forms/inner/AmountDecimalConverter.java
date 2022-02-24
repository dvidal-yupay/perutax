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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

import static com.yupay.perutax.forms.FormUtils.DF_PERU;

/**
 * String converter to parse/format BigDecimal values in format #,##0.00;###0.00
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class AmountDecimalConverter extends StringConverter<BigDecimal> {
    /**
     * Creates a BigDecimal text fromatter backed by this converter.
     *
     * @return a new text formatter.
     */
    @Contract("->new")
    public static @NotNull TextFormatter<BigDecimal> formatter() {
        return new TextFormatter<>(new AmountDecimalConverter(), new BigDecimal("0.00"));
    }

    @Override
    @Contract("null->!null")
    public @NotNull String toString(@Nullable BigDecimal object) {
        return object == null
                ? ""
                : DF_PERU.format(object);
    }

    @Override
    @Contract("null->null")
    public @Nullable BigDecimal fromString(String string) {
        if (string == null || string.isBlank()) return null;
        try {
            return DF_PERU.parse(string.strip()) instanceof BigDecimal bd
                    ? bd : BigDecimal.ZERO.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ParseException e) {
            return null;
        }
    }
}
