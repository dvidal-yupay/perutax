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

package com.yupay.perutax.entities;

import jakarta.persistence.*;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * This entity represents an exchange rate record.
 * The rate of exchange is USD/PEN only in version 1.0
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Table(name = "xrate")
@Entity
public class XRate {
    /**
     * The autogenerated id.
     */
    private final LongProperty id
            = new SimpleLongProperty(this, "id");
    /**
     * The (unique) date of the published rate.
     */
    private final ObjectProperty<LocalDate> taxDate
            = new SimpleObjectProperty<>(this, "taxDate");
    /**
     * The purchase rate.
     */
    private final ObjectProperty<BigDecimal> prch
            = new SimpleObjectProperty<>(this, "prch");
    /**
     * The sale rate.
     */
    private final ObjectProperty<BigDecimal> sale
            = new SimpleObjectProperty<>(this, "sale");

    /**
     * Empty constructor.
     */
    public XRate() {
    }

    /**
     * Constructor to copy values from another instance.
     * @param another the another instance.
     */
    public XRate(@NotNull XRate another) {
        setId(another.getId());
        setPrch(another.getPrch());
        setSale(another.getSale());
        setTaxDate(another.getTaxDate());
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    public long getId() {
        return id.get();
    }

    /**
     * Accessor - setter.
     *
     * @param id value to set on {@link #id}
     */
    public void setId(long id) {
        this.id.set(id);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #taxDate}
     */
    @Basic
    @Column(name = "taxDate", nullable = false, columnDefinition = "DATE")
    public LocalDate getTaxDate() {
        return taxDate.get();
    }

    /**
     * Accessor - setter.
     *
     * @param taxDate value to set on {@link #taxDate}
     */
    public void setTaxDate(LocalDate taxDate) {
        this.taxDate.set(taxDate);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #prch}
     */
    @Basic
    @Column(name = "prch", nullable = false, scale = 3, precision = 6)
    public BigDecimal getPrch() {
        return prch.get();
    }

    /**
     * Accessor - setter.
     *
     * @param prch value to set on {@link #prch}
     */
    public void setPrch(BigDecimal prch) {
        this.prch.set(prch);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #sale}
     */
    @Basic
    @Column(name = "sale", nullable = false, scale = 3, precision = 6)
    public BigDecimal getSale() {
        return sale.get();
    }

    /**
     * Accessor - setter.
     *
     * @param sale value to set on {@link #sale}
     */
    public void setSale(BigDecimal sale) {
        this.sale.set(sale);
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property {@link  #id}
     */
    public LongProperty idProperty() {
        return id;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property {@link  #taxDate}
     */
    public ObjectProperty<LocalDate> taxDateProperty() {
        return taxDate;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property {@link  #prch}
     */
    public ObjectProperty<BigDecimal> prchProperty() {
        return prch;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property {@link  #sale}
     */
    public ObjectProperty<BigDecimal> saleProperty() {
        return sale;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof XRate xrate && Objects.equals(getId(), xrate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}