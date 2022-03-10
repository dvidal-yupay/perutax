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

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "folio_total", schema = "public", catalog = "perutaxdb")
public class FolioTotal {
    private long id;
    private Object totalType;
    private BigDecimal amountFc;
    private BigDecimal amountSc;
    private Folio owner;

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
    @Column(name = "total_type")
    public Object getTotalType() {
        return totalType;
    }

    public void setTotalType(Object totalType) {
        this.totalType = totalType;
    }

    @Basic
    @Column(name = "amount_fc")
    public BigDecimal getAmountFc() {
        return amountFc;
    }

    public void setAmountFc(BigDecimal amountFc) {
        this.amountFc = amountFc;
    }

    @Basic
    @Column(name = "amount_sc")
    public BigDecimal getAmountSc() {
        return amountSc;
    }

    public void setAmountSc(BigDecimal amountSc) {
        this.amountSc = amountSc;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FolioTotal that) {
            return id == that.id && Objects.equals(totalType, that.totalType) && Objects.equals(amountFc, that.amountFc) && Objects.equals(amountSc, that.amountSc);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalType, amountFc, amountSc);
    }

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id", nullable = false)
    public Folio getOwner() {
        return owner;
    }

    public void setOwner(Folio owner) {
        this.owner = owner;
    }
}
