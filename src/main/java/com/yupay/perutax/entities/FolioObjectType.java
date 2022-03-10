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

package com.yupay.perutax.entities;

/**
 * The type of folio object is a discriminator
 * that allows the system to know which UX show,
 * which options to enable/disable and what ledger
 * entries write when creating the folio.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public enum FolioObjectType {
    /**
     * This represents a folio used in a simple sale
     * operation. (The simple sale is not a credit op).
     */
    SALE,
    /**
     * This represents a folio used in a prepayment
     * invoice.
     */
    SALE_PREPAID,
    /**
     * Thie represents a folio used in a credit
     * operation. If the sale has due date but
     * no installments, it should be considered
     * as a 1 installment sale.
     */
    SALE_INSTALLMENT,
    /**
     * This represents a folio used in an additional sale
     * operation (usually using a debit note), an additional
     * sale operation is often used due interests and price
     * adjustments.
     */
    SALE_ADDITIONAL,
    /**
     * This represents a discount applied to a sale
     * operation (using a credit note). It also includes
     * reversion of a sale operation.
     */
    SALE_DISCOUNT,
    /**
     * A simple purchase operation.
     */
    PURCHASE,
    /**
     * A prepayment invoice for purchase usage.
     */
    PURCHASE_PREPAID,
    /**
     * A credit purchase. If the purchase invoice
     * has a due date but no installments, it should
     * be treated as a 1 installment purchase.
     */
    PURCHASE_INSTALLMENT,
    /**
     * Additional charges to a purchase, using
     * a debit note, usually due interests and
     * price adjustements.
     */
    PURCHASE_ADDITIONAL,
    /**
     * Discounts (including reversion), using
     * a credit note, applied to a purchase.
     */
    PURCHASE_DISCOUNT
}
