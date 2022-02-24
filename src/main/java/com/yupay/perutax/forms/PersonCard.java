/*
 *  Perutax - Taxation software for Peru.
 *     Copyright (C) 2021-2022  Ingenieria Informatica Yupay SACS
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.yupay.perutax.forms;

import com.yupay.perutax.entities.Country;
import com.yupay.perutax.entities.Person;
import com.yupay.perutax.entities.TypeDOI;
import com.yupay.perutax.forms.flows.SelectActiveFlow;
import com.yupay.perutax.forms.inner.BaseChangeListener;
import com.yupay.perutax.forms.inner.VarcharFormatter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static com.yupay.perutax.forms.ErrorAlert.easy;
import static com.yupay.perutax.forms.FormUtils.*;

/**
 * The person card form controller.
 * See in person-card.fxml
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class PersonCard extends Dialog<Person> {

    //<editor-fold desc="Inner fields.">
    /**
     * The value being edited (or created).
     */
    private final ObjectProperty<Person> value
            = new SimpleObjectProperty<>(this, "value");
    /**
     * The DOI number text formatter.
     */
    private final TextFormatter<String> fmtDoiNum =
            new VarcharFormatter(15, false);
    /**
     * The person full name text formatter.
     */
    private final TextFormatter<String> fmtName =
            new VarcharFormatter(255, true);
    /**
     * The list of countries.
     */
    private final ObservableList<Country> lsCountries =
            FXCollections.observableArrayList();
    /**
     * The list of DOITypes.
     */
    private final ObservableList<TypeDOI> lsDoiTypes =
            FXCollections.observableArrayList();
    //</editor-fold>

    //<editor-fold desc="FXML controls.">
    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    private DialogPane top;
    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    private Label lblID;
    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    private ComboBox<TypeDOI> cboDoiType;
    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    private TextField txtDoiNum;
    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    private TextField txtName;
    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    private CheckBox chkTrash;
    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    private TextField txtAddress;
    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    private ComboBox<Country> cboCountry;
    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    @SuppressWarnings("unused")
    private TitledPane paneDefault;
    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    private CheckBox chkRCustomer;
    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    private CheckBox chkRSupplier;
    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    private CheckBox chkREmployee;
    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    private CheckBox chkRFreelancer;


    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    private CheckBox chkRShareholder;
    /**
     * FXML injected control from person-card.fxml
     */
    @FXML
    private CheckBox chkRAssociated;
    //</editor-fold>

    //<editor-fold desc="Initializers">

    /**
     * Package-private constructor.
     * To instanciate use a static factory.
     */
    PersonCard() {
    }

    /**
     * FXML initialization.
     */
    @FXML
    void initialize() {
        setDialogPane(top);
        setTitle("Editor de Personas");

        setOnShown(e -> loadLists());
        setResultConverter(r -> r == ButtonType.APPLY ? getValue() : null);

        txtDoiNum.setTextFormatter(fmtDoiNum);
        txtName.setTextFormatter(fmtName);

        cboCountry.setItems(lsCountries);
        cboDoiType.setItems(lsDoiTypes);

        value.addListener(new ValueChanged());
    }
    //</editor-fold>

    //<editor-fold desc="Public API.">

    /**
     * Fluent setter - with.
     *
     * @param value new value to set in {@link #value}
     * @return this instance.
     */
    public final PersonCard withValue(Person value) {
        this.value.set(value);
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #value}
     */
    public final Person getValue() {
        return value.get();
    }

    /**
     * Locks or un locks the GUI/UX controls based on
     * the form modality.
     *
     * @param mode the form modality.
     * @return this instance.
     */
    @Contract("_->this")
    public final @NotNull PersonCard withMode(@NotNull FormMode mode) {
        var disable = mode == FormMode.READ_ONLY;
        cboDoiType.setDisable(disable);
        txtDoiNum.setEditable(!disable);
        txtName.setEditable(!disable);
        chkTrash.setDisable(disable);
        txtAddress.setEditable(!disable);
        cboCountry.setDisable(disable);
        chkRCustomer.setDisable(disable);
        chkRSupplier.setDisable(disable);
        chkREmployee.setDisable(disable);
        chkRFreelancer.setDisable(disable);
        chkRShareholder.setDisable(disable);
        chkRAssociated.setDisable(disable);
        return this;
    }

    /**
     * Sets the mode to CREATOR and the
     * value to a {@code new Person();}
     *
     * @return this instance.
     */
    @Contract("->this")
    public @NotNull PersonCard creator() {
        return withMode(FormMode.CREATOR)
                .withValue(new Person());
    }
    //</editor-fold>

    //<editor-fold desc="Delegated methods.">

    /**
     * Loads the lists for user selection
     * on combo boxes.
     */
    private void loadLists() {
        new SelectActiveFlow<>(TypeDOI.class)
                .first(lsDoiTypes::clear)
                .forEach(lsDoiTypes::add)
                .onError(easy("No se pudo cargar el listado de tipos de DOI."))
                .execute();
        new SelectActiveFlow<>(Country.class)
                .first(lsCountries::clear)
                .forEach(lsCountries::add)
                .onError(easy("No se pudo cargar el listado de países."))
                .execute();
    }

    //</editor-fold>

    //<editor-fold desc="Inner classes.">

    /**
     * Change listener implementation to attach an dettach
     * the person value to/from GUI.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class ValueChanged extends BaseChangeListener<Person> {
        @Override
        protected void attach(@NotNull Person value) {
            lblID.textProperty().bind(value.idProperty());
            cboDoiType.valueProperty().bindBidirectional(value.doiTypeProperty());
            fmtDoiNum.valueProperty().bindBidirectional(value.doiNumProperty());
            fmtName.valueProperty().bindBidirectional(value.fullNameProperty());
            chkTrash.selectedProperty().bindBidirectional(value.trashProperty());
            txtAddress.textProperty().bindBidirectional(value.addressProperty());
            cboCountry.valueProperty().bindBidirectional(value.countryProperty());
            chkRCustomer.selectedProperty().bindBidirectional(value.roleCustomerProperty());
            chkRSupplier.selectedProperty().bindBidirectional(value.roleSupplierProperty());
            chkREmployee.selectedProperty().bindBidirectional(value.roleEmployeeProperty());
            chkRFreelancer.selectedProperty().bindBidirectional(value.roleFreelancerProperty());
            chkRShareholder.selectedProperty().bindBidirectional(value.roleShareholderProperty());
            chkRAssociated.selectedProperty().bindBidirectional(value.roleAssociatedProperty());
        }

        @Override
        protected void dettach(@NotNull Person value) {
            lblID.textProperty().unbind();
            cboDoiType.valueProperty().unbindBidirectional(value.doiTypeProperty());
            fmtDoiNum.valueProperty().unbindBidirectional(value.doiNumProperty());
            fmtName.valueProperty().unbindBidirectional(value.fullNameProperty());
            chkTrash.selectedProperty().unbindBidirectional(value.trashProperty());
            txtAddress.textProperty().unbindBidirectional(value.addressProperty());
            cboCountry.valueProperty().unbindBidirectional(value.countryProperty());
            chkRCustomer.selectedProperty().unbindBidirectional(value.roleCustomerProperty());
            chkRSupplier.selectedProperty().unbindBidirectional(value.roleSupplierProperty());
            chkREmployee.selectedProperty().unbindBidirectional(value.roleEmployeeProperty());
            chkRFreelancer.selectedProperty().unbindBidirectional(value.roleFreelancerProperty());
            chkRShareholder.selectedProperty().unbindBidirectional(value.roleShareholderProperty());
            chkRAssociated.selectedProperty().unbindBidirectional(value.roleAssociatedProperty());
        }

        @Override
        protected void clear() {
            clearFormatters("", fmtDoiNum, fmtName);
            lblID.setText("");
            txtAddress.setText("");
            clearComboBoxes(cboDoiType, cboCountry);
            clearCheckBoxes(chkTrash,
                    chkRCustomer,
                    chkRSupplier,
                    chkREmployee,
                    chkRFreelancer,
                    chkRShareholder,
                    chkRAssociated);
        }
    }
    //</editor-fold>
}
