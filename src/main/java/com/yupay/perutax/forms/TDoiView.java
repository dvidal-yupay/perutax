package com.yupay.perutax.forms;


import com.yupay.perutax.entities.TypeDOI;
import com.yupay.perutax.forms.flows.CheckRegexFlow;
import com.yupay.perutax.forms.flows.EditSelectionTrigger;
import com.yupay.perutax.forms.flows.InsertOneFlow;
import com.yupay.perutax.forms.flows.SelectAllFlow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.*;

/**
 * Form controller for the Type of DOI master view.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class TDoiView {
    //<editor-fold desc="Inner fields.">
    /**
     * List containing in-memory data.
     */
    private final ObservableList<TypeDOI> data
            = FXCollections.observableArrayList();
    //</editor-fold>

    //<editor-fold desc="FXML controls.">
    /**
     * FXML injected component from tdoi-view.fxml
     */
    @FXML
    private Stage top;
    /**
     * FXML injected component from tdoi-view.fxml
     */
    @FXML
    private CheckBox chkFilter;

    /**
     * FXML injected component from tdoi-view.fxml
     */
    @FXML
    private TextField txtFilter;

    /**
     * FXML injected component from tdoi-view.fxml
     */
    @FXML
    private TableView<TypeDOI> tblData;

    /**
     * FXML injected component from tdoi-view.fxml
     */
    @FXML
    private TableColumn<TypeDOI, String> colID;

    /**
     * FXML injected component from tdoi-view.fxml
     */
    @FXML
    private TableColumn<TypeDOI, String> colTitle;

    /**
     * FXML injected component from tdoi-view.fxml
     */
    @FXML
    private TableColumn<TypeDOI, String> colRegex;

    /**
     * FXML injected component from tdoi-view.fxml
     */
    @FXML
    private TableColumn<TypeDOI, Boolean> colTrash;

    /**
     * FXML injected component from tdoi-view.fxml
     */
    @FXML
    private MenuItem mniRegexCheck;
    //</editor-fold>

    //<editor-fold desc="Initialization.">

    /**
     * Package-private constructor.
     * To instanciate, use a static factory.
     *
     * @see Forms#tDoiView()
     */
    TDoiView() {
    }

    /**
     * FXML initializaiton.
     */
    @FXML
    void initialize() {
        initChild(top);

        stringTableColumns(colID, colRegex, colTitle);
        booleanTableColumns(colTrash);

        columnValueFactory(colID, TypeDOI::idProperty);
        columnValueFactory(colRegex, TypeDOI::regexProperty);
        columnValueFactory(colTitle, TypeDOI::titleProperty);
        columnValueFactory(colTrash, TypeDOI::trashProperty);

        setupTableFilter(tblData,
                data,
                new UserFilter(),
                chkFilter.selectedProperty(), txtFilter.textProperty());

        //noinspection unchecked
        tblData.getSortOrder().setAll(colTrash, colID);

        mniRegexCheck.disableProperty().bind(
                tblData.getSelectionModel().selectedItemProperty().isNull());
    }
    //</editor-fold>

    //<editor-fold desc="Event handling.">

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void stageShown(@NotNull WindowEvent event) {
        if (!event.isConsumed()) loadData();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void addAction(@NotNull ActionEvent event) {
        if (!event.isConsumed())
            Forms.tDoiCard()
                    .withValue(new TypeDOI())
                    .showAndWait()
                    .ifPresent(new InsertOneFlow<TypeDOI>()
                            .defaultFail("Ocurrió un error al grabar el nuevo tipo de documento.")
                            .withOnSuccess(data::add)
                            .asConsumer());
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void editAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) editData();
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

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void tableClicked(@NotNull MouseEvent event) {
        if (!event.isConsumed() && event.getClickCount() > 1) editData();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void regexCheckAction(@NotNull ActionEvent event) {
        if (!event.isConsumed())
            Optional.ofNullable(tblData.getSelectionModel().getSelectedItem())
                    .map(TypeDOI::getRegex)
                    .map(new CheckRegexFlow()::withRegex)
                    .ifPresent(CheckRegexFlow::run);
    }
    //</editor-fold>

    //<editor-fold desc="Delegated methods.">

    /**
     * Delegated method to load the data from the database.
     */
    private void loadData() {
        new SelectAllFlow<>(TypeDOI.class)
                .first(data::clear)
                .forEach(data::add)
                .onError(easy("No se pudo completar la carga de tipos de documento."))
                .execute();
    }

    /**
     * Delegated method to edit the current selection.
     */
    private void editData() {
        new EditSelectionTrigger<TypeDOI>()
                .withCloner(TypeDOI::new)
                .withUserInput(x -> Forms
                        .tDoiCard()
                        .withMode(FormMode.EDITOR)
                        .withValue(x)
                        .showAndWait())
                .withView(tblData)
                .withErrorText("Ocurrió un errror al actualizar el país.")
                .withOnSuccess(upsertList(data))
                .run();
    }
    //</editor-fold>

    //<editor-fold desc="Public API.">

    /**
     * Shows this view and blocks thread.
     */
    public void show() {
        top.showAndWait();
    }
    //</editor-fold>

    //<editor-fold desc="Inner classes.">

    /**
     * Inner predicate implementation that checks the text filter against
     * a typedoi ID or title. The callable simply returns this as a useful
     * feature for javaFX controls.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class UserFilter implements Predicate<TypeDOI>, Callable<Predicate<TypeDOI>> {
        @Override
        public Predicate<TypeDOI> call() {
            return chkFilter.isSelected() && !txtFilter.getText().isBlank()
                    ? this
                    : FormUtils.always();
        }

        @Override
        public boolean test(@Nullable TypeDOI typeDOI) {
            if (typeDOI == null) return false;
            var txt = txtFilter.getText().strip().toUpperCase();
            return switch (txt.length()) {
                case 0 -> true;
                case 1 -> txt.equals(typeDOI.getId().toUpperCase())
                        || typeDOI.getTitle().toUpperCase().startsWith(txt);
                default -> typeDOI.getTitle().toUpperCase().startsWith(txt);
            };
        }
    }
    //</editor-fold>
}
