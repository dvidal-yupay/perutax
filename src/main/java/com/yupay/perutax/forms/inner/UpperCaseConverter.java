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
/**
 * Converter to parse and format to upper case.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class UpperCaseConverter extends StringConverter<String> {
    /**
     * Convenient method to process the strings.
     *
     * @param value the value to process.
     * @return {@code value.toUpperCase}
     */
    @Contract("null->!null")
    private @NotNull String call(@Nullable String value) {
        return value == null ? "" : value.toUpperCase();
    }

    @Override
    public String toString(@Nullable String object) {
        return call(object);
    }

    @Override
    public String fromString(@Nullable String string) {
        return call(string);
    }
}
