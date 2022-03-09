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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The country entity.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "country", schema = "public")
public class Country {
    /**
     * Country ID (which is alfa2 ISO code).
     */
    private final StringProperty id =
            new SimpleStringProperty(this, "id");
    /**
     * Country name.
     */
    private final StringProperty name =
            new SimpleStringProperty(this, "name");
    /**
     * Taxation ID of 3 characters.
     */
    private final StringProperty taxID3 =
            new SimpleStringProperty(this, "taxID3");
    /**
     * Taxation ID of 4 characters.
     */
    private final StringProperty taxID4 =
            new SimpleStringProperty(this, "taxID4");
    /**
     * The trash flag. If true, the entity should be disregarded.
     */
    private final BooleanProperty trash =
            new SimpleBooleanProperty(this, "trash");

    /**
     * Default constructor.
     */
    public Country() {
    }

    /**
     * Constructor that copies all values from another instance.
     * The result is such an instance:
     * <pre>{@code
     * Country x = new Country(y);
     * x == y; //Is false
     * x.equals(y); //Is true
     * y.equals(x); //Is true
     * x.get****().equals(y.get****()); //Is true
     *     }  </pre>
     *
     * @param another instance whose values will be copied.
     */
    public Country(@NotNull Country another) {
        setId(another.getId());
        setName(another.getName());
        setTaxID3(another.getTaxID3());
        setTaxID4(another.getTaxID4());
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
     * @return value of {@link #name}
     */
    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name.get();
    }

    /**
     * Accessor - setter.
     *
     * @param name value to set on {@link #name}
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #taxID3}
     */
    @Basic
    @Column(name = "tax_id3", length = 3)
    public String getTaxID3() {
        return taxID3.get();
    }

    /**
     * Accessor - setter.
     *
     * @param taxID3 value to set on {@link #taxID3}
     */
    public void setTaxID3(String taxID3) {
        this.taxID3.set(taxID3);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #taxID4}
     */
    @Basic
    @Column(name = "tax_id4", length = 4)
    public String getTaxID4() {
        return taxID4.get();
    }

    /**
     * Accessor - setter.
     *
     * @param taxID4 value to set on {@link #taxID4}
     */
    public void setTaxID4(String taxID4) {
        this.taxID4.set(taxID4);
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
     * FX Accessor - property.
     *
     * @return property {@link  #id}
     */
    public StringProperty idProperty() {
        return id;
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link  #name}
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link  #taxID3}
     */
    public StringProperty taxID3Property() {
        return taxID3;
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link  #taxID4}
     */
    public StringProperty taxID4Property() {
        return taxID4;
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link  #trash}
     */
    public BooleanProperty trashProperty() {
        return trash;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Country country && Objects.equals(getId(), country.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return getId() + " - " + getName();
    }
}
