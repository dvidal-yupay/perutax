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

package com.yupay.perutax;

import com.yupay.perutax.dao.DAOSource;
import com.yupay.perutax.forms.PeruTaxFXApp;
import javafx.application.Application;

/**
 * The peru tax application main class.
 * This is the entry point.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class PeruTaxApp {
    /**
     * Main method, entry point to app.
     *
     * @param args command line args.
     */
    public static void main(String[] args) {
        DAOSource.get().initPersistence(LocalUser.JPA
                .resolve("developer.properties"));
        Application.launch(PeruTaxFXApp.class, args);
    }
}
