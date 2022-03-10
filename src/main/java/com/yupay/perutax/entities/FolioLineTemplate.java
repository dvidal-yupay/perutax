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

/**
 * This entity is a template for a folio line, so
 * the user will be able to choose it when registering
 * the folio, and will have prices, briefings and Taxation ID
 * of the items being added.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "folio_line_tmp", schema = "public")
public class FolioLineTemplate {
    /**
     * Autogenerated ID.
     */
    private long id;
    /**
     * Unique title to identify the template.
     */
    private String title;
    /**
     * The taxation ID of the item, assigned by UN.
     */
    private String taxId;
    /**
     * Briefing text of the line.
     *
     * @implNote here only there's briefing
     * because the details (Dimensions) should be
     * specific of each sold item.
     */
    private String briefing;
    /**
     * Taxable amount - folio currency.
     */
    private BigDecimal taxableFc;
    /**
     * Tax amount - folio currency.
     */
    private BigDecimal taxFc;
    /**
     * Exportaiton value - folio currency.
     */
    private BigDecimal exportFc;
    /**
     * Discount amount (price portion) - folio currency.
     */
    private BigDecimal discountFc;
    /**
     * Discount amount (tax portion) - folio currency.
     */
    private BigDecimal discountTaxFc;
    /**
     * Tax exempt amount.
     * Expressed in folio currency.
     *
     * @implNote The difference between a tax free
     * item and exempt amount, is that the tax free
     * item is an item not included in the taxation law
     * as a taxable amount; while an exempt amount is
     * the one that is included in law as a taxable item
     * but for some sepcial reason, has been
     * temporarily exempted from paying the tax.
     */
    private BigDecimal exemptFc;
    /**
     * Tax free amount - folio currency.
     */
    private BigDecimal taxFreeFc;
    /**
     * ICBP amount (bag plastic tax) - folio currency.
     */
    private BigDecimal icbpFc;
    /**
     * Others amounts - folio currency.
     */
    private BigDecimal othersFc;
    /**
     * Total payable amount - folio currency.
     */
    private BigDecimal payableFc;
    /**
     * The currency in which the folio line template
     * is expressed.
     */
    private Currenci currency;

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #id }
     */
    @GeneratedValue(generator = "sq_folioline_tmp")
    @SequenceGenerator(name = "sq_folioline_tmp",
            schema = "public",
            sequenceName = "sq_folio_line_template",
            allocationSize = 1)
    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    /**
     * Public accessor - setter.
     *
     * @param id value to set into {@link #id}
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #title }
     */
    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    /**
     * Public accessor - setter.
     *
     * @param title value to set into {@link #title}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #taxId }
     */
    @Basic
    @Column(name = "tax_id")
    public String getTaxId() {
        return taxId;
    }

    /**
     * Public accessor - setter.
     *
     * @param taxId value to set into {@link #taxId}
     */
    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #briefing }
     */
    @Basic
    @Column(name = "briefing")
    public String getBriefing() {
        return briefing;
    }

    public void setBriefing(String briefing) {
        this.briefing = briefing;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #taxableFc }
     */
    @Basic
    @Column(name = "taxable_fc")
    public BigDecimal getTaxableFc() {
        return taxableFc;
    }

    /**
     * Public accessor - setter.
     *
     * @param taxableFc value to set into {@link #taxableFc}
     */
    public void setTaxableFc(BigDecimal taxableFc) {
        this.taxableFc = taxableFc;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #taxFc }
     */
    @Basic
    @Column(name = "tax_fc")
    public BigDecimal getTaxFc() {
        return taxFc;
    }

    /**
     * Public accessor - setter.
     *
     * @param taxFc value to set into {@link #taxFc}
     */
    public void setTaxFc(BigDecimal taxFc) {
        this.taxFc = taxFc;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #exportFc }
     */
    @Basic
    @Column(name = "export_fc")
    public BigDecimal getExportFc() {
        return exportFc;
    }

    /**
     * Public accessor - setter.
     *
     * @param exportFc value to set into {@link #exportFc}
     */
    public void setExportFc(BigDecimal exportFc) {
        this.exportFc = exportFc;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #discountFc }
     */
    @Basic
    @Column(name = "discount_fc")
    public BigDecimal getDiscountFc() {
        return discountFc;
    }

    /**
     * Public accessor - setter.
     *
     * @param discountFc value to set into {@link #discountFc}
     */
    public void setDiscountFc(BigDecimal discountFc) {
        this.discountFc = discountFc;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #discountTaxFc }
     */
    @Basic
    @Column(name = "discount_tax_fc")
    public BigDecimal getDiscountTaxFc() {
        return discountTaxFc;
    }

    /**
     * Public accessor - setter.
     *
     * @param discountTaxFc value to set into {@link #discountTaxFc}
     */
    public void setDiscountTaxFc(BigDecimal discountTaxFc) {
        this.discountTaxFc = discountTaxFc;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #exemptFc }
     */
    @Basic
    @Column(name = "exempt_fc")
    public BigDecimal getExemptFc() {
        return exemptFc;
    }

    /**
     * Public accessor - setter.
     *
     * @param exemptFc value to set into {@link #exemptFc}
     */
    public void setExemptFc(BigDecimal exemptFc) {
        this.exemptFc = exemptFc;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #taxFreeFc }
     */
    @Basic
    @Column(name = "tax_free_fc")
    public BigDecimal getTaxFreeFc() {
        return taxFreeFc;
    }

    /**
     * Public accessor - setter.
     *
     * @param taxFreeFc value to set into {@link #taxFreeFc}
     */
    public void setTaxFreeFc(BigDecimal taxFreeFc) {
        this.taxFreeFc = taxFreeFc;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #icbpFc }
     */
    @Basic
    @Column(name = "icbp_fc")
    public BigDecimal getIcbpFc() {
        return icbpFc;
    }

    /**
     * Public accessor - setter.
     *
     * @param icbpFc value to set into {@link #icbpFc}
     */
    public void setIcbpFc(BigDecimal icbpFc) {
        this.icbpFc = icbpFc;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #othersFc }
     */
    @Basic
    @Column(name = "others_fc")
    public BigDecimal getOthersFc() {
        return othersFc;
    }

    /**
     * Public accessor - setter.
     *
     * @param othersFc value to set into {@link #othersFc}
     */
    public void setOthersFc(BigDecimal othersFc) {
        this.othersFc = othersFc;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #payableFc }
     */
    @Basic
    @Column(name = "payable_fc")
    public BigDecimal getPayableFc() {
        return payableFc;
    }

    /**
     * Public accessor - setter.
     *
     * @param payableFc value to set into {@link #payableFc}
     */
    public void setPayableFc(BigDecimal payableFc) {
        this.payableFc = payableFc;
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #currency }
     */
    @Basic
    @Column(name = "currency")
    public Currenci getCurrency() {
        return currency;
    }

    /**
     * Public accessor - setter.
     *
     * @param currency value to set into {@link #currency}
     */
    public void setCurrency(Currenci currency) {
        this.currency = currency;
    }

}
