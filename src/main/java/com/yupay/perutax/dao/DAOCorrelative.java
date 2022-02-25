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

import com.yupay.perutax.entities.Correlative;
import com.yupay.perutax.entities.TaxPeriod;
import jakarta.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * DAO implementation for Correlative entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAOCorrelative extends DAOBase<Correlative, DAOCorrelative> {
    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see DAO#correlative()
     */
    DAOCorrelative() {
    }

    @Override
    protected @NotNull Class<Correlative> tClass() {
        return Correlative.class;
    }

    @Override
    public @NotNull DAOCorrelative specialize() {
        return this;
    }

    @Override
    protected @NotNull @Unmodifiable Object id(@NotNull Correlative item) {
        return item.getId();
    }

    /**
     * Fetches a correlative from database for
     * given book and period. If not found, a
     * new one will be created.
     *
     * @param book   the book code.
     * @param period the tax period.
     * @param em     the active entity manager.
     * @return fetched correlative, or a new one if not found.
     */
    @NotNull Correlative findOrSupply(@NotNull String book,
                                      @NotNull TaxPeriod period,
                                      @NotNull EntityManager em) {
        var qry = em.createQuery(
                "SELECT C FROM Correlative C" +
                        " WHERE C.period = :period " +
                        "AND C.book = :book",
                Correlative.class);
        qry.setParameter("period", period);
        qry.setParameter("book", book);
        return qry.getResultStream()
                .findFirst()
                .orElseGet(() -> {
                    var x = new Correlative();
                    x.setBook(book);
                    x.setPeriod(period);
                    x.setLastA(0);
                    x.setLastM(0);
                    x.setLastC(0);
                    return x;
                });
    }
}
