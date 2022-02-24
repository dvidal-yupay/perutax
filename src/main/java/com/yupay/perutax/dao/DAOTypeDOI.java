package com.yupay.perutax.dao;

import com.yupay.perutax.entities.TypeDOI;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * DAO implementation for Type of DOI entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAOTypeDOI extends DAOBase<TypeDOI, DAOTypeDOI> {
    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see DAO#typeDOI()
     */
    DAOTypeDOI() {
    }

    @Override
    protected @NotNull Class<TypeDOI> tClass() {
        return TypeDOI.class;
    }

    @Override
    @Contract("->this")
    public @NotNull DAOTypeDOI specialize() {
        return this;
    }

    @Override
    protected @NotNull @Unmodifiable Object id(@NotNull TypeDOI item) {
        return item.getId();
    }
}
