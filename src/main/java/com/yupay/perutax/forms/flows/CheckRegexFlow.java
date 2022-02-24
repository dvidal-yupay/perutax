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

package com.yupay.perutax.forms.flows;

import com.yupay.perutax.forms.FluentAlert;
import javafx.scene.control.TextInputDialog;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.regex.PatternSyntaxException;

import static com.yupay.perutax.forms.ErrorAlert.easy;

/**
 * This flow is an end-user test facility
 * for regular expressions.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class CheckRegexFlow {
    /**
     * The regular expression to check.
     */
    private String regex;

    /**
     * Fluent setter - with.
     *
     * @param regex new value to set in {@link #regex}
     * @return this instance.
     */
    public final CheckRegexFlow withRegex(@RegExp @NotNull String regex) {
        this.regex = regex;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #regex}
     */
    public final String getRegex() {
        return regex;
    }

    /**
     * Method to get the user input of a sample text.
     *
     * @return the user input text.
     */
    private Optional<String> getUserInput() {
        var dlg = new TextInputDialog();
        dlg.setHeaderText(
                """
                        Vamos a verificar cómo funciona esta expresión regular.
                        Para ello, escribe un texto de prueba y haz clic en aceptar.""");
        return dlg.showAndWait();
    }

    /**
     * Delegated method to show the result to the user in
     * a brief and friendly message.
     *
     * @param result the result (true if matches, false if not).
     */
    private void showResult(boolean result) {
        FluentAlert.info()
                .defaultButtons()
                .withHeader(result ? "CUMPLE" : "NO CUMPLE")
                .withContent("El texto ingresado "
                        + (result ? "cumple" : "no cumple")
                        + " con la expresión regular.")
                .withTitle("Resultado de la operación")
                .show();
    }

    /**
     * Runs the flow.
     */
    public void run() {
        getUserInput()
                .map(s -> {
                    try {
                        return s.matches(getRegex());
                    } catch (PatternSyntaxException e) {
                        easy("La expresión regular está mal formulada.\n" +
                                "Consulta el manual de expresiones regulares de Java.")
                                .accept(e);
                        //After alerting user of the syntax error, should empty the optional.
                        return null;
                    }
                })
                .ifPresent(this::showResult);
    }

}
