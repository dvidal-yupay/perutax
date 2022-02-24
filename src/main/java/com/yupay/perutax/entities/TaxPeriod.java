package com.yupay.perutax.entities;

import jakarta.persistence.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This entity represents a taxation period,
 * that is a month in a year.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "tax_period")
public class TaxPeriod {
    /**
     * The ID conformed by year and month
     * of the period in format YYYYMM.
     */
    private final StringProperty id
            = new SimpleStringProperty(this, "id");
    /**
     * The date at which the period begins (inclusive).
     */
    private final ObjectProperty<LocalDate> dateFrom
            = new SimpleObjectProperty<>(this, "dateFrom");
    /**
     * The date at which the period ends (inclusive).
     */
    private final ObjectProperty<LocalDate> dateUntil
            = new SimpleObjectProperty<>(this, "dateUntil");
    /**
     * Closed timestamp. If this is not null, no transaction should be
     * admited for this period.
     */
    private final ObjectProperty<LocalDateTime> closed
            = new SimpleObjectProperty<>(this, "closed");

    /**
     * Default constructor.
     */
    public TaxPeriod() {
    }

    /**
     * Consstructor to parse a tax period defined by a year and month
     * in format YYYYMM, from there it will compute from and until dates.
     *
     * @param id the new tax period id.
     */
    public TaxPeriod(@NotNull String id) {
        setId(id);
        var toInt = Integer.parseInt(id);
        setDateFrom(LocalDate.of(toInt / 100, toInt % 100, 1));
        setDateUntil(getDateFrom().plusMonths(1).minusDays(1));
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @Id
    @Column(name = "id", nullable = false, length = 6)
    public String getId() {
        return id.get();
    }

    /**
     * Accessor - setter.
     *
     * @param id value to set on {@link #id}
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #dateFrom}
     */
    @Basic
    @Column(name = "dateFrom", nullable = false, columnDefinition = "DATE")
    public LocalDate getDateFrom() {
        return dateFrom.get();
    }

    /**
     * Accessor - setter.
     *
     * @param dateFrom value to set on {@link #dateFrom}
     */
    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom.set(dateFrom);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #dateUntil}
     */
    @Basic
    @Column(name = "dateUntil", nullable = false, columnDefinition = "DATE")
    public LocalDate getDateUntil() {
        return dateUntil.get();
    }

    /**
     * Accessor - setter.
     *
     * @param dateUntil value to set on {@link #dateUntil}
     */
    public void setDateUntil(LocalDate dateUntil) {
        this.dateUntil.set(dateUntil);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #closed}
     */
    @Basic
    @Column(name = "closed", columnDefinition = "TIMESTAMP")
    public LocalDateTime getClosed() {
        return closed.get();
    }

    /**
     * Accessor - setter.
     *
     * @param closed value to set on {@link #closed}
     */
    public void setClosed(LocalDateTime closed) {
        this.closed.set(closed);
    }

    /**
     * JavaFX property accessor.
     *
     * @return property {@link  #id}
     */
    public StringProperty idProperty() {
        return id;
    }

    /**
     * JavaFX property accessor.
     *
     * @return property {@link  #dateFrom}
     */
    public ObjectProperty<LocalDate> dateFromProperty() {
        return dateFrom;
    }

    /**
     * JavaFX property accessor.
     *
     * @return property {@link  #dateUntil}
     */
    public ObjectProperty<LocalDate> dateUntilProperty() {
        return dateUntil;
    }

    /**
     * JavaFX property accessor.
     *
     * @return property {@link  #closed}
     */
    public ObjectProperty<LocalDateTime> closedProperty() {
        return closed;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TaxPeriod taxPeriod && Objects.equals(getId(), taxPeriod.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return getId();
    }
}
