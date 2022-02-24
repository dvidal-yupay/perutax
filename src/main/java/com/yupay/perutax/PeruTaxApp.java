package com.yupay.perutax;

import com.yupay.perutax.dao.DAOSource;
import com.yupay.perutax.forms.PeruTaxFXApp;
import javafx.application.Application;

/**
 * The peru tax application main class.
 * This is the entry point.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class PeruTaxApp {
    /**
     * Main method, entry point to app.
     *
     * @param args command line args.
     */
    public static void main(String[] args) {
        DAOSource.get().initPersistence(LocalUser.JPA
                .resolve("developer.properties"));
        Application.launch(PeruTaxFXApp.class, args);
    }
}
