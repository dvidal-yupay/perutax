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
import org.eclipse.persistence.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * The Folio entity represents any folio in the system
 * of book keeping. The folio may be linked to a purchase
 * or sale operation, and depending upon that will be shown
 * in their respective accounting books, in addition to any
 * entry in the ledger book.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "folio", schema = "public")
public class Folio {
    /**
     * Autogenerated, 36-chars UUID primary key.
     * In the SUNAT-PLE specification this field is
     * referred to as a CUO.
     */
    private String id;
    /**
     * The type of object of this folio.
     * It's used to determine which automatic
     * ledger entry will be generated, and in
     * which user UX should be shown.
     */
    private FolioObjectType objectType;
    /**
     * The string code for the book. It's specified
     * in the SUNAT-PLE specification. In the case of a
     * sale it would be 140100, and in the case of a purchase
     * it would be 050100.
     */
    private String book;
    /**
     * Book keeping correlative, it's generated for
     * each month.
     *
     * @see Correlative
     */
    private String correlative;
    /**
     * Folio series. (In Peru, an invoice number
     * is formed by a XXXX-YYYYYY where XXXX is a serie,
     * and each issuing point (point of sale, or branch)
     * must have its own serie, so the combination of XXXX-YYYYYY
     * never should be repeated, and at the same time each
     * issuing point must have its own number sequence.
     */
    private String folioSerie;
    /**
     * Folio number.
     */
    private String folioNum;
    /**
     * Document date as shown in printed invoice.
     */
    private LocalDate dateDoc;
    /**
     * Due date of the folio.
     */
    private LocalDate dateDue;
    /**
     * Taxation date. If you're registering a credit note to
     * revert the folio, taxation date should be the modified
     * folio document date.
     */
    private LocalDate dateTax;
    /**
     * General remarks of the folio. It's optional
     * and may be any information the user wants to
     * state, it's not necessarily printed in the folio.
     */
    private String remarks;
    /**
     * The currency in which the folio amounts are
     * expressed (Folio Currency).
     */
    private Currenci currency;
    /**
     * The eXchange rate used to convert Folio currency
     * into System currency.
     */
    private BigDecimal xrate;
    /**
     * Total payable amount (sum of all totals, subsctract
     * prepaid amount). Expressed as Folio currency. At any
     * given time, this should be true: {@code payableFc - paidFc = unpaidFc}
     */
    private BigDecimal payableFc;
    /**
     * Total payable amount (sum of all totals,
     * substract prepaid amount). Expressed as
     * System currency.
     */
    private BigDecimal payableSc;
    /**
     * Total prepaid amount (Expressed as Folio currency).
     * The prepaid amount is only reserved for prepayments
     * invoices.
     */
    private BigDecimal prepaidFc;
    /**
     * Total prepaid amount (expressed as System Currency).
     * The prepaid amount is only reserved for prepayments
     * invoices.
     */
    private BigDecimal prepaidSc;
    /**
     * The total paid amount of the folio.
     * When first registering the folio,
     * it'd be 0.00
     */
    private BigDecimal paidFc;
    /**
     * The unpaid amount of the folio.
     * When first registering the folio,
     * it'd be the same as {@link #payableFc}.
     */
    private BigDecimal unpaidFc;
    /**
     * Falg for voided folio. The difference
     * between reverted and voided is that
     * a voided folio is the one that has been
     * physically damaged, so it can't be used
     * but as per taxation regulation it should
     * be stated in the book keeping. Then, only
     * a sale folio may state this flag as true.
     */
    private boolean voided;
    /**
     * Flag for reverted folio. If this folio has
     * been reverted by a credit note, should be true.
     */
    private boolean reverted;
    /**
     * The taxation period.
     */
    private TaxPeriod period;
    /**
     * The folio party object. In a purchase folio,
     * this would be the supplier; while in a sale
     * folio this would be the customer.
     */
    private FolioParty party;
    /**
     * The type of folio.
     */
    private TypeFolio folioType;
    /**
     * The entry in the ledger book.
     */
    private Journal ledger;
    /**
     * The folio that has been modified
     * (in a credit or debit note).
     */
    private Folio modified;
    /**
     * The assigned cost center for purchases folios.
     */
    private CostCenter costCenter;
    /**
     * The class of purchase (if the folio represents a purchase).
     * This parameter is a special business requirement from the
     * SUNAT-PLE specification.
     */
    private FolioPurchaseClass purchaseClass;
    /**
     * The detail (lines) of the folio as shown
     * in the physical document (business entity).
     */
    private Collection<FolioLine> detail;
    /**
     * The footer detail of totals contained
     * by this folio.
     */
    private Collection<FolioTotal> totals;
    /**
     * Time stamp of the exact moment at which the
     * folio was created.
     */
    private LocalDateTime createdAt;

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #id }
     */
    @GeneratedValue(generator = "UUID_FOLIO")
    @UuidGenerator(name = "UUID_FOLIO")
    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    /**
     * Accessor - setter.
     *
     * @param id value to set on {@link #id }
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #objectType }
     */
    @Basic
    @Column(name = "object_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public FolioObjectType getObjectType() {
        return objectType;
    }

