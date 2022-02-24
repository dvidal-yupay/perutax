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

package com.yupay.perutax.dao;

/**
 * The person role is a representation of a state
 * on the entity {@link com.yupay.perutax.entities.Person}
 * based on flags. It's main purpose is to filter
 * when making some queries where a determined role is required.
 * Each role is associated with a column (field) name.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public enum PersonRole {
    /**
     * When I need to find a customer.
     */
    CUSTOMER("roleCustomer"),
    /**
     * When I need to find a supplier.
     */
    SUPPLIER("roleSupplier"),
    /**
     * When I need to find an employee.
     */
    EMPLOYEE("roleEmployee"),
    /**
     * When I need to find a share holder.
     */
    SHAREHOLDER("roleShareholder"),
    /**
     * When I need to find a freelancer.
     */
    FREELANCER("roleFreelancer"),
    /**
     * When I need to find an associated person.
     */
    ASSOCIATED("roleAssociated");
    /**
     * The column (field) name where the flag is stored.
     */
    public final String column;

    /**
     * Default constructor.
     *
     * @param column The column (field) name where the flag is stored.
     */
    PersonRole(String column) {
        this.column = column;
    }
}
