package com.yupay.perutax.forms;

import com.yupay.perutax.entities.AccountNature;
import com.yupay.perutax.entities.CostGroup;
import com.yupay.perutax.entities.Currenci;
import com.yupay.perutax.entities.TaxAccount;
import com.yupay.perutax.forms.inner.AmountDecimalConverter;
import com.yupay.perutax.forms.inner.BaseChangeListener;
import com.yupay.perutax.forms.inner.NumericFormatter;
import com.yupay.perutax.forms.inner.VarcharFormatter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

import static com.yupay.perutax.forms.FormUtils.*;

/**
 * The controller for a TaxAccount editor card.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class TaxAccountCard extends Dialog<TaxAccount> {

    //<editor-fold desc="Inner fields.">
    /**
     * The editing value property.
     */
    private final ObjectProperty<TaxAccount> value =
            new SimpleObjectProperty<>(this, "value");
    /**
     * Text formatter for the ID field.
     */
    private final TextFormatter<String> fmtID = new NumericFormatter(8),
    /**
     * Text formatter for the name field.
     */
    fmtName = new VarcharFormatter(200, true);
    /**
     * Text formatter for the balance field.
     */
    private final TextFormatter<BigDecimal> fmtBalance =
            new TextFormatter<>(new AmountDecimalConverter());
    //</editor-fold>

    //<editor-fold desc="FXML controls.">
    /**
     * FXML component injected from taxaccount-card.fxml
     */
    @FXML
    private DialogPane top;

    /**
     * FXML component injected from taxaccount-card.fxml
     */
    @FXML
    private ComboBox<Currenci> cboCurrency;

    /**
     * FXML component injected from taxaccount-card.fxml
     */
    @FXML
    private ComboBox<CostGroup> cboGrpCost;

    /**
     * FXML component injected from taxaccount-card.fxml
     */
    @FXML
    private ComboBox<AccountNature> cboNature;

    /**
     * FXML component injected from taxaccount-card.fxml
     */
    @FXML
    private CheckBox chkTrash;

    /**
     * FXML component injected from taxaccount-card.fxml
     */
    @FXML
    private CheckBox chkUsable;

    /**
     * FXML component injected from taxaccount-card.fxml
     */
    @FXML
    private TextField txtBalance;

    /**
     * FXML component injected from taxaccount-card.fxml
     */
    @FXML
    private TextField txtID;

    /**
     * FXML component injected from taxaccount-card.fxml
     */
    @FXML
    private TextField txtName;
    //</editor-fold>

    //<editor-fold desc="Initialization.">

    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see Forms#taxAccountCard()
     */
    TaxAccountCard() {
    }

    /**
     * FXML initializer.
     */
    @FXML
    void initialize() {
        setDialogPane(top);
        setResultConverter(p -> p == ButtonType.APPLY ? value.get() : null);
        setTitle("Editor de Cuenta Contable");

        txtID.setTextFormatter(fmtID);
        txtName.setTextFormatter(fmtName);
        txtBalance.setTextFormatter(fmtBalance);

        cboCurrency.setItems(Currenci.observable());
        cboGrpCost.setItems(CostGroup.observable());
        cboNature.setItems(AccountNature.observable());
        value.addListener(new ValueChanged());
    }
    //</editor-fold>

    //<editor-fold desc="Event handling.">

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void groupCostKeyReleased(@NotNull KeyEvent event) {
        if (!event.isConsumed()
                && event.isControlDown()
                && event.getCode() == KeyCode.DELETE) cboGrpCost.setValue(null);
    }
    //</editor-fold>

    //<editor-fold desc="Public API.">

    /**
     * Fluent Accessor - setter.
     *
     * @param value value to assign into {@link #value}.
     * @return this instance.
     */
    @Contract("_->this")
    public final @NotNull TaxAccountCard withValue(@NotNull TaxAccount value) {
        this.value.set(value);
        return this;
    }

    /**
     * Sets the card to create mode and value to a new
     * and empty tax account.
     *
     * @return this instance.
     */
    @Contract("->this")
    public final @NotNull TaxAccountCard createNew() {
        return withMode(FormMode.CREATOR).withValue(new TaxAccount());
    }

    /**
     * Sets the form card modality.
     *
     * @param mode the card modality.
     * @return this instance.
     */
    @Contract("_->this")
    public final @NotNull TaxAccountCard withMode(@NotNull FormMode mode) {
        var creator = mode == FormMode.CREATOR;
        var writable = mode != FormMode.READ_ONLY;
        txtID.setEditable(creator);
        txtName.setEditable(writable);
        cboNature.setDisable(!creator);
        cboCurrency.setDisable(!creator);
        txtBalance.setEditable(false);
        cboGrpCost.setDisable(!writable);
        chkUsable.setDisable(!creator);
        chkTrash.setDisable(!writable);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Inner classes.">

    /**
     * Listener for changes in value, to attach
     * and dettach values from the GUI.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class ValueChanged extends BaseChangeListener<TaxAccount> {
        @Override
        protected void attach(@NotNull TaxAccount value) {
            fmtID.valueProperty().bindBidirectional(value.idProperty());
            fmtName.valueProperty().bindBidirectional(value.nameProperty());
            cboNature.valueProperty().bindBidirectional(value.natureProperty());
            cboCurrency.valueProperty().bindBidirectional(value.currencyProperty());
            fmtBalance.valueProperty().bind(value.balanceProperty());
            cboGrpCost.valueProperty().bindBidirectional(value.groupCostProperty());
            chkUsable.selectedProperty().bindBidirectional(value.usableProperty());
            chkTrash.selectedProperty().bindBidirectional(value.trashProperty());
        }

        @Override
        protected void dettach(@NotNull TaxAccount value) {
            fmtID.valueProperty().unbindBidirectional(value.idProperty());
            fmtName.valueProperty().unbindBidirectional(value.nameProperty());
            cboNature.valueProperty().unbindBidirectional(value.natureProperty());
            cboCurrency.valueProperty().unbindBidirectional(value.currencyProperty());
            fmtBalance.valueProperty().unbind();
            cboGrpCost.valueProperty().unbindBidirectional(value.groupCostProperty());
            chkUsable.selectedProperty().unbindBidirectional(value.usableProperty());
            chkTrash.selectedProperty().unbindBidirectional(value.trashProperty());
        }

        @Override
        protected void clear() {
            clearFormatters("", fmtID, fmtName);
            clearComboBoxes(cboNature, cboCurrency, cboGrpCost);
            clearCheckBoxes(chkUsable, chkTrash);
            txtBalance.setText("");
        }
    }
    //</editor-fold>

}
