package com.yupay.perutax.dao;

import com.yupay.perutax.entities.JournalSnapshot;
import org.jetbrains.annotations.NotNull;
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
}
