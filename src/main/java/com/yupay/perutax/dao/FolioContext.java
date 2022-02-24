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
    PLAME("plameID"),
    /**
     * The context for purchases records.
     */
    PURCHASE("ctxtPurchase"),
    /**
     * The context for sales records.
     */
    SALE("ctxtSale"),
    /**
     * The context for foreigners operations.
     */
    FOREIGN("ctxtForeign"),
    /**
     * The context for taxation credit.
     */
    TAX_CREDIT("ctxtTaxCredit");
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
