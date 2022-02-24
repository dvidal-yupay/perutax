package com.yupay.perutax.dao;

import com.yupay.perutax.entities.TaxPeriod;
import com.yupay.perutax.entities.XRate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * DAO implementation for eXcahnge Rate entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAOXRate extends DAOBase<XRate, DAOXRate> {
    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see DAO#xrate()
     */
    DAOXRate() {
    }

    @Override
    protected @NotNull Class<XRate> tClass() {
        return XRate.class;
    }

    @Override
    public @NotNull DAOXRate specialize() {
        return this;
    }

    @Override
    protected @NotNull @Unmodifiable Object id(@NotNull XRate item) {
        return item.getId();
    }

    /**
     * Fetches all eXchange rates within a given tax period.
     * An eXchange rate is within a tax period, if the
     * {@code xrate.taxDate} is within {@code period.dateFrom}
     * and {@code period.dateUntil}, inclusive range.
     *
     * @param period the tax period to find.
     * @return all eXchange rates within period.
     */
    public Stream<XRate> findInPeriod(@NotNull TaxPeriod period) {
        var em = DAOSource.manager();
        try {
            var ql = em.createQuery(
                    "SELECT X FROM XRate X " +
                            "WHERE X.taxDate >= :dFrom AND X.taxDate <= :dUntl",
                    XRate.class);
            ql.setParameter("dFrom", period.getDateFrom());
            ql.setParameter("dUntl", period.getDateUntil());
            return ql.getResultStream();
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    /**
     * Fetches the exchange rate for a given date.
     *
     * @param date the required rate date.
     * @return optional containing the result, empty if not found.
     */
    public @NotNull Optional<XRate> findForDate(@NotNull LocalDate date) {
        var em = DAOSource.manager();
        try {
            var ql = em.createQuery("SELECT X FROM XRate X " +
                            "WHERE X.taxDate = :aDate",
                    XRate.class);
            ql.setParameter("aDate", date);
            return ql.getResultStream().findAny();
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
