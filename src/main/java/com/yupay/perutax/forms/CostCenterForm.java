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
import com.yupay.perutax.entities.CostCenter;
import com.yupay.perutax.forms.flows.SelectAllFlow;
import com.yupay.perutax.forms.inner.VarcharConverter;
import com.yupay.perutax.forms.inner.VarcharFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.*;

/**
 * Controller class for the cost center master view form.
 * Check on costcenter.fxml resource.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class CostCenterForm {

    //<editor-fold desc="Inner fields.">
    /**
     * The text formatter for the cost center ID.
     */
    private final TextFormatter<String> fmtID = new VarcharFormatter(3, true),
    /**
     * The text formatter for the cost center title.
     */
    fmtTitle = new VarcharFormatter(255, true);
    /**
     * The list holding data to show in table view.
     */
    private final ObservableList<CostCenter> data =
            FXCollections.observableArrayList();
    //</editor-fold>

    //<editor-fold desc="FXML controls.">
    /**
     * FXML control injected from costcenter.fxml
     */
    @FXML
    private CheckBox chkFilter;

    /**
     * FXML control injected from costcenter.fxml
     */
    @FXML
    private TableColumn<CostCenter, String> colID;

    /**
     * FXML control injected from costcenter.fxml
     */
    @FXML
    private TableColumn<CostCenter, String> colTitle;

    /**
     * FXML control injected from costcenter.fxml
     */
    @FXML
    private TableColumn<CostCenter, Boolean> colTrash;

    /**
     * FXML control injected from costcenter.fxml
     */
    @FXML
    private TextField editID;

    /**
     * FXML control injected from costcenter.fxml
     */
    @FXML
    private TextField editTitle;

    /**
     * FXML control injected from costcenter.fxml
     */
    @FXML
    private TableView<CostCenter> tblData;

    /**
     * FXML control injected from costcenter.fxml
     */
    @FXML
    private Stage top;

    /**
     * FXML control injected from costcenter.fxml
     */
    @FXML
    private TextField txtFilter;
    //</editor-fold>

    //<editor-fold desc="Initialization.">

    /**
     * Package private constructor.
     * Use a static factory.
     *
     * @see Forms#costcenter()
     */
    CostCenterForm() {
    }

    /**
     * FXML initializer.
     */
    @SuppressWarnings("unchecked")
    @FXML
    void initialize() {
        editID.setTextFormatter(fmtID);
        editTitle.setTextFormatter(fmtTitle);

        booleanTableColumns(colTrash);
        stringTableColumns(colID);
        colTitle.setCellFactory(TextFieldTableCell
                .forTableColumn(new VarcharConverter(255, true)));

        columnValueFactory(colID, CostCenter::idProperty);
        columnValueFactory(colTitle, CostCenter::titleProperty);
        columnValueFactory(colTrash, CostCenter::trashProperty);

        setupTableFilter(tblData,
                data,
                new TextFilter(),
                chkFilter.selectedProperty(), txtFilter.textProperty());

        tblData.getSortOrder().setAll(colTrash, colID);
    }
    //</editor-fold>

    //<editor-fold desc="Event handling.">

    /**
     * FXML event handling.
     *
     * @param event the event object.
     */
    @FXML
    void saveAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        var read = new CostCenter();
        read.setId(fmtID.getValue().strip());
        read.setTrash(false);
        read.setTitle(fmtTitle.getValue().strip());
        if (read.getId().length() != 3) {
            FluentAlert.warn()
                    .withTitle("Error")
                    .withHeader("El centro de costos que deseas grabar no es válido.")
                    .withContent("El código tiene que tener 3 caracteres y no puede estar en blanco.")
                    .defaultButtons()
                    .show();
            return;
        }
        if (read.getTitle().isBlank()
                || read.getTitle().length() > 255) {
            FluentAlert.warn()
                    .withTitle("Error")
                    .withHeader("El centro de costos que deseas grabar no es válido.")
                    .withContent("El título no puede quedar en blanco y debe tener un máximo de 255 caracteres.")
                    .defaultButtons()
                    .show();
            return;
        }
        try {
            FormUtils.upsertList(DAO.costcenter().insertOne(read), data);
            fmtID.setValue("");
            fmtTitle.setValue("");
        } catch (RuntimeException e) {
            easy("No se pudo grabar el nuevo centro de costos.").accept(e);
        }
    }

    /**
     * FXML event handling.
     *
     * @param event the event object.
     */
    @FXML
    void syncAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) loadData();
    }

    /**
     * FXML event handling.
     *
     * @param event the event object.
     */
    @FXML
    void titleCommit(@NotNull CellEditEvent<CostCenter, String> event) {
        if (event.isConsumed()) return;
        var item = event.getRowValue();
        try {
            item.setTitle(event.getNewValue());
            DAO.costcenter().updateOne(item);
        } catch (RuntimeException e) {
            item.setTitle(event.getOldValue());
            easy("No se pudo modificar el título en la base de datos.").accept(e);
        } finally {
            event.consume();
        }
    }

    /**
     * FXML event handling.
     *
     * @param event the event object.
     */
    @FXML
    void trashAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) trashRun(true);
    }

    /**
     * FXML event handling.
     *
     * @param event the event object.
     */
    @FXML
    void untrashAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) trashRun(false);
    }
    //</editor-fold>

    //<editor-fold desc="Public API">

    /**
     * Initializes the top stage of this form as a child
     * of application primary stage. The, shows.
     */
    public void show() {
        FormUtils.initChild(top);
        top.show();
        loadData();
    }
    //</editor-fold>

    //<editor-fold desc="Delegated methods.">

    /**
     * Loads the data on the table view.
     */
    private void loadData() {
        new SelectAllFlow<>(CostCenter.class)
                .first(data::clear)
                .forEach(data::add)
                .onError(easy("No se pudieron cargar los centros de costos."))
                .execute();
    }

    /**
     * Runs an update in trash state of selected item.
     *
     * @param trash the new trash flag value.
     */
    private void trashRun(boolean trash) {
        Optional.ofNullable(tblData.getSelectionModel().getSelectedItem())
                .ifPresentOrElse(
                        e -> {
                            try {
                                var c = DAO.costcenter().updateTrash(trash, e);
                                if (c > 0) e.setTrash(trash);
                            } catch (RuntimeException ex) {
                                easy("Ocurrió un error al modificar el centro de costo.").accept(ex);
                            }
                        },
                        () -> FluentAlert.warn()
                                .withTitle("Error")
                                .withHeader("No hay elemento seleccionado.")
                                .withContent("Para poder actualizar un elemento en la papelera, hay que seleccionarlo.")
                                .defaultButtons()
                                .show()
                );
    }
    //</editor-fold>

    //<editor-fold desc="Inner classes.">

    /**
     * Callback and predicate to filter based on the
     * ui filter criteria.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class TextFilter implements
            Callable<Predicate<CostCenter>>,
            Predicate<CostCenter> {
        /**
         * Temporal hold of pattern quoted txtFilter.text
         */
        private String str;

        @Override
        public Predicate<CostCenter> call() {
            str = Pattern.quote(txtFilter.getText());
            return !chkFilter.isSelected() || str.isBlank()
                    ? always()
                    : this;
        }

        @Override
        public boolean test(CostCenter costCenter) {
            if (costCenter == null) return false;
            return costCenter.getTitle().matches("(?i).*" + str + ".*")
                    || costCenter.getId().matches("(?i)^" + str + ".*");
        }
    }
    //</editor-fold>
}
