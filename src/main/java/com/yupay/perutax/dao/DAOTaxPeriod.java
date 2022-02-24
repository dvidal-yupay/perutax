package com.yupay.perutax.dao;

import com.yupay.perutax.entities.TaxPeriod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * DAO implementation for Taxation period entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAOTaxPeriod extends DAOBase<TaxPeriod, DAOTaxPeriod> {
    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see DAO#period()
     */
    DAOTaxPeriod() {
    }

    @Override
    protected @NotNull Class<TaxPeriod> tClass() {
        return TaxPeriod.class;
    }

    @Override
    public @NotNull DAOTaxPeriod specialize() {
        return this;
    }

    @Override
    protected @NotNull @Unmodifiable Object id(@NotNull TaxPeriod item) {
        return item.getId();
    }

    /**
     * Closes a TaxPeriod using the server-side timestamp.
     * It also performs the operation on all correlatives
     * for this period.
     *
     * @param period the tax period to close.
     * @return the stored tax period with updated timestamp.
     */
    public TaxPeriod close(@NotNull TaxPeriod period) {
        var em = DAOSource.manager();
        var tx = em.getTransaction();
        try {
            tx.begin();
            var qry = em.createQuery("UPDATE Correlative C" +
                    " SET C.closed = current_timestamp WHERE" +
                    " C.period = :period");
            qry.setParameter("period", period);
            qry.executeUpdate();
            var upd = em.createQuery("UPDATE TaxPeriod T" +
                    " SET T.closed = current_timestamp " +
                    " WHERE T.id = :id");
            upd.setParameter("id", period.getId());
            upd.executeUpdate();
            tx.commit();
            return em.getReference(TaxPeriod.class, period.getId());
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    /**
     * Takes a date and finds a tax period which is within
     * the date range. Ie: the date 2021-01-15 should return
     * the tax period 202101, and null if there's no such period.
     *
     * @param date the required date.
     * @return the tax period for such a date or empty.
     */
    public @NotNull Optional<TaxPeriod> forDate(@NotNull LocalDate date) {
        var em = DAOSource.manager();
        try {
            return em.createQuery("SELECT T" +
                                    " FROM TaxPeriod T" +
                                    " WHERE T.dateFrom <= :qDate AND T.dateUntil >= :qDate",
                            TaxPeriod.class)
                    .setParameter("qDate", date)
                    .getResultStream()
                    .findFirst();
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    /**
     * Selects only those tax periods which has not been closed.
     *
     * @return result stream.
     */
    public @NotNull Stream<TaxPeriod> findOpen() {
        var em = DAOSource.manager();
        try {
            return em.createQuery("SELECT T " +
                            "FROM TaxPeriod T " +
                            "WHERE T.closed IS NULL", TaxPeriod.class)
                    .getResultStream();
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
