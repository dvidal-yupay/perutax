package com.yupay.perutax.forms;

import com.yupay.perutax.dao.DAO;
import com.yupay.perutax.entities.TaxPeriod;
import com.yupay.perutax.forms.flows.SelectAllFlow;
import com.yupay.perutax.forms.inner.NumericFormatter;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.*;

/**
 * Taxation period master view controller.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class TaxPeriodForm {

    //<editor-fold desc="Private fields.">
    /**
     * The text formatter to enforce only 6 numeric characters
     * as a filter for the table view.
     */
    private final TextFormatter<String> fmtFilter
            = new NumericFormatter(6);
    /**
     * The taxation periods that will be shown in the table view.
     */
    private final ObservableList<TaxPeriod> data
            = FXCollections.observableArrayList();
    //</editor-fold>

    //<editor-fold desc="FXML controls.">
    /**
     * FXML Controls injected from taxperiod.fxml
     */
    @FXML
    private Stage top;

    /**
     * FXML Controls injected from taxperiod.fxml
     */
    @FXML
    private TableColumn<TaxPeriod, LocalDateTime> colClosed;

    /**
     * FXML Controls injected from taxperiod.fxml
     */
    @FXML
    private TableColumn<TaxPeriod, LocalDate> colFrom;

    /**
     * FXML Controls injected from taxperiod.fxml
     */
    @FXML
    private TableColumn<TaxPeriod, String> colID;

    /**
     * FXML Controls injected from taxperiod.fxml
     */
    @FXML
    private TableColumn<TaxPeriod, LocalDate> colUntil;

    /**
     * FXML Controls injected from taxperiod.fxml
     */
    @FXML
    private TableView<TaxPeriod> tblData;

    /**
     * FXML Controls injected from taxperiod.fxml
     */
    @FXML
    private TextField txtFilter;
    //</editor-fold>

    //<editor-fold desc="Initialization.">

    /**
     * Package private constructor. To invoke, use a factory.
     *
     * @see Forms#periodForm()
     */
    TaxPeriodForm() {
    }

    /**
     * FXML initialization.
     */
    @FXML
    void initialize() {
        //Setup filter constraints.
        txtFilter.setTextFormatter(fmtFilter);

        //Setup table columns.
        stringTableColumns(colID);
        dateTableColumns(colFrom, colUntil);
        dateTableColumns(colClosed);
        columnValueFactory(colID, TaxPeriod::idProperty);
        columnValueFactory(colFrom, TaxPeriod::dateFromProperty);
        columnValueFactory(colUntil, TaxPeriod::dateUntilProperty);
        columnValueFactory(colClosed, TaxPeriod::closedProperty);

        //Setup filtering and sorting.
        var filtered = new FilteredList<>(data, always());
        filtered.predicateProperty().bind(Bindings.createObjectBinding(
                new FilterSupplier(), txtFilter.textProperty()));
        var sorted = new SortedList<>(filtered);
        sorted.comparatorProperty().bind(tblData.comparatorProperty());
        tblData.setItems(sorted);
        tblData.getSortOrder().clear();
        tblData.getSortOrder().add(colID);
    }
    //</editor-fold>

    //<editor-fold desc="Events handling.">

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void createAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        var ls = new FluentInput().withNumericInput(4)
                .withHeaderText("Indique el año calendario en el cual se crearán los períodos.")
                .withContentText("Año calendario")
                .withTitle("Datos Requeridos")
                .showAndWait()
                .filter(s -> s.length() == 4)
                .stream()
                .flatMap(s -> IntStream.range(1, 13).mapToObj(i -> "%s%02d".formatted(s, i)))
                .map(TaxPeriod::new)
                .toList();
        try {
            data.addAll(DAO.period().insertMany(ls));
        } catch (RuntimeException e) {
            easy("No se pudieron grabar los períodos en la base de datos.").accept(e);
        }
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void lockAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        var select = tblData.getSelectionModel().getSelectedItem();
        if (select == null) {
            FluentAlert.info()
                    .withTitle("Selección Vacía")
                    .defaultButtons()
                    .withHeader("Debe seleccionar un elemento.")
                    .show();
            return;
        }
        if (select.getClosed() != null) {
            FluentAlert.info()
                    .withTitle("Período Cerrado")
                    .defaultButtons()
                    .withHeader("El período seleccionado ya está cerrado.")
                    .show();
            return;
        }
        var reject = FluentAlert.warn()
                .withTitle("Confirmación Requerida")
                .withHeader("NO SE PUEDE DESHACER ESTA OPERACIÓN, ¿ESTÁ SEGURO?")
                .withContent("Va a cerrar un período tributario, esta operación no puede deshacerse.")
                .withButtons(ButtonType.YES, ButtonType.NO)
                .showAndWait()
                .filter(ButtonType.YES::equals)
                .isEmpty();
        if (reject) return;
        try {
            var closed = DAO.period().specialize().close(select);
            upsertList(closed, data);
        } catch (RuntimeException e) {
            easy("No se pudo grabar el sello en la base de datos.").accept(e);
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
    //</editor-fold>

    //<editor-fold desc="Public API.">

    /**
     * Initializes the top stage as a child
     * of application current main window.
     * Then, shows the form.
     */
    public void show() {
        FormUtils.initChild(top);
        top.show();
        loadData();
    }
    //</editor-fold>

    //<editor-fold desc="Delegated methods.">

    /**
     * Loads the data from database. It's invoked once
     * when showing, and then every time the user clicks
     * on update/refresh/sync button.
     */
    private void loadData() {
        new SelectAllFlow<>(TaxPeriod.class)
                .first(data::clear)
                .onError(easy("No se pudieron cargar los períodos tributarios de la base de datos."))
                .forEach(data::add)
                .execute();
    }
    //</editor-fold>

    //<editor-fold desc="Inner implementations.">

    /**
     * Inner callable to create an object binding for filtering.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class FilterSupplier implements Callable<Predicate<TaxPeriod>>,
            Predicate<TaxPeriod> {
        @Override
        public Predicate<TaxPeriod> call() {
            var str = txtFilter.getText();
            return str == null || str.isBlank()
                    ? always()
                    : this;
        }

        @Override
        public boolean test(TaxPeriod taxPeriod) {
            return taxPeriod != null
                    && taxPeriod.getId()
                    .startsWith(txtFilter.getText().strip());
        }
    }
    //</editor-fold>
}
