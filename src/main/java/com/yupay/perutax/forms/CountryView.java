package com.yupay.perutax.forms;

import com.yupay.perutax.entities.Country;
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
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.*;

/**
 * Controller class for Country master view.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class CountryView {

    //<editor-fold desc="Inner fields.">
    /**
     * The observable list that holds the in-memory data.
     */
    private final ObservableList<Country> data = FXCollections.observableArrayList();
    //</editor-fold>

    //<editor-fold desc="FXML controls.">
    /**
     * FXML injected control, country-view.fxml
     */
    @FXML
    private Stage top;
    /**
     * FXML injected control, country-view.fxml
     */
    @FXML
    private CheckBox chkFilter;
    /**
     * FXML injected control, country-view.fxml
     */
    @FXML
    private TextField txtFilter;
    /**
     * FXML injected control, country-view.fxml
     */
    @FXML
    private TableView<Country> tblData;
    /**
     * FXML injected control, country-view.fxml
     */
    @FXML
    private TableColumn<Country, String> colID;
    /**
     * FXML injected control, country-view.fxml
     */
    @FXML
    private TableColumn<Country, String> colName;
    /**
     * FXML injected control, country-view.fxml
     */
    @FXML
    private TableColumn<Country, String> colID3;
    /**
     * FXML injected control, country-view.fxml
     */
    @FXML
    private TableColumn<Country, String> colID4;
    /**
     * FXML injected control, country-view.fxml
     */
    @FXML
    private TableColumn<Country, Boolean> colTrash;
    //</editor-fold>

    //<editor-fold desc="Initialization.">

    /**
     * Package-private constructor.
     * To instanciate, use a static factory.
     *
     * @see Forms#countryView()
     */
    CountryView() {
    }

    /**
     * FXML initialization.
     */
    @FXML
    void initialize() {
        initChild(top);

        stringTableColumns(colID, colID3, colID4, colName);
        booleanTableColumns(colTrash);

        columnValueFactory(colID, Country::idProperty);
        columnValueFactory(colID3, Country::taxID3Property);
        columnValueFactory(colID4, Country::taxID4Property);
        columnValueFactory(colName, Country::nameProperty);
        columnValueFactory(colTrash, Country::trashProperty);

        setupTableFilter(tblData,
                data,
                new DelegatedFilter(),
                chkFilter.selectedProperty(),
                txtFilter.textProperty());
        //noinspection unchecked
        tblData.getSortOrder().setAll(colTrash, colName, colID);

    }
    //</editor-fold>

    //<editor-fold desc="FXML event handlers.">

    /**
     * FXML event handlers.
     *
     * @param event the event object.
     */
    @FXML
    void createAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        Forms.countryCard().withValue(
                        new Country())
                .showAndWait()
                .ifPresent(new InsertOneFlow<Country>()
                        .defaultFail("No se pudo grabar el nuevo país.")
                        .withOnSuccess(data::add)
                        .asConsumer());
    }

    /**
     * FXML event handlers.
     *
     * @param event the event object.
     */
    @FXML
    void editAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        editSelection();
    }

    /**
     * FXML event handlers.
     *
     * @param event the event object.
     */
    @FXML
    void reloadAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) loadData();
    }

    /**
     * FXML event handlers.
     *
     * @param event the event object.
     */
    @FXML
    void shown(@NotNull WindowEvent event) {
        if (!event.isConsumed()) loadData();
    }

    /**
     * FXML event handlers.
     *
     * @param event the event object.
     */
    @FXML
    void tblDataClick(@NotNull MouseEvent event) {
        if (event.getClickCount() > 1) editSelection();
    }
    //</editor-fold>

    //<editor-fold desc="Delegated methods.">

    /**
     * Delegated method to load data into the table view.
     */
    private void loadData() {
        new SelectAllFlow<>(Country.class)
                .first(data::clear)
                .forEach(data::add)
                .onError(easy("No se pudo completar la carga de países."))
                .execute();
    }

    /**
     * Delegated method to edit selected item in table view.
     */
    private void editSelection() {
        new EditSelectionTrigger<Country>()
                .withCloner(Country::new)
                .withUserInput(x -> Forms
                        .countryCard()
                        .withMode(FormMode.EDITOR)
                        .withValue(x)
                        .showAndWait())
                .withView(tblData)
                .withErrorText("Ocurrió un errror al actualizar el país.")
                .withOnSuccess(upsertList(data))
                .run();
    }
    //</editor-fold>

    //<editor-fold desc="Public API">

    /**
     * Shows this form.
     */
    public void show() {
        top.showAndWait();
    }
    //</editor-fold>

    //<editor-fold desc="Inner classes.">

    /**
     * Delegated implementation of a text filter
     * that changes when user changes the filter
     * controls state or text.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class DelegatedFilter implements Predicate<Country>, Callable<Predicate<Country>> {
        @Override
        public boolean test(Country country) {
            if (country == null) return false;
            var txt = txtFilter.getText().strip().toUpperCase();
            var nam = country.getName().strip().toUpperCase();
            var id = country.getId().strip().toUpperCase();
            return switch (txt.length()) {
                case 0 -> true;
                case 1, 2 -> id.startsWith(txt)
                        || nam.startsWith(txt)
                        || Objects.toString(country.getTaxID3(), "").startsWith(txt)
                        || Objects.toString(country.getTaxID4(), "").startsWith(txt);
                case 3 -> nam.contains(txt)
                        || Objects.toString(country.getTaxID3(), "").startsWith(txt)
                        || Objects.toString(country.getTaxID4(), "").startsWith(txt);
                case 4 -> nam.contains(txt)
                        || Objects.toString(country.getTaxID4(), "").startsWith(txt);
                default -> nam.contains(txt);
            };
        }

        @Override
        public Predicate<Country> call() {
            return !chkFilter.isSelected() || txtFilter.getText().isBlank()
                    ? always()
                    : this;
        }
    }
    //</editor-fold>
}
