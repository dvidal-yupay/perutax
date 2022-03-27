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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * This enumeration represents the total amount of
 * a sale object.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public enum SaleTotalClass {
    /**
     * Taxable amount.
     */
    TAXABLE("Op. Gravada"),
    /**
     * Tax amount.
     */
    TAX("IGV"),
    /**
     * Discount - value portion.
     */
    DISCOUNT("Descuento"),
    /**
     * Discount - tax portion.
     */
    DISCOUNT_TAX("Dscto - IGV"),
    /**
     * Exportation value.
     */
    EXPORT("Exportación"),
    /**
     * Exepmt (Allowance) amount.
     */
    EXEMPT("Exonerado"),
    /**
     * Free of taxes.
     */
    TAX_FREE("Inafecto"),
    /**
     * Plastic bag tax (ICBP)
     */
    ICBP("ICBP"),
    /**
     * Other (various) amounts.
     */
    OTHERS("Otros");
    /**
     * Human firendly caption to show
     * in the user experience.
     */
    public final String text;

    /**
     * Default constructor.
     *
     * @param text the caption to show in UX screens.
     */
    SaleTotalClass(String text) {
        this.text = text;
    }

    /**
     * Static factory to get an observable list
     * of all values. Short-hand for
     * {@code FXCollections.observableArrayList(SaleTotalClass.values());}
     *
     * @return a new observable list, never null.
     */
    @Contract(" -> new")
    public static @NotNull ObservableList<SaleTotalClass> observable() {
        return FXCollections.observableArrayList(values());
    }

    @Override
    public String toString() {
        return text;
    }
}
