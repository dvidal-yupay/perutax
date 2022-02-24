package com.yupay.perutax.dao;

import com.yupay.perutax.entities.Country;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * DAO implementation for Country entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAOCountry extends DAOBase<Country, DAOCountry> {
    /**
     * Package-private constructor.
     * Please use a static factory.
     *
     * @see DAO#country()
     */
    DAOCountry() {
    }

    @Override
    protected @NotNull Class<Country> tClass() {
        return Country.class;
    }

    @Override
    @Contract("->this")
    public @NotNull DAOCountry specialize() {
        return this;
    }

    @Override
    protected @NotNull @Unmodifiable Object id(@NotNull Country item) {
        return item.getId();
    }
}
