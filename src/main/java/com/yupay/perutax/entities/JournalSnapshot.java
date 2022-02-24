package com.yupay.perutax.entities;

import jakarta.persistence.*;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class is not an entity by itself, but a snapshot
 * of a Journal entry entity. Due performance reasons, when
 * creating a table view for a Journal entry, we should use this
 * view (journal_snap) as a source of data. This pseudo-entity has
 * a reduced size with most important fields values.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "journal_snap")
public class JournalSnapshot {
    /**
     * The ID of journal entry.
     */
    private final StringProperty id =
            new SimpleStringProperty(this, "id");
    /**
     * Taxable period.
     */
    private final StringProperty taxPeriod =
            new SimpleStringProperty(this, "taxPeriod");
    /**
     * Correlative of entry.
     */
    private final StringProperty correlative =
            new SimpleStringProperty(this, "correlative");
    /**
     * Subdiary ID.
     */
    private final StringProperty subId =
            new SimpleStringProperty(this, "subId");
    /**
     * Subdiary title.
     */
    private final StringProperty subTitle =
            new SimpleStringProperty(this, "subTitle");
    /**
     * General Briefing text.
     */
    private final StringProperty briefing =
            new SimpleStringProperty(this, "briefing");
    /**
     * The currency of transaction.
     */
    private final ObjectProperty<Currenci> currency =
            new SimpleObjectProperty<>(this, "currency");
    /**
     * Taxable date.
     */
    private final ObjectProperty<LocalDate> dateTax =
            new SimpleObjectProperty<>(this, "dateTax");
    /**
     * Document (folio issuance) date.
     */
    private final ObjectProperty<LocalDate> dateDoc =
            new SimpleObjectProperty<>(this, "dateDoc");
    /**
     * Due date.
     */
    private final ObjectProperty<LocalDate> dateDue =
            new SimpleObjectProperty<>(this, "dateDue");
    /**
     * Date at which this entry was created.
     */
    private final ObjectProperty<LocalDateTime> createdAt =
            new SimpleObjectProperty<>(this, "createdAt");
    /**
     * Flag for presence of revertedBy entry. If null,
     * will return false, otherwise will return true.
     */
    private final BooleanProperty reverted =
            new SimpleBooleanProperty(this, "reverted");

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @Id
    @Basic
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
     * @return value of {@link #taxPeriod}
     */
    @Basic
    @Column(name = "taxPeriod", nullable = false, length = 6)
    public String getTaxPeriod() {
        return taxPeriod.get();
    }

    /**
     * Accessor - setter.
     *
     * @param taxPeriod value to set on {@link #taxPeriod}
     */
    public void setTaxPeriod(String taxPeriod) {
        this.taxPeriod.set(taxPeriod);
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
     * @return value of {@link #subId}
     */
    @Basic
    @Column(name = "subID", nullable = false, length = 2)
    public String getSubId() {
        return subId.get();
    }

    /**
     * Accessor - setter.
     *
     * @param subId value to set on {@link #subId}
     */
    public void setSubId(String subId) {
        this.subId.set(subId);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #subTitle}
     */
    @Basic
    @Column(name = "subTitle", nullable = false)
    public String getSubTitle() {
        return subTitle.get();
    }

    /**
     * Accessor - setter.
     *
     * @param subTitle value to set on {@link #subTitle}
     */
    public void setSubTitle(String subTitle) {
        this.subTitle.set(subTitle);
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
     * @return value of {@link #reverted}
     */
    @Basic
    @Column(name = "reverted", nullable = false)
    public boolean isReverted() {
        return reverted.get();
    }

    /**
     * Accessor - setter.
     *
     * @param reverted value to set on {@link #reverted}
     */
    public void setReverted(boolean reverted) {
        this.reverted.set(reverted);
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
     * @return the property {@link  #taxPeriod}
     */
    public StringProperty taxPeriodProperty() {
        return taxPeriod;
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
     * @return the property {@link  #subId}
     */
    public StringProperty subIdProperty() {
        return subId;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #subTitle}
     */
    public StringProperty subTitleProperty() {
        return subTitle;
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
     * @return the property {@link  #currency}
     */
    public ObjectProperty<Currenci> currencyProperty() {
        return currency;
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
     * @return the property {@link  #createdAt}
     */
    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #reverted}
     */
    public BooleanProperty revertedProperty() {
        return reverted;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof JournalSnapshot that && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
