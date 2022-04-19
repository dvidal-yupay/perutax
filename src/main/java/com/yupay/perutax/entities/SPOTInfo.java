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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The SPOT info is the required information
 * to generate the Detraccion Batch file.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(schema = "public", name = "spot_info")
public class SPOTInfo {
    /**
     * The unique ID, sequence generated.
     */
    private final LongProperty id =
            new SimpleLongProperty(this, "id");
    /**
     * The spot withhold amount.
     */
    private final ObjectProperty<BigDecimal> amount =
            new SimpleObjectProperty<>(this, "amount", new BigDecimal("0.00"));
    /**
     * The SPOT batch number.
     */
    private final StringProperty batch =
            new SimpleStringProperty(this, "batch");
    /**
     * The payment transaction ID.
     */
    private final StringProperty paymentId =
            new SimpleStringProperty(this, "paymentId");
    /**
     * The payment date.
     */
    private final ObjectProperty<LocalDate> paymentDate =
            new SimpleObjectProperty<>(this, "paymentDate");
    /**
     * Self paid flag: if true, the transaction is made by
     * the user, false if the transaction is made by the
     * other party and the user only registers it.
     */
    private final BooleanProperty selfPaid =
            new SimpleBooleanProperty(this, "selfPaid");
    /**
     * Optional Remarks.
     */
    private final StringProperty remarks =
            new SimpleStringProperty(this, "remarks");
    /**
     * The role.
     */
    private final ObjectProperty<SPOTRole> role =
            new SimpleObjectProperty<>(this, "role");
    /**
     * The scheme of SPOT used to compute the amounts.
     */
    private final ObjectProperty<SPOTScheme> scheme =
            new SimpleObjectProperty<>(this, "scheme");

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #id}.get();
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "gen_spotinfo_id")
    @SequenceGenerator(name = "gen_spotinfo_id",
            schema = "public",
            sequenceName = "sq_spot_info_id")
    @Column(name = "id", nullable = false)
    public final long getId() {
        return id.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param id value to assign into {@link #id}.
     */
    public final void setId(long id) {
        this.id.set(id);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #id}.
     */
    public final LongProperty idProperty() {
        return id;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #amount}.get();
     */
    @Basic
    @Column(name = "amount", nullable = false, precision = 14, scale = 2)
    public final BigDecimal getAmount() {
        return amount.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param amount value to assign into {@link #amount}.
     */
    public final void setAmount(BigDecimal amount) {
        this.amount.set(amount);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #amount}.
     */
    public final ObjectProperty<BigDecimal> amountProperty() {
        return amount;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #batch}.get();
     */
    @Basic
    @Column(name = "batch", length = 6)
    public final String getBatch() {
        return batch.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param batch value to assign into {@link #batch}.
     */
    public final void setBatch(String batch) {
        this.batch.set(batch);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #batch}.
     */
    public final StringProperty batchProperty() {
        return batch;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #paymentId}.get();
     */
    @Basic
    @Column(name = "paymentId", length = 20)
    public final String getPaymentId() {
        return paymentId.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param paymentId value to assign into {@link #paymentId}.
     */
    public final void setPaymentId(String paymentId) {
        this.paymentId.set(paymentId);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #paymentId}.
     */
    public final StringProperty paymentIdProperty() {
        return paymentId;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #paymentDate}.get();
     */
    @Basic
    @Column(name = "paymentDate", columnDefinition = "DATE")
    public final LocalDate getPaymentDate() {
        return paymentDate.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param paymentDate value to assign into {@link #paymentDate}.
     */
    public final void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate.set(paymentDate);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #paymentDate}.
     */
    public final ObjectProperty<LocalDate> paymentDateProperty() {
        return paymentDate;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #selfPaid}.get();
     */
    @Basic
    @Column(name = "selfPaid")
    public final boolean isSelfPaid() {
        return selfPaid.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param selfPaid value to assign into {@link #selfPaid}.
     */
    public final void setSelfPaid(boolean selfPaid) {
        this.selfPaid.set(selfPaid);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #selfPaid}.
     */
    public final BooleanProperty selfPaidProperty() {
        return selfPaid;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #remarks}.get();
     */
    @Basic
    @Column(name = "remarks", length = -1)
    public final String getRemarks() {
        return remarks.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param remarks value to assign into {@link #remarks}.
     */
    public final void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #remarks}.
     */
    public final StringProperty remarksProperty() {
        return remarks;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #role}.get();
     */
    @Basic
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    public final SPOTRole getRole() {
        return role.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param role value to assign into {@link #role}.
     */
    public final void setRole(SPOTRole role) {
        this.role.set(role);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #role}.
     */
    public final ObjectProperty<SPOTRole> roleProperty() {
        return role;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #scheme}.get();
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "scheme")
    public final SPOTScheme getScheme() {
        return scheme.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param scheme value to assign into {@link #scheme}.
     */
    public final void setScheme(SPOTScheme scheme) {
        this.scheme.set(scheme);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #scheme}.
     */
    public final ObjectProperty<SPOTScheme> schemeProperty() {
        return scheme;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SPOTInfo spotInfo &&
                Objects.equals(getId(), spotInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
