package com.yupay.perutax.dao;

import com.yupay.perutax.entities.TypeFolio;
import jakarta.persistence.criteria.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.stream.Stream;

/**
 * DAO implementation for Type of Folio entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAOTypeFolio extends DAOBase<TypeFolio, DAOTypeFolio> {
    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see DAO#typeFolio()
     */
    DAOTypeFolio() {
    }

    @Override
    protected @NotNull Class<TypeFolio> tClass() {
        return TypeFolio.class;
    }

    @Override
    public @NotNull DAOTypeFolio specialize() {
        return this;
    }

    @Override
    protected @NotNull @Unmodifiable Object id(@NotNull TypeFolio item) {
        return item.getId();
    }

    /**
     * Loads all active types of folio with given
     * contexts set to true.
     *
     * @param contexts the given contexts.
     * @return result list. NEVER NULL.
     */
    public @NotNull @Unmodifiable List<TypeFolio> findByContext(
            @NotNull FolioContext @NotNull ... contexts) {
        var em = DAOSource.manager();
        try {
            var cb = em.getCriteriaBuilder();
            var qry = cb.createQuery(TypeFolio.class);
            var root = qry.from(TypeFolio.class);
            var whereRoles = Stream.of(contexts)
                    .map(FolioContext.toCriteria(cb, root))
                    .toList();
            //If context is empty, get only 1 predicate.
            Predicate[] all = new Predicate[whereRoles.isEmpty() ? 1 : 2];
            //Trash flag should be false.
            all[0] = cb.isFalse(root.get("trash"));
            //If contexts filtering were applied, add to WHERE criteria.
            if (!whereRoles.isEmpty()) all[1] = cb.or(whereRoles.toArray(Predicate[]::new));
            //Create query and return
            qry.select(root).where(all.length == 1 ? all[0] : cb.and(all));
            return em.createQuery(qry).getResultList();
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
