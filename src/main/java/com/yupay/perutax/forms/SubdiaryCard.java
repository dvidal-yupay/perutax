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

import com.yupay.perutax.entities.Subdiary;
import com.yupay.perutax.entities.SubdiaryRole;
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
    private ComboBox<SubdiaryRole> cboRole;

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
        cboRole.setItems(SubdiaryRole.observable());
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
            cboRole.valueProperty().bindBidirectional(value.roleProperty());
        }

        @Override
        protected void dettach(@NotNull Subdiary value) {
            fmtID.valueProperty().unbindBidirectional(value.idProperty());
            fmtTitle.valueProperty().unbindBidirectional(value.titleProperty());
            chkTrash.selectedProperty().unbindBidirectional(value.trashProperty());
            cboRole.valueProperty().unbindBidirectional(value.roleProperty());
        }

        @Override
        protected void clear() {
            clearFormatters("", fmtID, fmtTitle);
            chkTrash.setSelected(false);
            cboRole.setValue(null);
        }
    }
}
