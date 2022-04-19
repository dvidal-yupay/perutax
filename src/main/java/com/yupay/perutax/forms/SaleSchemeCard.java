package com.yupay.perutax.forms;

import com.yupay.perutax.entities.SaleScheme;
import com.yupay.perutax.entities.SaleTotalClass;
import com.yupay.perutax.forms.inner.AmountDecimalFormatter;
import com.yupay.perutax.forms.inner.BaseChangeListener;
import com.yupay.perutax.forms.inner.RateDecimalFormatter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

import static com.yupay.perutax.forms.FormUtils.*;

/**
 * The form card to edit a sale scheme object.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class SaleSchemeCard extends Dialog<SaleScheme>
        implements Function<SaleScheme, Optional<SaleScheme>> {
    /**
     * Editing item.
     */
    private final ObjectProperty<SaleScheme> item =
            new SimpleObjectProperty<>(this, "item");
    /**
     * The formatter for tax rate.
     */
    private final TextFormatter<BigDecimal> fmtRate =
            new RateDecimalFormatter();
    /**
     * The formatter for tax fee.
     */
    private final TextFormatter<BigDecimal> fmtFee =
            new AmountDecimalFormatter();

    /**
     * FXML control injected from sale_scheme-card.fxml
     */
    @FXML
    private ComboBox<SaleTotalClass> cboClassBase;

    /**
     * FXML control injected from sale_scheme-card.fxml
     */
    @FXML
    private ComboBox<SaleTotalClass> cboClassTax;

    /**
     * FXML control injected from sale_scheme-card.fxml
     */
    @FXML
    private CheckBox chkTrash;

    /**
     * FXML control injected from sale_scheme-card.fxml
     */
    @FXML
    private Hyperlink lnkID;

    /**
     * FXML control injected from sale_scheme-card.fxml
     */
    @FXML
    private DialogPane top;

    /**
     * FXML control injected from sale_scheme-card.fxml
     */
    @FXML
    private TextField txtFactID;

    /**
     * FXML control injected from sale_scheme-card.fxml
     */
    @FXML
    private TextField txtFee;

    /**
     * FXML control injected from sale_scheme-card.fxml
     */
    @FXML
    private TextField txtRate;

    /**
     * FXML control injected from sale_scheme-card.fxml
     */
    @FXML
    private TextField txtTitle;

    /**
     * Package-private constructor.
     * Use a factory.
     *
     * @see Forms#saleSchemeCard()
     */
    SaleSchemeCard() {
    }

    /**
     * FXML initialization.
     */
    @FXML
    void initialize() {
        setDialogPane(top);
        setTitle("Editor de Esquema de Venta");
        txtFee.setTextFormatter(fmtFee);
        txtRate.setTextFormatter(fmtRate);
        item.addListener(new ValueChanged());
        setResultConverter(b -> b == ButtonType.APPLY ? item.get() : null);
        cboClassBase.setItems(SaleTotalClass.observable());
        cboClassTax.setItems(SaleTotalClass.observable());
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void lnkidAction(@NotNull MouseEvent event) {
        if (!event.isConsumed()) {
            Optional.ofNullable(item.get())
                    .map(SaleScheme::getId)
                    .map(i -> {
                        var x = new ClipboardContent();
                        x.putString(i);
                        return x;
                    })
                    .ifPresent(Clipboard.getSystemClipboard()::setContent);
            var tooltip = new Tooltip("ID de objeto copiado.");
            tooltip.setAutoHide(true);
            tooltip.setHideDelay(Duration.seconds(3));
            tooltip.show(lnkID, event.getScreenX(), event.getScreenY());
            event.consume();
        }
    }

    /**
     * Fluent setter - with.
     *
     * @param item the editing item (never null).
     * @return this.
     */
    @Contract("_->this")
    public @NotNull SaleSchemeCard withItem(@NotNull SaleScheme item) {
        this.item.set(item);
        return this;
    }

    /**
     * Sets the form mode to lock/unlock controls.
     *
     * @param mode the new mode.
     * @return this.
     */
    @Contract("_->this")
    public @NotNull SaleSchemeCard withMode(@NotNull FormMode mode) {
        var writable = mode != FormMode.READ_ONLY;
        txtTitle.setEditable(writable);
        txtRate.setEditable(writable);
        txtFee.setEditable(writable);
        cboClassBase.setDisable(!writable);
        cboClassTax.setDisable(!writable);
        txtFactID.setEditable(writable);
        chkTrash.setDisable(!writable);
        return this;
    }

    /**
     * Sets the mode to creator and the editing
     * item to a new SaleScheme object.
     *
     * @return this.
     */
    @Contract("->this")
    public @NotNull SaleSchemeCard creator() {
        return withItem(new SaleScheme())
                .withMode(FormMode.CREATOR);
    }

    /**
     * Sets the mode to editor and the editing
     * item to an object.
     *
     * @param item the object.
     * @return this.
     */
    @Contract("_->this")
    public @NotNull SaleSchemeCard editor(@NotNull SaleScheme item) {
        return withItem(item)
                .withMode(FormMode.EDITOR);
    }

    @Override
    public Optional<SaleScheme> apply(SaleScheme saleScheme) {
        return editor(saleScheme).showAndWait();
    }

    /**
     * Listener to keep UI properly attached when editing item changes.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class ValueChanged extends BaseChangeListener<SaleScheme> {
        @Override
        protected void attach(@NotNull SaleScheme value) {
            lnkID.textProperty().bind(value.idProperty());
            cboClassTax.valueProperty().bindBidirectional(value.taxClassProperty());
            cboClassBase.valueProperty().bindBidirectional(value.amountClassProperty());
            chkTrash.selectedProperty().bindBidirectional(value.trashProperty());
            txtTitle.textProperty().bindBidirectional(value.titleProperty());
            fmtFee.valueProperty().bindBidirectional(value.taxFeeProperty());
            fmtRate.valueProperty().bindBidirectional(value.taxRateProperty());
            txtFactID.textProperty().bindBidirectional(value.factoringIdProperty());
        }

        @Override
        protected void dettach(@NotNull SaleScheme value) {
            lnkID.textProperty().unbind();
            cboClassTax.valueProperty().unbindBidirectional(value.taxClassProperty());
            cboClassBase.valueProperty().unbindBidirectional(value.amountClassProperty());
            chkTrash.selectedProperty().unbindBidirectional(value.trashProperty());
            txtTitle.textProperty().unbindBidirectional(value.titleProperty());
            fmtFee.valueProperty().unbindBidirectional(value.taxFeeProperty());
            fmtRate.valueProperty().unbindBidirectional(value.taxRateProperty());
            txtFactID.textProperty().unbindBidirectional(value.factoringIdProperty());
        }

        @Override
        protected void clear() {
            clearLabeled(lnkID);
            clearCheckBoxes(chkTrash);
            clearComboBoxes(cboClassBase, cboClassTax);
            clearTextFields(txtFactID, txtTitle);
            clearFormatters(null, fmtFee, fmtRate);
        }
    }
}
