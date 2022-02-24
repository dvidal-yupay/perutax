package com.yupay.perutax.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * This enumeration represents the taxation account
 * natures. Debit nature is for those whose balance
 * increases for debit transactions, and Credit nature
 * for those whose balance increses for credit transactions.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public enum AccountNature {
    /**
     * The debit nature of an account.
     */
    DEBIT("Deudora"),
    /**
     * The credit nature of an account.
     */
    CREDIT("Acreedora");
    /**
     * Human friendly text.
     */
    public final String title;

    /**
     * Default constructor.
     *
     * @param title human friendly text.
     */
    AccountNature(String title) {
        this.title = title;
    }

    /**
     * Wraps the values of this enum into an observable list
     * to use in combo boxes and similar controls.
     *
     * @return a new observable list.
     */
    @Contract(" -> new")
    public static @NotNull ObservableList<AccountNature> observable() {
        return FXCollections.observableArrayList(values());
    }

    @Override
    public String toString() {
        return title;
    }
}
