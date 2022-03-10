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

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "folio_line_tmp", schema = "public", catalog = "perutaxdb")
public class FolioLineTmp {
    private long id;
    private String title;
    private String taxId;
    private String briefing;
    private BigDecimal taxableFc;
    private BigDecimal taxFc;
    private BigDecimal exportFc;
    private BigDecimal discountFc;
    private BigDecimal discountTaxFc;
    private BigDecimal exemptFc;
    private BigDecimal taxFreeFc;
    private BigDecimal icbpFc;
    private BigDecimal othersFc;
    private BigDecimal payableFc;
    private Object currency;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "tax_id")
    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    @Basic
    @Column(name = "briefing")
    public String getBriefing() {
        return briefing;
    }

    public void setBriefing(String briefing) {
        this.briefing = briefing;
    }

    @Basic
    @Column(name = "taxable_fc")
    public BigDecimal getTaxableFc() {
        return taxableFc;
    }

    public void setTaxableFc(BigDecimal taxableFc) {
        this.taxableFc = taxableFc;
    }

    @Basic
    @Column(name = "tax_fc")
    public BigDecimal getTaxFc() {
        return taxFc;
    }

    public void setTaxFc(BigDecimal taxFc) {
        this.taxFc = taxFc;
    }

    @Basic
    @Column(name = "export_fc")
    public BigDecimal getExportFc() {
        return exportFc;
    }

    public void setExportFc(BigDecimal exportFc) {
        this.exportFc = exportFc;
    }

    @Basic
    @Column(name = "discount_fc")
    public BigDecimal getDiscountFc() {
        return discountFc;
    }

    public void setDiscountFc(BigDecimal discountFc) {
        this.discountFc = discountFc;
    }

    @Basic
    @Column(name = "discount_tax_fc")
    public BigDecimal getDiscountTaxFc() {
        return discountTaxFc;
    }

    public void setDiscountTaxFc(BigDecimal discountTaxFc) {
        this.discountTaxFc = discountTaxFc;
    }

    @Basic
    @Column(name = "exempt_fc")
    public BigDecimal getExemptFc() {
        return exemptFc;
    }

    public void setExemptFc(BigDecimal exemptFc) {
        this.exemptFc = exemptFc;
    }

    @Basic
    @Column(name = "tax_free_fc")
    public BigDecimal getTaxFreeFc() {
        return taxFreeFc;
    }

    public void setTaxFreeFc(BigDecimal taxFreeFc) {
        this.taxFreeFc = taxFreeFc;
    }

    @Basic
    @Column(name = "icbp_fc")
    public BigDecimal getIcbpFc() {
        return icbpFc;
    }

    public void setIcbpFc(BigDecimal icbpFc) {
        this.icbpFc = icbpFc;
    }

    @Basic
    @Column(name = "others_fc")
    public BigDecimal getOthersFc() {
        return othersFc;
    }

    public void setOthersFc(BigDecimal othersFc) {
        this.othersFc = othersFc;
    }

    @Basic
    @Column(name = "payable_fc")
    public BigDecimal getPayableFc() {
        return payableFc;
    }

    public void setPayableFc(BigDecimal payableFc) {
        this.payableFc = payableFc;
    }

    @Basic
    @Column(name = "currency")
    public Object getCurrency() {
        return currency;
    }

    public void setCurrency(Object currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FolioLineTmp that) {
            return id == that.id && Objects.equals(title, that.title) && Objects.equals(taxId, that.taxId) && Objects.equals(briefing, that.briefing) && Objects.equals(taxableFc, that.taxableFc) && Objects.equals(taxFc, that.taxFc) && Objects.equals(exportFc, that.exportFc) && Objects.equals(discountFc, that.discountFc) && Objects.equals(discountTaxFc, that.discountTaxFc) && Objects.equals(exemptFc, that.exemptFc) && Objects.equals(taxFreeFc, that.taxFreeFc) && Objects.equals(icbpFc, that.icbpFc) && Objects.equals(othersFc, that.othersFc) && Objects.equals(payableFc, that.payableFc) && Objects.equals(currency, that.currency);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, taxId, briefing, taxableFc, taxFc, exportFc, discountFc, discountTaxFc, exemptFc, taxFreeFc, icbpFc, othersFc, payableFc, currency);
    }
}
