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

import com.yupay.perutax.entities.TypeFolio;

import java.util.function.Function;

import static java.lang.Boolean.parseBoolean;

/**
 * The type folio parser is a function to read a txt file line
 * and parse each part from that. The file is a csv separated by
 * semicolon (;), expecting the following structure:
 * <pre>
 *     {id};{title};{regexSerie};{regexNumber}:{ctxtPurchase};{ctxtSale};{ctxtForeign};{ctxtTaxCredit}
 * </pre>
 * Only ; should be amitted as a separator provided the nature of regular expressions.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class TypeFolioParser implements Function<String, TypeFolio> {
    @Override
    public TypeFolio apply(String s) {
        if (s == null || s.isBlank()) return null;
        var str = s.strip();
        var parts = str.split(";", -1);
        var r = new TypeFolio();
        r.setId(parts[0]);
        r.setTitle(parts[1].strip().toUpperCase());
        r.setRegexSerie(parts[2].strip());
        r.setRegexNumber(parts[3].strip());
        r.setCtxtPurchase(parseBoolean(parts[4].strip()));
        r.setCtxtSale(parseBoolean(parts[5].strip()));
        r.setCtxtForeign(parseBoolean(parts[6].strip()));
        r.setCtxtTaxCredit(parseBoolean(parts[7].strip()));
        r.setTrash(false);
        return r;
    }
}
