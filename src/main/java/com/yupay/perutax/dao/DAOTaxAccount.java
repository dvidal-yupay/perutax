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

import com.yupay.perutax.entities.TaxAccount;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

/**
 * DAO ipmlementation for TaxAccount entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAOTaxAccount extends DAOBase<TaxAccount, DAOTaxAccount> {
    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see DAO#taxAccount()
     */
    DAOTaxAccount() {
    }

    @Override
    protected @NotNull Class<TaxAccount> tClass() {
        return TaxAccount.class;
    }

    @Override
    public @NotNull DAOTaxAccount specialize() {
        return this;
    }

    @Override
    protected @NotNull @Unmodifiable Object id(@NotNull TaxAccount item) {
        return item.getId();
    }

    /**
     * Searches all tax accounts with a given text. The search
     * will query for accounts with ID starting with text OR
     * name containing text. Only usable active accounts will
     * be fetched from database.
     *
     * @param text the text to search.
     * @return the result list.
     */
    public @NotNull @Unmodifiable List<TaxAccount>
    search(@NotNull String text) {
        var em = DAOSource.manager();
        try {
            var qry = em.createQuery(
                    "SELECT A FROM TaxAccount A " +
                            "WHERE (A.id LIKE :myID " +
                            "OR A.name LIKE :myName) " +
                            "AND A.usable = TRUE AND A.trash = FALSE",
                    TaxAccount.class);
            qry.setParameter("myID", text + "%");
            qry.setParameter("myName", "%" + text + "%");
            return qry.getResultList();
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
