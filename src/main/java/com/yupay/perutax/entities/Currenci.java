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
 * This enumeration represents a currency in the system.
 * The name has been modified to Currenci for sake of productivity
 * to avoid collisions with many other Currency classes.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public enum Currenci {
    /**
     * The peruvian soles.
     */
    PEN("soles", "S/"),
    /**
     * The US dollars.
     */
    USD("dólares americanos", "$");
    /**
     * Text to show to the users.
     */
    public final String title;
    /**
     * Currency symbol.
     */
    public final String symbol;

    /**
     * Default constructor.
     *
     * @param title  the title to show in UIs.
     * @param symbol the symbol of the currency.
     */
    Currenci(String title, String symbol) {
        this.title = title;
        this.symbol = symbol;
    }

    /**
     * Wraps the values of this enum into an observable list
     * to use in combo boxes and similar controls.
     *
     * @return a new observable list.
     */
    @Contract(" -> new")
    public static @NotNull ObservableList<Currenci> observable() {
        return FXCollections.observableArrayList(values());
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return name() + " - " + title + " (" + symbol + ")";
    }
}
