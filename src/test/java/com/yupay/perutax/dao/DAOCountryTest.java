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