    /**
     * Accessor - setter.
     *
     * @param objectType value to set on {@link #objectType }
     */
    public void setObjectType(FolioObjectType objectType) {
        this.objectType = objectType;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #book }
     */
    @Basic
    @Column(name = "book")
    public String getBook() {
        return book;
    }

    /**
     * Accessor - setter.
     *
     * @param book value to set on {@link #book }
     */
    public void setBook(String book) {
        this.book = book;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #correlative }
     */
    @Basic
    @Column(name = "correlative")
    public String getCorrelative() {
        return correlative;
    }

    /**
     * Accessor - setter.
     *
     * @param correlative value to set on {@link #correlative }
     */
    public void setCorrelative(String correlative) {
        this.correlative = correlative;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #folioSerie }
     */
    @Basic
    @Column(name = "folio_serie")
    public String getFolioSerie() {
        return folioSerie;
    }

    /**
     * Accessor - setter.
     *
     * @param folioSerie value to set on {@link #folioSerie }
     */
    public void setFolioSerie(String folioSerie) {
        this.folioSerie = folioSerie;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #folioNum }
     */
    @Basic
    @Column(name = "folio_num")
    public String getFolioNum() {
        return folioNum;
    }

    /**
     * Accessor - setter.
     *
     * @param folioNum value to set on {@link #folioNum }
     */
    public void setFolioNum(String folioNum) {
        this.folioNum = folioNum;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #dateDoc }
     */
    @Basic
    @Column(name = "date_doc", nullable = false, columnDefinition = "DATE")
    public LocalDate getDateDoc() {
        return dateDoc;
    }

    /**
     * Accessor - setter.
     *
     * @param dateDoc value to set on {@link #dateDoc }
     */
    public void setDateDoc(LocalDate dateDoc) {
        this.dateDoc = dateDoc;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #dateDue }
     */
    @Basic
    @Column(name = "date_due", columnDefinition = "DATE")
    public LocalDate getDateDue() {
        return dateDue;
    }

    /**
     * Accessor - setter.
     *
     * @param dateDue value to set on {@link #dateDue }
     */
    public void setDateDue(LocalDate dateDue) {
        this.dateDue = dateDue;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #dateTax }
     */
    @Basic
    @Column(name = "date_tax", nullable = false, columnDefinition = "DATE")
    public LocalDate getDateTax() {
        return dateTax;
    }

    /**
     * Accessor - setter.
     *
     * @param dateTax value to set on {@link #dateTax }
     */
    public void setDateTax(LocalDate dateTax) {
        this.dateTax = dateTax;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #remarks }
     */
    @Basic
    @Column(name = "remarks")
    public String getRemarks() {
        return remarks;
    }

    /**
     * Accessor - setter.
     *
     * @param remarks value to set on {@link #remarks }
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #currency }
     */
    @Basic
    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    public Currenci getCurrency() {
        return currency;
    }

    /**
     * Accessor - setter.
     *
     * @param currency value to set on {@link #currency }
     */
    public void setCurrency(Currenci currency) {
        this.currency = currency;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #xrate }
     */
    @Basic
    @Column(name = "xrate", scale = 3, precision = 6)
    public BigDecimal getXrate() {
        return xrate;
    }

    /**
     * Accessor - setter.
     *
     * @param xrate value to set on {@link #xrate }
     */
    public void setXrate(BigDecimal xrate) {
        this.xrate = xrate;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #payableFc }
     */
    @Basic
    @Column(name = "payable_fc", precision = 14, scale = 2)
    public BigDecimal getPayableFc() {
        return payableFc;
    }

    /**
     * Accessor - setter.
     *
     * @param payableFc value to set on {@link #payableFc }
     */
    public void setPayableFc(BigDecimal payableFc) {
        this.payableFc = payableFc;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #payableSc }
     */
    @Basic
    @Column(name = "payable_sc", precision = 14, scale = 2)
    public BigDecimal getPayableSc() {
        return payableSc;
    }

    /**
     * Accessor - setter.
     *
     * @param payableSc value to set on {@link #payableSc }
     */
    public void setPayableSc(BigDecimal payableSc) {
        this.payableSc = payableSc;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #prepaidFc }
     */
    @Basic
    @Column(name = "prepaid_fc", precision = 14, scale = 2)
    public BigDecimal getPrepaidFc() {
        return prepaidFc;
    }

    /**
     * Accessor - setter.
     *
     * @param prepaidFc value to set on {@link #prepaidFc }
     */
    public void setPrepaidFc(BigDecimal prepaidFc) {
        this.prepaidFc = prepaidFc;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #prepaidSc }
     */
    @Basic
    @Column(name = "prepaid_sc", precision = 14, scale = 2)
    public BigDecimal getPrepaidSc() {
        return prepaidSc;
    }

    /**
     * Accessor - setter.
     *
     * @param prepaidSc value to set on {@link #prepaidSc }
     */
    public void setPrepaidSc(BigDecimal prepaidSc) {
        this.prepaidSc = prepaidSc;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #paidFc }
     */
    @Basic
    @Column(name = "paid_fc", precision = 14, scale = 2)
    public BigDecimal getPaidFc() {
        return paidFc;
    }

    /**
     * Accessor - setter.
     *
     * @param paidFc value to set on {@link #paidFc }
     */
    public void setPaidFc(BigDecimal paidFc) {
        this.paidFc = paidFc;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #unpaidFc }
     */
    @Basic
    @Column(name = "unpaid_fc", precision = 14, scale = 2)
    public BigDecimal getUnpaidFc() {
        return unpaidFc;
    }

    /**
     * Accessor - setter.
     *
     * @param unpaidFc value to set on {@link #unpaidFc }
     */
    public void setUnpaidFc(BigDecimal unpaidFc) {
        this.unpaidFc = unpaidFc;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #voided }
     */
    @Basic
    @Column(name = "voided")
    public boolean isVoided() {
        return voided;
    }

    /**
     * Accessor - setter.
     *
     * @param voided value to set on {@link #voided }
     */
    public void setVoided(boolean voided) {
        this.voided = voided;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #reverted }
     */
    @Basic
    @Column(name = "reverted")
    public boolean isReverted() {
        return reverted;
    }

    /**
     * Accessor - setter.
     *
     * @param reverted value to set on {@link #reverted }
     */
    public void setReverted(boolean reverted) {
        this.reverted = reverted;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #period }
     */
    @ManyToOne
    @JoinColumn(name = "period", referencedColumnName = "id", nullable = false)
    public TaxPeriod getPeriod() {
        return period;
    }

    /**
     * Accessor - setter.
     *
     * @param period value to set on {@link #period }
     */
    public void setPeriod(TaxPeriod period) {
        this.period = period;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #party }
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "party", referencedColumnName = "id", nullable = false)
    public FolioParty getParty() {
        return party;
    }

    /**
     * Accessor - setter.
     *
     * @param party value to set on {@link #party }
     */
    public void setParty(FolioParty party) {
        this.party = party;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #folioType }
     */
    @ManyToOne
    @JoinColumn(name = "folio_type", referencedColumnName = "id", nullable = false)
    public TypeFolio getFolioType() {
        return folioType;
    }

    /**
     * Accessor - setter.
     *
     * @param folioType value to set on {@link #folioType }
     */
    public void setFolioType(TypeFolio folioType) {
        this.folioType = folioType;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #ledger }
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ledger", referencedColumnName = "id", nullable = false)
    public Journal getLedger() {
        return ledger;
    }

    /**
     * Accessor - setter.
     *
     * @param ledger value to set on {@link #ledger }
     */
    public void setLedger(Journal ledger) {
        this.ledger = ledger;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #modified }
     */
    @OneToOne
    @JoinColumn(name = "modified", referencedColumnName = "id")
    public Folio getModified() {
        return modified;
    }

    /**
     * Accessor - setter.
     *
     * @param modified value to set on {@link #modified }
     */
    public void setModified(Folio modified) {
        this.modified = modified;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #costCenter }
     */
    @ManyToOne
    @JoinColumn(name = "cost_center", referencedColumnName = "id")
    public CostCenter getCostCenter() {
        return costCenter;
    }

    /**
     * Accessor - setter.
     *
     * @param costCenter value to set on {@link #costCenter }
     */
    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #purchaseClass }
     */
    @ManyToOne
    @JoinColumn(name = "purchase_class", referencedColumnName = "id")
    public FolioPurchaseClass getPurchaseClass() {
        return purchaseClass;
    }

    /**
     * Accessor - setter.
     *
     * @param purchaseClass value to set on {@link #purchaseClass }
     */
    public void setPurchaseClass(FolioPurchaseClass purchaseClass) {
        this.purchaseClass = purchaseClass;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #detail }
     */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    public Collection<FolioLine> getDetail() {
        return detail;
    }

    /**
     * Accessor - setter.
     *
     * @param detail value to set on {@link #detail }
     */
    public void setDetail(Collection<FolioLine> detail) {
        this.detail = detail;
    }

    /**
     * Accessor - getter.
     *
     * @return value of  {@link #totals }
     */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    public Collection<FolioTotal> getTotals() {
        return totals;
    }

    /**
     * Accessor - setter.
     *
     * @param totals value to set on {@link #totals }
     */
    public void setTotals(Collection<FolioTotal> totals) {
        this.totals = totals;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #createdAt}
     */
    @Basic
    @Column(name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Public accessor - setter.
     *
     * @param createdAt value to set into {@link #createdAt}
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
