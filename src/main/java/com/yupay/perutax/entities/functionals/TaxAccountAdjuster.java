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

package com.yupay.perutax.entities.functionals;

import com.yupay.perutax.entities.AccountNature;
import com.yupay.perutax.entities.Currenci;
import com.yupay.perutax.entities.JournalDt;
import jakarta.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.function.Consumer;

import static com.yupay.perutax.entities.AccountNature.CREDIT;
import static com.yupay.perutax.entities.AccountNature.DEBIT;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

/**
 * Adjusts the tax account balance dependeing on a journal detail line.
 *
 * @param em       The entity manager object (to merge the account state).
 * @param currency the journal entry currency.
 * @param xrate    the journal entry exchange rate value.
 * @author InfoYupay SACS
 * @version 1.0
 */
public record TaxAccountAdjuster(@NotNull EntityManager em,
                                 @NotNull Currenci currency,
                                 @NotNull BigDecimal xrate)
        implements Consumer<JournalDt> {

    @Override
    public void accept(@NotNull JournalDt o) {
        //Hold account.
        var acc = o.getAccount();
        //Compute the movement nature.
        var mov = computeMovement(o);
        //Compute the movement ammount (always positive).
        var amn = computeAmmount(o);
        //Update balance.
        if (acc.getNature() == mov) {
            acc.setBalance(acc.getBalance().add(amn));
        } else {
            acc.setBalance(acc.getBalance().subtract(amn));
        }
        //Merge new account state.
        em.merge(acc);
    }

    /**
     * Computes the amount in the tax account currency.
     *
     * @param o the tax account object.
     * @return computed amount.
     */
    private BigDecimal computeAmmount(@NotNull JournalDt o) {
        var r = ZERO;
        switch (o.getAccount().getCurrency()) {
            //If account is PEN, system currency will do.
            case PEN -> r = o.getDebitSc().add(o.getCreditSc());
            //If account is USD...
            case USD -> {
                switch (currency()) {
                    //And movement is USD, foreign currency will do.
                    case USD -> r = o.getDebitFc().add(o.getCreditFc());
                    //And movement is PEN, need to divide SC/xrate
                    case PEN -> r = o.getDebitSc().add(o.getCreditSc())
                            .divide(xrate(), 2, HALF_UP);
                }
            }
        }
        return r;
    }

    /**
     * Computes movement nature.
     *
     * @param o the movement detail object.
     * @return the nature for the movement.
     */
    private AccountNature computeMovement(@NotNull JournalDt o) {
        var r0 = o.getDebitFc().compareTo(ZERO) == 0 ? CREDIT : DEBIT;
        var r1 = o.getCreditFc().compareTo(ZERO) == 0 ? DEBIT : CREDIT;
        if (r0 == r1) return r0;
        else throw new IllegalStateException("Unable to identify amount nature.");
    }
}
