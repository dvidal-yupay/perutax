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
