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
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Entity for a person, who may be a supplier, customer, etc.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "person", schema = "public")
public class Person {
    /**
     * Person ID (UUID-36 chars autogenerated).
     */
    private final StringProperty id
            = new SimpleStringProperty(this, "id");
    /**
     * Number of DOI.
     */
    private final StringProperty doiNum
            = new SimpleStringProperty(this, "doiNum");
    /**
     * Name of the person.
     */
    private final StringProperty fullName
            = new SimpleStringProperty(this, "fullName");
    /**
     * Address of the person. (Full text).
     */
    private final StringProperty address
            = new SimpleStringProperty(this, "address");
    /**
     * Customer role flag.
     */
    private final BooleanProperty roleCustomer
            = new SimpleBooleanProperty(this, "roleCustomer", false);
    /**
     * Supplier role flag.
     */
    private final BooleanProperty roleSupplier
            = new SimpleBooleanProperty(this, "roleSupplier", false);
    /**
     * Employee role flag.
     */
    private final BooleanProperty roleEmployee
            = new SimpleBooleanProperty(this, "roleEmployee", false);
    /**
     * Shareholder role flag.
     */
    private final BooleanProperty roleShareholder
            = new SimpleBooleanProperty(this, "roleShareholder", false);
    /**
     * Freelancer role flag.
     */
    private final BooleanProperty roleFreelancer
            = new SimpleBooleanProperty(this, "roleFreelancer", false);
    /**
     * Associated role flag.
     */
    private final BooleanProperty roleAssociated
            = new SimpleBooleanProperty(this, "roleAssociated", false);
    /**
     * Trash flag, if true the person should be disregarded.
     */
    private final BooleanProperty trash
            = new SimpleBooleanProperty(this, "trash", false);
    /**
     * The type of DOI.
     */
    private final ObjectProperty<TypeDOI> doiType
            = new SimpleObjectProperty<>(this, "doiType");
    /**
     * The abroad country where the person lives.
     */
    private final ObjectProperty<Country> country
            = new SimpleObjectProperty<>(this, "country");

    /**
     * Default constructor.
     */
    public Person() {
    }

