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
 * The roles of a SPOT batch. Needed for
 * the Detracciones batch generation feature.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public enum SPOTRole {
    /**
     * When the user is the supplier party.
     */
    SUPPLIER("Proveedor"),
    /**
     * When the user is the customer party.
     */
    CUSTOMER("Adquiriente");
    /**
     * Title used to show a user-friendly
     * text in the UIs.
     */
    private final String title;

    /**
     * Default constructor.
     *
     * @param title the user-friendly text.
     */
    SPOTRole(String title) {
        this.title = title;
    }

    /**
     * Static factory of an observable list
     * backed by values()
     *
     * @return {@code FXCollections.observableArrayList(values());}
     */
    @Contract(" -> new")
    public static @NotNull ObservableList<SPOTRole> observable() {
        return FXCollections.observableArrayList(values());
    }

    @Override
    public String toString() {
        return title;
    }
}
