package com.yupay.perutax.forms;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * PeruTax application implementation.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class PeruTaxFXApp extends Application {
    /**
     * Primary stage.
     */
    private static Stage primary;

    /**
     * Static getter.
     *
     * @return value of {@link  #primary}
     */
    public static Stage getPrimary() {
        return primary;
    }

    @Override
    public void start(Stage primaryStage) {
        //Hold the primary stage.
        primary = primaryStage;
        //Creates the application form.
        Forms.application().show(primaryStage);
    }
}
