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

import com.yupay.perutax.entities.TypeDOI;
import com.yupay.perutax.forms.flows.CheckRegexFlow;
import com.yupay.perutax.forms.inner.BaseChangeListener;
import com.yupay.perutax.forms.inner.VarcharFormatter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.yupay.perutax.forms.FormUtils.clearFormatters;

/**
 * This dialog is the Type of DOI editor.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class TDoiCard extends Dialog<TypeDOI> {

    //<editor-fold desc="Inner fields.">
    /**
     * The formatter for ID.
     */
    private final TextFormatter<String> fmtID =
            new VarcharFormatter(1, true);
    /**
     * Formatter for title.
     */
    private final TextFormatter<String> fmtTitle =
            new VarcharFormatter(10, true);
    /**
     * Formatter for regular expression.
     */
    private final TextFormatter<String> fmtRegex =
            new VarcharFormatter(255, false);
    /**
     * Observable value to be editing in this form.
     */
    private final ObjectProperty<TypeDOI> value =
            new SimpleObjectProperty<>(this, "value");
    //</editor-fold>

    //<editor-fold desc="FXML controls.">
    /**
     * FXML injected controls from tdoi-card.fxml
     */
    @FXML
    private DialogPane top;

    /**
     * FXML injected controls from tdoi-card.fxml
     */
    @FXML
    private TextField txtID;

    /**
     * FXML injected controls from tdoi-card.fxml
     */
    @FXML
    private TextField txtTitle;

    /**
     * FXML injected controls from tdoi-card.fxml
     */
    @FXML
    private TextField txtRegex;

    /**
     * FXML injected controls from tdoi-card.fxml
     */
    @FXML
    private CheckBox chkTrash;
    //</editor-fold>

    // <editor-fold desc="Initialization.">

    /**
     * Package-private constructor.
     * To instanciate use a static factory.
     *
     * @see Forms#tDoiCard()
     */
    TDoiCard() {
    }


    /**
     * FXML initialization.
     */
    @FXML
    void initialize() {
        setDialogPane(top);
        setTitle("Editor de Tipos de DOI");

        txtID.setTextFormatter(fmtID);
        txtTitle.setTextFormatter(fmtTitle);
        txtRegex.setTextFormatter(fmtRegex);

        value.addListener(new ValueChanged());
        setResultConverter(this::readResult);
    }
    //</editor-fold>

    //<editor-fold desc="Public accessors">

    /**
     * Fluent setter - with.
     *
     * @param value new value to set in {@link #value}
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull TDoiCard withValue(@NotNull TypeDOI value) {
        this.value.set(value);
        return this;
    }

    /**
     * Setts the controls states based on a form mode.
     * The default behavior is the same as setting
     * this to {@link  FormMode#CREATOR}
     *
     * @param mode the new form mode.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull TDoiCard withMode(@NotNull FormMode mode) {
        var editable = mode != FormMode.READ_ONLY;
        txtID.setEditable(mode == FormMode.CREATOR);
        txtRegex.setEditable(editable);
        txtTitle.setEditable(editable);
        chkTrash.setDisable(!editable);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Event handling.">

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void testRegexAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        @RegExp var txt = fmtRegex.getValue();
        if (txt == null || txt.isBlank()) return;
        new CheckRegexFlow().withRegex(txt).run();
    }
    //</editor-fold>

    /**
     * Converts the dialog result (button type) to a type of DOI value.
     *
     * @param result the user dialog result.
     * @return {@code  value.get()} if result is APPLY, null otherwise.
     */
    private @Nullable TypeDOI readResult(@NotNull ButtonType result) {
        return result == ButtonType.APPLY ? value.get() : null;
    }

    //<editor-fold desc="Inner classes.">

    /**
     * Listener implementation to use when the value has changed.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class ValueChanged extends BaseChangeListener<TypeDOI> {
        /**
         * Attaches the value to the ui, binding each property
         * to the UI observables.
         *
         * @param value the value to attach (bind).
         */
        @Override
        protected void attach(@NotNull TypeDOI value) {
            fmtID.valueProperty().bindBidirectional(value.idProperty());
            fmtRegex.valueProperty().bindBidirectional(value.regexProperty());
            fmtTitle.valueProperty().bindBidirectional(value.titleProperty());
            chkTrash.selectedProperty().bindBidirectional(value.trashProperty());
        }

        /**
         * Dettaches the value from the ui, unbinding each
         * property from the UI observables.
         *
         * @param value the value to dettach (unbind).
         */
        @Override
        protected void dettach(@NotNull TypeDOI value) {
            fmtID.valueProperty().unbindBidirectional(value.idProperty());
            fmtRegex.valueProperty().unbindBidirectional(value.regexProperty());
            fmtTitle.valueProperty().unbindBidirectional(value.titleProperty());
            chkTrash.selectedProperty().unbindBidirectional(value.trashProperty());
        }

        /**
         * Clears the UI values, preparing the form
         * to accept a new value.
         */
        @Override
        protected void clear() {
            clearFormatters("", fmtID, fmtRegex, fmtTitle);
            chkTrash.setSelected(false);
        }
    }
    //</editor-fold>
}
