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

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The taxation account entity.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "tax_account", schema = "public")
public class TaxAccount {
    /**
     * The account ID, assigned by user. The account ID should
     * follow the accountability rules of levels, and fill with
     * right zeroes up to 8 levels.
     */
    private final StringProperty id
            = new SimpleStringProperty(this, "id");
    /**
     * The name of the account. (Max: 200 chars).
     */
    private final StringProperty name
            = new SimpleStringProperty(this, "name");
    /**
     * The nature of the account.
     */
    private final ObjectProperty<AccountNature> nature
            = new SimpleObjectProperty<>(this, "nature");
    /**
     * The currency in which the account is expressed.
     * Any foreign-currency movement will be debited
     * or credited in that currency at xrate of date.
     */
    private final ObjectProperty<Currenci> currency
            = new SimpleObjectProperty<>(this, "currency");
    /**
     * The balance (always expressed in account {@link  #currency}).
     */
    private final ObjectProperty<BigDecimal> balance
            = new SimpleObjectProperty<>(this, "balance", new BigDecimal("0.00"));
    /**
     *
     */
    private final ObjectProperty<CostGroup> groupCost
            = new SimpleObjectProperty<>(this, "groupCost");
    /**
     * Usable flag. Only those accounts that are not titles or groups,
     * should be usable in a book keeping journal/ledger. Ie: tax account
     * Cash and banks shouldn't be usable since any movement will be performed
     * in a sub-account of that.
     */
    private final BooleanProperty usable =
            new SimpleBooleanProperty(this, "usable");
    /**
     * Trash flag: if true, account should be ignored.
     */
    private final BooleanProperty trash =
            new SimpleBooleanProperty(this, "trash");

    /**
     * Default empty constructor.
     */
    public TaxAccount() {

    }

    /**
     * Consturctor that copies all inner fields values from another
     * instance values.
     *
     * @param another source instance.
     */
    public TaxAccount(@NotNull TaxAccount another) {
        setBalance(another.getBalance());
        setCurrency(another.getCurrency());
        setGroupCost(another.getGroupCost());
        setId(another.getId());
        setName(another.getName());
        setNature(another.getNature());
        setTrash(another.isTrash());
        setUsable(another.isUsable());

    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @Id
    @Column(name = "id", nullable = false, length = 8)
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
    @Column(name = "name", nullable = false, length = 200)
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
     * @return value of {@link #nature}
     */
    @Basic
    @Column(name = "nature", nullable = false)
    @Enumerated(EnumType.STRING)
    public AccountNature getNature() {
        return nature.get();
    }

    /**
     * Accessor - setter.
     *
     * @param nature value to set on {@link #nature}
     */
    public void setNature(AccountNature nature) {
        this.nature.set(nature);
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
     * @return value of {@link #balance}
     */
    @Basic
    @Column(name = "balance", nullable = false, precision = 2)
    public BigDecimal getBalance() {
        return balance.get();
    }

    /**
     * Accessor - setter.
     *
     * @param balance value to set on {@link #balance}
     */
    public void setBalance(BigDecimal balance) {
        this.balance.set(balance);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #groupCost}
     */
    @Basic
    @Column(name = "group_cost")
    @Enumerated(EnumType.STRING)
    public CostGroup getGroupCost() {
        return groupCost.get();
    }

    /**
     * Accessor - setter.
     *
     * @param groupCost value to set on {@link #groupCost}
     */
    public void setGroupCost(CostGroup groupCost) {
        this.groupCost.set(groupCost);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #usable}
     */
    @Basic
    @Column(name = "usable", nullable = false)
    public boolean isUsable() {
        return usable.get();
    }

    /**
     * Accessor - setter.
     *
     * @param usable value to set on {@link #usable}
     */
    public void setUsable(boolean usable) {
        this.usable.set(usable);
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
     * @return the property {@link  #name}
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #nature}
     */
    public ObjectProperty<AccountNature> natureProperty() {
        return nature;
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
     * @return the property {@link  #balance}
     */
    public ObjectProperty<BigDecimal> balanceProperty() {
        return balance;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #groupCost}
     */
    public ObjectProperty<CostGroup> groupCostProperty() {
        return groupCost;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #usable}
     */
    public BooleanProperty usableProperty() {
        return usable;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #trash}
     */
    public BooleanProperty trashProperty() {
        return trash;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TaxAccount that && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
