package com.yupay.perutax.forms;

import com.yupay.perutax.entities.MeasureUnit;
import com.yupay.perutax.forms.inner.BaseChangeListener;
import com.yupay.perutax.forms.inner.UpperCaseFormatter;
import com.yupay.perutax.forms.inner.VarcharFormatter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static com.yupay.perutax.forms.FormUtils.*;

/**
 * The measurement unit card form dialog.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class MunitCard extends Dialog<MeasureUnit> {
    /**
     * The editing item.
     */
    private final ObjectProperty<MeasureUnit> item =
            new SimpleObjectProperty<>(this, "item");
    /**
     * Text formatter for name.
     */
    private final TextFormatter<String> fmtName = new UpperCaseFormatter(),
    /**
     * Text formatter for taxation ID.
     */
    fmtTaxID = new VarcharFormatter(3, true),
    /**
     * Text formatter for symbol.
     */
    fmtSymbol = new VarcharFormatter(6, true);

    /**
     * FXML injected component - munit-card.fxml
     */
    @FXML
    private CheckBox chkTrash;
    /**
     * FXML injected component - munit-card.fxml
     */
    @FXML
    private Label lblID;
    /**
     * FXML injected component - munit-card.fxml
     */
    @FXML
    private DialogPane top;
    /**
     * FXML injected component - munit-card.fxml
     */
    @FXML
    private TextField txtName;
    /**
     * FXML injected component - munit-card.fxml
     */
    @FXML
    private TextField txtSymbol;
    /**
     * FXML injected component - munit-card.fxml
     */
    @FXML
    private TextField txtTaxID;

    /**
     * Package-private constructor.
     * Use a factory instead.
     *
     * @see Forms#munitCard()
     */
    MunitCard() {
    }

    /**
     * FXML initialization.
     */
    @FXML
    void initialize() {
        setDialogPane(top);
        setTitle("Unidad de Medida");
        setResultConverter(b -> b == ButtonType.APPLY ? getItem() : null);
        txtName.setTextFormatter(fmtName);
        txtSymbol.setTextFormatter(fmtSymbol);
        txtTaxID.setTextFormatter(fmtTaxID);
        item.addListener(new ValueChanged());
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #item}.get();
     */
    public final MeasureUnit getItem() {
        return item.get();
    }

    /**
     * Fluent Accessor - setter.
     *
     * @param item value to assign into {@link #item}.
     * @return this instance.
     */
    @Contract("_->this")
    public final @NotNull MunitCard setItem(@NotNull MeasureUnit item) {
        this.item.set(item);
        return this;
    }

    /**
     * Sets the mode of the form. When setting this,
     * all controls will be editable if mode is not READ_ONLY.
     *
     * @param mode the new mode. Never null.
     * @return this instance.
     */
    @Contract("_->this")
    public final @NotNull MunitCard withMode(@NotNull FormMode mode) {
        var writable = mode != FormMode.READ_ONLY;
        txtName.setEditable(writable);
        txtSymbol.setEditable(writable);
        txtTaxID.setEditable(writable);
        chkTrash.setDisable(!writable);
        return this;
    }

    /**
     * Sets the mode to CREATOR and the editing item to
     * a new measurement unit.
     *
     * @return this instance.
     */
    @Contract("->this")
    public final @NotNull MunitCard creator() {
        setItem(new MeasureUnit());
        return withMode(FormMode.CREATOR);
    }

    /**
     * Sets the mode to EDITOR and the editing item to
     * a given unit item.
     *
     * @param item the item to edit.
     * @return this instance.
     */
    @Contract("_->this")
    public final @NotNull MunitCard editor(@NotNull MeasureUnit item) {
        setItem(item);
        return withMode(FormMode.EDITOR);
    }

    /**
     * The listener to bind/unbind properties when changing editing value.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class ValueChanged extends BaseChangeListener<MeasureUnit> {
        @Override
        protected void attach(@NotNull MeasureUnit value) {
            lblID.textProperty().bind(value.idProperty());
            fmtName.valueProperty().bindBidirectional(value.nameProperty());
            fmtSymbol.valueProperty().bindBidirectional(value.symbolProperty());
            fmtTaxID.valueProperty().bindBidirectional(value.taxIdProperty());
            chkTrash.selectedProperty().bindBidirectional(value.trashProperty());
        }

        @Override
        protected void dettach(@NotNull MeasureUnit value) {
            lblID.textProperty().unbind();
            fmtName.valueProperty().unbindBidirectional(value.nameProperty());
            fmtSymbol.valueProperty().unbindBidirectional(value.symbolProperty());
            fmtTaxID.valueProperty().unbindBidirectional(value.taxIdProperty());
            chkTrash.selectedProperty().unbindBidirectional(value.trashProperty());
        }

        @Override
        protected void clear() {
            clearLabeled(lblID);
            clearCheckBoxes(chkTrash);
            clearFormatters(null, fmtName, fmtSymbol, fmtTaxID);
        }
    }
}