    /**
     * Copy constructor, copies all values from another instance.
     *
     * @param another the another instance.
     */
    public Person(@NotNull Person another) {
        setAddress(another.getAddress());
        setCountry(another.getCountry());
        setDoiNum(another.getDoiNum());
        setDoiType(another.getDoiType());
        setFullName(another.getFullName());
        setId(another.getId());
        setRoleAssociated(another.isRoleAssociated());
        setRoleCustomer(another.isRoleCustomer());
        setRoleEmployee(another.isRoleEmployee());
        setRoleFreelancer(another.isRoleFreelancer());
        setRoleShareholder(another.isRoleShareholder());
        setRoleSupplier(another.isRoleSupplier());
        setTrash(another.isTrash());
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @GeneratedValue(generator = "UUID_PERSON")
    @UuidGenerator(name = "UUID_PERSON")
    @Id
    @Column(name = "id", nullable = false, length = 36)
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
    @Column(name = "full_name", nullable = false)
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
     * @return value of {@link #address}
     */
    @Basic
    @Column(name = "address", length = -1)
    public String getAddress() {
        return address.get();
    }

    /**
     * Accessor - setter.
     *
     * @param address value to set on {@link #address}
     */
    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #roleCustomer}
     */
    @Basic
    @Column(name = "role_customer", nullable = false)
    public boolean isRoleCustomer() {
        return roleCustomer.get();
    }

    /**
     * Accessor - setter.
     *
     * @param roleCustomer value to set on {@link #roleCustomer}
     */
    public void setRoleCustomer(boolean roleCustomer) {
        this.roleCustomer.set(roleCustomer);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #roleSupplier}
     */
    @Basic
    @Column(name = "role_supplier", nullable = false)
    public boolean isRoleSupplier() {
        return roleSupplier.get();
    }

    /**
     * Accessor - setter.
     *
     * @param roleSupplier value to set on {@link #roleSupplier}
     */
    public void setRoleSupplier(boolean roleSupplier) {
        this.roleSupplier.set(roleSupplier);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #roleEmployee}
     */
    @Basic
    @Column(name = "role_employee", nullable = false)
    public boolean isRoleEmployee() {
        return roleEmployee.get();
    }

    /**
     * Accessor - setter.
     *
     * @param roleEmployee value to set on {@link #roleEmployee}
     */
    public void setRoleEmployee(boolean roleEmployee) {
        this.roleEmployee.set(roleEmployee);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #roleShareholder}
     */
    @Basic
    @Column(name = "role_shareholder", nullable = false)
    public boolean isRoleShareholder() {
        return roleShareholder.get();
    }

    /**
     * Accessor - setter.
     *
     * @param roleShareholder value to set on {@link #roleShareholder}
     */
    public void setRoleShareholder(boolean roleShareholder) {
        this.roleShareholder.set(roleShareholder);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #roleFreelancer}
     */
    @Basic
    @Column(name = "role_freelancer", nullable = false)
    public boolean isRoleFreelancer() {
        return roleFreelancer.get();
    }

    /**
     * Accessor - setter.
     *
     * @param roleFreelancer value to set on {@link #roleFreelancer}
     */
    public void setRoleFreelancer(boolean roleFreelancer) {
        this.roleFreelancer.set(roleFreelancer);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #roleAssociated}
     */
    @Basic
    @Column(name = "role_associated", nullable = false)
    public boolean isRoleAssociated() {
        return roleAssociated.get();
    }

    /**
     * Accessor - setter.
     *
     * @param roleAssociated value to set on {@link #roleAssociated}
     */
    public void setRoleAssociated(boolean roleAssociated) {
        this.roleAssociated.set(roleAssociated);
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
     * Accessor - getter.
     *
     * @return value of {@link #doiType}
     */
    @ManyToOne
    @JoinColumn(name = "doi_type", referencedColumnName = "id", nullable = false)
    public TypeDOI getDoiType() {
        return doiType.get();
    }

    /**
     * Accessor - setter.
     *
     * @param doiType value to set on {@link #doiType}
     */
    public void setDoiType(TypeDOI doiType) {
        this.doiType.set(doiType);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #country}
     */
    @ManyToOne
    @JoinColumn(name = "country", referencedColumnName = "id")
    public Country getCountry() {
        return country.get();
    }

    /**
     * Accessor - setter.
     *
     * @param country value to set on {@link #country}
     */
    public void setCountry(Country country) {
        this.country.set(country);
    }

    /**
     * JavaFX property accessor.
     *
     * @return the property of {@link  #id}
     */
    public StringProperty idProperty() {
        return id;
    }

    /**
     * JavaFX property accessor.
     *
     * @return the property of {@link  #doiNum}
     */
    public StringProperty doiNumProperty() {
        return doiNum;
    }

    /**
     * JavaFX property accessor.
     *
     * @return the property of {@link  #fullName}
     */
    public StringProperty fullNameProperty() {
        return fullName;
    }

    /**
     * JavaFX property accessor.
     *
     * @return the property of {@link  #address}
     */
    public StringProperty addressProperty() {
        return address;
    }

    /**
     * JavaFX property accessor.
     *
     * @return the property of {@link  #roleCustomer}
     */
    public BooleanProperty roleCustomerProperty() {
        return roleCustomer;
    }

    /**
     * JavaFX property accessor.
     *
     * @return the property of {@link  #roleSupplier}
     */
    public BooleanProperty roleSupplierProperty() {
        return roleSupplier;
    }

    /**
     * JavaFX property accessor.
     *
     * @return the property of {@link  #roleEmployee}
     */
    public BooleanProperty roleEmployeeProperty() {
        return roleEmployee;
    }

    /**
     * JavaFX property accessor.
     *
     * @return the property of {@link  #roleShareholder}
     */
    public BooleanProperty roleShareholderProperty() {
        return roleShareholder;
    }

    /**
     * JavaFX property accessor.
     *
     * @return the property of {@link  #roleFreelancer}
     */
    public BooleanProperty roleFreelancerProperty() {
        return roleFreelancer;
    }

    /**
     * JavaFX property accessor.
     *
     * @return the property of {@link  #roleAssociated}
     */
    public BooleanProperty roleAssociatedProperty() {
        return roleAssociated;
    }

    /**
     * JavaFX property accessor.
     *
     * @return the property of {@link  #trash}
     */
    public BooleanProperty trashProperty() {
        return trash;
    }

    /**
     * JavaFX property accessor.
     *
     * @return the property of {@link  #doiType}
     */
    public ObjectProperty<TypeDOI> doiTypeProperty() {
        return doiType;
    }

    /**
     * JavaFX property accessor.
     *
     * @return the property of {@link  #country}
     */
    public ObjectProperty<Country> countryProperty() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Person person && Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
