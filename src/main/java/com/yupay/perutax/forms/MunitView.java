package com.yupay.perutax.forms;

import com.yupay.perutax.entities.MeasureUnit;
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
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.yupay.perutax.forms.FormUtils.*;

/**
 * Contorller for master Measurement Units view. munit-view.fxml
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class MunitView {
    /**
     * Data held on memory to show in the table view.
     */
    private final ObservableList<MeasureUnit> data
            = FXCollections.observableArrayList();
    /**
     * FXML Injected controls from munit-view.fxml
     */
    @FXML
    private CheckBox chkFilter;

    /**
     * FXML Injected controls from munit-view.fxml
     */
    @FXML
    private TableColumn<MeasureUnit, String> colDescription;

    /**
     * FXML Injected controls from munit-view.fxml
     */
    @FXML
    private TableColumn<MeasureUnit, String> colID;

    /**
     * FXML Injected controls from munit-view.fxml
     */
    @FXML
    private TableColumn<MeasureUnit, String> colSymbol;

    /**
     * FXML Injected controls from munit-view.fxml
     */
    @FXML
    private TableColumn<MeasureUnit, String> colTaxID;

    /**
     * FXML Injected controls from munit-view.fxml
     */
    @FXML
    private TableColumn<MeasureUnit, Boolean> colTrash;

    /**
     * FXML Injected controls from munit-view.fxml
     */
    @FXML
    private TableView<MeasureUnit> tblData;

    /**
     * FXML Injected controls from munit-view.fxml
     */
    @FXML
    private Stage top;

    /**
     * FXML Injected controls from munit-view.fxml
     */
    @FXML
    private TextField txtFilter;

    /**
     * Package-private constructor.
     * Use a factory.
     *
     * @see Forms#munitView()
     */
    MunitView() {
    }

    /**
     * FXML initializer.
     */
    @SuppressWarnings("DuplicatedCode")
    @FXML
    void initialize() {
        stringTableColumns(colID, colDescription, colSymbol, colTaxID);
        booleanTableColumns(colTrash);

        columnValueFactory(colID, MeasureUnit::idProperty);
        columnValueFactory(colDescription, MeasureUnit::nameProperty);
        columnValueFactory(colSymbol, MeasureUnit::symbolProperty);
        columnValueFactory(colTaxID, MeasureUnit::taxIdProperty);
        columnValueFactory(colTrash, MeasureUnit::trashProperty);

        setupTableFilter(tblData, data, new TextFilter(),
                txtFilter.textProperty(),
                chkFilter.selectedProperty());
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void createAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) {
            Forms.munitCard().creator().showAndWait()
                    .ifPresent(new InsertOneFlow<MeasureUnit>()
                            .withOnSuccess(data::add)
                            .defaultFail("Ocurrió un error al grabar nueva unidad de medida.")
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
            new EditSelectionTrigger<MeasureUnit>()
                    .withCloner(MeasureUnit::new)
                    .withErrorText("Ha ocurrido un error al actualizar la unidad de medida.")
                    .withOnSuccess(FormUtils.upsertList(data))
                    .withUserInput(i -> Forms.munitCard().editor(i).showAndWait())
                    .withView(tblData)
                    .run();
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
     * Loads from the database into the TableView.
     */
    private void loadData() {
        new SelectAllFlow<>(MeasureUnit.class)
                .first(data::clear)
                .forEach(data::add)
                .onError(ErrorAlert.easy("Ocurrió un error al cargar las unidades de medida."))
                .execute();
    }

    /**
     * Shows the top stage of this form
     * as a child of primary stage.
     */
    public void show() {
        FormUtils.initChild(top);
        top.show();
        loadData();
    }

    /**
     * Text filter implementation.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class TextFilter implements Predicate<MeasureUnit>,
            Callable<Predicate<MeasureUnit>> {
        /**
         * Local and convient copy of text filter escaped to be a regex pattern.
         */
        private String str;

        @Override
        public Predicate<MeasureUnit> call() throws Exception {
            var txt = txtFilter.getText();
            str = Pattern.quote(txt);
            return txt.isBlank() || !chkFilter.isSelected()
                    ? always()
                    : this;
        }

        @Override
        public boolean test(MeasureUnit obj) {
            if (obj == null) return false;
            return obj.getName().matches("(?i).*" + str + ".*")
                    || obj.getTaxId().matches("(?i)^" + str + ".*")
                    || obj.getSymbol().matches("(?i)^" + str + ".*");
        }
    }
}
