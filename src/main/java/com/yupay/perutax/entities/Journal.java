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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.eclipse.persistence.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * The journal entity. Each journal represents an
 * entry in the journal/ledger record.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "journal")
public class Journal {
    /**
     * UUID of the journal entry.
     */
    private final StringProperty id =
            new SimpleStringProperty(this, "id");
    /**
     * Correlative within period.
     */
    private final StringProperty correlative =
            new SimpleStringProperty(this, "correlative");
    /**
     * Currency of operation.
     */
    private final ObjectProperty<Currenci> currency =
            new SimpleObjectProperty<>(this, "currency");
    /**
     * eXchange rate.
     */
    private final ObjectProperty<BigDecimal> xrate =
            new SimpleObjectProperty<>(this, "xrate", BigDecimal.ONE);
    /**
     * Taxable date.
     */
    private final ObjectProperty<LocalDate> dateTax =
            new SimpleObjectProperty<>(this, "dateTax");
    /**
     * Document (issuance) date.
     */
    private final ObjectProperty<LocalDate> dateDoc =
            new SimpleObjectProperty<>(this, "dateDoc");
    /**
     * Due date.
     */
    private final ObjectProperty<LocalDate> dateDue =
            new SimpleObjectProperty<>(this, "dateDue");
    /**
     * Briefing of entry.
     */
    private final StringProperty briefing =
            new SimpleStringProperty(this, "briefing");
    /**
     * Time stamp of when the entry was created.
     */
    private final ObjectProperty<LocalDateTime> createdAt =
            new SimpleObjectProperty<>(this, "createdAt");
    /**
     * Reference's UUID.
     */
    private final StringProperty refId =
            new SimpleStringProperty(this, "refId");
    /**
     * Reference's book.
     */
    private final StringProperty refBook =
            new SimpleStringProperty(this, "refBook");
    /**
     * Reference's tax period.
     */
    private final StringProperty refPeriod =
            new SimpleStringProperty(this, "refPeriod");
    /**
     * Reference's correlative.
     */
    private final StringProperty refCorrelative =
            new SimpleStringProperty(this, "refCorrelative");
    /**
     * Tax period.
     */
    private final ObjectProperty<TaxPeriod> period =
            new SimpleObjectProperty<>(this, "period");
    /**
     * Subdiary of entry.
     */
    private final ObjectProperty<Subdiary> subdiary =
            new SimpleObjectProperty<>(this, "subdiary");
    /**
     * Reverted by (if there's another entry reverting this entry).
     */
    private final ObjectProperty<Journal> revertedBy =
            new SimpleObjectProperty<>(this, "revertedBy");
    /**
     * The detail lines of the entry.
     */
    private final ListProperty<JournalDt> detail =
            new SimpleListProperty<>(this, "detail",
                    FXCollections.observableArrayList());

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @UuidGenerator(name = "UUID_JOURNAL")
    @GeneratedValue(generator = "UUID_JOURNAL")
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
     * @return value of {@link #correlative}
     */
    @Basic
    @Column(name = "correlative", nullable = false, length = 10)
    public String getCorrelative() {
        return correlative.get();
    }

    /**
     * Accessor - setter.
     *
     * @param correlative value to set on {@link #correlative}
     */
    public void setCorrelative(String correlative) {
        this.correlative.set(correlative);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #currency}
     */
    @Basic
    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    public Currenci getCurrency() {
        return currency.get();
    }

    /**
     * Accessor - setter.
     *
     * @param currency value to set on {@link #currency}
     */
    public void setCurrency(Currenci currency) {
        this.currency.set(currency);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #xrate}
     */
    @Basic
    @Column(name = "xrate", nullable = false, precision = 6, scale = 3)
    public BigDecimal getXrate() {
        return xrate.get();
    }

    /**
     * Accessor - setter.
     *
     * @param xrate value to set on {@link #xrate}
     */
    public void setXrate(BigDecimal xrate) {
        this.xrate.set(xrate);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #dateTax}
     */
    @Basic
    @Column(name = "dateTax", nullable = false, columnDefinition = "DATE")
    public LocalDate getDateTax() {
        return dateTax.get();
    }

    /**
     * Accessor - setter.
     *
     * @param dateTax value to set on {@link #dateTax}
     */
    public void setDateTax(LocalDate dateTax) {
        this.dateTax.set(dateTax);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #dateDoc}
     */
    @Basic
    @Column(name = "dateDoc", nullable = false, columnDefinition = "DATE")
    public LocalDate getDateDoc() {
        return dateDoc.get();
    }

    /**
     * Accessor - setter.
     *
     * @param dateDoc value to set on {@link #dateDoc}
     */
    public void setDateDoc(LocalDate dateDoc) {
        this.dateDoc.set(dateDoc);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #dateDue}
     */
    @Basic
    @Column(name = "dateDue", columnDefinition = "DATE")
    public LocalDate getDateDue() {
        return dateDue.get();
    }

    /**
     * Accessor - setter.
     *
     * @param dateDue value to set on {@link #dateDue}
     */
    public void setDateDue(LocalDate dateDue) {
        this.dateDue.set(dateDue);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #briefing}
     */
    @Basic
    @Column(name = "briefing", nullable = false, length = -1)
    public String getBriefing() {
        return briefing.get();
    }

    /**
     * Accessor - setter.
     *
     * @param briefing value to set on {@link #briefing}
     */
    public void setBriefing(String briefing) {
        this.briefing.set(briefing);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #createdAt}
     */
    @Basic
    @Column(name = "createdAt", nullable = false, columnDefinition = "TIMESTAMP")
    public LocalDateTime getCreatedAt() {
        return createdAt.get();
    }

    /**
     * Accessor - setter.
     *
     * @param createdAt value to set on {@link #createdAt}
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt.set(createdAt);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #refId}
     */
    @Basic
    @Column(name = "refID", length = 36)
    public String getRefId() {
        return refId.get();
    }

    /**
     * Accessor - setter.
     *
     * @param refId value to set on {@link #refId}
     */
    public void setRefId(String refId) {
        this.refId.set(refId);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #refBook}
     */
    @Basic
    @Column(name = "refBook", length = 6)
    public String getRefBook() {
        return refBook.get();
    }

    /**
     * Accessor - setter.
     *
     * @param refBook value to set on {@link #refBook}
     */
    public void setRefBook(String refBook) {
        this.refBook.set(refBook);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #refPeriod}
     */
    @Basic
    @Column(name = "refPeriod", length = 6)
    public String getRefPeriod() {
        return refPeriod.get();
    }

    /**
     * Accessor - setter.
     *
     * @param refPeriod value to set on {@link #refPeriod}
     */
    public void setRefPeriod(String refPeriod) {
        this.refPeriod.set(refPeriod);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #refCorrelative}
     */
    @Basic
    @Column(name = "refCorrelative", length = 10)
    public String getRefCorrelative() {
        return refCorrelative.get();
    }

    /**
     * Accessor - setter.
     *
     * @param refCorrelative value to set on {@link #refCorrelative}
     */
    public void setRefCorrelative(String refCorrelative) {
        this.refCorrelative.set(refCorrelative);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #period}
     */
    @ManyToOne
    @JoinColumn(name = "taxPeriod", referencedColumnName = "id", nullable = false)
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
     * Accessor - getter.
     *
     * @return value of {@link #subdiary}
     */
    @ManyToOne
    @JoinColumn(name = "subdiary", referencedColumnName = "id", nullable = false)
    public Subdiary getSubdiary() {
        return subdiary.get();
    }

    /**
     * Accessor - setter.
     *
     * @param subdiary value to set on {@link #subdiary}
     */
    public void setSubdiary(Subdiary subdiary) {
        this.subdiary.set(subdiary);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #revertedBy}
     */
    @ManyToOne
    @JoinColumn(name = "revertedBy", referencedColumnName = "id")
    public Journal getRevertedBy() {
        return revertedBy.get();
    }

    /**
     * Accessor - setter.
     *
     * @param revertedBy value to set on {@link #revertedBy}
     */
    public void setRevertedBy(Journal revertedBy) {
        this.revertedBy.set(revertedBy);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #detail}
     */
    @Transient
    public ObservableList<JournalDt> getDetail() {
        return detail.get();
    }

    /**
     * Accessor - setter.
     *
     * @param detail value to set on {@link #detail}
     */
    public void setDetail(ObservableList<JournalDt> detail) {
        this.detail.set(detail);
    }

    /**
     * Virtual getter to get the detail (for JPA purposes).
     *
     * @return the details elements.
     */
    @OneToMany(cascade = CascadeType.PERSIST)
    public Collection<JournalDt> getDetails() {
        return getDetail();
    }

    /**
     * Virtual setter to set the detail (for JPA purposes).
     *
     * @param details the new details elements.
     */
    public void setDetails(Collection<JournalDt> details) {
        detail.setAll(details);
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
     * @return the property {@link  #correlative}
     */
    public StringProperty correlativeProperty() {
        return correlative;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #currency}
     */
    public ObjectProperty<Currenci> currencyProperty() {
        return currency;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #xrate}
     */
    public ObjectProperty<BigDecimal> xrateProperty() {
        return xrate;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #dateTax}
     */
    public ObjectProperty<LocalDate> dateTaxProperty() {
        return dateTax;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #dateDoc}
     */
    public ObjectProperty<LocalDate> dateDocProperty() {
        return dateDoc;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #dateDue}
     */
    public ObjectProperty<LocalDate> dateDueProperty() {
        return dateDue;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #briefing}
     */
    public StringProperty briefingProperty() {
        return briefing;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #createdAt}
     */
    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #refId}
     */
    public StringProperty refIdProperty() {
        return refId;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #refBook}
     */
    public StringProperty refBookProperty() {
        return refBook;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #refPeriod}
     */
    public StringProperty refPeriodProperty() {
        return refPeriod;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #refCorrelative}
     */
    public StringProperty refCorrelativeProperty() {
        return refCorrelative;
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
     * JavaFX accessor - property.
     *
     * @return the property {@link  #subdiary}
     */
    public ObjectProperty<Subdiary> subdiaryProperty() {
        return subdiary;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #revertedBy}
     */
    public ObjectProperty<Journal> revertedByProperty() {
        return revertedBy;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #detail}
     */
    public ListProperty<JournalDt> detailProperty() {
        return detail;
    }

    /**
     * Creates a new line for this detail.
     */
    public void createLine() {
        var x = new JournalDt();
        //x.setTransaction(this);//TODO: check this.
        detail.add(x);
        var i = 0;
        for (var dt : detail) dt.setLine(++i);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Journal journal && Objects.equals(getId(), journal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
