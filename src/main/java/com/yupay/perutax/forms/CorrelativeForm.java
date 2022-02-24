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

import com.yupay.perutax.entities.Correlative;
import com.yupay.perutax.entities.TaxPeriod;
import com.yupay.perutax.forms.flows.SelectAllFlow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LongStringConverter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.*;

/**
 * The correlative view form controller.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class CorrelativeForm {

    //<editor-fold desc="Private fields.">
    /**
     * In-memory list of data to show in table view.
     */
    private final ObservableList<Correlative> data
            = FXCollections.observableArrayList();
    //</editor-fold>

    //<editor-fold desc="FXML controls.">
    /**
     * FXML injected control from correlative.fxml
     */
    @FXML
    private TableColumn<Correlative, String> colBook;

    /**
     * FXML injected control from correlative.fxml
     */
    @FXML
    private TableColumn<Correlative, LocalDateTime> colClosedAt;

    /**
     * FXML injected control from correlative.fxml
     */
    @FXML
    private TableColumn<Correlative, Long> colClosing;

    /**
     * FXML injected control from correlative.fxml
     */
    @FXML
    private TableColumn<Correlative, String> colID;

    /**
     * FXML injected control from correlative.fxml
     */
    @FXML
    private TableColumn<Correlative, Long> colMovement;

    /**
     * FXML injected control from correlative.fxml
     */
    @FXML
    private TableColumn<Correlative, Long> colOpen;

    /**
     * FXML injected control from correlative.fxml
     */
    @FXML
    private TableColumn<Correlative, TaxPeriod> colPeriod;

    /**
     * FXML injected control from correlative.fxml
     */
    @FXML
    private TableView<Correlative> tblData;

    /**
     * FXML injected control from correlative.fxml
     */
    @FXML
    private Stage top;

    /**
     * FXML injected control from correlative.fxml
     */
    @FXML
    private TextField txtFilter;
    //</editor-fold>

    //<editor-fold desc="Initialization.">

    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see Forms#correlative()
     */
    CorrelativeForm() {
    }

    /**
     * FXML initialization.
     */
    @SuppressWarnings("unchecked")
    @FXML
    void initialize() {

        stringTableColumns(colID, colBook);
        objectTableColumn(colPeriod, Objects::toString);
        dateTableColumns(colClosedAt);
        customTableColumns(new LongStringConverter(), colOpen, colMovement, colClosing);

        columnValueFactory(colID, Correlative::idProperty);
        columnValueFactory(colBook, Correlative::bookProperty);
        columnValueFactory(colPeriod, Correlative::periodProperty);
        columnValueFactory(colClosedAt, Correlative::closedProperty);
        longColumnValueFactory(colOpen, Correlative::lastAProperty);
        longColumnValueFactory(colMovement, Correlative::lastMProperty);
        longColumnValueFactory(colClosing, Correlative::lastCProperty);

        setupTableFilter(tblData,
                data,
                new TextFilter(),
                txtFilter.textProperty());
        tblData.getSortOrder().setAll(colPeriod, colBook);
    }
    //</editor-fold>

    //<editor-fold desc="Event handling.">

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void syncAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) loadData();
    }
    //</editor-fold>

    //<editor-fold desc="Public API">

    /**
     * Will initialize the top stage of this form as
     * a child of app primary stage, then will show.
     * After showing, will trigger a loadData cycle.
     */
    public void show() {
        initChild(top);
        top.show();
        loadData();
    }
    //</editor-fold>

    //<editor-fold desc="Delegated methods.">

    /**
     * Delegated to load the data to show.
     */
    private void loadData() {
        new SelectAllFlow<>(Correlative.class)
                .first(data::clear)
                .forEach(data::add)
                .onError(easy("No se pudieron cargar los correlativos de la base de datos."))
                .execute();
    }
    //</editor-fold>

    //<editor-fold desc="Inner classes.">

    /**
     * The predicate of correlative that supplies it self,
     * to use in filter bindings for table view.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class TextFilter implements
            Callable<Predicate<Correlative>>,
            Predicate<Correlative> {
        @Override
        public Predicate<Correlative> call() {
            return txtFilter.getText().isBlank()
                    ? always()
                    : this;
        }

        @Override
        public boolean test(@NotNull Correlative correlative) {
            return correlative.getBook().startsWith(txtFilter.getText());
        }
    }
    //</editor-fold>
}
