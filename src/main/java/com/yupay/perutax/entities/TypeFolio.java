package com.yupay.perutax.entities;

import jakarta.persistence.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
/**
 * The Type of Folio entity.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "tfolio")
public class TypeFolio {
    /**
     * The unique ID of the entity. This is assigned by user
     * based on SUNAT - PLE specification.
     */
    private final StringProperty id =
            new SimpleStringProperty(this, "id");
    /**
     * The ID for SUNAT PDT-PLAME specification. Only entities
     * with a non value in this field may be used in the PLAME
     * module operations.
     */
    private final StringProperty plameId =
            new SimpleStringProperty(this, "plameId");
    /**
     * The unique title of the folio type.
     */
    private final StringProperty title =
            new SimpleStringProperty(this, "title");
    /**
     * The regular expression to validate series number on
     * folios of this type of folio.
     */
    private final StringProperty regexSerie =
            new SimpleStringProperty(this, "regexSerie");
    /**
     * The regular expression to validate folios numbers on folios
     * of this type of folio.
     */
    private final StringProperty regexNumber =
            new SimpleStringProperty(this, "regexNumber");
    /**
     * Context: this folio type may be used on purchases operations.
     */
    private final BooleanProperty ctxtPurchase =
            new SimpleBooleanProperty(this, "ctxtPurchase");
    /**
     * Context: this folio type may be used on sales operations.
     */
    private final BooleanProperty ctxtSale =
            new SimpleBooleanProperty(this, "ctxtSale");
    /**
     * Context: this folio type may be used on foreign parties operations.
     */
    private final BooleanProperty ctxtForeign =
            new SimpleBooleanProperty(this, "ctxtForeign");
    /**
     * Context: this folio type has taxation credit.
     */
    private final BooleanProperty ctxtTaxCredit =
            new SimpleBooleanProperty(this, "ctxtTaxCredit");
    /**
     * Trash flag: since this entity should not be physically removed,
     * if this flag is true the entity will be ignored as if deleted.
     */
    private final BooleanProperty trash =
            new SimpleBooleanProperty(this, "trash");

    /**
     * Default empty constructor.
     */
    public TypeFolio() {
    }

    /**
     * Copy constructor, takes another instance and
     * copies each field value to this new instance.
     *
     * @param another the source instance.
     */
    public TypeFolio(@NotNull TypeFolio another) {
        setCtxtForeign(another.isCtxtForeign());
        setCtxtPurchase(another.isCtxtPurchase());
        setCtxtSale(another.isCtxtSale());
        setCtxtTaxCredit(another.isCtxtTaxCredit());
        setId(another.getId());
        setPlameId(another.getPlameId());
        setRegexNumber(another.getRegexNumber());
        setRegexSerie(another.getRegexSerie());
        setTitle(another.getTitle());
        setTrash(another.isTrash());
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @Id
    @Column(name = "id", nullable = false, length = 2)
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
     * @return value of {@link #plameId}
     */
    @Basic
    @Column(name = "plameID", length = 1)
    public String getPlameId() {
        return plameId.get();
    }

    /**
     * Accessor - setter.
     *
     * @param plameId value to set on {@link #plameId}
     */
    public void setPlameId(String plameId) {
        this.plameId.set(plameId);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #title}
     */
    @Basic
    @Column(name = "title", nullable = false, length = 10)
    public String getTitle() {
        return title.get();
    }

    /**
     * Accessor - setter.
     *
     * @param title value to set on {@link #title}
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #regexSerie}
     */
    @Basic
    @Column(name = "regexSerie", nullable = false)
    public @RegExp
    String getRegexSerie() {
        return regexSerie.get();
    }

    /**
     * Accessor - setter.
     *
     * @param regexSerie value to set on {@link #regexSerie}
     */
    public void setRegexSerie(@RegExp String regexSerie) {
        this.regexSerie.set(regexSerie);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #regexNumber}
     */
    @Basic
    @Column(name = "regexNumber", nullable = false)
    public @RegExp
    String getRegexNumber() {
        return regexNumber.get();
    }

    /**
     * Accessor - setter.
     *
     * @param regexNumber value to set on {@link #regexNumber}
     */
    public void setRegexNumber(@RegExp String regexNumber) {
        this.regexNumber.set(regexNumber);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #ctxtPurchase}
     */
    @Basic
    @Column(name = "ctxtPurchase", nullable = false)
    public boolean isCtxtPurchase() {
        return ctxtPurchase.get();
    }

    /**
     * Accessor - setter.
     *
     * @param ctxtPurchase value to set on {@link #ctxtPurchase}
     */
    public void setCtxtPurchase(boolean ctxtPurchase) {
        this.ctxtPurchase.set(ctxtPurchase);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #ctxtSale}
     */
    @Basic
    @Column(name = "ctxtSale", nullable = false)
    public boolean isCtxtSale() {
        return ctxtSale.get();
    }

    /**
     * Accessor - setter.
     *
     * @param ctxtSale value to set on {@link #ctxtSale}
     */
    public void setCtxtSale(boolean ctxtSale) {
        this.ctxtSale.set(ctxtSale);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #ctxtForeign}
     */
    @Basic
    @Column(name = "ctxtForeign", nullable = false)
    public boolean isCtxtForeign() {
        return ctxtForeign.get();
    }

    /**
     * Accessor - setter.
     *
     * @param ctxtForeign value to set on {@link #ctxtForeign}
     */
    public void setCtxtForeign(boolean ctxtForeign) {
        this.ctxtForeign.set(ctxtForeign);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #ctxtTaxCredit}
     */
    @Basic
    @Column(name = "ctxtTaxCredit", nullable = false)
    public boolean isCtxtTaxCredit() {
        return ctxtTaxCredit.get();
    }

    /**
     * Accessor - setter.
     *
     * @param ctxtTaxCredit value to set on {@link #ctxtTaxCredit}
     */
    public void setCtxtTaxCredit(boolean ctxtTaxCredit) {
        this.ctxtTaxCredit.set(ctxtTaxCredit);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #trash}
     */
    @Basic
    @Column(name = "trash", nullable = false)
    public boolean isTrash() {
        return trash.get();
    }

    /**
     * Accessor - setter.
     *
     * @param trash value to set on {@link #trash}
     */
    public void setTrash(boolean trash) {
        this.trash.set(trash);
    }

    /**
     * JavaFX Accessor - Property.
     *
     * @return the property of {@link #id}
     */
    public StringProperty idProperty() {
        return id;
    }

    /**
     * JavaFX Accessor - Property.
     *
     * @return the property of {@link #plameId}
     */
    public StringProperty plameIdProperty() {
        return plameId;
    }

    /**
     * JavaFX Accessor - Property.
     *
     * @return the property of {@link #title}
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * JavaFX Accessor - Property.
     *
     * @return the property of {@link #regexSerie}
     */
    public StringProperty regexSerieProperty() {
        return regexSerie;
    }

    /**
     * JavaFX Accessor - Property.
     *
     * @return the property of {@link #regexNumber}
     */
    public StringProperty regexNumberProperty() {
        return regexNumber;
    }

    /**
     * JavaFX Accessor - Property.
     *
     * @return the property of {@link #ctxtPurchase}
     */
    public BooleanProperty ctxtPurchaseProperty() {
        return ctxtPurchase;
    }

    /**
     * JavaFX Accessor - Property.
     *
     * @return the property of {@link #ctxtSale}
     */
    public BooleanProperty ctxtSaleProperty() {
        return ctxtSale;
    }

    /**
     * JavaFX Accessor - Property.
     *
     * @return the property of {@link #ctxtForeign}
     */
    public BooleanProperty ctxtForeignProperty() {
        return ctxtForeign;
    }

    /**
     * JavaFX Accessor - Property.
     *
     * @return the property of {@link #ctxtTaxCredit}
     */
    public BooleanProperty ctxtTaxCreditProperty() {
        return ctxtTaxCredit;
    }

    /**
     * JavaFX Accessor - Property.
     *
     * @return the property of {@link #trash}
     */
    public BooleanProperty trashProperty() {
        return trash;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TypeFolio typeFolio && Objects.equals(getId(), typeFolio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return getId() + " - " + getTitle();
    }
}
