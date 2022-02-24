package com.yupay.perutax.forms;

import com.yupay.perutax.entities.Subdiary;
import com.yupay.perutax.forms.inner.BaseChangeListener;
import com.yupay.perutax.forms.inner.UpperCaseFormatter;
import com.yupay.perutax.forms.inner.VarcharFormatter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static com.yupay.perutax.forms.FormUtils.clearFormatters;

/**
 * Form controller for subdiary card.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class SubdiaryCard extends Dialog<Subdiary> {

    /**
     * The editing value.
     */
    private final ObjectProperty<Subdiary> value
            = new SimpleObjectProperty<>(this, "value");
    /**
     * The text formatter for ID.
     */
    private final TextFormatter<String> fmtID =
            new VarcharFormatter(2, true);
    /**
     * The text formatter for title.
     */
    private final TextFormatter<String> fmtTitle =
            new UpperCaseFormatter();

    /**
     * FXML controls injected from subdiary-card.fxml
     */
    @FXML
    private CheckBox chkTrash;

    /**
     * FXML controls injected from subdiary-card.fxml
     */
    @FXML
    private DialogPane top;

    /**
     * FXML controls injected from subdiary-card.fxml
     */
    @FXML
    private TextField txtID;

    /**
     * FXML controls injected from subdiary-card.fxml
     */
    @FXML
    private TextField txtTitle;

    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see Forms#subdiaryCard()
     */
    SubdiaryCard() {
    }

    /**
     * FXML initializer.
     */
    @FXML
    void initialize() {
        setResultConverter(b -> ButtonType.APPLY == b ? value.get() : null);
        setDialogPane(top);
        setTitle("Editor de Subdiarios");
        txtID.setTextFormatter(fmtID);
        txtTitle.setTextFormatter(fmtTitle);
        value.addListener(new ValueChanged());
    }

    /**
     * Fluent accessor - setter.
     *
     * @param value the value of {@link #value}
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull SubdiaryCard withValue(@NotNull Subdiary value) {
        this.value.set(value);
        return this;
    }

    /**
     * Sets the form editor mode.
     *
     * @param mode the new editor mode.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull SubdiaryCard withMode(@NotNull FormMode mode) {
        txtID.setEditable(mode == FormMode.CREATOR);
        txtTitle.setEditable(mode != FormMode.READ_ONLY);
        chkTrash.setDisable(mode == FormMode.READ_ONLY);
        return this;
    }

    /**
     * Sets the value to a new empty subdiary
     * and form mode to CREATOR.
     *
     * @return this instance.
     */
    @Contract("->this")
    public @NotNull SubdiaryCard creator() {
        return withValue(new Subdiary()).withMode(FormMode.CREATOR);
    }

    /**
     * Inner change listener for changes in value property.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class ValueChanged extends BaseChangeListener<Subdiary> {
        @Override
        protected void attach(@NotNull Subdiary value) {
            fmtID.valueProperty().bindBidirectional(value.idProperty());
            fmtTitle.valueProperty().bindBidirectional(value.titleProperty());
            chkTrash.selectedProperty().bindBidirectional(value.trashProperty());
        }

        @Override
        protected void dettach(@NotNull Subdiary value) {
            fmtID.valueProperty().unbindBidirectional(value.idProperty());
            fmtTitle.valueProperty().unbindBidirectional(value.titleProperty());
            chkTrash.selectedProperty().unbindBidirectional(value.trashProperty());
        }

        @Override
        protected void clear() {
            clearFormatters("", fmtID, fmtTitle);
            chkTrash.setSelected(false);
        }
    }
}
