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

package com.yupay.perutax.dao;

import com.yupay.perutax.entities.TaxPeriod;
import com.yupay.perutax.entities.XRate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * DAO implementation for eXcahnge Rate entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAOXRate extends DAOBase<XRate, DAOXRate> {
    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see DAO#xrate()
     */
    DAOXRate() {
    }

    @Override
    protected @NotNull Class<XRate> tClass() {
        return XRate.class;
    }

    @Override
    public @NotNull DAOXRate specialize() {
        return this;
    }

    @Override
    protected @NotNull @Unmodifiable Object id(@NotNull XRate item) {
        return item.getId();
    }

    /**
     * Fetches all eXchange rates within a given tax period.
     * An eXchange rate is within a tax period, if the
     * {@code xrate.taxDate} is within {@code period.dateFrom}
     * and {@code period.dateUntil}, inclusive range.
     *
     * @param period the tax period to find.
     * @return all eXchange rates within period.
     */
    public Stream<XRate> findInPeriod(@NotNull TaxPeriod period) {
        var em = DAOSource.manager();
        try {
            var ql = em.createQuery(
                    "SELECT X FROM XRate X " +
                            "WHERE X.taxDate >= :dFrom AND X.taxDate <= :dUntl",
                    XRate.class);
            ql.setParameter("dFrom", period.getDateFrom());
            ql.setParameter("dUntl", period.getDateUntil());
            return ql.getResultStream();
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    /**
     * Fetches the exchange rate for a given date.
     *
     * @param date the required rate date.
     * @return optional containing the result, empty if not found.
     */
    public @NotNull Optional<XRate> findForDate(@NotNull LocalDate date) {
        var em = DAOSource.manager();
        try {
            var ql = em.createQuery("SELECT X FROM XRate X " +
                            "WHERE X.taxDate = :aDate",
                    XRate.class);
            ql.setParameter("aDate", date);
            return ql.getResultStream().findAny();
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
