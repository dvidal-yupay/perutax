/*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 ! Copyright (C) 2020 David Vidal Escudero - Free Open Source Software Consultancy
 !
 !       This program is free software: you can redistribute it and/or modify
 !       it under the terms of the GNU General Public License as published by
 !       the Free Software Foundation, either version 3 of the License, or
 !       (at your option) any later version.
 !
 !       This program is distributed in the hope that it will be useful,
 !       but WITHOUT ANY WARRANTY; without even the implied warranty of
 !       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 !       GNU General Public License for more details.
 !
 !       You should have received a copy of the GNU General Public License
 !       along with this program.  If not, see <https://www.gnu.org/licenses/>.
 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

package com.yupay.perutax.forms.inner;

import javafx.util.StringConverter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This is a smart date converter. If user input is only
 * a day between 1 to 31, returns the current month and year + that days.<br>
 * If user input is only day and month, adds the year.<br>
 * If user input is ddMMyy or ddMMyyyy accepts that format.<br>
 * If user input is dd-MM-yy or dd-MM-yyyy or d/m/yyyy accepts also that format.<br>
 * If user input is -x, then returns now.minusDays(x);<br>
 * If user input is +x, then returns now.plusDays(x);<br>
 * If user input is 0 or now, then returns now.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class SmartDateConverter extends StringConverter<LocalDate> {
    /**
     * The mask/pattern to format toString.
     */
    public static final String OUTPUT_MASK = "dd/MM/uuuu";

    @Override
    public String toString(LocalDate object) {
        return object == null
                ? ""
                : object.format(DateTimeFormatter.ofPattern(OUTPUT_MASK));
    }

    @Override
    public LocalDate fromString(String string) {
        var _string = string == null ? "" : string.strip();
        if (_string.matches("[1-9]|0[1-9]|[1-2]\\d|3[0-1]")) {
            return parseDD(_string);
        } else if (_string.matches("([1-9]|0[1-9]|[1-2]\\d|3[0-1])" +
                "([-/.·])([1-9]|0[1-9]|1[0-2])")) {
            return parseDD_MM(_string);
        } else if (_string.matches("([1-9]|0[1-9]|[1-2]\\d|3[0-1])" +
                "([-/.·])([1-9]|0[1-9]|1[0-2])([-/.·])(\\d{4}|\\d{2})")) {
            return parseDD_MM_YY(_string);
        } else if (_string.matches("(0[1-9]|[1-2]\\d|3[0-1])(0[1-9]|1[0-2])")) {
            return parseDDMM(_string);
        } else if (_string.matches("(0[1-9]|[1-2]\\d|3[0-1])(0[1-9]|1[0-2])(\\d{4}|\\d{2})")) {
            return parseDDMMYY(_string);
        } else if (_string.matches("\\+\\d+")) {
            return parsePlusD(_string);
        } else if (_string.matches("-\\d+")) {
            return parseMinusD(_string);
        } else if (_string.matches("0|hoy|today|now|ahora|-0|\\+0|ya")) {
            return LocalDate.now();
        } else {
            return null;
        }
    }

    /**
     * Parses from format DD as follows:
     * LocalDate.now, sets dayOfMonth to 1
     * and adds the DD number of days (minus 1).
     *
     * @param string the string with DD value.
     * @return local date.
     */
    private @NotNull LocalDate parseDD(String string) {
        return LocalDate.now()
                .withDayOfMonth(1)
                .plusDays(Integer.parseInt(string) - 1);
    }

    /**
     * Parses for dd(-/.·)mm
     *
     * @param string the value of day and month.
     * @return local date.
     */
    private @NotNull LocalDate parseDD_MM(@NotNull String string) {
        var parts = string.split("[-/.·]");
        return LocalDate.now()
                .withDayOfMonth(1)
                .withMonth(Integer.parseInt(parts[1]))
                .plusDays(Integer.parseInt(parts[0]) - 1);
    }

    /**
     * Parses dd.mm.yy or dd.mm.yyyy
     *
     * @param string dd.mm.yy(yy)
     * @return local date.
     */
    private @NotNull LocalDate parseDD_MM_YY(@NotNull String string) {
        var parts = string.split("[-/.·]");
        var year = Integer.parseInt(parts[2]);
        year += year < 100 ? 2000 : 0;
        return LocalDate.of(year, Integer.parseInt(parts[1]), 1)
                .plusDays(Integer.parseInt(parts[0]) - 1);
    }

    /**
     * Parses a string of length 4 containing ddmm.
     *
     * @param string the ddmm.
     * @return local date.
     */
    private @NotNull LocalDate parseDDMM(@NotNull String string) {
        var day = Integer.parseInt(string.substring(0, 2));
        var month = Integer.parseInt(string.substring(2, 4));
        return LocalDate.now()
                .withDayOfMonth(1)
                .withMonth(month)
                .plusDays(day - 1);
    }

    /**
     * Parses a string of length 6 or 8 containing ddmmyy or ddmmyyyy.
     * If yy, then adds 2000: EVERY YY WILL BE GREATER THAN 2000.
     *
     * @param string ddmmyy or ddmmyyyy
     * @return local date.
     */
    private @NotNull LocalDate parseDDMMYY(@NotNull String string) {
        var day = Integer.parseInt(string.substring(0, 2));
        var month = Integer.parseInt(string.substring(2, 4));
        var year = Integer.parseInt(string.substring(4));
        year += year < 100 ? 2000 : 0;
        return LocalDate.of(year, month, 1).plusDays(day - 1);
    }

    /**
     * Parses a date adding the +days in string.
     *
     * @param string +d
     * @return local date.
     */
    private @NotNull LocalDate parsePlusD(@NotNull String string) {
        return LocalDate.now().plusDays(Integer.parseInt(string.substring(1)));
    }

    /**
     * Parses a date substracting the -days in string.
     *
     * @param string -d
     * @return local date.
     */
    private @NotNull LocalDate parseMinusD(@NotNull String string) {
        return LocalDate.now().minusDays(Integer.parseInt(string.substring(1)));
    }
}
