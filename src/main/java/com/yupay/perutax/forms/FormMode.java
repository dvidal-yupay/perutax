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

package com.yupay.perutax.forms;

/**
 * The card form modality defines the behavior
 * (editability) of the fields.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public enum FormMode {
    /**
     * The read only mode is to show a value
     * but not edit it.
     */
    READ_ONLY,
    /**
     * The editor allows to edit a value.
     * However, some fields shouldn't be
     * editable, like IDs.
     */
    EDITOR,
    /**
     * The creator mode, allows to edit every
     * field because the item is being created.
     */
    CREATOR
}
