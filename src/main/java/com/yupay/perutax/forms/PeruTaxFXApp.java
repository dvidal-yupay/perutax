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

package com.yupay.perutax.forms;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * PeruTax application implementation.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class PeruTaxFXApp extends Application {
    /**
     * Primary stage.
     */
    private static Stage primary;

    /**
     * Static getter.
     *
     * @return value of {@link  #primary}
     */
    public static Stage getPrimary() {
        return primary;
    }

    @Override
    public void start(Stage primaryStage) {
        //Hold the primary stage.
        primary = primaryStage;
        //Creates the application form.
        Forms.application().show(primaryStage);
    }
}
