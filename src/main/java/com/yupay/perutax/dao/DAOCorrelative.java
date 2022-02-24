package com.yupay.perutax.dao;

import com.yupay.perutax.entities.Correlative;
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
}
