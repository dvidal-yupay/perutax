package com.yupay.perutax.dao;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utilities for data access layer on testing cycles.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
class DAOTestUtil {
    /**
     * Initializes the DAO for testing using a configuration file
     * in (user.home)/.yupay/perutax/jpa/developer.properties
     */
    static void initDAOTest() {
        var p = Path.of(System.getProperty("user.home"),
                ".yupay",
                "perutax",
                "jpa",
                "developer.properties");
        DAOSource.get().initPersistence(p);
    }
}
