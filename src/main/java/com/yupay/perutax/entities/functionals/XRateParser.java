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

package com.yupay.perutax.entities.functionals;

import com.yupay.perutax.entities.XRate;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * A function implementation to read a String representation
 * of an xrate, so this can be read from a txt source. The
 * file will contain 3 columns, which are: taxDate, ratePrch, rateSale,
 * Comma separated values with taxDate in format yyyy-MM-dd,
 * for examples:
 * <pre>
 *     2021-01-23,3.895,3.945
 *     2021-01-24,3.884,3.899
 *     2021-01-25,3.754,3.779
 * </pre>
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class XRateParser implements Function<String, XRate> {
    /**
     * Time formatter for LocalDate parsing on format uuuu-MM-dd.
     * Lazily initialized.
     */
    private static DateTimeFormatter TIME_FORMATTER;

    /**
     * Checks if TIME_FORMATTER has been initialized, initializes
     * if necessary before returning value.
     *
     * @return the TIME_FORMATTER.
     */
    private static @NotNull DateTimeFormatter formatter() {
        if (TIME_FORMATTER == null)
            TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        return TIME_FORMATTER;
    }

    @Override
    public XRate apply(String s) {
        if (s == null || s.isBlank()) return null;
        var str = s.strip();
        if (!str.matches("\\d{4}-\\d{2}-\\d{2}[,;|]\\d+\\.\\d+[,;|]\\d+\\.\\d+"))
            return null;

        var parts = str.split("[,;|]");
        var xrte = new XRate();
        xrte.setTaxDate(LocalDate.parse(parts[0], formatter()));
        xrte.setPrch(new BigDecimal(parts[1]));
        xrte.setSale(new BigDecimal(parts[2]));
        return xrte;
    }
}
