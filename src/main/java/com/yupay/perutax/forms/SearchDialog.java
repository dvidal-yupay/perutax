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

import com.yupay.perutax.forms.inner.SearchableInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static com.yupay.perutax.forms.FormUtils.*;

/**
 * This dialog is a utility to select search results when
 * the result size is greater than 1.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class SearchDialog<T> extends Dialog<T> {

    //<editor-fold desc="Inner fields.">
    /**
     * The searchable information.
     */
    private final SearchableInfo<T> info;
    /**
     * In-memory store for result data.
     */
    private final ObservableList<T> data = FXCollections.observableArrayList();
    //</editor-fold>

    //<editor-fold desc="FXML controls.">
    /**
     * FXML control injected from search.fxml
     */
    @FXML
    private TableView<T> tblData;
    /**
     * FXML control injected from search.fxml
     */
    @FXML
    private DialogPane top;
    /**
     * FXML control injected from search.fxml
     */
    @FXML
    private TextField txtFilter;
    //</editor-fold>

    //<editor-fold desc="Initialization.">

    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @param info the searchable information.
     * @see Forms#search(SearchableInfo)
     */
    SearchDialog(SearchableInfo<T> info) {
        this.info = info;
    }

    /**
     * Private constructor for IDE tools purposes.
     * DONT USE.
     *
     * @throws IllegalAccessException always
     * @see #SearchDialog(SearchableInfo)
     */
    @Contract("->fail")
    @SuppressWarnings("unused")
    private SearchDialog() throws IllegalAccessException {
        throw new IllegalAccessException("NEVER INSTANCIATE A PRIVATE CONSTRUCTOR");
    }

    /**
     * FXML initializer.
     */
    @FXML
    void initialize() {
        setTitle("Selector de Objetos");
        setDialogPane(top);
        setResultConverter(b -> b == ButtonType.OK
                ? tblData.getSelectionModel().getSelectedItem()
                : null);
        setupTable();
    }
    //</editor-fold>

    //<editor-fold desc="Event handling.">

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void tableClicked(@NotNull MouseEvent event) {
        if (!event.isConsumed()
                && event.getClickCount() > 1) {
            processSelection();
            event.consume();
        }

    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void selectAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) {
            processSelection();
            event.consume();
        }
    }
    //</editor-fold>

    //<editor-fold desc="Convenient and delegated methods.">

    /**
     * Delegated method to process the user selection on double click.
     */
    private void processSelection() {
        Optional.ofNullable(tblData.getSelectionModel().getSelectedItem())
                .ifPresent(this::setResult);
    }

    /**
     * Convenient method to setup the table columns.
     */
    private void setupTable() {
        IntStream.range(0, info.getHeaders().length)
                .mapToObj(this::createColumn)
                .forEach(tblData.getColumns()::add);
        setupTableFilter(tblData, data, new TextFilter(), txtFilter.textProperty());
    }

    /**
     * Delegated method to create a column for index.
     *
     * @param index the index of column.
     * @return created column.
     */
    private @NotNull TableColumn<T, String> createColumn(int index) {
        var r = new TableColumn<T, String>(info.getHeaders()[index]);
        stringTableColumns(r);
        r.setPrefWidth(info.getWidths()[index]);
        columnValueFactory(r, info.getColumnValueAt()[index]);
        return r;
    }
    //</editor-fold>

    //<editor-fold desc="Public API.">

    /**
     * Fluent setter -with.
     *
     * @param data the result data.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull SearchDialog<T> withData(@NotNull Collection<T> data) {
        this.data.setAll(data);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Inner classes.">
    /**
     * Inner implementation to create predicates to allow
     * for user filtering. This will be backed by the
     * info.getFilter() object.
     *
     * @author InfoYupay SACS
     * @version 1.0
     * @see SearchableInfo#getFilter()
     */
    private class TextFilter implements
            Callable<Predicate<T>>,
            Predicate<T> {
        private String regex;

        @Override
        public Predicate<T> call() throws Exception {
            var txt = txtFilter.getText().strip();
            if (txt.isBlank()) {
                return always();
            } else {
                regex = Pattern.quote(txt);
                return this;
            }
        }

        @Override
        public boolean test(T t) {
            return t != null && info.getFilter().test(regex, t);
        }
    }
    //</editor-fold>
}
