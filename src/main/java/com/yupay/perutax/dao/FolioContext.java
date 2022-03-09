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

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Each folio context represents a scope where
 * the type of folio may be used, and is associated
 * with a boolean field on the
 * {@link  com.yupay.perutax.entities.TypeFolio} entity,
 * which is a flag to find and fetch types of folio.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public enum FolioContext {

    /**
     * The PLAME id column.
     */
    PLAME("plame_id"),
    /**
     * The context for purchases records.
     */
    PURCHASE("ctxt_purchase"),
    /**
     * The context for sales records.
     */
    SALE("ctxt_sale"),
    /**
     * The context for foreigners operations.
     */
    FOREIGN("ctxt_foreign"),
    /**
     * The context for taxation credit.
     */
    TAX_CREDIT("ctxt_tax_credit");
    /**
     * The column (field) name where to find.
     */
    public final String column;

    /**
     * Default constructor.
     *
     * @param column the column (field) name where to filter.
     */
    FolioContext(String column) {
        this.column = column;
    }

    /**
     * Function to map a context with a JPA predicate.
     *
     * @param cb   the criteria builder object.
     * @param root the criteria API root object.
     * @return a new mapping function.
     */
    @Contract(pure = true, value = "_,_->new")
    public static @NotNull Function<FolioContext, Predicate> toCriteria(
            @NotNull CriteriaBuilder cb,
            @NotNull Root<?> root) {
        return p -> {
            if (p == null) return null;
            else if (p == PLAME) return cb.isNotNull(root.get(p.column));
            else return cb.isTrue(root.get(p.column));
        };
    }
}
