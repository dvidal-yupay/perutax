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

import java.util.Objects;

/**
 * The journal detail attached folio information.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "journal_dt_folio")
@PrimaryKeyJoinColumn(name = "id")
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
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "journaldt_folio_id")
    @SequenceGenerator(name = "journaldt_folio_id",
            sequenceName = "sq_journal_dt_folio_id",
            schema = "public",
            allocationSize = 1)
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
    @Column(name = "folio_serie", length = 15)
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
    @Column(name = "folio_num", nullable = false, length = 15)
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
    @JoinColumn(name = "folio_type", referencedColumnName = "id", nullable = false)
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
