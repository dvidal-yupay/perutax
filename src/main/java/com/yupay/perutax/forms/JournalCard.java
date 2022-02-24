package com.yupay.perutax.forms;

import com.yupay.perutax.dao.DAO;
import com.yupay.perutax.entities.*;
import com.yupay.perutax.entities.functionals.PeriodComparator;
import com.yupay.perutax.forms.flows.SelectActiveFlow;
import com.yupay.perutax.forms.inner.*;
import freetimelabs.io.reactorfx.schedulers.FxSchedulers;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.converter.IntegerStringConverter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import reactor.core.Disposables;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.Optional;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.*;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

/**
 * Controller form for journal-card.fxml
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class JournalCard extends Dialog<Journal> {

    //<editor-fold desc="Private fields.">
    /**
     * The editing value.
     */
    private final ObjectProperty<Journal> value
            = new SimpleObjectProperty<>(this, "value");
    /**
     * The formatter for briefing.
     */
    private final TextFormatter<String> fmtBriefing = new UpperCaseFormatter();
    /**
     * The formatter for xRate.
     */
    private final TextFormatter<BigDecimal> fmtXRate = new FixedDecimalFormatter(3),
    /**
     * The formatter for total amount text field. - debit (FC)
     */
    fmtDebitFC = AmountDecimalConverter.formatter(),
    /**
     * The formatter for total amount text field. - credit (FC)
     */
    fmtCreditFC = AmountDecimalConverter.formatter(),
    /**
     * The formatter for total amount text field. - credit (SC)
     */
    fmtCreditSC = AmountDecimalConverter.formatter(),
    /**
     * The formatter for total amount text field. - debit (SC)
     */
    fmtDebitSC = AmountDecimalConverter.formatter();
    /**
     * Flag to check if the {@link #value} is adjusting when some
     * events or change listeners are triggered.
     */
    private boolean valueAdjusting;
    //</editor-fold>

    //<editor-fold desc="FXML controls.">
    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private ComboBox<Currenci> cboCurrency;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private ComboBox<TaxPeriod> cboPeriod;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private ComboBox<Subdiary> cboSubdiary;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private CheckBox chkReverted;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TableColumn<JournalDt, TaxAccount> colAccount;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TableColumn<JournalDt, CostCenter> colCostCenter;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TableColumn<JournalDt, BigDecimal> colCreditFC;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TableColumn<JournalDt, BigDecimal> colCreditSC;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TableColumn<JournalDt, BigDecimal> colDebitFC;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TableColumn<JournalDt, BigDecimal> colDebitSC;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TableColumn<JournalDt, JournalDtFolio> colFolio;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TableColumn<JournalDt, Integer> colLine;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TableColumn<JournalDt, JournalDtPerson> colPerson;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TableColumn<JournalDt, String> colRemark;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private DatePicker dtpDoc;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private DatePicker dtpDue;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private DatePicker dtpTax;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private Label lblExtBook;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private Label lblExtCUO;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private Label lblExtCorrelative;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private Label lblExtPeriod;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private Label lblStamp;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private Hyperlink lnkCorrelative;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private Hyperlink lnkID;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private MenuItem mniAddLine;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private MenuItem mniLink;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private MenuItem mniRemoveLine;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private MenuItem mniRevert;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private MenuItem mniVerify;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private Tab tabLinked;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TableView<JournalDt> tblDetail;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private DialogPane top;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TextField txtBriefing;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TextField txtCreditFC;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TextField txtCreditSC;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TextField txtDebitFC;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TextField txtDebitSC;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private TextField txtXRate;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private ToggleGroup grpRate;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private RadioMenuItem mniOptPrch;

    /**
     * FXML control injected from journal-card.fxml
     */
    @FXML
    private RadioMenuItem mniOptSale;
    //</editor-fold>

    //<editor-fold desc="Initialization.">

    /**
     * Package private constructor.
     * Use a static factory.
     *
     * @see Forms#journalCard()
     */
    JournalCard() {
    }

    /**
     * FXML initializer.
     */
    @SuppressWarnings("DuplicatedCode")
    @FXML
    void initialize() {
        setTitle("Asiento Contable - Diario");
        setDialogPane(top);
        setOnShowing(this::onShown);
        setResultConverter(b -> b == ButtonType.APPLY ? value.get() : null);
        txtXRate.setTextFormatter(fmtXRate);
        txtCreditFC.setTextFormatter(fmtCreditFC);
        txtDebitFC.setTextFormatter(fmtDebitFC);
        txtCreditSC.setTextFormatter(fmtCreditSC);
        txtDebitSC.setTextFormatter(fmtDebitSC);
        txtBriefing.setTextFormatter(fmtBriefing);

        customTableColumns(new IntegerStringConverter(), colLine);
        customTableColumns(new AmountDecimalConverter(),
                colDebitFC, colDebitSC,
                colDebitFC, colCreditFC);
        customTableColumns(new UpperCaseConverter(), colRemark);
        colAccount.setCellFactory(SearchableCell.forColumn(SearchableInfo.account()));
        colCostCenter.setCellFactory(SearchableCell.forColumn(SearchableInfo.costcenter()));
        colPerson.setCellFactory(SearchableWrapCell.journalDtPerson());
        colFolio.setCellFactory(DialogCell.forJournalFolio());

        intColumnValueFactory(colLine, JournalDt::lineProperty);
        columnValueFactory(colAccount, JournalDt::accountProperty);
        columnValueFactory(colCostCenter, JournalDt::costCenterProperty);
        columnValueFactory(colPerson, JournalDt::personProperty);
        columnValueFactory(colFolio, JournalDt::folioProperty);
        columnValueFactory(colRemark, JournalDt::referenceProperty);
        columnValueFactory(colDebitFC, JournalDt::debitFcProperty);
        columnValueFactory(colCreditFC, JournalDt::creditFcProperty);
        columnValueFactory(colDebitSC, JournalDt::debitScProperty);
        columnValueFactory(colCreditSC, JournalDt::creditScProperty);

        tblDetail.getSortOrder().clear();
        tblDetail.getSortOrder().add(colLine);

        value.addListener(new ValueChanged());
        fmtXRate.valueProperty().addListener(o -> {
            if (!valueAdjusting) updateTotals();
        });
        smartDatePickers(dtpDoc, dtpDue, dtpTax);
        grpRate.selectedToggleProperty().addListener(o -> {
            if (!valueAdjusting) computeXRate();
        });
        dtpTax.valueProperty().addListener(o -> {
            if (!valueAdjusting) computeXRate();
        });
        cboCurrency.valueProperty().addListener(o -> {
            if (!valueAdjusting) computeXRate();
        });
        top.lookupButton(ButtonType.APPLY).setDisable(true);
    }
    //</editor-fold>

    //<editor-fold desc="Event handling.">

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void addLineAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) value.get().createLine();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void linkAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) {
            System.out.println("LINK!!");
            event.consume();
        }
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void removeLineAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) {
            var sel = tblDetail.getSelectionModel().getFocusedIndex();
            if (sel >= 0) value.get().getDetail().remove(sel);
        }
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void revertAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) FluentAlert.info()
                .defaultButtons()
                .withContent("")
                .show();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void copyCUO(@NotNull ActionEvent event) {
        if (!event.isConsumed()) {
            var cb = Clipboard.getSystemClipboard();
            var cnt = new ClipboardContent();
            cnt.putString(value.get().getId());
            cb.setContent(cnt);
        }
        event.consume();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void copyCorrelative(@NotNull ActionEvent event) {
        if (!event.isConsumed()) {
            var cb = Clipboard.getSystemClipboard();
            var cnt = new ClipboardContent();
            cnt.putString(value.get().getCorrelative());
            cb.setContent(cnt);
        }
        event.consume();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void verifyAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        var val = value.get();
        if (val == null) return;
        var warns = FXCollections.<String>observableArrayList();
        if (val.getPeriod() == null) warns.add("Debe seleccionar un período tirbutario.");
        if (val.getSubdiary() == null) warns.add("Debe seleccionar un subdiario.");
        if (val.getDateTax() == null) warns.add("Debe consignar una fecha tributaria.");
        if (val.getDateDoc() == null) warns.add("Debe consignar una fecha de emisión o documento.");
        if (val.getCurrency() == null) warns.add("Debe seleccionar una moneda.");
        if (val.getXrate() == null || val.getXrate().compareTo(ZERO) < 1)
            warns.add("Debe poner un tipo de cambio mayor a 0.");
        else if (val.getCurrency() == Currenci.PEN && val.getXrate().compareTo(BigDecimal.ONE) != 0)
            warns.add("Ha seleccionado la moneda Soles, el tipo de cambio debe ser 1.000");
        if (val.getBriefing() == null || val.getBriefing().isBlank())
            warns.add("La glosa no puede estar en blanco.");
        if (val.getDetail().isEmpty()) warns.add("El asiento no tiene líneas de detalle.");
        val.getDetail().stream()
                .filter(p -> p.getAccount() == null)
                .mapToInt(JournalDt::getLine)
                .mapToObj("No ha consignado la cuenta contable en la línea %d."::formatted)
                .forEach(warns::add);
        val.getDetail().stream()
                .filter(p -> p.getDebitFc().compareTo(p.getCreditFc()) == 0)
                .mapToInt(JournalDt::getLine)
                .mapToObj("No ha consignado monto debe - haber en la línea %d."::formatted)
                .forEach(warns::add);
        if (fmtDebitFC.getValue().compareTo(fmtCreditFC.getValue()) != 0)
            warns.add("El total del debe y haber no cumplen el principio de partida doble.");
        top.lookupButton(ButtonType.APPLY).setDisable(!warns.isEmpty());
        event.consume();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void commitDebitFC(TableColumn.@NotNull CellEditEvent<JournalDt, BigDecimal> event) {
        if (!event.isConsumed()) {
            var nv = event.getNewValue();
            nv = nv == null ? new BigDecimal("0.00") : nv;
            if (nv.signum() >= 0) {
                event.getRowValue().setDebitFc(nv);
                event.getRowValue().setCreditFc(ZERO);
            } else {
                event.getRowValue().setCreditFc(nv.abs());
                event.getRowValue().setDebitFc(ZERO);
            }
            updateTotals();
        }
        event.consume();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void commitCreditFC(TableColumn.@NotNull CellEditEvent<JournalDt, BigDecimal> event) {
        if (!event.isConsumed()) {
            var nv = event.getNewValue();
            nv = nv == null ? new BigDecimal("0.00") : nv;
            if (nv.signum() >= 0) {
                event.getRowValue().setCreditFc(nv);
                event.getRowValue().setDebitFc(ZERO);
            } else {
                event.getRowValue().setDebitFc(nv.abs());
                event.getRowValue().setCreditFc(ZERO);
            }
            updateTotals();
        }
        event.consume();
    }

    /**
     * Private event handler.
     *
     * @param event the event object.
     */
    private void onShown(@NotNull DialogEvent event) {
        if (event.isConsumed()) return;
        cboCurrency.setItems(Currenci.observable());
        new SelectActiveFlow<>(Subdiary.class)
                .first(cboSubdiary.getItems()::clear)
                .forEach(cboSubdiary.getItems()::add)
                .onError(easy("No se pudieron cargar los subdiarios."))
                .execute();
        var cnt = Disposables.swap();
        var dsp = Flux.fromStream(DAO.period().specialize()::findOpen)
                .subscribeOn(Schedulers.single())
                .doFirst(cboPeriod.getItems()::clear)
                .publishOn(FxSchedulers.fxThread())
                .doAfterTerminate(() -> cboPeriod.getItems().sort(new PeriodComparator().descending()))
                .subscribe(cboPeriod.getItems()::add,
                        easy("No se pudieron cargar los períodos tributarios."));
        cnt.update(dsp);
    }
    //</editor-fold>

    //<editor-fold desc="Public API">

    /**
     * Fluent setter - with.
     *
     * @param value the value for {@link #value}
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull JournalCard withValue(@NotNull Journal value) {
        this.value.set(value);
        return this;
    }

    /**
     * Fluent setter for the card modality, which defines
     * disabled and enabled controls.
     *
     * @param mode the card modality.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull JournalCard withMode(@NotNull FormMode mode) {
        var creator = mode == FormMode.CREATOR;
        var viewer = mode == FormMode.READ_ONLY;
        cboPeriod.setDisable(!creator);
        cboSubdiary.setDisable(!creator);
        dtpTax.setEditable(creator);
        dtpDoc.setEditable(creator);
        dtpDue.setEditable(creator);
        cboCurrency.setDisable(!creator);
        txtXRate.setDisable(!creator);
        txtBriefing.setDisable(!creator);
        tblDetail.setEditable(creator);
        mniAddLine.setDisable(!creator);
        mniRemoveLine.setDisable(!creator);
        mniOptPrch.setDisable(!creator);
        mniOptSale.setDisable(!creator);
        mniRevert.setDisable(viewer);
        mniLink.setDisable(viewer);
        mniVerify.setDisable(viewer);
        return this;
    }

    /**
     * Sets the value with a new journal and modal to creator.
     *
     * @return this instance.
     */
    @Contract("->this")
    public @NotNull JournalCard creator() {
        return withValue(new Journal()).withMode(FormMode.CREATOR);
    }
    //</editor-fold>

    //<editor-fold desc="Convenient and delegated methods.">

    /**
     * Convenient method to update the total fields.
     */
    private void updateTotals() {
        var rte = Optional
                .ofNullable(fmtXRate.getValue())
                .orElseGet(() -> new BigDecimal("0.00"));
        value.get().getDetail()
                .forEach(l -> l.setDebitSc(l.getDebitFc().multiply(rte)
                        .setScale(2, HALF_UP)));
        value.get().getDetail()
                .forEach(l -> l.setCreditSc(l.getCreditFc().multiply(rte)
                        .setScale(2, HALF_UP)));
        fmtDebitFC.setValue(value.get()
                .getDetail()
                .stream()
                .map(JournalDt::getDebitFc)
                .reduce(new BigDecimal("0.00"),
                        BigDecimal::add));
        fmtCreditFC.setValue(value.get()
                .getDetail()
                .stream()
                .map(JournalDt::getCreditFc)
                .reduce(new BigDecimal("0.00"),
                        BigDecimal::add));
        fmtDebitSC.setValue(value.get()
                .getDetail()
                .stream()
                .map(JournalDt::getDebitSc)
                .reduce(new BigDecimal("0.00"),
                        BigDecimal::add));
        fmtCreditSC.setValue(value.get()
                .getDetail()
                .stream()
                .map(JournalDt::getCreditSc)
                .reduce(new BigDecimal("0.00"),
                        BigDecimal::add));
        if (!valueAdjusting) tblDetail.refresh();
    }

    /**
     * Computes the x rate for a given tax date.
     */
    private void computeXRate() {
        var dte = dtpTax.getValue();
        var cur = cboCurrency.getValue();
        if (cur == null) return;
        if (cur == Currenci.PEN) {
            fmtXRate.setValue(BigDecimal.ONE);
            return;
        }
        if (dte != null) {
            try {
                DAO.xrate()
                        .specialize()
                        .findForDate(dte)
                        .map(x -> {
                            if (grpRate.getSelectedToggle() == mniOptSale) {
                                return x.getSale();
                            } else if (grpRate.getSelectedToggle() == mniOptPrch) {
                                return x.getPrch();
                            } else {
                                return BigDecimal.ONE;
                            }
                        })
                        .ifPresentOrElse(fmtXRate::setValue,
                                () -> FluentAlert
                                        .warn()
                                        .withTitle("¡ATENCIÓN!")
                                        .withHeader("NO HAY TIPO DE CAMBIO PARA EL DÍA " + dte)
                                        .withContent("Por favor, verifique que en la fecha requerida hay " +
                                                "un tipo de cambio registrado y vuelva a intentar.")
                                        .defaultButtons()
                                        .show());
            } catch (RuntimeException e) {
                easy("No se pudo buscar el tipo de cambio para moneda y fecha.").accept(e);
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Inner classes.">

    /**
     * Listener for changes on value item.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class ValueChanged extends BaseChangeListener<Journal> {
        @Override
        protected void attach(@NotNull Journal value) {
            valueAdjusting = true;
            lnkID.textProperty().bind(value.idProperty());
            cboPeriod.valueProperty().bindBidirectional(value.periodProperty());
            lnkCorrelative.textProperty().bind(value.correlativeProperty());
            cboSubdiary.valueProperty().bindBidirectional(value.subdiaryProperty());
            dtpDoc.valueProperty().bindBidirectional(value.dateDocProperty());
            dtpDue.valueProperty().bindBidirectional(value.dateDueProperty());
            dtpTax.valueProperty().bindBidirectional(value.dateTaxProperty());
            lblStamp.textProperty().bind(value.createdAtProperty()
                    .asString("%1$tY-%1$tm-%1$td %1$tT.%1$tL"));
            cboCurrency.valueProperty().bindBidirectional(value.currencyProperty());
            fmtXRate.valueProperty().bindBidirectional(value.xrateProperty());
            chkReverted.selectedProperty().bind(value.revertedByProperty().isNotNull());
            tabLinked.disableProperty().bind(value.revertedByProperty().isNull());
            tblDetail.itemsProperty().bind(value.detailProperty());
            lblExtBook.textProperty().bind(value.refBookProperty());
            lblExtCorrelative.textProperty().bind(value.refCorrelativeProperty());
            lblExtCUO.textProperty().bind(value.refIdProperty());
            lblExtPeriod.textProperty().bind(value.refPeriodProperty());
            fmtBriefing.valueProperty().bindBidirectional(value.briefingProperty());
            updateTotals();
            valueAdjusting = false;
            mniRevert.disableProperty().bind(value.revertedByProperty().isNotNull());
        }

        @Override
        protected void dettach(@NotNull Journal value) {
            valueAdjusting = true;
            lnkID.textProperty().unbind();
            cboPeriod.valueProperty().unbindBidirectional(value.periodProperty());
            lnkCorrelative.textProperty().unbind();
            cboSubdiary.valueProperty().unbindBidirectional(value.subdiaryProperty());
            dtpDoc.valueProperty().unbindBidirectional(value.dateDocProperty());
            dtpDue.valueProperty().unbindBidirectional(value.dateDueProperty());
            dtpTax.valueProperty().unbindBidirectional(value.dateTaxProperty());
            lblStamp.textProperty().unbind();
            cboCurrency.valueProperty().unbindBidirectional(value.currencyProperty());
            fmtXRate.valueProperty().unbindBidirectional(value.xrateProperty());
            chkReverted.selectedProperty().unbind();
            tabLinked.disableProperty().unbind();
            tblDetail.itemsProperty().unbind();
            lblExtBook.textProperty().unbind();
            lblExtCorrelative.textProperty().unbind();
            lblExtCUO.textProperty().unbind();
            lblExtPeriod.textProperty().unbind();
            fmtBriefing.valueProperty().unbindBidirectional(value.briefingProperty());
            valueAdjusting = false;
            mniRevert.disableProperty().unbind();
        }

        @Override
        protected void clear() {
            valueAdjusting = true;
            clearLabeled(lnkID, lnkCorrelative, lblStamp,
                    lblExtBook, lblExtCorrelative, lblExtCUO, lblExtPeriod);
            clearComboBoxes(cboPeriod, cboCurrency, cboSubdiary);
            clearFormatters(new BigDecimal("0.00"),
                    fmtCreditFC, fmtCreditSC, fmtDebitFC, fmtDebitSC);
            fmtXRate.setValue(BigDecimal.ONE);
            tabLinked.setDisable(true);
            tblDetail.setItems(FXCollections.emptyObservableList());
            chkReverted.setSelected(false);
            clearDatePickers(dtpDoc, dtpDue, dtpTax);
            fmtBriefing.setValue("");
            valueAdjusting = false;
            mniRevert.setDisable(false);
        }
    }
    //</editor-fold>
}
