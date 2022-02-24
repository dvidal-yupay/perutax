package com.yupay.perutax.dao;

import com.yupay.perutax.entities.Subdiary;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * DAO implementation for Subdiary entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAOSubdiary extends DAOBase<Subdiary, DAOSubdiary> {
    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see DAO#subdiary()
     */
    DAOSubdiary() {
    }

    @Override
    protected @NotNull Class<Subdiary> tClass() {
        return Subdiary.class;
    }

    @Override
    public @NotNull DAOSubdiary specialize() {
        return this;
    }

    @Override
    protected @NotNull @Unmodifiable Object id(@NotNull Subdiary item) {
        return item.getId();
    }
}
