package com.yupay.perutax.forms;

import com.yupay.perutax.dao.DAO;
import com.yupay.perutax.entities.TaxPeriod;
import com.yupay.perutax.entities.XRate;
import com.yupay.perutax.entities.functionals.PeriodComparator;
import com.yupay.perutax.entities.functionals.XRateParser;
import com.yupay.perutax.forms.flows.ImportFileFlow;
import com.yupay.perutax.forms.flows.SelectAllFlow;
import com.yupay.perutax.forms.inner.FixedDecimalFormatter;
import com.yupay.perutax.forms.inner.SmartDateConverter;
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

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Objects;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.*;
import static java.math.BigDecimal.ZERO;

/**
 * Controller class for the master eXchange rate form.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class XRateForm {

    //<editor-fold desc="Inner fields.">
    /**
     * The in-memory list backingup elements
     * shown in table view.
     */
    private final ObservableList<XRate> data
            = FXCollections.observableArrayList();
    /**
     * The formatter for purchase rate in the editor area.
     */
    private final TextFormatter<BigDecimal> fmtPurchase = new FixedDecimalFormatter(3),
    /**
     * The formatter for sale rate in the editor area.
     */
    fmtSale = new FixedDecimalFormatter(3);
    /**
     * The currently editing item.
     */
    private XRate editing;
    //</editor-fold>

    //<editor-fold desc="FXML controls.">
    /**
     * FXML control injected from xrate.fxml
     */
    @FXML
    private ComboBox<TaxPeriod> cboPeriod;

    /**
     * FXML control injected from xrate.fxml
     */
    @FXML
    private TableView<XRate> tblData;

    /**
     * FXML control injected from xrate.fxml
     */
    @FXML
    private TableColumn<XRate, Long> colID;

    /**
     * FXML control injected from xrate.fxml
     */
    @FXML
    private TableColumn<XRate, LocalDate> colDate;

    /**
     * FXML control injected from xrate.fxml
     */
    @FXML
    private TableColumn<XRate, BigDecimal> colPurchase;

    /**
     * FXML control injected from xrate.fxml
     */
    @FXML
    private TableColumn<XRate, BigDecimal> colSale;

    /**
     * FXML control injected from xrate.fxml
     */
    @FXML
    private DatePicker editDate;

    /**
     * FXML control injected from xrate.fxml
     */
    @FXML
    private TextField editPurchase;

    /**
     * FXML control injected from xrate.fxml
     */
    @FXML
    private TextField editSale;

    /**
     * FXML control injected from xrate.fxml
     */
    @FXML
    private Stage top;
    /**
     * FXML control injected from xrate.fxml
     */
    @FXML
    private Button btnImport;
    //</editor-fold>

    //<editor-fold desc="Initialization.">

    /**
     * Package-private constructor,
     * use a factory instead
     *
     * @see Forms#xrate()
     */
    XRateForm() {
    }

    /**
     * FXML initializer.
     */
    @FXML
    void initialize() {
        editPurchase.setTextFormatter(fmtPurchase);
        editSale.setTextFormatter(fmtSale);
        editDate.setConverter(new SmartDateConverter());

        setEditing(null);

        dateTableColumns(colDate);
        objectTableColumn(colID, Objects::toString);
        decimalTableColumns(3, colPurchase, colSale);

        columnValueFactory(colDate, XRate::taxDateProperty);
        columnValueFactory(colPurchase, XRate::prchProperty);
        columnValueFactory(colSale, XRate::saleProperty);
        longColumnValueFactory(colID, XRate::idProperty);

        tblData.setItems(data);
        tblData.getSelectionModel()
                .selectedItemProperty()
                .addListener((ob, o, n) -> setEditing(n == null ? new XRate() : n));
        tblData.getSortOrder().clear();
        tblData.getSortOrder().add(colDate);
    }
    //</editor-fold>

    //<editor-fold desc="Event handling.">

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void editCreateAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) tblData.getSelectionModel().clearSelection();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void editDeleteAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        //Check editing.
        if (tblData.getSelectionModel().isEmpty()) {
            FluentAlert.warn()
                    .defaultButtons()
                    .withContent("Debe seleccionar el elemento a eliminar.")
                    .withTitle("Error")
                    .show();
            return;
        }
        //Ask user confirmation.
        if (FluentAlert.warn()
                .withButtons(ButtonType.YES, ButtonType.NO)
                .withContent("""
                        Usted está a punto de eliminar un tipo de cambio del sistema.
                        Esta operación no se puede deshacer, ni alterará el tipo de cambio
                        en ninguna de las operaciones ya registradas.""")
                .withHeader("¿Desea continuar? NO PUEDE DESHACER ESTA OPERACIÓN")
                .withTitle("Confirmación Requerida")
                .showAndWait()
                .filter(ButtonType.YES::equals)
                .isEmpty()) return;

        //Item should be read from selection model, since editing is a detached copy.
        var selection = tblData.getSelectionModel().getSelectedItem();
        try {
            //Get the tax period for date.
            var p = DAO.period().specialize()
                    .forDate(selection.getTaxDate())
                    .filter(x -> x.getClosed() == null);
            //If the taxperiod exist and is not closed.
            if (p.isPresent()) {
                DAO.xrate().deleteOne(selection);
                data.removeIf(x -> x.getId() == selection.getId());
            }//If doesn't exist or is closed.
            else {
                FluentAlert.err()
                        .defaultButtons()
                        .withContent("No hay un período abierto registrado para la fecha.")
                        .withHeader("No se puede eliminar el tipo de cambio del "
                                + selection.getTaxDate())
                        .withTitle("¡ERROR!")
                        .show();
            }
        } catch (RuntimeException e) {
            easy("No se pudo eliminar el tipo de cambio del día " +
                    selection.getTaxDate())
                    .accept(e);
        }
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void editSaveAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        //Validate purchase value.
        if (editing.getPrch() == null || editing.getPrch().compareTo(ZERO) <= 0) {
            FluentAlert.warn()
                    .withTitle("¡Atención!")
                    .withContent("El tipo de cambio compra no puede ser 0 o negativo.")
                    .show();
            return;
        }
        //Validate sale value.
        if (editing.getSale() == null || editing.getSale().compareTo(ZERO) <= 0) {
            FluentAlert.warn()
                    .withTitle("¡Atención!")
                    .withContent("El tipo de cambio venta no puede ser 0 o negativo.")
                    .show();
            return;
        }
        //Validate tax date.
        if (editing.getTaxDate() == null) {
            FluentAlert.warn()
                    .withTitle("¡Atención!")
                    .withContent("No ha consignado la fecha del tipo de cambio.")
                    .show();
            return;
        }
        //Check that there's a tax period in database for the taxDate.
        var tPer = DAO.period().specialize().forDate(editing.getTaxDate());
        if (tPer.isEmpty()) {
            FluentAlert.warn()
                    .withTitle("¡Atención!")
                    .withContent("No hay un período tributario registrado para esta fecha.")
                    .show();
            return;
        }
        //Check that the tax period for the taxDate is not closed.
        var tPerVal = tPer.get();
        if (tPerVal.getClosed() != null) {
            FluentAlert.warn()
                    .withTitle("¡Atención!")
                    .withContent("El período " + tPerVal + " ya está cerrado.")
                    .show();
            return;
        }

        //Save the item.
        try {
            if (editing.getId() < 1) DAO.xrate().insertOne(editing);
            else DAO.xrate().updateOne(editing);
            //Update table view.
            upsertList(editing, data);
            //Clear editing.
            setEditing(null);
        } catch (RuntimeException e) {
            easy("No se pudo grabar el tipo de cambio.").accept(e);
        }
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void importAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) {
            var ls = ImportFileFlow.forEntity(XRate.class)
                    .withDefaultFilters()
                    .withErrorBriefing("No se pudo importar el archivo con tipos de cambio.")
                    .withLineParser(new XRateParser())
                    .execute();
            data.addAll(ls);
        }
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
    void importDragDropped(@NotNull DragEvent event) {
        onImportDragDropped(event, this::runImport);
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void syncAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        var period = cboPeriod.getValue();
        if (period == null) {
            FluentAlert.warn()
                    .withTitle("¡Atención!")
                    .withHeader("Necesito un período tributario.")
                    .withContent("Para poder descargar los tipos de cambio, " +
                            "necesito que selecciones un período.")
                    .defaultButtons()
                    .show();
            return;
        }
        try {
            data.clear();
            DAO.xrate().specialize()
                    .findInPeriod(period)
                    .forEach(data::add);
        } catch (RuntimeException e) {
            easy("Ocurrió un error al descargar los tipos de cambio del período " + period)
                    .accept(e);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Public API">

    /**
     * Initializes the top stage of this form
     * as a child of the main application window.
     * Then, will show.
     */
    public void show() {
        FormUtils.initChild(top);
        top.show();
        loadCombo();
    }
    //</editor-fold>

    //<editor-fold desc="Delegated methods.">

    /**
     * Delegated method to load the data into table view.
     */
    private void loadCombo() {
        new SelectAllFlow<>(TaxPeriod.class)
                .first(cboPeriod.getItems()::clear)
                .forEach(cboPeriod.getItems()::add)
                .onComplete(() -> cboPeriod.getItems().sort(new PeriodComparator().descending()))
                .onError(easy("Ocurrió un error al cargar los períodos tirbutarios."))
                .execute();
    }

    /**
     * Delegated method to run the importation process.
     *
     * @param dropped the dropped file if any, if null a
     *                file chooser will be shown.
     */
    private void runImport(@Nullable Path dropped) {
        var flow = ImportFileFlow
                .forEntity(XRate.class)
                .withErrorBriefing("No se pudieron importar los tipos de cambio.")
                .withLineParser(new XRateParser());
        if (dropped == null) {
            flow.withDefaultFilters();
        } else {
            flow.withDropFile(dropped);
        }
        data.addAll(flow.execute());
    }

    /**
     * Delegated method to check new editing values
     * and attach or dettach the UI from it.
     *
     * @param editing the new editing value.
     */
    private void setEditing(XRate editing) {
        if (this.editing != null) detach(this.editing);
        clear();
        this.editing = editing == null ? new XRate() : new XRate(editing);
        attach(this.editing);
    }

    /**
     * Dettaches a value property from the UI.
     *
     * @param editing the editing item to dettach.
     */
    private void detach(@NotNull XRate editing) {
        editDate.valueProperty().unbindBidirectional(editing.taxDateProperty());
        fmtPurchase.valueProperty().unbindBidirectional(editing.prchProperty());
        fmtSale.valueProperty().unbindBidirectional(editing.saleProperty());
    }

    /**
     * Clears all UI values.
     */
    private void clear() {
        editDate.setValue(null);
        fmtPurchase.setValue(null);
        fmtSale.setValue(null);
    }

    /**
     * Attaches a value property to the UI.
     *
     * @param editing the editing item to attach.
     */
    private void attach(@NotNull XRate editing) {
        editDate.valueProperty().bindBidirectional(editing.taxDateProperty());
        fmtPurchase.valueProperty().bindBidirectional(editing.prchProperty());
        fmtSale.valueProperty().bindBidirectional(editing.saleProperty());
    }
    //</editor-fold>
}
