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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test of the DAO country.
 * Successful at 22-01-12 01:52 (UTC)
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
class DAOCountryTest {
    @BeforeAll
    static void prepare() {
        DAOTestUtil.initDAOTest();
    }

    @AfterAll
    static void shutdown() {
        DAOSource.get().stopPersistence();
    }

    @Test
    void testFindAll() {
        new DAOCountry().findAll().forEach(x -> {
            assertNotNull(x);
            System.out.println(x);
        });
    }

    @Test
    void testFindActive() {
        new DAOCountry().findAll().forEach(x -> {
            assertNotNull(x);
            assertFalse(x.isTrash());
            System.out.println(x);
        });
    }
}
