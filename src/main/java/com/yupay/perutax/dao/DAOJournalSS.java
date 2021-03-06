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

import com.yupay.perutax.entities.Journal;
import com.yupay.perutax.entities.JournalSnapshot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

/**
 * DAO implementation for Journal snapshot entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAOJournalSS extends DAOBase<JournalSnapshot, DAOJournalSS> {
    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see DAO#journalSS()
     */
    DAOJournalSS() {
    }

    @Override
    protected @NotNull Class<JournalSnapshot> tClass() {
        return JournalSnapshot.class;
    }

    @Override
    public @NotNull DAOJournalSS specialize() {
        return this;
    }

    @Override
    protected @NotNull @Unmodifiable Object id(@NotNull JournalSnapshot item) {
        return item.getId();
    }

    /**
     * Fetches the journal entity for the given snapshot.
     *
     * @param object the snapshot object.
     * @return the journal entity, or null if not exists.
     * Since JournalSnapshot objects are a view, it's unlikely
     * that in any normal use case it would return null. It's
     * marked as nullable because in a test environmente
     * it could happen.
     */
    public @Nullable Journal fetchJournal(@NotNull JournalSnapshot object) {
        var em = DAOSource.manager();
        try {
            return em.find(Journal.class, object.getId());
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
