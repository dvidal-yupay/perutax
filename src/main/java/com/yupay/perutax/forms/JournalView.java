package com.yupay.perutax.forms;

import com.yupay.perutax.entities.Currenci;
import com.yupay.perutax.entities.Journal;
import com.yupay.perutax.entities.JournalSnapshot;
import com.yupay.perutax.entities.TaxPeriod;
import com.yupay.perutax.entities.functionals.PeriodComparator;
import com.yupay.perutax.forms.flows.InsertOneFlow;
import com.yupay.perutax.forms.flows.SelectAllFlow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.*;

/**
 * FXML controller for the master view of journal snapshots.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class JournalView {
    /**
     * The in-memory inner list data.
     */
    private final ObservableList<JournalSnapshot> data
            = FXCollections.observableArrayList();

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private Stage top;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private ComboBox<TaxPeriod> cboFilter;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private CheckBox chkFilter;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private TableColumn<JournalSnapshot, String> colBriefing;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private TableColumn<JournalSnapshot, String> colCUO;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private TableColumn<JournalSnapshot, String> colCorrelative;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private TableColumn<JournalSnapshot, Currenci> colCurrency;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private TableColumn<JournalSnapshot, LocalDate> colDateDoc;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private TableColumn<JournalSnapshot, LocalDate> colDateDue;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private TableColumn<JournalSnapshot, LocalDate> colDateTax;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private TableColumn<JournalSnapshot, String> colPeriod;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private TableColumn<JournalSnapshot, Boolean> colReverted;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private TableColumn<JournalSnapshot, LocalDateTime> colStamp;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private TableColumn<JournalSnapshot, String> colSubID;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private TableColumn<JournalSnapshot, String> colSubName;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private TableView<JournalSnapshot> tblData;

    /**
     * FXML control injected from journal-view.fxml
     */
    @FXML
    private TextField txtFilter;

    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see Forms#journalView()
     */
    JournalView() {
    }

    /**
     * FXML initializer.
     */
    @SuppressWarnings("unchecked")
    @FXML
    void initialize() {
        setupTableFilter(tblData,
                data,
                new TextFilter(),
                chkFilter.selectedProperty(),
                txtFilter.textProperty(),
                cboFilter.valueProperty());
        tblData.getSortOrder().setAll(colPeriod, colCorrelative);
        stringTableColumns(colBriefing, colPeriod, colCorrelative,
                colCUO, colSubID, colSubName);
        booleanTableColumns(colReverted);
        dateTableColumns(colDateDoc, colDateDue, colDateTax);
        dateTableColumns(colStamp);
        objectTableColumn(colCurrency, Objects::toString);

        columnValueFactory(colBriefing, JournalSnapshot::briefingProperty);
        columnValueFactory(colPeriod, JournalSnapshot::taxPeriodProperty);
        columnValueFactory(colCorrelative, JournalSnapshot::correlativeProperty);
        columnValueFactory(colCUO, JournalSnapshot::idProperty);
        columnValueFactory(colSubID, JournalSnapshot::subIdProperty);
        columnValueFactory(colSubName, JournalSnapshot::subTitleProperty);
        columnValueFactory(colReverted, JournalSnapshot::revertedProperty);
        columnValueFactory(colDateDoc, JournalSnapshot::dateDocProperty);
        columnValueFactory(colDateDue, JournalSnapshot::dateDueProperty);
        columnValueFactory(colDateTax, JournalSnapshot::dateTaxProperty);
        columnValueFactory(colStamp, JournalSnapshot::createdAtProperty);
        columnValueFactory(colCurrency, JournalSnapshot::currencyProperty);

    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void addAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        Forms.journalCard().creator()
                .showAndWait()
                .ifPresent(new InsertOneFlow<Journal>()
                        .withOnSuccess(x -> data.add(new JournalSnapshot(x)))
                        .withOnFail(easy("No se pudo grabar el asiento contable."))
                        .asConsumer());
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
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void viewAction(@NotNull ActionEvent event) {

    }

    /**
     * Initializes this form stage as child of application's
     * primary stage. Then will show. This also triggers a
     * load data cycle.
     */
    public void show() {
        initChild(top);
        top.show();
        secondaryLoad();
        loadData();
    }

    /**
     * Loads secondary data lists.
     */
    private void secondaryLoad() {
        new SelectAllFlow<>(TaxPeriod.class)
                .first(cboFilter.getItems()::clear)
                .forEach(cboFilter.getItems()::add)
                .onComplete(() -> cboFilter.getItems().sort(new PeriodComparator().descending()))
                .execute();
    }

    /**
     * Convenient method to load the data into the table view.
     */
    private void loadData() {
        new SelectAllFlow<>(JournalSnapshot.class)
                .first(data::clear)
                .forEach(data::add)
                .onError(easy("No se pudo cargar el listado de asientos del diario."))
                .execute();
    }

    /**
     * Text filtering implementation.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class TextFilter implements
            Callable<Predicate<JournalSnapshot>>,
            Predicate<JournalSnapshot> {
        /**
         * Hold quoted text filter in-memory.
         */
        private String str;

        @Override
        public Predicate<JournalSnapshot> call() throws Exception {
            var txt = txtFilter.getText();
            str = txt.isBlank() ? "" : Pattern.quote(txt);
            return chkFilter.isSelected() ? this : always();
        }

        @Override
        public boolean test(JournalSnapshot o) {
            var per = cboFilter.getValue();
            //Check if text filtering matches.
            var text = str.isBlank()
                    || o.getBriefing().matches("(?i).*" + str + ".*");
            //Check if period filtering matches.
            var period = per == null || o.getTaxPeriod().equals(per.getId());
            return text && period;
        }
    }

}
