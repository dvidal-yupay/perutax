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

import com.yupay.perutax.entities.Subdiary;
import com.yupay.perutax.forms.flows.EditSelectionTrigger;
import com.yupay.perutax.forms.flows.InsertOneFlow;
import com.yupay.perutax.forms.flows.SelectAllFlow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.*;

/**
 * Controller for Subdiary master view.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class SubdiaryView {
    /**
     * The in-memory list that holds data to show in table view.
     */
    private final ObservableList<Subdiary> data
            = FXCollections.observableArrayList();
    /**
     * FXML control injected from subdiary-view.fxml
     */
    @FXML
    private CheckBox chkFilter;

    /**
     * FXML control injected from subdiary-view.fxml
     */
    @FXML
    private TableColumn<Subdiary, String> colID;

    /**
     * FXML control injected from subdiary-view.fxml
     */
    @FXML
    private TableColumn<Subdiary, String> colTitle;

    /**
     * FXML control injected from subdiary-view.fxml
     */
    @FXML
    private TableColumn<Subdiary, Boolean> colTrash;

    /**
     * FXML control injected from subdiary-view.fxml
     */
    @FXML
    private TableView<Subdiary> tblData;

    /**
     * FXML control injected from subdiary-view.fxml
     */
    @FXML
    private Stage top;

    /**
     * FXML control injected from subdiary-view.fxml
     */
    @FXML
    private TextField txtFilter;

    /**
     * Package-private constructor.
     * Use static factory.
     *
     * @see Forms#subdiaryView()
     */
    SubdiaryView() {
    }

    /**
     * FXML initializer.
     */
    @FXML
    void initialize() {
        stringTableColumns(colID, colTitle);
        booleanTableColumns(colTrash);
        columnValueFactory(colID, Subdiary::idProperty);
        columnValueFactory(colTitle, Subdiary::titleProperty);
        columnValueFactory(colTrash, Subdiary::trashProperty);
        setupTableFilter(tblData,
                data,
                new TextFilter(),
                txtFilter.textProperty(), chkFilter.selectedProperty());
        //noinspection unchecked
        tblData.getSortOrder().setAll(colTrash, colID);
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void addAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) Forms.subdiaryCard()
                .creator()
                .showAndWait()
                .ifPresent(new InsertOneFlow<Subdiary>()
                        .withOnFail(easy("No se pudo grabar el nuevo subdiario."))
                        .withOnSuccess(data::add)
                        .asConsumer());
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void editAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) editData();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void dataClicked(@NotNull MouseEvent event) {
        if (!event.isConsumed()
                && event.getClickCount() > 1) {
            editData();
            event.consume();
        }
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void refreshAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) loadData();
    }

    /**
     * Initializes the top stage of this form
     * as a child of app's primary stage and shows.
     * This also will trigger a loadData cycle.
     */
    public void show() {
        initChild(top);
        top.show();
        loadData();
    }

    /**
     * Effectively loads the data.
     */
    private void loadData() {
        new SelectAllFlow<>(Subdiary.class)
                .first(data::clear)
                .forEach(data::add)
                .onError(easy("No se pudo cargar el listado de subdiarios."))
                .execute();
    }

    /**
     * Delegated method to edit selected item.
     */
    private void editData() {
        new EditSelectionTrigger<Subdiary>()
                .withOnSuccess(upsertList(data))
                .withView(tblData)
                .withUserInput(i -> Forms.subdiaryCard()
                        .withValue(i)
                        .withMode(FormMode.EDITOR)
                        .showAndWait())
                .withCloner(Subdiary::new)
                .withErrorText("No se pudo actualizar el Subdiario.")
                .run();
    }

    /**
     * Text filter that calls itself in a callable.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class TextFilter implements
            Callable<Predicate<Subdiary>>,
            Predicate<Subdiary> {
        /**
         * The text to find, escaped by Pattern.quote
         */
        private String str;

        @Override
        public @NotNull Predicate<Subdiary> call() {
            var txt = txtFilter.getText();
            str = Pattern.quote(txt);
            return txt.isBlank() || !chkFilter.isSelected()
                    ? always()
                    : this;
        }

        @Override
        @Contract("null->false")
        public boolean test(Subdiary subdiary) {
            if (subdiary == null) return false;
            return subdiary.getId().matches("(?i)^" + str + ".*")
                    || subdiary.getTitle().matches("(?i).*" + str + ".*");
        }
    }

}
