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
import com.yupay.perutax.entities.functionals.TaxAccountAdjuster;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import static com.yupay.perutax.dao.BookConstants.JOURNAL;

/**
 * DAO implementation for Jounral entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAOJournal extends DAOBase<Journal, DAOJournal> {
    /**
     * Package private constructor.
     * Use static factory.
     *
     * @see DAO#journal()
     */
    DAOJournal() {
    }

    @Override
    protected @NotNull Class<Journal> tClass() {
        return Journal.class;
    }

    @Override
    public @NotNull DAOJournal specialize() {
        return this;
    }

    @Override
    protected @NotNull @Unmodifiable Object id(@NotNull Journal item) {
        return item.getId();
    }

    @Override
    public @NotNull Journal insertOne(@NotNull Journal item) {
        var em = DAOSource.manager();
        var tx = em.getTransaction();
        try {
            //Begin transaction.
            tx.begin();

            //Fetch or create correlative and upsert.
            var corr = DAO.correlative()
                    .specialize()
                    .findOrSupply(JOURNAL, item.getPeriod(), em);
            switch (item.getSubdiary().getRole()) {
                case A -> {
                    corr.stepA();
                    item.setCorrelative("A%09d".formatted(corr.getLastA()));
                }
                case M -> {
                    corr.stepM();
                    item.setCorrelative("M%09d".formatted(corr.getLastM()));
                }
                case C -> {
                    corr.stepC();
                    item.setCorrelative("C%09d".formatted(corr.getLastC()));
                }
            }
            em.merge(corr);

            //Update tax account balances.
            item.getDetail().forEach(new TaxAccountAdjuster(em, item.getCurrency(), item.getXrate()));

            //Sync redundant information
            item.getDetail().forEach(ln-> ln.setAccountName(ln.getAccount().getName()));

            //Insert journal entry and details.
            em.persist(item);

            //Commit transaction.
            tx.commit();
            return item;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
