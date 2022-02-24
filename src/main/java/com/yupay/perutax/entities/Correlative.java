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
import javafx.beans.property.*;
import org.eclipse.persistence.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This entity is a representation of the monthly correlatives
 * for book keeping purposes, and is used to fullfill the monthly
 * correlative requisite of SUNAT - PLE specification.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "correlative")
public class Correlative {
    /**
     * The ID of this correlative. Generated with as a UUID
     * string representation.
     */
    private final StringProperty id
            = new SimpleStringProperty(this, "id");
    /**
     * The Book's tax id for which this correlative is used.
     * This book tax id is given by sunat in PLE specification.
     * Ie, purchases books are 140100.
     */
    private final StringProperty book
            = new SimpleStringProperty(this, "book");
    /**
     * The last used value for A-type correlatives.
     */
    private final LongProperty lastA
            = new SimpleLongProperty(this, "lastA", 0);
    /**
     * The last used value for M-type correlatives.
     */
    private final LongProperty lastM
            = new SimpleLongProperty(this, "lastM", 0);
    /**
     * Tha last used value for C-type correlatives.
     */
    private final LongProperty lastC
            = new SimpleLongProperty(this, "lastC", 0);
    /**
     * Closed timestamp. If not null, the correlative
     * shouldn't admit further modifications.
     */
    private final ObjectProperty<LocalDateTime> closed
            = new SimpleObjectProperty<>(this, "closed");
    /**
     * The taxation period to where this correlative belongs.
     */
    private final ObjectProperty<TaxPeriod> period
            = new SimpleObjectProperty<>(this, "period");

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @UuidGenerator(name = "uuid_correlative")
    @GeneratedValue(generator = "uuid_correlative")
    @Id
    @Column(name = "id", nullable = false, length = 36)
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
     * @return value of {@link #book}
     */
    @Basic
    @Column(name = "book", nullable = false, length = 6)
    public String getBook() {
        return book.get();
    }

    /**
     * Accessor - setter.
     *
     * @param book value to set on {@link #book}
     */
    public void setBook(String book) {
        this.book.set(book);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #lastA}
     */
    @Basic
    @Column(name = "lastA", nullable = false)
    public long getLastA() {
        return lastA.get();
    }

    /**
     * Accessor - setter.
     *
     * @param lastA value to set on {@link #lastA}
     */
    public void setLastA(long lastA) {
        this.lastA.set(lastA);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #lastM}
     */
    @Basic
    @Column(name = "lastM", nullable = false)
    public long getLastM() {
        return lastM.get();
    }

    /**
     * Accessor - setter.
     *
     * @param lastM value to set on {@link #lastM}
     */
    public void setLastM(long lastM) {
        this.lastM.set(lastM);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #lastC}
     */
    @Basic
    @Column(name = "lastC", nullable = false)
    public long getLastC() {
        return lastC.get();
    }

    /**
     * Accessor - setter.
     *
     * @param lastC value to set on {@link #lastC}
     */
    public void setLastC(long lastC) {
        this.lastC.set(lastC);
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
     * Accessor - getter.
     *
     * @return value of {@link #period}
     */
    @ManyToOne
    @JoinColumn(name = "period", referencedColumnName = "id", nullable = false)
    public TaxPeriod getPeriod() {
        return period.get();
    }

    /**
     * Accessor - setter.
     *
     * @param period value to set on {@link #period}
     */
    public void setPeriod(TaxPeriod period) {
        this.period.set(period);
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #id}
     */
    public StringProperty idProperty() {
        return id;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #book}
     */
    public StringProperty bookProperty() {
        return book;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #lastA}
     */
    public LongProperty lastAProperty() {
        return lastA;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #lastM}
     */
    public LongProperty lastMProperty() {
        return lastM;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #lastC}
     */
    public LongProperty lastCProperty() {
        return lastC;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #closed}
     */
    public ObjectProperty<LocalDateTime> closedProperty() {
        return closed;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #period}
     */
    public ObjectProperty<TaxPeriod> periodProperty() {
        return period;
    }

    /**
     * Modifies entity state, incrementing by one
     * the lastA property.
     */
    public void stepA() {
        setLastA(getLastA() + 1);
    }

    /**
     * Modifies entity state, incrementing by one
     * the lastM property.
     */
    public void stepM() {
        setLastM(getLastM() + 1);
    }

    /**
     * Modifies entity state, incrementing by one
     * the lastC property.
     */
    public void stepC() {
        setLastC(getLastC() + 1);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Correlative that && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
