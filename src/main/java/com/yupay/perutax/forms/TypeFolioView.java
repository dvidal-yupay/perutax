package com.yupay.perutax.forms;

import com.yupay.perutax.entities.TypeFolio;
import com.yupay.perutax.entities.functionals.TypeFolioParser;
import com.yupay.perutax.forms.flows.EditSelectionTrigger;
import com.yupay.perutax.forms.flows.ImportFileFlow;
import com.yupay.perutax.forms.flows.InsertOneFlow;
import com.yupay.perutax.forms.flows.SelectAllFlow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.*;

/**
 * The form controller for the Type of Folio master view.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class TypeFolioView {
    /**
     * The in-memory list holding data to show in table view.
     */
    private final ObservableList<TypeFolio> data
            = FXCollections.observableArrayList();

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private Button btnImport;

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private CheckBox chkFilter;

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private TableColumn<TypeFolio, Boolean> colCForeign;

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private TableColumn<TypeFolio, Boolean> colCPrch;

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private TableColumn<TypeFolio, Boolean> colCSale;

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private TableColumn<TypeFolio, Boolean> colCTaxCredit;

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private TableColumn<TypeFolio, String> colID;

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private TableColumn<TypeFolio, String> colPLAME;

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private TableColumn<TypeFolio, String> colRegexNum;

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private TableColumn<TypeFolio, String> colRegexSerie;

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private TableColumn<TypeFolio, String> colTitle;

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private TableColumn<TypeFolio, Boolean> colTrash;

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private TableView<TypeFolio> tblData;

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private Stage top;

    /**
     * FXML control injected from tfolio-view.fxml
     */
    @FXML
    private TextField txtFilter;

    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see Forms#typeFolioView()
     */
    TypeFolioView() {
    }

    /**
     * FXML initializer.
     */
    @SuppressWarnings("unchecked")
    @FXML
    void initialize() {

        stringTableColumns(colID, colPLAME, colTitle, colRegexSerie, colRegexNum);
        booleanTableColumns(colTrash, colCForeign, colCPrch, colCSale, colCTaxCredit);
        //noinspection DuplicatedCode
        columnValueFactory(colCForeign, TypeFolio::ctxtForeignProperty);
        columnValueFactory(colCPrch, TypeFolio::ctxtPurchaseProperty);
        columnValueFactory(colCSale, TypeFolio::ctxtSaleProperty);
        columnValueFactory(colCTaxCredit, TypeFolio::ctxtTaxCreditProperty);
        columnValueFactory(colID, TypeFolio::idProperty);
        columnValueFactory(colPLAME, TypeFolio::plameIdProperty);
        columnValueFactory(colRegexNum, TypeFolio::regexNumberProperty);
        columnValueFactory(colRegexSerie, TypeFolio::regexSerieProperty);
        columnValueFactory(colTitle, TypeFolio::titleProperty);
        columnValueFactory(colTrash, TypeFolio::trashProperty);
        setupTableFilter(tblData,
                data,
                new TextFilter(),
                chkFilter.selectedProperty(), txtFilter.textProperty());
        tblData.getSortOrder().setAll(colTrash, colID);
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void createAction(@NotNull ActionEvent event) {
        if (!event.isConsumed())
            Forms.typeFolioCard()
                    .creator()
                    .showAndWait()
                    .ifPresent(
                            new InsertOneFlow<TypeFolio>()
                                    .withOnSuccess(upsertList(data))
                                    .withOnFail(easy("Error al grabar nuevo tipo de folio."))
                                    .asConsumer());
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void editAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) new EditSelectionTrigger<TypeFolio>()
                .withView(tblData)
                .withCloner(TypeFolio::new)
                .withErrorText("Ocurrió un error al actualizar el tipo de folio.")
                .withOnSuccess(upsertList(data))
                .withUserInput(t -> Forms
                        .typeFolioCard()
                        .withValue(t)
                        .withMode(FormMode.EDITOR)
                        .showAndWait())
                .run();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void importAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) runImport(null);

    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void importDragDropped(@NotNull DragEvent event) {
        onImportDragDropped(event, this::runImport);
    }

    /**
     * FXML event handler.
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
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void refreshAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) loadData();
        event.consume();
    }

    /**
     * Loads the data from database into in-memory list.
     */
    private void loadData() {
        new SelectAllFlow<>(TypeFolio.class)
                .first(data::clear)
                .forEach(data::add)
                .onError(easy("No se pudieorn cargar los tipos de comprobante."))
                .execute();
    }

    /**
     * Delegated method to run the import flow from a file.
     *
     * @param dropped the dropped file or null to show file chooser.
     */
    private void runImport(@Nullable Path dropped) {
        var flow = ImportFileFlow
                .forEntity(TypeFolio.class)
                .withErrorBriefing("No se pudieron importar los tipos de comprobante.")
                .withLineParser(new TypeFolioParser());
        if (dropped == null) flow.withDefaultFilters();
        else flow.withDropFile(dropped);
        data.addAll(flow.execute());
    }

    /**
     * Initializes the top stage as child of
     * application's primary stage and shows.
     * This will trigger a loadData cycle.
     */
    public void show() {
        initChild(top);
        top.show();
        loadData();
    }

    /**
     * Text filter for user filtering.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class TextFilter implements
            Callable<Predicate<TypeFolio>>,
            Predicate<TypeFolio> {
        private String str;

        @Override
        public Predicate<TypeFolio> call() throws Exception {
            var txt = txtFilter.getText().strip();
            str = Pattern.quote(txt);
            return txt.isBlank() || !chkFilter.isSelected() ? always() : this;
        }

        @Override
        public boolean test(@Nullable TypeFolio typeFolio) {
            return typeFolio != null
                    && (typeFolio.getId().matches("(?i)^" + str + ".*")
                    || typeFolio.getTitle().matches("(?i).*" + str + ".*"));
        }
    }
}
