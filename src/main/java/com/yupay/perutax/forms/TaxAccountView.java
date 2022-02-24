package com.yupay.perutax.forms;

import com.yupay.perutax.entities.AccountNature;
import com.yupay.perutax.entities.CostGroup;
import com.yupay.perutax.entities.Currenci;
import com.yupay.perutax.entities.TaxAccount;
import com.yupay.perutax.entities.functionals.TaxAccountParser;
import com.yupay.perutax.forms.flows.ImportFileFlow;
import com.yupay.perutax.forms.flows.InsertOneFlow;
import com.yupay.perutax.forms.flows.SelectAllFlow;
import com.yupay.perutax.forms.flows.UpdateOneFlow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.*;

/**
 * A controller for TaxAccount master view form.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class TaxAccountView {

    //<editor-fold desc="Private fields.">
    /**
     * The in-memory list holding the table view data.
     */
    private final ObservableList<TaxAccount> data
            = FXCollections.observableArrayList();
    //</editor-fold>

    //<editor-fold desc="FXML controls.">
    /**
     * FXML control injected from taxaccount-view.fxml
     */
    @FXML
    private Button btnImport;

    /**
     * FXML control injected from taxaccount-view.fxml
     */
    @FXML
    private Stage top;

    /**
     * FXML control injected from taxaccount-view.fxml
     */
    @FXML
    private CheckBox chkFilter;

    /**
     * FXML control injected from taxaccount-view.fxml
     */
    @FXML
    private TableColumn<TaxAccount, BigDecimal> colBalance;

    /**
     * FXML control injected from taxaccount-view.fxml
     */
    @FXML
    private TableColumn<TaxAccount, Currenci> colCurrency;

    /**
     * FXML control injected from taxaccount-view.fxml
     */
    @FXML
    private TableColumn<TaxAccount, CostGroup> colGrpCost;

    /**
     * FXML control injected from taxaccount-view.fxml
     */
    @FXML
    private TableColumn<TaxAccount, String> colID;

    /**
     * FXML control injected from taxaccount-view.fxml
     */
    @FXML
    private TableColumn<TaxAccount, String> colName;

    /**
     * FXML control injected from taxaccount-view.fxml
     */
    @FXML
    private TableColumn<TaxAccount, AccountNature> colNature;

    /**
     * FXML control injected from taxaccount-view.fxml
     */
    @FXML
    private TableColumn<TaxAccount, Boolean> colTrash;

    /**
     * FXML control injected from taxaccount-view.fxml
     */
    @FXML
    private TableColumn<TaxAccount, Boolean> colUsable;

    /**
     * FXML control injected from taxaccount-view.fxml
     */
    @FXML
    private TableView<TaxAccount> tblData;

    /**
     * FXML control injected from taxaccount-view.fxml
     */
    @FXML
    private TextField txtFilter;
    //</editor-fold>

    //<editor-fold desc="Initialization.">

    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see Forms#taxAccountView()
     */
    TaxAccountView() {
    }

    /**
     * FXML initializer.
     */
    @FXML
    void initialize() {
        stringTableColumns(colID, colName);
        booleanTableColumns(colTrash, colUsable);
        amountTableColumns(colBalance);
        objectTableColumn(colCurrency, Objects::toString);
        objectTableColumn(colGrpCost, c -> Objects.toString(c, ""));
        //noinspection DuplicatedCode
        objectTableColumn(colNature, Objects::toString);
        columnValueFactory(colID, TaxAccount::idProperty);
        columnValueFactory(colName, TaxAccount::nameProperty);
        columnValueFactory(colTrash, TaxAccount::trashProperty);
        columnValueFactory(colUsable, TaxAccount::usableProperty);
        columnValueFactory(colBalance, TaxAccount::balanceProperty);
        columnValueFactory(colCurrency, TaxAccount::currencyProperty);
        columnValueFactory(colGrpCost, TaxAccount::groupCostProperty);
        columnValueFactory(colNature, TaxAccount::natureProperty);

        setupTableFilter(tblData,
                data,
                new TextFilter(),
                chkFilter.selectedProperty(), txtFilter.textProperty());
        tblData.getSortOrder().clear();
        tblData.getSortOrder().add(colID);


    }
    //</editor-fold>

    //<editor-fold desc="Event handling.">

    /**
     * FXML Event handler.
     *
     * @param event the event object.
     */
    @FXML
    void createAction(@NotNull ActionEvent event) {
        if (!event.isConsumed())
            Forms.taxAccountCard()
                    .createNew()
                    .showAndWait()
                    .ifPresent(new InsertOneFlow<TaxAccount>()
                            .withOnSuccess(i -> upsertList(i, data))
                            .withOnFail(easy("No se pudo grabar la cuenta contable."))
                            .asConsumer());
    }

    /**
     * FXML Event handler.
     *
     * @param event the event object.
     */
    @FXML
    void editAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) editSelected();
    }

    /**
     * FXML Event handler.
     *
     * @param event the event object.
     */
    @FXML
    void tableClicked(@NotNull MouseEvent event) {
        if (!event.isConsumed()
                && event.getClickCount() > 1) editSelected();
    }

    /**
     * FXML Event handler.
     *
     * @param event the event object.
     */
    @FXML
    void syncAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) loadData();
    }

    /**
     * FXML event hanlder.
     *
     * @param event the event object.
     */
    @FXML
    void importAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) runImport(null);
    }

    /**
     * FXML event hanlder.
     *
     * @param event the event object.
     */
    @FXML
    void importDragOver(@NotNull DragEvent event) {
        if (!event.isConsumed()
                && event.getGestureSource() != btnImport
                && event.getDragboard().hasFiles())
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        event.consume();
    }

    /**
     * FXML event hanlder.
     *
     * @param event the event object.
     */
    @FXML
    void importDragDropped(@NotNull DragEvent event) {
        onImportDragDropped(event, this::runImport);
    }
    //</editor-fold>

    //<editor-fold desc="Public API.">

    /**
     * Initializes the top stage of this form as
     * a child of app's primary stage and shows it.
     * Then, will trigger a data sync cycle.
     */
    public void show() {
        initChild(top);
        top.show();
        loadData();
    }
    //</editor-fold>

    //<editor-fold desc="Delegated methods.">

    /**
     * Loads the data into table view.
     */
    private void loadData() {
        new SelectAllFlow<>(TaxAccount.class)
                .first(data::clear)
                .forEach(data::add)
                .onError(easy("No se pudo cargar el plan contable de la base de datos."))
                .execute();
    }

    /**
     * Edits the selected item in a TaxAccount card form.
     */
    private void editSelected() {
        var selected = tblData.getSelectionModel().getSelectedItem();
        if (selected == null) {
            FluentAlert.err()
                    .withTitle("Error")
                    .withHeader("Necesitas seleccionar un elemento.")
                    .withContent("Para poder editar un elemento, primero debes seleccionarlo " +
                            "en la tabla de la vista maestra.")
                    .defaultButtons()
                    .show();
        } else {
            Forms.taxAccountCard()
                    .withMode(FormMode.EDITOR)
                    .withValue(new TaxAccount(selected))
                    .showAndWait()
                    .ifPresent(new UpdateOneFlow<TaxAccount>()
                            .withOnSuccess(i -> upsertList(i, data))
                            .withOnFail(easy("No se pudo actualizar la cuenta contable."))
                            .asConsumer());
        }
    }

    /**
     * Delegated method to run the import flow from a file.
     *
     * @param dropped the dropped file or null to show file chooser.
     */
    private void runImport(@Nullable Path dropped) {
        var flow = ImportFileFlow
                .forEntity(TaxAccount.class)
                .withErrorBriefing("No se pudo importar el plan contable desde el archivo externo.")
                .withLineParser(new TaxAccountParser());
        if (dropped == null) {
            flow.withDefaultFilters();
        } else {
            flow.withDropFile(dropped);
        }
        data.addAll(flow.execute());
    }
    //</editor-fold>

    //<editor-fold desc="Inner classes.">

    /**
     * Callable and predicate for tax account filtering.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class TextFilter implements
            Callable<Predicate<TaxAccount>>,
            Predicate<TaxAccount> {
        /**
         * Temporal hold of txtFilter.text quoted by pattern.
         */
        private String str;

        @Override
        public Predicate<TaxAccount> call() {
            str = Pattern.quote(txtFilter.getText());
            return !chkFilter.isSelected()
                    || str.isBlank()
                    ? always()
                    : this;
        }

        @Override
        public boolean test(TaxAccount taxAccount) {
            if (taxAccount == null) return false;

            var r = taxAccount.getName().matches("(?i)^.*" + str + ".*");
            if (str.length() <= 8) {
                r |= taxAccount.getId().matches("(?i)^" + str + ".*");
            }
            return r;

        }
    }
    //</editor-fold>
}
