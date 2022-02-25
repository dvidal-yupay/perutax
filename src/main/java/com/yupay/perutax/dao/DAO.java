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

import com.yupay.perutax.entities.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;

/**
 * Factory class of each DAO implementation.
 * This is the entry point to data access layer.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAO {
    /**
     * Private constructor meant to avoid instanciation
     * of this factory class.
     *
     * @throws IllegalAccessException always.
     */
    @Contract("->fail")
    private DAO() throws IllegalAccessException {
        throw new IllegalAccessException("Don't instanciate static factories.");
    }

    /**
     * Factory of entity DAO implementation.
     *
     * @return a new DAO for Country.
     */
    @Contract("->new")
    public static @NotNull DAOBase<Country, DAOCountry> country() {
        return new DAOCountry();
    }


    /**
     * Factory of entity DAO implementation.
     *
     * @return a new DAO for Type of DOI.
     */
    @Contract("->new")
    public static @NotNull DAOBase<TypeDOI, DAOTypeDOI> typeDOI() {
        return new DAOTypeDOI();
    }

    /**
     * Factory of entity DAO implementation.
     *
     * @return a new DAO for Person.
     */
    @Contract("->new")
    public static @NotNull DAOBase<Person, DAOPerson> person() {
        return new DAOPerson();
    }

    /**
     * Factory of entity DAO implementation.
     *
     * @return a new DAO for Period.
     */
    @Contract("->new")
    public static @NotNull DAOBase<TaxPeriod, DAOTaxPeriod> period() {
        return new DAOTaxPeriod();
    }

    /**
     * Factory of entity DAO implementation.
     *
     * @return a new DAO for XRate.
     */
    @Contract("->new")
    public static @NotNull DAOBase<XRate, DAOXRate> xrate() {
        return new DAOXRate();
    }

    /**
     * Factory of entity DAO implementation.
     *
     * @return a new DAO for Cost Center.
     */
    @Contract("->new")
    public static @NotNull DAOBase<CostCenter, DAOCostCenter> costcenter() {
        return new DAOCostCenter();
    }

    /**
     * Factory of entity DAO implementation.
     *
     * @return a new DAO for Correlative.
     */
    @Contract("->new")
    public static @NotNull DAOBase<Correlative, DAOCorrelative> correlative() {
        return new DAOCorrelative();
    }

    /**
     * Factory of entity DAO implementation.
     *
     * @return a new DAO for TaxAccount.
     */
    @Contract("->new")
    public static @NotNull DAOBase<TaxAccount, DAOTaxAccount> taxAccount() {
        return new DAOTaxAccount();
    }

    /**
     * Factory of entity DAO implementation.
     *
     * @return a new DAO for Type Folio.
     */
    @Contract("->new")
    public static @NotNull DAOBase<TypeFolio, DAOTypeFolio> typeFolio() {
        return new DAOTypeFolio();
    }

    /**
     * Factory of entity DAO implementation.
     *
     * @return a new DAO for Subdiary.
     */
    @Contract("->new")
    public static @NotNull DAOBase<Subdiary, DAOSubdiary> subdiary() {
        return new DAOSubdiary();
    }

    /**
     * Factory of entity DAO implementation.
     *
     * @return a new DAO for Journal snapshots.
     */
    @Contract("->new")
    public static @NotNull DAOBase<JournalSnapshot, DAOJournalSS> journalSS() {
        return new DAOJournalSS();
    }

    /**
     * Factory of entity DAO implementation.
     *
     * @return a new DAO for Journal entries.
     */
    @Contract("->new")
    public static @NotNull DAOBase<Journal, DAOJournal> journal() {
        return new DAOJournal();
    }

    /**
     * Utility method to fetch a DAO implementation for a given tClass.
     *
     * @param tClass the given tClass for required entity.
     * @param <T>    type erasure of entity.
     * @param <U>    type erasure of implementation.
     * @return DAO implementation.
     * @throws NoSuchElementException if tClass has no implementation.
     */
    @SuppressWarnings("unchecked")
    public static <T, U extends DAOBase<T, U>> DAOBase<T, U> forEntity(Class<T> tClass) {
        if (tClass == Country.class) {
            return (DAOBase<T, U>) country();
        } else if (tClass == TypeDOI.class) {
            return (DAOBase<T, U>) typeDOI();
        } else if (tClass == Person.class) {
            return (DAOBase<T, U>) person();
        } else if (tClass == TaxPeriod.class) {
            return (DAOBase<T, U>) period();
        } else if (tClass == XRate.class) {
            return (DAOBase<T, U>) xrate();
        } else if (tClass == CostCenter.class) {
            return (DAOBase<T, U>) costcenter();
        } else if (tClass == Correlative.class) {
            return (DAOBase<T, U>) correlative();
        } else if (tClass == TaxAccount.class) {
            return (DAOBase<T, U>) taxAccount();
        } else if (tClass == TypeFolio.class) {
            return (DAOBase<T, U>) typeFolio();
        } else if (tClass == Subdiary.class) {
            return (DAOBase<T, U>) subdiary();
        } else if (tClass == JournalSnapshot.class) {
            return (DAOBase<T, U>) journalSS();
        } else if (tClass == Journal.class) {
            return (DAOBase<T, U>) journal();
        } else {
            throw new NoSuchElementException("Cannot identify a DAO implementation for " + tClass);
        }
    }

}
