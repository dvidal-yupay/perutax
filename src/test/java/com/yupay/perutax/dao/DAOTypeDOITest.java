package com.yupay.perutax.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test of the DAO Type of DOI.
 * Succeded at 22-01-12 01:54 (UTC)
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
class DAOTypeDOITest {
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
        new DAOTypeDOI().findAll().forEach(x -> {
            assertNotNull(x);
            assertNotNull(x.getRegex());
            assertNotNull(x.getTitle());
            assertFalse(x.getRegex().isBlank());
            assertFalse(x.getTitle().isBlank());
            System.out.println(x);
        });
    }

    @Test
    void testFindActive() {
        new DAOTypeDOI().findAll().forEach(x -> {
            assertNotNull(x);
            assertNotNull(x.getRegex());
            assertNotNull(x.getTitle());
            assertFalse(x.getRegex().isBlank());
            assertFalse(x.getTitle().isBlank());
            assertFalse(x.isTrash());
            System.out.println(x);
        });
    }
}
