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
 * This represents a product or service.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(schema = "public", name = "product")
public class Product {
    /**
     * The autogenerated ID.
     */
    private final LongProperty id =
            new SimpleLongProperty(this, "id");
    /**
     * Description of the product.
     */
    private final StringProperty description =
            new SimpleStringProperty(this, "description");
    /**
     * Taxation ID (UN product ID).
     */
    private final StringProperty taxId =
            new SimpleStringProperty(this, "taxId");
    /**
     * Currency of product.
     */
    private final ObjectProperty<Currenci> priceCurrency =
            new SimpleObjectProperty<>(this, "priceCurrency");
    /**
     * Net price, in foreign currency.
     */
    private final ObjectProperty<BigDecimal> priceNetFC =
            new SimpleObjectProperty<>(this, "priceNetFC");
    /**
     * Measurement units.
     */
    private final ObjectProperty<MeasureUnit> unit =
            new SimpleObjectProperty<>(this, "unit");
    /**
     * Sale scheme associated to this product.
     */
    private final ObjectProperty<SaleScheme> saleScheme =
            new SimpleObjectProperty<>(this, "saleScheme");
    /**
     * Trash flag. If true, treat as if deleted.
     */
    private BooleanProperty trash =
            new SimpleBooleanProperty(this, "trash");

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #id}
     */
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sq_productID")
    @SequenceGenerator(schema = "public",
            sequenceName = "sq_product_id",
            name = "sq_productID",
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
     * @return value of {@link #taxId}
     */
    @Basic
    @Column(name = "tax_id", nullable = false, length = 8)
    public String getTaxId() {
        return taxId.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param taxId value to set into {@link #taxId}
     */
    public void setTaxId(String taxId) {
        this.taxId.set(taxId);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #priceCurrency}
     */
    @Basic
    @Column(name = "price_currency", nullable = false)
    @Enumerated(EnumType.STRING)
    public Currenci getPriceCurrency() {
        return priceCurrency.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param priceCurrency value to set into {@link #priceCurrency}
     */
    public void setPriceCurrency(Currenci priceCurrency) {
        this.priceCurrency.set(priceCurrency);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #priceNetFC}
     */
    @Basic
    @Column(name = "price_net_fc", nullable = false, precision = 22, scale = 10)
    public BigDecimal getPriceNetFC() {
        return priceNetFC.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param priceNetFC value to set into {@link #priceNetFC}
     */
    public void setPriceNetFC(BigDecimal priceNetFC) {
        this.priceNetFC.set(priceNetFC);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #trash}
     */
    @Basic
    @Column(name = "trash", nullable = false)
    public boolean isTrash() {
        return trash.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param trash value to set into {@link #trash}
     */
    public void setTrash(boolean trash) {
        this.trash.set(trash);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #unit}
     */
    @ManyToOne
    @JoinColumn(name = "unit", referencedColumnName = "id", nullable = false)
    public MeasureUnit getUnit() {
        return unit.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param unit value to set into {@link #unit}
     */
    public void setUnit(MeasureUnit unit) {
        this.unit.set(unit);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #saleScheme}
     */
    @ManyToOne
    @JoinColumn(name = "sale_scheme", referencedColumnName = "id", nullable = false)
    public SaleScheme getSaleScheme() {
        return saleScheme.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param saleScheme value to set into {@link #saleScheme}
     */
    public void setSaleScheme(SaleScheme saleScheme) {
        this.saleScheme.set(saleScheme);
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property of {@link #id}
     */
    public LongProperty idProperty() {
        return id;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property of {@link #description}
     */
    public StringProperty descriptionProperty() {
        return description;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property of {@link #taxId}
     */
    public StringProperty taxIdProperty() {
        return taxId;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property of {@link #priceCurrency}
     */
    public ObjectProperty<Currenci> priceCurrencyProperty() {
        return priceCurrency;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property of {@link #priceNetFC}
     */
    public ObjectProperty<BigDecimal> priceNetFCProperty() {
        return priceNetFC;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property of {@link #unit}
     */
    public ObjectProperty<MeasureUnit> unitProperty() {
        return unit;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property of {@link #saleScheme}
     */
    public ObjectProperty<SaleScheme> saleSchemeProperty() {
        return saleScheme;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property of {@link #trash}
     */
    public BooleanProperty trashProperty() {
        return trash;
    }

    @Override
    public boolean equals(Object o) {

        return o instanceof Product product &&
                Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
