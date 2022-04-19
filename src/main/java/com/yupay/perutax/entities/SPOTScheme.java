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
import org.eclipse.persistence.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The SPOT scheme is an entity designed to hold
 * information of the SPOT/detraccion system as
 * defined by SUNAT. This is required for Detracciones
 * batch generator feature.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(schema = "public", name = "spot_scheme")
public class SPOTScheme {
    /**
     * The autogenerated ID.
     */
    private final StringProperty id =
            new SimpleStringProperty(this, "id");
    /**
     * The title of the scheme.
     */
    private final StringProperty title =
            new SimpleStringProperty(this, "title");
    /**
     * The tax ID of type.
     */
    private final StringProperty typeID =
            new SimpleStringProperty(this, "typeID");
    /**
     * The tax ID of item.
     */
    private final StringProperty itemID =
            new SimpleStringProperty(this, "itemID");
    /**
     * The percentage rate. Ie: 12% is 0.12
     */
    private final ObjectProperty<BigDecimal> rate =
            new SimpleObjectProperty<>(this, "rate", new BigDecimal("0.0000"));
    /**
     * Trash flag. If true, should be treated as deleted.
     */
    private final BooleanProperty trash =
            new SimpleBooleanProperty(this, "trash");

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #id}.get();
     */
    @GeneratedValue(generator = "uuid_spotscheme")
    @UuidGenerator(name = "uuid_spotscheme")
    @Id
    @Column(name = "id", nullable = false, length = 36)
    public final String getId() {
        return id.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param id value to assign into {@link #id}.
     */
    public final void setId(String id) {
        this.id.set(id);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #id}.
     */
    public final StringProperty idProperty() {
        return id;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #title}.get();
     */
    @Basic
    @Column(name = "title", length = -1, nullable = false)
    public final String getTitle() {
        return title.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param title value to assign into {@link #title}.
     */
    public final void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #title}.
     */
    public final StringProperty titleProperty() {
        return title;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #typeID}.get();
     */
    @Basic
    @Column(name = "typeID", length = 2, nullable = false)
    public final String getTypeID() {
        return typeID.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param typeID value to assign into {@link #typeID}.
     */
    public final void setTypeID(String typeID) {
        this.typeID.set(typeID);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #typeID}.
     */
    public final StringProperty typeIDProperty() {
        return typeID;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #itemID}.get();
     */
    @Basic
    @Column(name = "itemID", length = 3, nullable = false)
    public final String getItemID() {
        return itemID.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param itemID value to assign into {@link #itemID}.
     */
    public final void setItemID(String itemID) {
        this.itemID.set(itemID);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #itemID}.
     */
    public final StringProperty itemIDProperty() {
        return itemID;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #rate}.get();
     */
    @Basic
    @Column(name = "rate", nullable = false, precision = 5, scale = 4)
    public final BigDecimal getRate() {
        return rate.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param rate value to assign into {@link #rate}.
     */
    public final void setRate(BigDecimal rate) {
        this.rate.set(rate);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #rate}.
     */
    public final ObjectProperty<BigDecimal> rateProperty() {
        return rate;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #trash}.get();
     */
    @Basic
    @Column(name = "trash", nullable = false)
    public final boolean isTrash() {
        return trash.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param trash value to assign into {@link #trash}.
     */
    public final void setTrash(boolean trash) {
        this.trash.set(trash);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #trash}.
     */
    public final BooleanProperty trashProperty() {
        return trash;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SPOTScheme that &&
                Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}