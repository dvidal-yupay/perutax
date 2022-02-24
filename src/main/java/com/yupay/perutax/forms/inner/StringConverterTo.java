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

import java.util.function.Function;

/**
 * A string converter that only performs toString conversions.
 * This is useful for read-only table columns.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class StringConverterTo<T> extends StringConverter<T> {
    /**
     * The string function to map a T into String.
     * The value passed to the function is never null.
     */
    private final Function<T, String> mapper;

    /**
     * Default constructor.
     *
     * @param mapper function to map a T into String.
     */
    public StringConverterTo(Function<T, String> mapper) {
        this.mapper = mapper;
    }

    @Override
    public String toString(T object) {
        return object == null
                ? ""
                : mapper.apply(object);
    }

    @Override
    public T fromString(String string) {
        return null;
    }
}
