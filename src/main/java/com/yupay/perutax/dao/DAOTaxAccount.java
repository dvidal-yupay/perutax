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
