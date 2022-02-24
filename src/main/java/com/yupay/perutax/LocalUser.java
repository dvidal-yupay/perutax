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
