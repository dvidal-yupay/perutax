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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * Journal detail's person entity.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "person_reference", schema = "public")
public class PersonReference {
    /**
     * The ID identifying the journal DT object.
     */
    private final LongProperty id =
            new SimpleLongProperty(this, "id");
    /**
     * The type of DOI of the person (this is a snapshot).
     */
    private final StringProperty doiType =
            new SimpleStringProperty(this, "doiType");
    /**
     * The DOI number of the person. (Snapshot)
     */
    private final StringProperty doiNum =
            new SimpleStringProperty(this, "doiNum");
    /**
     * The full name of the person. (Snapshot)
     */
    private final StringProperty fullName =
            new SimpleStringProperty(this, "fullName");
    /**
     * The referenced person. (Realtime data).
     */
    private final ObjectProperty<Person> reference =
            new SimpleObjectProperty<>(this, "reference");
    /**
     * The address of the person. (Snapshot)
     */
    private final StringProperty address =
            new SimpleStringProperty(this, "address");

    /**
     * Empty constructor for entities.
     */
    public PersonReference() {
    }

    /**
     * Constructor to copy person data.
     *
     * @param person the person to asign.
     */
    public PersonReference(@NotNull Person person) {
        setReference(person);
        setFullName(person.getFullName());
        setDoiNum(person.getDoiNum());
        setDoiType(person.getDoiType().getId());
        setAddress(person.getAddress());
    }

    /**
     * Craetes a function to format this entity in a string.
     *
     * @return the function.
     */
    @Contract("->new")
    public static @NotNull Function<PersonReference, String> formatter() {
        return x -> x == null ? ""
                : x.getDoiType() + "-" +
                x.getDoiNum() + "-" +
                x.getFullName();
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #address}.get();
     */
    @Basic
    @Column(name = "address", length = -1)
    public final String getAddress() {
        return address.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param address value to assign into {@link #address}.
     */
    public final void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #address}.
     */
    public final StringProperty addressProperty() {
        return address;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "personref_id")
    @SequenceGenerator(name = "personref_id",
            sequenceName = "sq_person_reference_id",
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
     * @return value of {@link #doiType}
     */
    @Basic
    @Column(name = "doi_type", nullable = false, length = -1)
    public String getDoiType() {
        return doiType.get();
    }

    /**
     * Accessor - setter.
     *
     * @param doiType value to set on {@link #doiType}
     */
    public void setDoiType(String doiType) {
        this.doiType.set(doiType);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #doiNum}
     */
    @Basic
    @Column(name = "doi_num", nullable = false, length = 15)
    public String getDoiNum() {
        return doiNum.get();
    }

    /**
     * Accessor - setter.
     *
     * @param doiNum value to set on {@link #doiNum}
     */
    public void setDoiNum(String doiNum) {
        this.doiNum.set(doiNum);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #fullName}
     */
    @Basic
    @Column(name = "full_name", nullable = false, length = -1)
    public String getFullName() {
        return fullName.get();
    }

    /**
     * Accessor - setter.
     *
     * @param fullName value to set on {@link #fullName}
     */
    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #reference}
     */
    @ManyToOne
    @JoinColumn(name = "person", referencedColumnName = "id", nullable = false)
    public Person getReference() {
        return reference.get();
    }

    /**
     * Accessor - setter.
     *
     * @param personByPerson value to set on {@link #reference}
     */
    public void setReference(Person personByPerson) {
        this.reference.set(personByPerson);
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
     * @return the property {@link  #doiType}
     */
    public StringProperty doiTypeProperty() {
        return doiType;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #doiNum}
     */
    public StringProperty doiNumProperty() {
        return doiNum;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #fullName}
     */
    public StringProperty fullNameProperty() {
        return fullName;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #reference}
     */
    public ObjectProperty<Person> referenceProperty() {
        return reference;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PersonReference that && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
