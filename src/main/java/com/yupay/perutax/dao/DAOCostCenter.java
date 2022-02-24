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
