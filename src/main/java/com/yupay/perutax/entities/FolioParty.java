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

import java.util.Objects;

@Entity
@Table(name = "folio_party", schema = "public", catalog = "perutaxdb")
public class FolioParty {
    private long id;
    private String doiType;
    private String doiNum;
    private String fullName;
    private String address;
    private Person0 person;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "doi_type")
    public String getDoiType() {
        return doiType;
    }

    public void setDoiType(String doiType) {
        this.doiType = doiType;
    }

    @Basic
    @Column(name = "doi_num")
    public String getDoiNum() {
        return doiNum;
    }

    public void setDoiNum(String doiNum) {
        this.doiNum = doiNum;
    }

    @Basic
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FolioParty that) {
            return id == that.id && Objects.equals(doiType, that.doiType) && Objects.equals(doiNum, that.doiNum) && Objects.equals(fullName, that.fullName) && Objects.equals(address, that.address);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doiType, doiNum, fullName, address);
    }

    @ManyToOne
    @JoinColumn(name = "person", referencedColumnName = "id", nullable = false)
    public Person0 getPerson() {
        return person;
    }

    public void setPerson(Person0 person) {
        this.person = person;
    }
}
