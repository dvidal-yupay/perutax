package com.yupay.perutax.entities;

import jakarta.persistence.*;
import javafx.beans.property.*;

import java.util.Objects;

/**
 * The journal detail attached folio information.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "journal_dt_folio")

public class JournalDtFolio {
    /**
     * The object ID from linked detail.
     */
    private final LongProperty id =
            new SimpleLongProperty(this, "id");
    /**
     * The folio serie.
     */
    private final StringProperty folioSerie =
            new SimpleStringProperty(this, "folioSerie");
    /**
     * The folio number.
     */
    private final StringProperty folioNum =
            new SimpleStringProperty(this, "folioNum");
    /**
     * The folio type.
     */
    private final ObjectProperty<TypeFolio> folioType =
            new SimpleObjectProperty<>(this, "folioType");
    /**
     * The owner journal detail line. It's not observable
     * since it won't be directly used. This is only for
     * JPA mapping purposes.
     */
    private JournalDt owner;

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
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
     * @return value of {@link #folioSerie}
     */
    @Basic
    @Column(name = "folioSerie", length = 15)
    public String getFolioSerie() {
        return folioSerie.get();
    }

    /**
     * Accessor - setter.
     *
     * @param folioSerie value to set on {@link #folioSerie}
     */
    public void setFolioSerie(String folioSerie) {
        this.folioSerie.set(folioSerie);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #folioNum}
     */
    @Basic
    @Column(name = "folioNum", nullable = false, length = 15)
    public String getFolioNum() {
        return folioNum.get();
    }

    /**
     * Accessor - setter.
     *
     * @param folioNum value to set on {@link #folioNum}
     */
    public void setFolioNum(String folioNum) {
        this.folioNum.set(folioNum);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #folioType}
     */
    @ManyToOne
    @JoinColumn(name = "folioType", referencedColumnName = "id", nullable = false)
    public TypeFolio getFolioType() {
        return folioType.get();
    }

    /**
     * Accessor - setter.
     *
     * @param tfolioByFolioType value to set on {@link #folioType}
     */
    public void setFolioType(TypeFolio tfolioByFolioType) {
        this.folioType.set(tfolioByFolioType);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #owner}
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    public JournalDt getOwner() {
        return owner;
    }

    /**
     * Accessor - setter.
     *
     * @param owner value to set on {@link #owner}
     */
    public void setOwner(JournalDt owner) {
        this.owner = owner;
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
     * @return the property {@link  #folioSerie}
     */
    public StringProperty folioSerieProperty() {
        return folioSerie;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #folioNum}
     */
    public StringProperty folioNumProperty() {
        return folioNum;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #folioType}
     */
    public ObjectProperty<TypeFolio> folioTypeProperty() {
        return folioType;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof JournalDtFolio that && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
