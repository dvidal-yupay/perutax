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
import org.jetbrains.annotations.NotNull;

/**
 * This entity is the party of a folio, maybe a
 * supplier or customer party. This party is necessary
 * because a person information may change along time,
 * but the information shown in the folio should stay
 * the same once issued, despite any underlying changes.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "folio_party", schema = "public")
public class FolioParty {

    /**
     * Autogenerated ID.
     */
    private final LongProperty id =
            new SimpleLongProperty(this, "id");
    /**
     * The DOI type ID of the person
     * at the moment of creation.
     */
    private final StringProperty doiType =
            new SimpleStringProperty(this, "doiType");
    /**
     * The DOI number of the person
     * at the moment of creation.
     */
    private final StringProperty doiNum =
            new SimpleStringProperty(this, "doiNum");
    /**
     * The full name of the person at the moment
     * of creation.
     */
    private final StringProperty fullName =
            new SimpleStringProperty(this, "fullName");
    /**
     * The address of the person at the moment
     * of creation.
     */
    private final StringProperty address =
            new SimpleStringProperty(this, "address");
    /**
     * The underlying person object that
     * is referenced by this object.
     */
    private final ObjectProperty<Person> person =
            new SimpleObjectProperty<>(this, "person");

    /**
     * Default empty constructor.
     */
    public FolioParty() {
    }

    /**
     * Constructor to build a new instance from an person
     * party information.
     *
     * @param party the person party.
     */
    public FolioParty(@NotNull Person party) {
        setPerson(party);
        if (party.getDoiType() != null)
            setDoiType(party.getDoiType().getId());
        setDoiNum(party.getDoiNum());
        setFullName(party.getFullName());
        setAddress(party.getAddress());
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #id}
     */
    @GeneratedValue(generator = "sq_folioparty")

    @SequenceGenerator(name = "sq_folioparty",
            schema = "public",
            sequenceName = "sq_folio_party_id",
            allocationSize = 1)
    @Id
    @Column(name = "id")
    public long getId() {
        return id.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param id the value to set into {@link #id}
     */
    public void setId(long id) {
        this.id.set(id);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #doiType}
     */
    @Basic
    @Column(name = "doi_type")
    public String getDoiType() {
        return doiType.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param doiType the value to set into {@link #doiType}
     */
    public void setDoiType(String doiType) {
        this.doiType.set(doiType);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #doiNum}
     */
    @Basic
    @Column(name = "doi_num")
    public String getDoiNum() {
        return doiNum.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param doiNum the value to set into {@link #doiNum}
     */
    public void setDoiNum(String doiNum) {
        this.doiNum.set(doiNum);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #fullName}
     */
    @Basic
    @Column(name = "full_name")
    public String getFullName() {
        return fullName.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param fullName the value to set into {@link #fullName}
     */
    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #address}
     */
    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param address the value to set into {@link #address}
     */
    public void setAddress(String address) {
        this.address.set(address);
    }


    /**
     * Public accessor - getter.
     *
     * @return value of {@link #person}
     */
    @ManyToOne
    @JoinColumn(name = "person", referencedColumnName = "id", nullable = false)
    public Person getPerson() {
        return person.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param person the value to set into {@link #person}
     */
    public void setPerson(Person person) {
        this.person.set(person);
    }
}
