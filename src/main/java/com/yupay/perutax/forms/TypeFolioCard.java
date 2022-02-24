package com.yupay.perutax.forms;

import com.yupay.perutax.entities.TypeFolio;
import com.yupay.perutax.forms.flows.CheckRegexFlow;
import com.yupay.perutax.forms.inner.BaseChangeListener;
import com.yupay.perutax.forms.inner.NumericFormatter;
import com.yupay.perutax.forms.inner.VarcharFormatter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static com.yupay.perutax.forms.FormUtils.*;

/**
 * Controller for eitor card of Type of Folio entities.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class TypeFolioCard extends Dialog<TypeFolio> {

    /**
     * Inner property for editing value.
     */
    private final ObjectProperty<TypeFolio> value
            = new SimpleObjectProperty<>(this, "value");
    /**
     * Formatter for ID field.
     */
    private final TextFormatter<String> fmtID = new NumericFormatter(2),
    /**
     * Formatter for PLAME ID field.
     */
    fmtPLAME = new VarcharFormatter(1, true),
    /**
     * Formatter for title field.
     */
    fmtTitle = new VarcharFormatter(10, true);

    /**
     * FXML control injected from tfolio-card.fxml
     */
    @FXML
    private DialogPane top;

    /**
     * FXML control injected from tfolio-card.fxml
     */
    @FXML
    private CheckBox chkCForeign;

    /**
     * FXML control injected from tfolio-card.fxml
     */
    @FXML
    private CheckBox chkCPurchase;

    /**
     * FXML control injected from tfolio-card.fxml
     */
    @FXML
    private CheckBox chkCSale;

    /**
     * FXML control injected from tfolio-card.fxml
     */
    @FXML
    private CheckBox chkCTaxCredit;

    /**
     * FXML control injected from tfolio-card.fxml
     */
    @FXML
    private CheckBox chkTrash;

    /**
     * FXML control injected from tfolio-card.fxml
     */
    @FXML
    private TextField txtID;

    /**
     * FXML control injected from tfolio-card.fxml
     */
    @FXML
    private TextField txtPLAME;

    /**
     * FXML control injected from tfolio-card.fxml
     */
    @FXML
    private TextField txtRegexNum;

    /**
     * FXML control injected from tfolio-card.fxml
     */
    @FXML
    private TextField txtRegexSerie;

    /**
     * FXML control injected from tfolio-card.fxml
     */
    @FXML
    private TextField txtTitle;

    /**
     * Package-private constructor.
     * Use a static factory.
     *
     * @see Forms#typeFolioCard()
     */
    TypeFolioCard() {
    }

    /**
     * FXML initializaer.
     */
    @FXML
    void initialize() {
        setDialogPane(top);
        setTitle("Editor de Tipos de Folio");

        txtID.setTextFormatter(fmtID);
        txtPLAME.setTextFormatter(fmtPLAME);
        txtTitle.setTextFormatter(fmtTitle);

        value.addListener(new ValueChanged());

        setResultConverter(b -> ButtonType.APPLY == b ? value.get() : null);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(PeruTaxFXApp.getPrimary());
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void regexNumAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        @RegExp var txt = txtRegexNum.getText();
        if (txt == null || txt.isBlank()) return;
        new CheckRegexFlow().withRegex(txt).run();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void regexSerieAction(@NotNull ActionEvent event) {
        if (event.isConsumed()) return;
        @RegExp var txt = txtRegexSerie.getText();
        if (txt == null || txt.isBlank()) return;
        new CheckRegexFlow().withRegex(txt).run();
    }

    /**
     * Public API to set as value a new TypeFolio
     * and modality to creator.
     *
     * @return this instance.
     */
    @Contract("->this")
    public @NotNull TypeFolioCard creator() {
        return withValue(new TypeFolio()).withMode(FormMode.CREATOR);
    }

    /**
     * Fluent setter - editing value.
     *
     * @param value new editing value.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull TypeFolioCard withValue(@NotNull TypeFolio value) {
        this.value.set(value);
        return this;
    }

    /**
     * Fluent setter- form modality.
     *
     * @param mode the new modality.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull TypeFolioCard withMode(@NotNull FormMode mode) {
        var writable = mode != FormMode.READ_ONLY;
        txtID.setEditable(mode == FormMode.CREATOR);
        txtPLAME.setEditable(writable);
        txtRegexNum.setEditable(writable);
        txtRegexSerie.setEditable(writable);
        txtTitle.setEditable(writable);
        chkCForeign.setDisable(!writable);
        chkCPurchase.setDisable(!writable);
        chkCSale.setDisable(!writable);
        chkCTaxCredit.setDisable(!writable);
        chkTrash.setDisable(!writable);
        return this;
    }

    /**
     * The listener implementation to attach and dettach values
     * changed on editing value property.
     *
     * @author InfoYupay SACS
     * @version 1.0
     */
    private class ValueChanged extends BaseChangeListener<TypeFolio> {
        @Override
        protected void attach(@NotNull TypeFolio value) {
            fmtID.valueProperty().bindBidirectional(value.idProperty());
            fmtPLAME.valueProperty().bindBidirectional(value.plameIdProperty());
            fmtTitle.valueProperty().bindBidirectional(value.titleProperty());
            txtRegexNum.textProperty().bindBidirectional(value.regexNumberProperty());
            txtRegexSerie.textProperty().bindBidirectional(value.regexSerieProperty());
            chkCForeign.selectedProperty().bindBidirectional(value.ctxtForeignProperty());
            chkCPurchase.selectedProperty().bindBidirectional(value.ctxtPurchaseProperty());
            chkCSale.selectedProperty().bindBidirectional(value.ctxtSaleProperty());
            chkCTaxCredit.selectedProperty().bindBidirectional(value.ctxtTaxCreditProperty());
            chkTrash.selectedProperty().bindBidirectional(value.trashProperty());
        }

        @Override
        protected void dettach(@NotNull TypeFolio value) {
            fmtID.valueProperty().unbindBidirectional(value.idProperty());
            fmtPLAME.valueProperty().unbindBidirectional(value.plameIdProperty());
            fmtTitle.valueProperty().unbindBidirectional(value.titleProperty());
            txtRegexNum.textProperty().unbindBidirectional(value.regexNumberProperty());
            txtRegexSerie.textProperty().unbindBidirectional(value.regexSerieProperty());
            chkCForeign.selectedProperty().unbindBidirectional(value.ctxtForeignProperty());
            chkCPurchase.selectedProperty().unbindBidirectional(value.ctxtPurchaseProperty());
            chkCSale.selectedProperty().unbindBidirectional(value.ctxtSaleProperty());
            chkCTaxCredit.selectedProperty().unbindBidirectional(value.ctxtTaxCreditProperty());
            chkTrash.selectedProperty().unbindBidirectional(value.trashProperty());
        }

        @Override
        protected void clear() {
            clearFormatters("", fmtID, fmtPLAME, fmtTitle);
            clearTextFields(txtRegexNum, txtRegexSerie);
            clearCheckBoxes(chkCForeign, chkCPurchase, chkCSale, chkCTaxCredit, chkTrash);
        }
    }
}
