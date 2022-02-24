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
 * The cost group for financial analysis.
 * This cost group is a property of a tax account.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public enum CostGroup {
    /**
     * Direct cost: supplies.
     */
    DIRECT_SUPPLY("Directo - Suministros"),
    /**
     * Direct cost: hand work.
     */
    DIRECT_HWORK("Directo - M. Obra"),
    /**
     * Direct cost: others.
     */
    DIRECT_OTH("Directo - Otros"),
    /**
     * Indirect cost: supplies.
     */
    INDIRECT_SUPPLY("Indirecto - Suministros"),
    /**
     * Indirect cost: hand work.
     */
    INDIRECT_HWORK("Indirecto - M. Obra"),
    /**
     * Indirect cost: others.
     */
    INDIRECT_OTH("Indirecto - Otros");
    /**
     * Human friendly text.
     */
    public final String title;

    /**
     * Default constructor.
     *
     * @param title human friendly text.
     */
    CostGroup(String title) {
        this.title = title;
    }

    /**
     * Wraps the values of this enum into an observable list
     * to use in combo boxes and similar controls.
     *
     * @return a new observable list.
     */
    @Contract(" -> new")
    public static @NotNull ObservableList<CostGroup> observable() {
        return FXCollections.observableArrayList(values());
    }

    @Override
    public String toString() {
        return title;
    }
}
