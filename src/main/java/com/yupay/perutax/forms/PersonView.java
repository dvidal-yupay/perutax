package com.yupay.perutax.forms;

import com.yupay.perutax.apiperu.ApiPeruEnpoint;
import com.yupay.perutax.apiperu.ApiPeruFlow;
import com.yupay.perutax.entities.Person;
import com.yupay.perutax.entities.TypeDOI;
import com.yupay.perutax.entities.validation.RUCValidation;
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
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.*;

/**
 * Controller class for Person's Master view form.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class PersonView {

    //<editor-fold desc="Private fields.">
    /**
     * List holding the table data.
     */
    private final ObservableList<Person> data
            = FXCollections.observableArrayList();
    //</editor-fold>

    //<editor-fold desc="FXML controls.">
    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private Stage top;

    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private CheckBox chkFilter;

    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private TextField txtFilter;

    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private TableView<Person> tblData;

    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private TableColumn<Person, String> colID;

    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private TableColumn<Person, String> colName;

    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private TableColumn<Person, TypeDOI> colDoiType;

    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private TableColumn<Person, String> colDoiNum;

    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private TableColumn<Person, Boolean> colRCustomer;

    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private TableColumn<Person, Boolean> colRSupplier;

    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private TableColumn<Person, Boolean> colREmployee;

    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private TableColumn<Person, Boolean> colRFreelancer;

    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private TableColumn<Person, Boolean> colRShareholder;

    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private TableColumn<Person, Boolean> colRAssociated;

    /**
     * Injected fxml control from person-view.fxml
     */
    @FXML
    private TableColumn<Person, Boolean> colTrash;
    //</editor-fold>

    //<editor-fold desc="Initialization.">

    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see Forms#personView()
     */
    PersonView() {
    }

    /**
     * FXML initializer.
     */
    @FXML
    void initialize() {
        initChild(top);

        stringTableColumns(colDoiNum, colID, colName);
        booleanTableColumns(
                colRAssociated, colRCustomer, colREmployee,
                colRFreelancer, colRShareholder, colRSupplier, colTrash);
        //noinspection DuplicatedCode
        objectTableColumn(colDoiType, Objects::toString);
        columnValueFactory(colDoiNum, Person::doiNumProperty);
        columnValueFactory(colDoiType, Person::doiTypeProperty);
        columnValueFactory(colID, Person::idProperty);
        columnValueFactory(colName, Person::fullNameProperty);
        columnValueFactory(colRAssociated, Person::roleAssociatedProperty);
        columnValueFactory(colRCustomer, Person::roleCustomerProperty);
        columnValueFactory(colREmployee, Person::roleEmployeeProperty);
        columnValueFactory(colRFreelancer, Person::roleFreelancerProperty);
        columnValueFactory(colRShareholder, Person::roleShareholderProperty);
        columnValueFactory(colRSupplier, Person::roleSupplierProperty);
        columnValueFactory(colTrash, Person::trashProperty);

        setupTableFilter(tblData,
                data,
                new TextFilter(),
                chkFilter.selectedProperty(), txtFilter.textProperty());
        //noinspection unchecked
        tblData.getSortOrder().setAll(colTrash, colName, colDoiNum);
    }
    //</editor-fold>

    //<editor-fold desc="Event handling.">

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void createAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) Forms
                .personCard()
                .creator()
                .showAndWait()
                .ifPresent(System.out::println);
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void createDNIAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        new FluentInput().withTitle("Ingrese Dato")
                .withHeaderText("Vamos a consultar el número DNI, por favor ingrese un dato.")
                .withContentText("Ingrese el DNI:")
                .withNumericInput(8)
                .showAndWait()
                .ifPresent(s -> createFromAPI(ApiPeruEnpoint.DNI, s));
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void createRUCAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        new FluentInput().withTitle("Ingrese Dato")
                .withHeaderText("Vamos a consultar el número RUC, por favor ingrese un dato.")
                .withContentText("Ingrese el RUC:")
                .withNumericInput(11)
                .showAndWait()
                .ifPresent(s -> createFromAPI(ApiPeruEnpoint.RUC, s));
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void editAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) editSelection();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void tblDataClicked(@NotNull MouseEvent event) {
        if (!event.isConsumed()
                && event.getClickCount() > 1) editSelection();
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
    void topShown(@NotNull WindowEvent event) {
        if (!event.isConsumed()) loadData();
    }

    //</editor-fold>

    //<editor-fold desc="Public API">

    /**
     * Shows this form's stage in APPLICATION_MODAL -ity.
     */
    public void show() {
        top.show();
    }
    //</editor-fold>

    //<editor-fold desc="Delegated methods.">

    /**
     * Effectively loads the data into the table view.
     */
    private void loadData() {
        new SelectAllFlow<>(Person.class)
                .first(data::clear)
                .forEach(data::add)
                .onError(easy("No se pudo completar la carga de personas."))
                .execute();
    }

    /**
     * Delegated method to take a user requested document
     * number and insert into database.
     *
     * @param endpoint the endpoint of query.
     * @param document the document number to find.
     */
    private void createFromAPI(@NotNull ApiPeruEnpoint endpoint,
                               @NotNull String document) {
        var str = document.strip();
        if (endpoint == ApiPeruEnpoint.RUC
                && !new RUCValidation().test(str)) {
            FluentAlert.warn()
                    .withTitle("¡Atención!")
                    .withHeader("El número de RUC es inválido.")
                    .withContent("El RUC " + document + " no es válido,\n" +
                            " no será posible realizar la consulta.")
                    .defaultButtons()
                    .show();
            return;
        }
        new ApiPeruFlow(endpoint)
                .withDocument(str)
                .defaultError()
                .withOnSuccess(p -> Forms.personCard()
                        .withMode(FormMode.CREATOR)
                        .withValue(p)
                        .showAndWait()
                        .ifPresent(new InsertOneFlow<Person>()
                                .defaultFail("Ocurrió un error al crear una Persona.")
                                .withOnSuccess(data::add)
                                .asConsumer()))
                .execute();
    }

    /**
     * Delegated method to edit the current selected person in the view.
     */
    public void editSelection() {
        new EditSelectionTrigger<Person>()
                .withCloner(Person::new)
                .withUserInput(x -> Forms
                        .personCard()
                        .withMode(FormMode.EDITOR)
                        .withValue(x)
                        .showAndWait())
                .withView(tblData)
                .withErrorText("Ocurrió un error al actualizar la Persona.")
                .withOnSuccess(upsertList(data))
                .run();
    }
    //</editor-fold>

    //<editor-fold desc="Inner classes.">

    /**
     * This implementation will provide a filter
     * based on user input on toolbar controls
     * chkFilter and txtFilter.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class TextFilter implements
            Callable<Predicate<Person>>,
            Predicate<Person> {
        /**
         * Temporal hold of txtFilter.text quoted by Pattern.
         */
        private String txt;

        @Override
        public @Nullable Predicate<Person> call() {
            txt = Pattern.quote(txtFilter.getText());
            return chkFilter.isSelected() && !txt.isBlank()
                    ? this
                    : always();
        }

        @Override
        public boolean test(Person person) {
            if (person == null) return false;
            return person.getFullName().matches("(?i).*" + txt + ".*")
                    || person.getDoiNum().matches("(?i)^" + txt + ".*");
        }
    }
    //</editor-fold>
}
