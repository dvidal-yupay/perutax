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

import com.yupay.perutax.entities.TaxPeriod;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * Comparator to use in TaxPeriod entities sorting.
 * The sorting will be from latest period to oldest period,
 * based on year and month.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class PeriodComparator implements Comparator<TaxPeriod> {

    /**
     * Creates a comparator for descending order, which simply
     * returns the same value of compare multiplied by -1.
     *
     * @return a reversed comparator.
     */
    @Contract(pure = true, value = "->new")
    public @NotNull Comparator<TaxPeriod> descending() {
        return (o1, o2) -> compare(o1, o2) * -1;
    }

    @Override
    public int compare(TaxPeriod o1, TaxPeriod o2) {
        if (o1 == o2) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        if (o1.equals(o2)) return 0;
        if (o1.getId() == null && o2.getId() == null) return 0;
        if (o1.getId() == null && o2.getId() != null) return -1;
        if (o1.getId() != null && o2.getId() == null) return 1;
        return o1.getId().compareTo(o2.getId());
    }
}
