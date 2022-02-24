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

import com.yupay.perutax.entities.AccountNature;
import com.yupay.perutax.entities.CostGroup;
import com.yupay.perutax.entities.Currenci;
import com.yupay.perutax.entities.TaxAccount;

import java.util.function.Function;

/**
 * The tax account parser is a function to read a txt file line
 * and parse each part from that. The file is a csv separated by
 * semicolon (;) or pipe (|), expecting the following structure:
 * <pre>
 *     {id}|{name}|{nature}|{currency}|{groupCost}|{usable}
 * </pre>
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class TaxAccountParser implements Function<String, TaxAccount> {
    @Override
    public TaxAccount apply(String s) {
        if (s == null || s.isBlank()) return null;
        var str = s.strip();
        var parts = str.split("[|;]", -1);
        var r = new TaxAccount();
        r.setId(parts[0]);
        r.setName(parts[1]);
        r.setNature(AccountNature.valueOf(parts[2]));
        r.setCurrency(Currenci.valueOf(parts[3]));
        if (!parts[4].isBlank()) r.setGroupCost(CostGroup.valueOf(parts[4]));
        r.setUsable(Boolean.parseBoolean(parts[5]));
        r.setTrash(false);
        return r;
    }
}
