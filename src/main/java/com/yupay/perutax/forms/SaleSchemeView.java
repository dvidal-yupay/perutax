package com.yupay.perutax.forms;

import com.yupay.perutax.entities.SaleScheme;
import com.yupay.perutax.entities.SaleTotalClass;
import com.yupay.perutax.forms.flows.EditSelectionTrigger;
import com.yupay.perutax.forms.flows.InsertOneFlow;
import com.yupay.perutax.forms.flows.SelectAllFlow;
import com.yupay.perutax.forms.inner.StringConverterTo;
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
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.*;

/**
 * The controller form for the sale scheme master view.
 * FXML definition: sale_scheme-view.fxml
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class SaleSchemeView {
    /**
     * In-memory hold of items shown in the table.
     */
    private final ObservableList<SaleScheme> data =
            FXCollections.observableArrayList();

    /**
     * Control fxml injected: sale_scheme-view.fxml
     */
    @FXML
    private CheckBox chkFilter;

    /**
     * Control fxml injected: sale_scheme-view.fxml
     */
    @FXML
    private TableColumn<SaleScheme, SaleTotalClass> colClassBase;

    /**
     * Control fxml injected: sale_scheme-view.fxml
     */
    @FXML
    private TableColumn<SaleScheme, SaleTotalClass> colClassTax;

    /**
     * Control fxml injected: sale_scheme-view.fxml
     */
    @FXML
    private TableColumn<SaleScheme, String> colFactID;

    /**
     * Control fxml injected: sale_scheme-view.fxml
     */
    @FXML
    private TableColumn<SaleScheme, BigDecimal> colFee;

    /**
     * Control fxml injected: sale_scheme-view.fxml
     */
    @FXML
    private TableColumn<SaleScheme, String> colID;

    /**
     * Control fxml injected: sale_scheme-view.fxml
     */
    @FXML
    private TableColumn<SaleScheme, BigDecimal> colRate;

    /**
     * Control fxml injected: sale_scheme-view.fxml
     */
    @FXML
    private TableColumn<SaleScheme, String> colTitle;

    /**
     * Control fxml injected: sale_scheme-view.fxml
     */
    @FXML
    private TableColumn<SaleScheme, Boolean> colTrash;

    /**
     * Control fxml injected: sale_scheme-view.fxml
     */
    @FXML
    private TableView<SaleScheme> tblData;

    /**
     * Control fxml injected: sale_scheme-view.fxml
     */
    @FXML
    private Stage top;

    /**
     * Control fxml injected: sale_scheme-view.fxml
     */
    @FXML
    private TextField txtFilter;

    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see Forms#saleSchemeView()
     */
    SaleSchemeView() {
    }

    /**
     * FXML intializer.
     */
    @SuppressWarnings("DuplicatedCode")
    @FXML
    void initialize() {
        stringTableColumns(colFactID, colID, colTitle);
        booleanTableColumns(colTrash);
        decimalTableColumns(2, colFee);
        percentTableColumns(colRate);
        customTableColumns(StringConverterTo.newDefault(),
                colClassBase, colClassTax);

        columnValueFactory(colID, SaleScheme::idProperty);
        columnValueFactory(colFactID, SaleScheme::factoringIdProperty);
        columnValueFactory(colClassBase, SaleScheme::amountClassProperty);
        columnValueFactory(colClassTax, SaleScheme::taxClassProperty);
        columnValueFactory(colFee, SaleScheme::taxFeeProperty);
        columnValueFactory(colRate, SaleScheme::taxRateProperty);
        columnValueFactory(colTitle, SaleScheme::titleProperty);
        columnValueFactory(colTrash, SaleScheme::trashProperty);

        setupTableFilter(tblData, data, new FilterChanged(),
                chkFilter.selectedProperty(),
                txtFilter.textProperty());
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void createAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) {
            Forms.saleSchemeCard()
                    .creator()
                    .showAndWait()
                    .ifPresent(new InsertOneFlow<SaleScheme>()
                            .withOnSuccess(upsertList(data))
                            .defaultFail("Error al grabar nuevo esquema en base de datos.")
                            .asConsumer());
            event.consume();
        }
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void editAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) {
            editSelection();
            event.consume();
        }
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void reloadAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) {
            loadData();
            event.consume();
        }
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void tableClicked(@NotNull MouseEvent event) {
        if (!event.isConsumed()
                && event.getClickCount() > 1) {
            editSelection();
            event.consume();
        }
    }

    /**
     * Shows the stage as a child
     * of app's primary stage.
     * Also triggers a database list load.
     */
    public void show() {
        initChild(top);
        top.show();
        loadData();
    }

    /**
     * Delegated method to load
     * the items from database.
     */
    private void loadData() {
        new SelectAllFlow<>(SaleScheme.class)
                .first(data::clear)
                .forEach(data::add)
                .onError(easy("Hubo un error al cargar esquemas de venta."))
                .execute();
    }

    /**
     * Delegated method to edit the selected item.
     */
    private void editSelection() {
        new EditSelectionTrigger<SaleScheme>()
                .withCloner(SaleScheme::new)
                .withErrorText("Ocurrió un error al actualizar esquema de venta.")
                .withOnSuccess(upsertList(data))
                .withUserInput(Forms.saleSchemeCard())
                .withView(tblData)
                .run();
    }

    /**
     * Text Filtering implementation.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class FilterChanged implements Callable<Predicate<SaleScheme>>,
            Predicate<SaleScheme> {
        /**
         * The escaped string to find.
         */
        private String str;

        @Override
        public Predicate<SaleScheme> call() throws Exception {
            var text = txtFilter.getText();
            str = Pattern.quote(text);
            return !chkFilter.isSelected() || text.isBlank()
                    ? always() : this;
        }

        @Override
        public boolean test(SaleScheme saleScheme) {
            if (saleScheme == null) return false;
            return saleScheme.getTitle().matches("(?i).*" + str + ".*");
        }
    }
}
