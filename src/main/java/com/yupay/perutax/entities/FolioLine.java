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
@Table(name = "folio_line", schema = "public", catalog = "perutaxdb")
public class FolioLine {
    private long id;
    private short line;
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
    private String briefing;
    private String dim1;
    private String dim2;
    private String dim3;
    private String dim4;
    private String taxId;
    private Folio owner;

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
    @Column(name = "line")
    public short getLine() {
        return line;
    }

    public void setLine(short line) {
        this.line = line;
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
    @Column(name = "briefing")
    public String getBriefing() {
        return briefing;
    }

    public void setBriefing(String briefing) {
        this.briefing = briefing;
    }

    @Basic
    @Column(name = "dim1")
    public String getDim1() {
        return dim1;
    }

    public void setDim1(String dim1) {
        this.dim1 = dim1;
    }

    @Basic
    @Column(name = "dim2")
    public String getDim2() {
        return dim2;
    }

    public void setDim2(String dim2) {
        this.dim2 = dim2;
    }

    @Basic
    @Column(name = "dim3")
    public String getDim3() {
        return dim3;
    }

    public void setDim3(String dim3) {
        this.dim3 = dim3;
    }

    @Basic
    @Column(name = "dim4")
    public String getDim4() {
        return dim4;
    }

    public void setDim4(String dim4) {
        this.dim4 = dim4;
    }

    @Basic
    @Column(name = "tax_id")
    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FolioLine folioLine) {
            return id == folioLine.id && line == folioLine.line && Objects.equals(taxableFc, folioLine.taxableFc) && Objects.equals(taxFc, folioLine.taxFc) && Objects.equals(exportFc, folioLine.exportFc) && Objects.equals(discountFc, folioLine.discountFc) && Objects.equals(discountTaxFc, folioLine.discountTaxFc) && Objects.equals(exemptFc, folioLine.exemptFc) && Objects.equals(taxFreeFc, folioLine.taxFreeFc) && Objects.equals(icbpFc, folioLine.icbpFc) && Objects.equals(othersFc, folioLine.othersFc) && Objects.equals(payableFc, folioLine.payableFc) && Objects.equals(briefing, folioLine.briefing) && Objects.equals(dim1, folioLine.dim1) && Objects.equals(dim2, folioLine.dim2) && Objects.equals(dim3, folioLine.dim3) && Objects.equals(dim4, folioLine.dim4) && Objects.equals(taxId, folioLine.taxId);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, line, taxableFc, taxFc, exportFc, discountFc, discountTaxFc, exemptFc, taxFreeFc, icbpFc, othersFc, payableFc, briefing, dim1, dim2, dim3, dim4, taxId);
    }

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id", nullable = false)
    public Folio getOwner() {
        return owner;
    }

    public void setOwner(Folio owner) {
        this.owner = owner;
    }
}
