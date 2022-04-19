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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.stream.Stream;

/**
 * This is the abstract root Data access object.
 * This is abstract to extract those common operations
 * of a CRUD application, and allowing each implementation
 * to have their specialized use cases.
 *
 * @param <T> the type erasure of entity.
 * @param <U> type erasure of implementation.
 */
public abstract sealed class DAOBase<T, U extends DAOBase<T, U>> permits DAOCorrelative, DAOCostCenter, DAOCountry, DAOJournal, DAOJournalSS, DAOMUnit, DAOPerson, DAOSaleScheme, DAOSubdiary, DAOTaxAccount, DAOTaxPeriod, DAOTypeDOI, DAOTypeFolio, DAOXRate {
    /**
     * The class representing the entity.
     *
     * @return entity class.
     */
    @NotNull
    protected abstract Class<T> tClass();

    /**
     * The specialized (implementation) object.
     *
     * @return this.
     */
    @NotNull
    @Contract("->this")
    public abstract U specialize();

    /**
     * Extracts the ID from an item.
     *
     * @param item the item.
     * @return the item id.
     */
    @NotNull
    @Unmodifiable
    protected abstract Object id(@NotNull T item);

    /**
     * Finds all elements in database, without any
     * kind of filter.
     *
     * @return all elements stored in database.
     */
    @NotNull
    public Stream<T> findAll() {
        var em = DAOSource.manager();
        try {
            var cb = em.getCriteriaBuilder();
            var cq = cb.createQuery(tClass());
            var root = cq.from(tClass());
            var all = cq.select(root);
            return em.createQuery(all).getResultStream();
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    /**
     * Finds all elements in database with trash flag = FALSE.
     *
     * @return all active elements (not in trash).
     */
    @NotNull
    public Stream<T> findActive() {
        var em = DAOSource.manager();
        try {
            var cb = em.getCriteriaBuilder();
            var cq = cb.createQuery(tClass());
            var root = cq.from(tClass());
            var all = cq.select(root).where(cb.isFalse(root.get("trash")));
            return em.createQuery(all).getResultStream();
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    /**
     * Persits one new item into the database.
     *
     * @param item the new item.
     * @return persisted item.
     */
    public @NotNull T insertOne(@NotNull T item) {
        var em = DAOSource.manager();
        var tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(item);
            tx.commit();
            return item;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    /**
     * Persits many new items into the database.
     *
     * @param items the new items.
     * @return persisted items.
     */
    public @NotNull List<T> insertMany(@NotNull List<T> items) {
        var em = DAOSource.manager();
        var tx = em.getTransaction();
        try {
            tx.begin();
            items.forEach(em::persist);
            tx.commit();
            return items;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    /**
     * Updates one item in database.
     *
     * @param item the item to update with all new data.
     *             No ID changes are allowed.
     * @return the updated item.
     */
    public @NotNull T updateOne(@NotNull T item) {
        var em = DAOSource.manager();
        var tx = em.getTransaction();
        try {
            tx.begin();
            var r = em.merge(item);
            tx.commit();
            return r;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    /**
     * Updates the trash state of many items, identified by ID.
     *
     * @param trash the new trash state.
     * @param items the items.
     * @return updated count.
     * @implNote some entities doesn't have a trash state, and
     * invoking this method would reuslt in a failure.
     */
    @SafeVarargs
    public final int updateTrash(boolean trash, @NotNull T... items) {
        var em = DAOSource.manager();
        var tx = em.getTransaction();
        try {
            tx.begin();
            var cb = em.getCriteriaBuilder();
            var qry = cb.createCriteriaUpdate(tClass());
            var root = qry.from(tClass());
            qry.set(root.get("trash"), trash);
            qry.where(root.get("id").in(Stream.of(items).map(this::id).toArray()));
            var r = em.createQuery(qry).executeUpdate();
            tx.commit();
            return r;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    /**
     * Removes a given item from the database.
     *
     * @param item the given item to remove.
     * @implNote NOT ALL ENTITIES MAY BE DELETED.
     */
    public void deleteOne(@NotNull T item) {
        var em = DAOSource.manager();
        var tx = em.getTransaction();
        try {
            tx.begin();
            var cb = em.getCriteriaBuilder();
            var qry = cb.createCriteriaDelete(tClass());
            var root = qry.from(tClass());
            qry.where(cb.equal(root.get("id"), id(item)));
            var x = em.createQuery(qry).executeUpdate();
            if (x != 1)
                throw new IllegalStateException(
                        "Expected one item to be deleted, but %d matched query."
                                .formatted(x));
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    /**
     * Fetches the entity with given ID.
     *
     * @param id the given ID.
     * @return fetched entity, or null if no such entity.
     */
    @Nullable
    public T fetch(@NotNull Object id) {
        var em = DAOSource.manager();
        try {
            return em.find(tClass(), id);
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
