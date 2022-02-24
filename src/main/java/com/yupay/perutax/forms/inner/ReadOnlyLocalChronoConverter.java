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

import com.yupay.perutax.forms.FormUtils;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

/**
 * Read-only string converter (only performs toString) to show
 * local dates as dd/MM/uuuu and local date times (usually for timestamps)
 * in format dd/MM/uuuu HH:mm:ss.SSS
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class ReadOnlyLocalChronoConverter<T extends Temporal>
        extends StringConverter<T> {
    @Override
    public String toString(T o) {
        if (o instanceof LocalDate ld) {
            return ld.format(FormUtils.DATE_ONLY);
        } else if (o instanceof LocalDateTime ldt) {
            return ldt.format(FormUtils.STAMP);
        } else {
            return "";
        }
    }

    @Override
    public T fromString(String string) {
        return null;
    }
}
