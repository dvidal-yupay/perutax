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

import com.yupay.perutax.entities.CostCenter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

/**
 * DAO implementation for Cost Center entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAOCostCenter extends DAOBase<CostCenter, DAOCostCenter> {
    /**
     * Package-private constructor.
     * Use static factory.
     *
     * @see DAO#costcenter()
     */
    DAOCostCenter() {
    }

    @Override
    protected @NotNull Class<CostCenter> tClass() {
        return CostCenter.class;
    }

    @Override
    public @NotNull DAOCostCenter specialize() {
        return this;
    }

    @Override
    protected @NotNull @Unmodifiable Object id(@NotNull CostCenter item) {
        return item.getId();
    }

    /**
     * Searches for any active cost center containing
     * the words s in the title, or starting its ID with s.
     *
     * @param s the text to search.
     * @return a list of result. NEVER NULL.
     */
    public @NotNull @Unmodifiable List<CostCenter> search(@NotNull String s) {
        var em = DAOSource.manager();
        try {
            var qry = em.createQuery(
                    "SELECT T FROM CostCenter T " +
                            "WHERE (T.id LIKE :myID OR T.title LIKE :myTitle) " +
                            "AND T.trash = FALSE",
                    CostCenter.class);
            qry.setParameter("myID", s + "%");
            qry.setParameter("myTitle", "%" + s + "%");
            return qry.getResultList();
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
