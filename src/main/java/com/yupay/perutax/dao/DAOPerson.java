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

import com.yupay.perutax.entities.Person;
import jakarta.persistence.criteria.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.stream.Stream;

/**
 * DAO implementation for Person entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAOPerson extends DAOBase<Person, DAOPerson> {
    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see DAO#person()
     */
    DAOPerson() {
    }

    @Override
    protected @NotNull Class<Person> tClass() {
        return Person.class;
    }

    @Override
    public @NotNull DAOPerson specialize() {
        return this;
    }

    @Override
    protected @NotNull @Unmodifiable Object id(@NotNull Person item) {
        return item.getId();
    }

    /**
     * Mehtod to find a person whose name contains s, or id number starts with s. It also
     * will ensure that the persons match at least one role of request roles.
     *
     * @param s     the text to find.
     * @param roles the roles to match.
     * @return a list of results.
     */
    public List<Person> search(@NotNull String s, @NotNull PersonRole @NotNull ... roles) {
        var em = DAOSource.manager();
        try {
            var cb = em.getCriteriaBuilder();
            var qry = cb.createQuery(Person.class);
            var root = qry.from(Person.class);
            var whereRoles = Stream.of(roles)
                    .map(p -> p.column)
                    .map(x -> cb.isTrue(root.get(x)))
                    .toList();
            //If roles is empty, get only 2 predicates.
            Predicate[] all = new Predicate[whereRoles.isEmpty() ? 2 : 3];
            //Trash flag should be false.
            all[0] = cb.isFalse(root.get("trash"));
            //Doi number or name matching.
            all[1] = cb.or(
                    cb.like(root.get("doiNum"), s + "%"),
                    cb.like(root.get("fullName"), "%" + s + "%"));
            //If roles filtering were applied, add to WHERE criteria.
            if (!whereRoles.isEmpty()) all[2] = cb.or(whereRoles.toArray(Predicate[]::new));
            //Create query and return
            qry.select(root).where(cb.and(all));
            return em.createQuery(qry).getResultList();
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
