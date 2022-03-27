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
 * This entity represents a line in a sale transaction record.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "sale_line", schema = "public")
public class SaleLine {
    /**
     * The unitary net price (before taxes).
     */
    private final ObjectProperty<BigDecimal> unitPriceFC =
            new SimpleObjectProperty<>(this, "unitPriceFC", new BigDecimal("0.00000000"));
    /**
     * The unitary tax amount.
     */
    private final ObjectProperty<BigDecimal> unitTaxFC =
            new SimpleObjectProperty<>(this, "unitTaxFC", new BigDecimal("0.00000000"));
    /**
     * Unitary payable amount. This is the unitaryPrice + unitaryTax.
     */
    private final ObjectProperty<BigDecimal> unitPayableFC =
            new SimpleObjectProperty<>(this, "unitPayableFC", new BigDecimal("0.00000000"));
    /**
     * Quantity of items.
     */
    private final ObjectProperty<BigDecimal> quantity =
            new SimpleObjectProperty<>(this, "quantity", new BigDecimal("0.00000000"));
    /**
     * Net price of this line. This is the unitPrice multiplied by quantity.
     */
    private final ObjectProperty<BigDecimal> linePriceFC =
            new SimpleObjectProperty<>(this, "linePriceFC", new BigDecimal("0.00"));
    /**
     * Tax amount of this line. This should be equals to the unitary
     * tax amount multiplied by the quantity.
     */
    private final ObjectProperty<BigDecimal> lineTaxFC =
            new SimpleObjectProperty<>(this, "lineTaxFC", new BigDecimal("0.00"));
    /**
     * Total (payable) amount of this line. This is linePriceFC + lineTaxFC.
     * Also the assertion unitPayableFC*quantity == linePayableFC should be true.
     */
    private final ObjectProperty<BigDecimal> linePayableFC =
            new SimpleObjectProperty<>(this, "linePayableFC", new BigDecimal("0.00"));
    /**
     * Tax rate applied in this line.
     */
    private final ObjectProperty<BigDecimal> taxRate =
            new SimpleObjectProperty<>(this, "taxRate", new BigDecimal("0.0000"));
    /**
     * The autogenerated ID of the line.
     */
    private final LongProperty id =
            new SimpleLongProperty(this, "id");
    /**
     * Sale (parent) of this line.
     */
    private final ObjectProperty<Sale> sale =
            new SimpleObjectProperty<>(this, "sale");
    /**
     * Line number (order of appareance).
     */
    private final IntegerProperty line =
            new SimpleIntegerProperty(this, "line");
    /**
     * The sale total class to which the net price of the line is linked.
     */
    private final ObjectProperty<SaleTotalClass> priceClass =
            new SimpleObjectProperty<>(this, "priceClass");
    /**
     * The Sale total class to which the tax portion of the price is linked.
     */
    private final ObjectProperty<SaleTotalClass> taxClass =
            new SimpleObjectProperty<>(this, "taxClass");
    /**
     * Item description.
     */
    private final StringProperty description =
            new SimpleStringProperty(this, "description");
    /**
     * Dimension 1 of the sold item.
     */
    private final StringProperty dim1 =
            new SimpleStringProperty(this, "dim1");
    /**
     * Dimension 2 of the sold item.
     */
    private final StringProperty dim2 =
            new SimpleStringProperty(this, "dim2");
    /**
     * Dimension 3 of the sold item.
     */
    private final StringProperty dim3 =
            new SimpleStringProperty(this, "dim3");
    /**
     * Dimension 4 of the sold item.
     */
    private final StringProperty dim4 =
            new SimpleStringProperty(this, "dim4");
    /**
     * The product sold in this line.
     */
    private final ObjectProperty<Product> product =
            new SimpleObjectProperty<>(this, "product");
    /**
     * The measurement unit.
     */
    private final ObjectProperty<MeasureUnit> mUnit =
            new SimpleObjectProperty<>(this, "mUnit");

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #id}
     */
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sq_salelineID")
    @SequenceGenerator(name = "sq_salelineID",
            schema = "public",
            sequenceName = "sq_sale_line_id",
            allocationSize = 1)
    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param id value to set into {@link #id}
     */
    public void setId(long id) {
        this.id.set(id);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #sale}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale")
    public Sale getSale() {
        return sale.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param sale value to set into {@link #sale}
     */
    public void setSale(Sale sale) {
        this.sale.set(sale);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #line}
     */
    @Basic
    @Column(name = "line", nullable = false)
    public short getLine() {
        return (short) line.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param line value to set into {@link #line}
     */
    public void setLine(short line) {
        this.line.set(line);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #unitPriceFC}
     */
    @Basic
    @Column(name = "unit_price_fc", nullable = false, precision = 20, scale = 8)
    public BigDecimal getUnitPriceFC() {
        return unitPriceFC.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param unitPriceFC value to set into {@link #unitPriceFC}
     */
    public void setUnitPriceFC(BigDecimal unitPriceFC) {
        this.unitPriceFC.set(unitPriceFC);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #unitTaxFC}
     */
    @Basic
    @Column(name = "unit_tax_fc", nullable = false, precision = 20, scale = 8)
    public BigDecimal getUnitTaxFC() {
        return unitTaxFC.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param unitTaxFC value to set into {@link #unitTaxFC}
     */
    public void setUnitTaxFC(BigDecimal unitTaxFC) {
        this.unitTaxFC.set(unitTaxFC);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #unitPayableFC}
     */
    @Basic
    @Column(name = "unit_payable_fc", nullable = false, precision = 20, scale = 8)
    public BigDecimal getUnitPayableFC() {
        return unitPayableFC.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param unitPayableFC value to set into {@link #unitPayableFC}
     */
    public void setUnitPayableFC(BigDecimal unitPayableFC) {
        this.unitPayableFC.set(unitPayableFC);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #quantity}
     */
    @Basic
    @Column(name = "quantity", nullable = false, precision = 20, scale = 8)
    public BigDecimal getQuantity() {
        return quantity.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param quantity value to set into {@link #quantity}
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity.set(quantity);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #linePriceFC}
     */
    @Basic
    @Column(name = "line_price_fc", nullable = false, precision = 14, scale = 2)
    public BigDecimal getLinePriceFC() {
        return linePriceFC.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param linePriceFC value to set into {@link #linePriceFC}
     */
    public void setLinePriceFC(BigDecimal linePriceFC) {
        this.linePriceFC.set(linePriceFC);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #lineTaxFC}
     */
    @Basic
    @Column(name = "line_tax_fc", nullable = false, precision = 14, scale = 2)
    public BigDecimal getLineTaxFC() {
        return lineTaxFC.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param lineTaxFC value to set into {@link #lineTaxFC}
     */
    public void setLineTaxFC(BigDecimal lineTaxFC) {
        this.lineTaxFC.set(lineTaxFC);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #linePayableFC}
     */
    @Basic
    @Column(name = "line_payable_fc", nullable = false, precision = 14, scale = 2)
    public BigDecimal getLinePayableFC() {
        return linePayableFC.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param linePayableFC value to set into {@link #linePayableFC}
     */
    public void setLinePayableFC(BigDecimal linePayableFC) {
        this.linePayableFC.set(linePayableFC);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #priceClass}
     */
    @Basic
    @Column(name = "price_class")
    @Enumerated(EnumType.STRING)
    public SaleTotalClass getPriceClass() {
        return priceClass.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param priceClass value to set into {@link #priceClass}
     */
    public void setPriceClass(SaleTotalClass priceClass) {
        this.priceClass.set(priceClass);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #taxClass}
     */
    @Basic
    @Column(name = "tax_class")
    @Enumerated(EnumType.STRING)
    public SaleTotalClass getTaxClass() {
        return taxClass.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param taxClass value to set into {@link #taxClass}
     */
    public void setTaxClass(SaleTotalClass taxClass) {
        this.taxClass.set(taxClass);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #description}
     */
    @Basic
    @Column(name = "description", nullable = false, length = 250)
    public String getDescription() {
        return description.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param description value to set into {@link #description}
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #dim1}
     */
    @Basic
    @Column(name = "dim1", length = 250)
    public String getDim1() {
        return dim1.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param dim1 value to set into {@link #dim1}
     */
    public void setDim1(String dim1) {
        this.dim1.set(dim1);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #dim2}
     */
    @Basic
    @Column(name = "dim2", length = 250)
    public String getDim2() {
        return dim2.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param dim2 value to set into {@link #dim2}
     */
    public void setDim2(String dim2) {
        this.dim2.set(dim2);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #dim3}
     */
    @Basic
    @Column(name = "dim3", length = 250)
    public String getDim3() {
        return dim3.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param dim3 value to set into {@link #dim3}
     */
    public void setDim3(String dim3) {
        this.dim3.set(dim3);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #dim4}
     */
    @Basic
    @Column(name = "dim4", length = 250)
    public String getDim4() {
        return dim4.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param dim4 value to set into {@link #dim4}
     */
    public void setDim4(String dim4) {
        this.dim4.set(dim4);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #taxRate}
     */
    @Basic
    @Column(name = "tax_rate", nullable = false, precision = 4)
    public BigDecimal getTaxRate() {
        return taxRate.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param taxRate value to set into {@link #taxRate}
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate.set(taxRate);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #product}
     */
    @ManyToOne
    @JoinColumn(name = "product", referencedColumnName = "id")
    public Product getProduct() {
        return product.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param product value to set into {@link #product}
     */
    public void setProduct(Product product) {
        this.product.set(product);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #mUnit}
     */
    @ManyToOne
    @JoinColumn(name = "m_unit", referencedColumnName = "id", nullable = false)
    public MeasureUnit getmUnit() {
        return mUnit.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param mUnit value to set into {@link #mUnit}
     */
    public void setmUnit(MeasureUnit mUnit) {
        this.mUnit.set(mUnit);
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #unitPriceFC}
     */
    public ObjectProperty<BigDecimal> unitPriceFCProperty() {
        return unitPriceFC;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #unitTaxFC}
     */
    public ObjectProperty<BigDecimal> unitTaxFCProperty() {
        return unitTaxFC;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #unitPayableFC}
     */
    public ObjectProperty<BigDecimal> unitPayableFCProperty() {
        return unitPayableFC;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #quantity}
     */
    public ObjectProperty<BigDecimal> quantityProperty() {
        return quantity;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #linePriceFC}
     */
    public ObjectProperty<BigDecimal> linePriceFCProperty() {
        return linePriceFC;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #lineTaxFC}
     */
    public ObjectProperty<BigDecimal> lineTaxFCProperty() {
        return lineTaxFC;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #linePayableFC}
     */
    public ObjectProperty<BigDecimal> linePayableFCProperty() {
        return linePayableFC;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #taxRate}
     */
    public ObjectProperty<BigDecimal> taxRateProperty() {
        return taxRate;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #id}
     */
    public LongProperty idProperty() {
        return id;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #sale}
     */
    public ObjectProperty<Sale> saleProperty() {
        return sale;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #line}
     */
    public IntegerProperty lineProperty() {
        return line;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #priceClass}
     */
    public ObjectProperty<SaleTotalClass> priceClassProperty() {
        return priceClass;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #taxClass}
     */
    public ObjectProperty<SaleTotalClass> taxClassProperty() {
        return taxClass;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #description}
     */
    public StringProperty descriptionProperty() {
        return description;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #dim1}
     */
    public StringProperty dim1Property() {
        return dim1;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #dim2}
     */
    public StringProperty dim2Property() {
        return dim2;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #dim3}
     */
    public StringProperty dim3Property() {
        return dim3;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #dim4}
     */
    public StringProperty dim4Property() {
        return dim4;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #product}
     */
    public ObjectProperty<Product> productProperty() {
        return product;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link #mUnit}
     */
    public ObjectProperty<MeasureUnit> mUnitProperty() {
        return mUnit;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SaleLine saleLine &&
                Objects.equals(getId(), saleLine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
