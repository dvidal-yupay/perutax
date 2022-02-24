package com.yupay.perutax.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * This enumeration represents a currency in the system.
 * The name has been modified to Currenci for sake of productivity
 * to avoid collisions with many other Currency classes.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public enum Currenci {
    /**
     * The peruvian soles.
     */
    PEN("soles", "S/"),
    /**
     * The US dollars.
     */
    USD("dólares americanos", "$");
    /**
     * Text to show to the users.
     */
    public final String title;
    /**
     * Currency symbol.
     */
    public final String symbol;

    /**
     * Default constructor.
     *
     * @param title  the title to show in UIs.
     * @param symbol the symbol of the currency.
     */
    Currenci(String title, String symbol) {
        this.title = title;
        this.symbol = symbol;
    }

    /**
     * Wraps the values of this enum into an observable list
     * to use in combo boxes and similar controls.
     *
     * @return a new observable list.
     */
    @Contract(" -> new")
    public static @NotNull ObservableList<Currenci> observable() {
        return FXCollections.observableArrayList(values());
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return name() + " - " + title + " (" + symbol + ")";
    }
}
