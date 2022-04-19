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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

/**
 * The main peru tax application form.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class PeruTaxFX {

    //<editor-fold desc="FXML controls.">
    /**
     * FXML injected control, application.fxml
     */
    @FXML
    private Scene top;
    //</editor-fold>

    //<editor-fold desc="Initialization.">

    /**
     * Package-private constructor.
     * To instanciate, use a static factory.
     *
     * @see Forms#application()
     */
    PeruTaxFX() {
    }
    //</editor-fold>

    //<editor-fold desc="FXML event handlers.">

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void masterCountryAction(@NotNull ActionEvent event) {
        if (!event.isConsumed())
            Forms.countryView().show();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void masterPersonAction(@NotNull ActionEvent event) {
        if (!event.isConsumed())
            Forms.personView().show();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void masterTypeDOIAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) Forms.tDoiView().show();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void masterXrateAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) Forms.xrate().show();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void masterTaxPeriod(@NotNull ActionEvent event) {
        if (!event.isConsumed()) Forms.periodForm().show();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void masterCostCenter(@NotNull ActionEvent event) {
        if (!event.isConsumed()) Forms.costcenter().show();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void masterCorrelative(@NotNull ActionEvent event) {
        if (!event.isConsumed()) Forms.correlative().show();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void masterTaxAccount(@NotNull ActionEvent event) {
        if (!event.isConsumed()) Forms.taxAccountView().show();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void masterTypeFolio(@NotNull ActionEvent event) {
        if (!event.isConsumed()) Forms.typeFolioView().show();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void masterMUnitAction(@NotNull ActionEvent event) {
        if (!event.isConsumed()) Forms.munitView().show();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void masterSubdiary(@NotNull ActionEvent event) {
        if (!event.isConsumed()) Forms.subdiaryView().show();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void accountJournal(@NotNull ActionEvent event) {
        if (!event.isConsumed()) Forms.journalView().show();
    }

    /**
     * FXML event handler.
     *
     * @param event the event object.
     */
    @FXML
    void saleScheme(@NotNull ActionEvent event) {
        if (!event.isConsumed()) {
            Forms.saleSchemeView().show();
            event.consume();
        }
    }
    //</editor-fold>

    //<editor-fold desc="Public API.">

    /**
     * Prepares a stage with a scene provided by this controller,
     * and then shows it maximized. The stage's title is set here.
     *
     * @param stage the stage to show.
     */
    public void show(@NotNull Stage stage) {
        stage.setScene(top);
        stage.setTitle("Peru Tax v1.0.0 (C) INFOYUPAY S.A.C.S.");
        stage.setMaximized(true);
        stage.show();
    }
    //</editor-fold>
}

