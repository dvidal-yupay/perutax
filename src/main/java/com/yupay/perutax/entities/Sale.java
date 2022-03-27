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
 * This entity represents a sale entry.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(schema = "public", name = "sale")
public class Sale {
    /**
     * The ID of the sale.
     */
    private final StringProperty id =
            new SimpleStringProperty(this, "id");
    /**
     * Correlative (restarted each period).
     */
    private final StringProperty correlative =
            new SimpleStringProperty(this, "correlative");
    /**
     * Document series.
     */
    private final StringProperty folioSerie =
            new SimpleStringProperty(this, "folioSerie");
    /**
     * Document number.
     */
    private final StringProperty folioNum =
            new SimpleStringProperty(this, "folioNum");
    /**
     * Document issue date.
     */
    private final ObjectProperty<LocalDate> dateDoc =
            new SimpleObjectProperty<>(this, "dateDoc");
    /**
     * Due date.
     */
    private final ObjectProperty<LocalDate> dateDue =
            new SimpleObjectProperty<>(this, "dateDue");
    /**
     * Taxable date.
     */
    private final ObjectProperty<LocalDate> dateTax =
            new SimpleObjectProperty<>(this, "dateTax");
    /**
     * Time stamp at when the sale was created in the system.
     */
    private final ObjectProperty<LocalDateTime> createdAt =
            new SimpleObjectProperty<>(this, "createdAt");
    /**
     * An optional remark of the sale.
     */
    private final StringProperty remark =
            new SimpleStringProperty(this, "remark");
    /**
     * The currency at which the sale folio amounts are expressed.
     */
    private final ObjectProperty<Currenci> currency =
            new SimpleObjectProperty<>(this, "currency");
    /**
     * eXchange rate.
     */
    private final ObjectProperty<BigDecimal> xrate =
            new SimpleObjectProperty<>(this, "xrate", new BigDecimal("0.000"));
    /**
     * Payable amount (folio currency).
     */
    private final ObjectProperty<BigDecimal> payableFc =
            new SimpleObjectProperty<>(this, "payableFc", new BigDecimal("0.00"));
    /**
     * Payable amount (system currency).
     */
    private final ObjectProperty<BigDecimal> payableSc =
            new SimpleObjectProperty<>(this, "payableSc", new BigDecimal("0.00"));
    /**
     * Paid amount.
     */
    private final ObjectProperty<BigDecimal> paidFc =
            new SimpleObjectProperty<>(this, "paidFc", new BigDecimal("0.00"));
    /**
     * Unpaid amount.
     */
    private final ObjectProperty<BigDecimal> unpaidFc =
            new SimpleObjectProperty<>(this, "unpaidFc", new BigDecimal("0.00"));
    /**
     * The tax period.
     */
    private final ObjectProperty<TaxPeriod> period =
            new SimpleObjectProperty<>(this, "period");
    /**
     * The type of folio of this sale.
     */
    private final ObjectProperty<TypeFolio> folioType =
            new SimpleObjectProperty<>(this, "folioType");
    /**
     * Modified sale. It's usually used for credit and debit notes.
     */
    private final ObjectProperty<Sale> modified =
            new SimpleObjectProperty<>(this, "modified");
    /**
     * The customer information as a person reference.
     */
    private final ObjectProperty<PersonReference> person =
            new SimpleObjectProperty<>(this, "person");
    /**
     * The journal entry asociated to this sale.
     */
    private final ObjectProperty<Journal> journal =
            new SimpleObjectProperty<>(this, "journal");
    /**
     * List of lines of detail.
     */
    private final ListProperty<SaleLine> detail =
            new SimpleListProperty<>(this, "detail", FXCollections.observableArrayList());
    /**
     * List of total amounts.
     */
    private final ListProperty<SaleTotal> totals =
            new SimpleListProperty<>(this, "totals", FXCollections.observableArrayList());
    /**
     * A sale is reverted when the folio has been granted to the user,
     * but later the sale was reverted (returns, pricing errors, etc).
     * Every reverted sale should be referenced by another sale in the
     * modified field.
     */
    private BooleanProperty reverted =
            new SimpleBooleanProperty(this, "reverted");
    /**
     * A voided sale is meant to be ignored. It's a state reserved
     * for special ocasions (ie: the folio paper got broken while printing,
     * the electronic folio was corrupted before sending to validation, etc).
     */
    private BooleanProperty voided =
            new SimpleBooleanProperty(this, "voided");

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #id}
     */
    @GeneratedValue(generator = "uuid_saleId")
    @UuidGenerator(name = "uuid_saleId")
    @Id
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return id.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param id value to set into {@link #id}
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #correlative}
     */
    @Basic
    @Column(name = "correlative", nullable = false, length = 10)
    public String getCorrelative() {
        return correlative.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param correlative value to set into {@link #correlative}
     */
    public void setCorrelative(String correlative) {
        this.correlative.set(correlative);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #folioSerie}
     */
    @Basic
    @Column(name = "folio_serie", nullable = false, length = 4)
    public String getFolioSerie() {
        return folioSerie.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param folioSerie value to set into {@link #folioSerie}
     */
    public void setFolioSerie(String folioSerie) {
        this.folioSerie.set(folioSerie);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #folioNum}
     */
    @Basic
    @Column(name = "folio_num", nullable = false, length = 8)
    public String getFolioNum() {
        return folioNum.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param folioNum value to set into {@link #folioNum}
     */
    public void setFolioNum(String folioNum) {
        this.folioNum.set(folioNum);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #dateDoc}
     */
    @Basic
    @Column(name = "date_doc", nullable = false, columnDefinition = "DATE")
    public LocalDate getDateDoc() {
        return dateDoc.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param dateDoc value to set into {@link #dateDoc}
     */
    public void setDateDoc(LocalDate dateDoc) {
        this.dateDoc.set(dateDoc);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #dateDue}
     */
    @Basic
    @Column(name = "date_due", columnDefinition = "DATE")
    public LocalDate getDateDue() {
        return dateDue.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param dateDue value to set into {@link #dateDue}
     */
    public void setDateDue(LocalDate dateDue) {
        this.dateDue.set(dateDue);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #dateTax}
     */
    @Basic
    @Column(name = "date_tax", nullable = false, columnDefinition = "DATE")
    public LocalDate getDateTax() {
        return dateTax.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param dateTax value to set into {@link #dateTax}
     */
    public void setDateTax(LocalDate dateTax) {
        this.dateTax.set(dateTax);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #createdAt}
     */
    @Basic
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    public LocalDateTime getCreatedAt() {
        return createdAt.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param createdAt value to set into {@link #createdAt}
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt.set(createdAt);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #remark}
     */
    @Basic
    @Column(name = "remark", length = -1)
    public String getRemark() {
        return remark.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param remark value to set into {@link #remark}
     */
    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #currency}
     */
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    public Currenci getCurrency() {
        return currency.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param currency value to set into {@link #currency}
     */
    public void setCurrency(Currenci currency) {
        this.currency.set(currency);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #xrate}
     */
    @Basic
    @Column(name = "xrate", nullable = false, precision = 6, scale = 3)
    public BigDecimal getXrate() {
        return xrate.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param xrate value to set into {@link #xrate}
     */
    public void setXrate(BigDecimal xrate) {
        this.xrate.set(xrate);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #payableFc}
     */
    @Basic
    @Column(name = "payable_fc", nullable = false, precision = 14, scale = 2)
    public BigDecimal getPayableFc() {
        return payableFc.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param payableFc value to set into {@link #payableFc}
     */
    public void setPayableFc(BigDecimal payableFc) {
        this.payableFc.set(payableFc);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #payableSc}
     */
    @Basic
    @Column(name = "payable_sc", nullable = false, precision = 14, scale = 2)
    public BigDecimal getPayableSc() {
        return payableSc.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param payableSc value to set into {@link #payableSc}
     */
    public void setPayableSc(BigDecimal payableSc) {
        this.payableSc.set(payableSc);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #paidFc}
     */
    @Basic
    @Column(name = "paid_fc", nullable = false, precision = 14, scale = 2)
    public BigDecimal getPaidFc() {
        return paidFc.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param paidFc value to set into {@link #paidFc}
     */
    public void setPaidFc(BigDecimal paidFc) {
        this.paidFc.set(paidFc);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #unpaidFc}
     */
    @Basic
    @Column(name = "unpaid_fc", nullable = false, precision = 14, scale = 2)
    public BigDecimal getUnpaidFc() {
        return unpaidFc.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param unpaidFc value to set into {@link #unpaidFc}
     */
    public void setUnpaidFc(BigDecimal unpaidFc) {
        this.unpaidFc.set(unpaidFc);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #reverted}
     */
    @Basic
    @Column(name = "reverted", nullable = false)
    public boolean isReverted() {
        return reverted.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param reverted value to set into {@link #reverted}
     */
    public void setReverted(boolean reverted) {
        this.reverted.set(reverted);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #voided}
     */
    @Basic
    @Column(name = "voided", nullable = false)
    public boolean isVoided() {
        return voided.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param voided value to set into {@link #voided}
     */
    public void setVoided(boolean voided) {
        this.voided.set(voided);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #period}
     */
    @ManyToOne
    @JoinColumn(name = "period", referencedColumnName = "id", nullable = false)
    public TaxPeriod getPeriod() {
        return period.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param period value to set into {@link #period}
     */
    public void setPeriod(TaxPeriod period) {
        this.period.set(period);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #folioType}
     */
    @ManyToOne
    @JoinColumn(name = "folio_type", referencedColumnName = "id", nullable = false)
    public TypeFolio getFolioType() {
        return folioType.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param folioType value to set into {@link #folioType}
     */
    public void setFolioType(TypeFolio folioType) {
        this.folioType.set(folioType);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #modified}
     */
    @OneToOne
    @JoinColumn(name = "modified", referencedColumnName = "id")
    public Sale getModified() {
        return modified.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param modified value to set into {@link #modified}
     */
    public void setModified(Sale modified) {
        this.modified.set(modified);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #person}
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person", referencedColumnName = "id", nullable = false)
    public PersonReference getPerson() {
        return person.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param person value to set into {@link #person}
     */
    public void setPerson(PersonReference person) {
        this.person.set(person);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #journal}
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "journal", referencedColumnName = "id", nullable = false)
    public Journal getJournal() {
        return journal.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param journal value to set into {@link #journal}
     */
    public void setJournal(Journal journal) {
        this.journal.set(journal);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #detail}
     */
    @OneToMany(mappedBy = "sale",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Collection<SaleLine> getDetail() {
        return observableDetail();
    }

    /**
     * Public accessor - setter.
     *
     * @param detail value to set into {@link #detail}
     */
    public void setDetail(Collection<SaleLine> detail) {
        observableDetail().setAll(detail);
    }

    /**
     * Alternative getter for the detail.
     * Since the standard getter is for JPA ussage,
     * this is for javaFX usage.
     *
     * @return {@link #detail} value.
     */
    @Transient
    public ObservableList<SaleLine> observableDetail() {
        return detail.get();
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #totals}
     */
    @OneToMany(mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Collection<SaleTotal> getTotals() {
        return observableTotals();
    }

    /**
     * Public accessor - setter.
     *
     * @param totals value to set into {@link #totals}
     */
    public void setTotals(Collection<SaleTotal> totals) {
        observableTotals().setAll(totals);
    }

    /**
     * Alternative getter for the totals.
     * Since the standard getter is for JPA usage,
     * this getter is for javaFX usage.
     *
     * @return {@link #totals} value.
     */
    @Transient
    public ObservableList<SaleTotal> observableTotals() {
        return totals.get();
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #id}
     */
    public StringProperty idProperty() {
        return id;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #correlative}
     */
    public StringProperty correlativeProperty() {
        return correlative;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #folioSerie}
     */
    public StringProperty folioSerieProperty() {
        return folioSerie;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #folioNum}
     */
    public StringProperty folioNumProperty() {
        return folioNum;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #dateDoc}
     */
    public ObjectProperty<LocalDate> dateDocProperty() {
        return dateDoc;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #dateDue}
     */
    public ObjectProperty<LocalDate> dateDueProperty() {
        return dateDue;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #dateTax}
     */
    public ObjectProperty<LocalDate> dateTaxProperty() {
        return dateTax;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #createdAt}
     */
    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #remark}
     */
    public StringProperty remarkProperty() {
        return remark;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #currency}
     */
    public ObjectProperty<Currenci> currencyProperty() {
        return currency;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #xrate}
     */
    public ObjectProperty<BigDecimal> xrateProperty() {
        return xrate;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #payableFc}
     */
    public ObjectProperty<BigDecimal> payableFcProperty() {
        return payableFc;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #payableSc}
     */
    public ObjectProperty<BigDecimal> payableScProperty() {
        return payableSc;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #paidFc}
     */
    public ObjectProperty<BigDecimal> paidFcProperty() {
        return paidFc;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #unpaidFc}
     */
    public ObjectProperty<BigDecimal> unpaidFcProperty() {
        return unpaidFc;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #period}
     */
    public ObjectProperty<TaxPeriod> periodProperty() {
        return period;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #folioType}
     */
    public ObjectProperty<TypeFolio> folioTypeProperty() {
        return folioType;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #modified}
     */
    public ObjectProperty<Sale> modifiedProperty() {
        return modified;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #person}
     */
    public ObjectProperty<PersonReference> personProperty() {
        return person;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #journal}
     */
    public ObjectProperty<Journal> journalProperty() {
        return journal;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #detail}
     */
    public ListProperty<SaleLine> detailProperty() {
        return detail;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #totals}
     */
    public ListProperty<SaleTotal> totalsProperty() {
        return totals;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #reverted}
     */
    public BooleanProperty revertedProperty() {
        return reverted;
    }

    /**
     * JavaFX accessor - Property.
     *
     * @return the property {@link #voided}
     */
    public BooleanProperty voidedProperty() {
        return voided;
    }

    @Override
    public boolean equals(Object o) {

        return o instanceof Sale sale &&
                Objects.equals(getId(), sale.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
