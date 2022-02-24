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

package com.yupay.perutax;

import java.nio.file.Path;

/**
 * This class holds references to paths representing
 * the local user files.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class LocalUser {
    /**
     * The local user home /home/USER in Linux, and
     * C:\Users\USER in MS-Windows 7+
     */
    public static final Path HOME = Path.of(System.getProperty("user.home"));
    /**
     * Hidden .yupay foler within HOME.
     */
    public static final Path YUPAY = HOME.resolve(".yupay");
    /**
     * Perutax folder within YUPAY.
     */
    public static final Path PERUTAX = YUPAY.resolve("perutax");
    /**
     * JPA folder, within PERUTAX containing
     * .properties files for JPA connection parameters.
     */
    public static final Path JPA = PERUTAX.resolve("jpa");
    /**
     * Tokens folder, within PERUTAX containing .token files
     * with different tokens for different purposes.
     */
    public static final Path TOKENS = PERUTAX.resolve("tokens");
}
