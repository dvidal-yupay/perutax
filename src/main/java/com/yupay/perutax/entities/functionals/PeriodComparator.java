package com.yupay.perutax.entities.functionals;

import com.yupay.perutax.entities.TaxPeriod;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * Comparator to use in TaxPeriod entities sorting.
 * The sorting will be from latest period to oldest period,
 * based on year and month.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class PeriodComparator implements Comparator<TaxPeriod> {

    /**
     * Creates a comparator for descending order, which simply
     * returns the same value of compare multiplied by -1.
     *
     * @return a reversed comparator.
     */
    @Contract(pure = true, value = "->new")
    public @NotNull Comparator<TaxPeriod> descending() {
        return (o1, o2) -> compare(o1, o2) * -1;
    }

    @Override
    public int compare(TaxPeriod o1, TaxPeriod o2) {
        if (o1 == o2) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        if (o1.equals(o2)) return 0;
        if (o1.getId() == null && o2.getId() == null) return 0;
        if (o1.getId() == null && o2.getId() != null) return -1;
        if (o1.getId() != null && o2.getId() == null) return 1;
        return o1.getId().compareTo(o2.getId());
    }
}
