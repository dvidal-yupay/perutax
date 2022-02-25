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
 * This represents all the subdiary roles.
 * Each roles is associated with a correlative prefix.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public enum SubdiaryRole {
    /**
     * Opening role.
     */
    A("Apertura"),
    /**
     * Movement role.
     */
    M("Movimiento"),
    /**
     * Closing role.
     */
    C("Cierre");

    /**
     * The role title to show to user.
     */
    public final String title;

    /**
     * Default constructor.
     *
     * @param title friendly text to show to user.
     */
    SubdiaryRole(String title) {
        this.title = title;
    }

    /**
     * Wraps all values in an observable list.
     *
     * @return the observable list.
     */
    @Contract(" -> new")
    public static @NotNull ObservableList<SubdiaryRole> observable() {
        return FXCollections.observableArrayList(values());
    }

    @Override
    public String toString() {
        return title;
    }
}
