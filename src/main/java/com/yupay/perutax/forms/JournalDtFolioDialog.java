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

import com.yupay.perutax.dao.DAO;
import com.yupay.perutax.dao.FolioContext;
import com.yupay.perutax.entities.JournalDtFolio;
import com.yupay.perutax.entities.TypeFolio;
import com.yupay.perutax.forms.inner.BaseChangeListener;
import com.yupay.perutax.forms.inner.SearchableInfo;
import freetimelabs.io.reactorfx.schedulers.FxSchedulers;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import reactor.core.Disposables;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.clearComboBoxes;
import static com.yupay.perutax.forms.FormUtils.clearTextFields;

/**
 * FXML controller class for the Folio information input.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class JournalDtFolioDialog extends Dialog<JournalDtFolio> {

    //<editor-fold desc="Private fields.">
    /**
     * The editing value.
     */
    private final ObjectProperty<JournalDtFolio> value
            = new SimpleObjectProperty<>(this, "value");
    /**
     * The required contexts to show in the folio type.
     */
    private final FolioContext[] contexts;
    //</editor-fold>

    //<editor-fold desc="FXML controls.">
    /**
     * FXML control injected from folio-info.fxml
     */
    @FXML
    private ComboBox<TypeFolio> cboType;

    /**
     * FXML control injected from folio-info.fxml
     */
    @FXML
    private DialogPane top;

    /**
     * FXML control injected from folio-info.fxml
     */
    @FXML
    private TextField txtNumber;

    /**
     * FXML control injected from folio-info.fxml
     */
    @FXML
    private TextField txtSerie;
    //</editor-fold>

    //<editor-fold desc="Initialization.">

    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @param contexts the required folio contexts.
     * @see Forms#folioInfo(FolioContext...)
     */
    JournalDtFolioDialog(@NotNull FolioContext @NotNull ... contexts) {
        this.contexts = contexts;
    }

    /**
     * Don't use it ever, it's only for JetBrains gunter icons.
     *
     * @throws IllegalAccessException always.
     */
    @SuppressWarnings("unused")
    @Contract("->fail")
    private JournalDtFolioDialog() throws IllegalAccessException {
        throw new IllegalAccessException("Use a factory please.");
    }

    /**
     * FXML initialization.
     */
    @FXML
    void initialize() {
        setDialogPane(top);
        setTitle("Folio Relacionado");
        setOnShown(e -> {
            var cnt = Disposables.swap();
            var dsp = Mono.fromSupplier(() -> DAO
                            .typeFolio()
                            .specialize()
                            .findByContext(contexts))
                    .subscribeOn(Schedulers.single())
                    .map(FXCollections::observableArrayList)
                    .publishOn(FxSchedulers.fxThread())
                    .subscribe(cboType::setItems,
                            easy("No se pudieron cargar los tipos de folio."));
            cnt.update(dsp);
        });
        setResultConverter(b -> b == ButtonType.APPLY ? value.get() : null);
        value.addListener(new ValueChanged());
    }
    //</editor-fold>

    //<editor-fold desc="Public API.">

    /**
     * Fluent Setter - with.
     *
     * @param value the new editing value (not null).
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull JournalDtFolioDialog withValue(@NotNull JournalDtFolio value) {
        this.value.set(value);
        return this;
    }

    /**
     * Shorthand for {@code  withValue(new JournalDtFolio())}
     *
     * @return this instance.
     */
    @Contract("->this")
    public @NotNull JournalDtFolioDialog creator() {
        return withValue(new JournalDtFolio());
    }
    //</editor-fold>

    //<editor-fold desc="Event handling.">

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void searchTypeAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) {
            Forms.search(SearchableInfo.folioTypeAll(contexts))
                    .withData(cboType.getItems())
                    .showAndWait()
                    .ifPresent(cboType::setValue);
            event.consume();
        }
    }
    //</editor-fold>

    //<editor-fold desc="Inner classes.">

    /**
     * Listener for changes in editing value.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class ValueChanged extends BaseChangeListener<JournalDtFolio> {
        @Override
        protected void attach(@NotNull JournalDtFolio value) {
            cboType.valueProperty().bindBidirectional(value.folioTypeProperty());
            txtNumber.textProperty().bindBidirectional(value.folioNumProperty());
            txtSerie.textProperty().bindBidirectional(value.folioSerieProperty());
        }

        @Override
        protected void dettach(@NotNull JournalDtFolio value) {
            cboType.valueProperty().unbindBidirectional(value.folioTypeProperty());
            txtNumber.textProperty().unbindBidirectional(value.folioNumProperty());
            txtSerie.textProperty().unbindBidirectional(value.folioSerieProperty());
        }

        @Override
        protected void clear() {
            clearComboBoxes(cboType);
            clearTextFields(txtNumber, txtSerie);
        }
    }
    //</editor-fold>
}
