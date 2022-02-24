package com.yupay.perutax.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The cost group for financial analysis.
 * This cost group is a property of a tax account.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public enum CostGroup {
    /**
     * Direct cost: supplies.
     */
    DIRECT_SUPPLY("Directo - Suministros"),
    /**
     * Direct cost: hand work.
     */
    DIRECT_HWORK("Directo - M. Obra"),
    /**
     * Direct cost: others.
     */
    DIRECT_OTH("Directo - Otros"),
    /**
     * Indirect cost: supplies.
     */
    INDIRECT_SUPPLY("Indirecto - Suministros"),
    /**
     * Indirect cost: hand work.
     */
    INDIRECT_HWORK("Indirecto - M. Obra"),
    /**
     * Indirect cost: others.
     */
    INDIRECT_OTH("Indirecto - Otros");
    /**
     * Human friendly text.
     */
    public final String title;

    /**
     * Default constructor.
     *
     * @param title human friendly text.
     */
    CostGroup(String title) {
        this.title = title;
    }

    /**
     * Wraps the values of this enum into an observable list
     * to use in combo boxes and similar controls.
     *
     * @return a new observable list.
     */
    @Contract(" -> new")
    public static @NotNull ObservableList<CostGroup> observable() {
        return FXCollections.observableArrayList(values());
    }

    @Override
    public String toString() {
        return title;
    }
}
