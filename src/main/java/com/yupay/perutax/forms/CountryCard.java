package com.yupay.perutax.forms;

import com.yupay.perutax.entities.Country;
import com.yupay.perutax.forms.inner.BaseChangeListener;
import com.yupay.perutax.forms.inner.NumericFormatter;
import com.yupay.perutax.forms.inner.VarcharFormatter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.yupay.perutax.forms.FormUtils.clearFormatters;

/**
 * The card form to create and/or edit country entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class CountryCard extends Dialog<Country> {
    //<editor-fold desc="Inner fields">
    /**
     * The value being edited.
     */
    private final ObjectProperty<Country> value =
            new SimpleObjectProperty<>(this, "value");

    /**
     * Text formatter for ID.
     */
    private final TextFormatter<String> fmtID =
            new VarcharFormatter(2, true);
    /**
     * Text formatter for name.
     */
    private final TextFormatter<String> fmtName =
            new VarcharFormatter(255, true);
    /**
     * Text formatter for taxID 3-chars.
     */
    private final TextFormatter<String> fmtID3 =
            new NumericFormatter(3);
    /**
     * Text formatter for taxID 4-chars.
     */
    private final TextFormatter<String> fmtID4 =
            new NumericFormatter(4);
    //</editor-fold>

    //<editor-fold desc="FXML controls">
    /**
     * FXML injected control from country-card.fxml
     */
    @FXML
    private DialogPane top;

    /**
     * FXML injected control from country-card.fxml
     */
    @FXML
    private TextField txtID;

    /**
     * FXML injected control from country-card.fxml
     */
    @FXML
    private TextField txtName;

    /**
     * FXML injected control from country-card.fxml
     */
    @FXML
    private TextField txtID3;

    /**
     * FXML injected control from country-card.fxml
     */
    @FXML
    private TextField txtID4;

    /**
     * FXML injected control from country-card.fxml
     */
    @FXML
    private CheckBox chkTrash;
    //</editor-fold>

    //<editor-fold desc="Initialization">

    /**
     * Making constructor package-private.
     * Please, use static factory.
     *
     * @see Forms#countryCard()
     */
    CountryCard() {
    }

    /**
     * FXML initialization.
     */
    @FXML
    void initialize() {
        setDialogPane(top);
        setResultConverter(this::getValue);
        setTitle("Editor de Países");
        value.addListener(new DelegatedValueChanged());

        txtID.setTextFormatter(fmtID);
        txtID3.setTextFormatter(fmtID3);
        txtID4.setTextFormatter(fmtID4);
        txtName.setTextFormatter(fmtName);
    }
    //</editor-fold>

    //<editor-fold desc="Delegated">

    /**
     * Delegated method to compute the value from the dialog result.
     *
     * @param result the dialog result.
     * @return the country value if the result is APPLY, null otherwise.
     */
    @Contract("null->null")
    private @Nullable Country getValue(@Nullable ButtonType result) {
        return result == ButtonType.APPLY ? value.get() : null;
    }
    //</editor-fold>

    //<editor-fold desc="Public accessors">

    /**
     * Fluent setter.
     *
     * @param value the value to set {@link  #value}
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull CountryCard withValue(@NotNull Country value) {
        this.value.set(value);
        return this;
    }

    /**
     * Sets the editaable and disable property of controls
     * based on the requested mode.
     *
     * @param mode the requested mode.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull CountryCard withMode(@NotNull FormMode mode) {
        txtID.setEditable(mode == FormMode.CREATOR);
        var editable = mode != FormMode.READ_ONLY;
        txtID3.setEditable(editable);
        txtID4.setEditable(editable);
        txtName.setEditable(editable);
        chkTrash.setDisable(!editable);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Inner classes">

    /**
     * Delegated change listener to attach and dettach the
     * ui controls from the changed value.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class DelegatedValueChanged extends BaseChangeListener<Country> {
        @Override
        protected void attach(@NotNull Country value) {
            fmtID.valueProperty().bindBidirectional(value.idProperty());
            fmtID3.valueProperty().bindBidirectional(value.taxID3Property());
            fmtID4.valueProperty().bindBidirectional(value.taxID4Property());
            fmtName.valueProperty().bindBidirectional(value.nameProperty());
            chkTrash.selectedProperty().bindBidirectional(value.trashProperty());
        }

        @Override
        protected void dettach(@NotNull Country value) {
            fmtID.valueProperty().unbindBidirectional(value.idProperty());
            fmtID3.valueProperty().unbindBidirectional(value.taxID3Property());
            fmtID4.valueProperty().unbindBidirectional(value.taxID4Property());
            fmtName.valueProperty().unbindBidirectional(value.nameProperty());
            chkTrash.selectedProperty().unbindBidirectional(value.trashProperty());
        }

        @Override
        protected void clear() {
            clearFormatters("", fmtID, fmtID3, fmtID4);
            chkTrash.setSelected(false);
        }
    }
    //</editor-fold>
}
