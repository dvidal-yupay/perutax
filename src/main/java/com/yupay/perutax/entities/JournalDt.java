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

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Journal detail line.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "journal_dt")
public class JournalDt {
    /**
     * The unique ID of the detail line.
     */
    private final LongProperty id =
            new SimpleLongProperty(this, "id");
    /**
     * The line number (order of appareance).
     */
    private final IntegerProperty line =
            new SimpleIntegerProperty(this, "line");
    /**
     * The tax account name.
     */
    private final StringProperty accountName =
            new SimpleStringProperty(this, "accountName");
    /**
     * Debit amount (FC).
     */
    private final ObjectProperty<BigDecimal> debitFc =
            new SimpleObjectProperty<>(this, "debitFc", new BigDecimal("0.00"));
    /**
     * Credit amount (FC)
     */
    private final ObjectProperty<BigDecimal> creditFc =
            new SimpleObjectProperty<>(this, "creditFc", new BigDecimal("0.00"));
    /**
     * Debit amount (SC).
     */
    private final ObjectProperty<BigDecimal> debitSc =
            new SimpleObjectProperty<>(this, "debitSc", new BigDecimal("0.00"));
    /**
     * Credit amount (SC).
     */
    private final ObjectProperty<BigDecimal> creditSc =
            new SimpleObjectProperty<>(this, "creditSc", new BigDecimal("0.00"));
    /**
     * Referencial briefing of this line.
     */
    private final StringProperty reference =
            new SimpleStringProperty(this, "reference");
    /**
     * Name of Cost center (snapshot).
     */
    private final StringProperty costCenterName =
            new SimpleStringProperty(this, "costCenterName");
    /**
     * Account reference.
     */
    private final ObjectProperty<TaxAccount> account =
            new SimpleObjectProperty<>(this, "account");
    /**
     * Cost center reference.
     */
    private final ObjectProperty<CostCenter> costCenter =
            new SimpleObjectProperty<>(this, "costCenter");
    /**
     * Linked folio (if any).
     */
    private final ObjectProperty<JournalDtFolio> folio =
            new SimpleObjectProperty<>(this, "folio");
    /**
     * Referenced person (if any).
     */
    private final ObjectProperty<JournalDtPerson> person =
            new SimpleObjectProperty<>(this, "person");
    /**
     * The referenced transaction to where this belongs.
     */
    private final ObjectProperty<Journal> transaction =
            new SimpleObjectProperty<>(this, "transaction");

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #transaction}.get();
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transID")
    public final Journal getTransaction() {
        return transaction.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param transaction value to assign into {@link #transaction}.
     */
    public final void setTransaction(Journal transaction) {
        this.transaction.set(transaction);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #transaction}.
     */
    public final ObjectProperty<Journal> transactionProperty() {
        return transaction;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
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
     * @return value of {@link #line}
     */
    @Basic
    @Column(name = "line", nullable = false)
    public int getLine() {
        return line.get();
    }

    /**
     * Accessor - setter.
     *
     * @param line value to set on {@link #line}
     */
    public void setLine(int line) {
        this.line.set(line);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #accountName}
     */
    @Basic
    @Column(name = "accountName", nullable = false, length = 200)
    public String getAccountName() {
        return accountName.get();
    }

    /**
     * Accessor - setter.
     *
     * @param accountName value to set on {@link #accountName}
     */
    public void setAccountName(String accountName) {
        this.accountName.set(accountName);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #debitFc}
     */
    @Basic
    @Column(name = "debitFC", nullable = false, scale = 2, precision = 14)
    public BigDecimal getDebitFc() {
        return debitFc.get();
    }

    /**
     * Accessor - setter.
     *
     * @param debitFc value to set on {@link #debitFc}
     */
    public void setDebitFc(BigDecimal debitFc) {
        this.debitFc.set(debitFc);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #creditFc}
     */
    @Basic
    @Column(name = "creditFC", nullable = false, scale = 2, precision = 14)
    public BigDecimal getCreditFc() {
        return creditFc.get();
    }

    /**
     * Accessor - setter.
     *
     * @param creditFc value to set on {@link #creditFc}
     */
    public void setCreditFc(BigDecimal creditFc) {
        this.creditFc.set(creditFc);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #debitSc}
     */
    @Basic
    @Column(name = "debitSC", nullable = false, scale = 2, precision = 14)
    public BigDecimal getDebitSc() {
        return debitSc.get();
    }

    /**
     * Accessor - setter.
     *
     * @param debitSc value to set on {@link #debitSc}
     */
    public void setDebitSc(BigDecimal debitSc) {
        this.debitSc.set(debitSc);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #creditSc}
     */
    @Basic
    @Column(name = "creditSC", nullable = false, scale = 2, precision = 14)
    public BigDecimal getCreditSc() {
        return creditSc.get();
    }

    /**
     * Accessor - setter.
     *
     * @param creditSc value to set on {@link #creditSc}
     */
    public void setCreditSc(BigDecimal creditSc) {
        this.creditSc.set(creditSc);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #reference}
     */
    @Basic
    @Column(name = "reference", length = -1)
    public String getReference() {
        return reference.get();
    }

    /**
     * Accessor - setter.
     *
     * @param reference value to set on {@link #reference}
     */
    public void setReference(String reference) {
        this.reference.set(reference);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #costCenterName}
     */
    @Basic
    @Column(name = "costCenterName")
    public String getCostCenterName() {
        return costCenterName.get();
    }

    /**
     * Accessor - setter.
     *
     * @param costCenterName value to set on {@link #costCenterName}
     */
    public void setCostCenterName(String costCenterName) {
        this.costCenterName.set(costCenterName);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #account}
     */
    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    public TaxAccount getAccount() {
        return account.get();
    }

    /**
     * Accessor - setter.
     *
     * @param taxAccountByAccountId value to set on {@link #account}
     */
    public void setAccount(TaxAccount taxAccountByAccountId) {
        this.account.set(taxAccountByAccountId);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #costCenter}
     */
    @ManyToOne
    @JoinColumn(name = "costCenterID", referencedColumnName = "id")
    public CostCenter getCostCenter() {
        return costCenter.get();
    }

    /**
     * Accessor - setter.
     *
     * @param costCenterByCostCenterId value to set on {@link #costCenter}
     */
    public void setCostCenter(CostCenter costCenterByCostCenterId) {
        this.costCenter.set(costCenterByCostCenterId);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #folio}
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "folio", referencedColumnName = "id")
    public JournalDtFolio getFolio() {
        return folio.get();
    }

    /**
     * Accessor - setter.
     *
     * @param folio value to set on {@link #folio}
     */
    public void setFolio(JournalDtFolio folio) {
        this.folio.set(folio);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #person}
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "person", referencedColumnName = "id")
    public JournalDtPerson getPerson() {
        return person.get();
    }

    /**
     * Accessor - setter.
     *
     * @param person value to set on {@link #person}
     */
    public void setPerson(JournalDtPerson person) {
        this.person.set(person);
    }


    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #id}
     */
    public LongProperty idProperty() {
        return id;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #line}
     */
    public IntegerProperty lineProperty() {
        return line;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #accountName}
     */
    public StringProperty accountNameProperty() {
        return accountName;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #debitFc}
     */
    public ObjectProperty<BigDecimal> debitFcProperty() {
        return debitFc;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #creditFc}
     */
    public ObjectProperty<BigDecimal> creditFcProperty() {
        return creditFc;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #debitSc}
     */
    public ObjectProperty<BigDecimal> debitScProperty() {
        return debitSc;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #creditSc}
     */
    public ObjectProperty<BigDecimal> creditScProperty() {
        return creditSc;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #reference}
     */
    public StringProperty referenceProperty() {
        return reference;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #costCenterName}
     */
    public StringProperty costCenterNameProperty() {
        return costCenterName;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #account}
     */
    public ObjectProperty<TaxAccount> accountProperty() {
        return account;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #costCenter}
     */
    public ObjectProperty<CostCenter> costCenterProperty() {
        return costCenter;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #folio}
     */
    public ObjectProperty<JournalDtFolio> folioProperty() {
        return folio;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #person}
     */
    public ObjectProperty<JournalDtPerson> personProperty() {
        return person;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof JournalDt journalDt
                && Objects.equals(getTransaction(), journalDt.getTransaction())
                && Objects.equals(getLine(), journalDt.getLine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLine());
    }


}
