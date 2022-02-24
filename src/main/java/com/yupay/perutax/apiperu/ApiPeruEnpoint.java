package com.yupay.perutax.apiperu;

import com.yupay.perutax.entities.Person;

import javax.net.ssl.HttpsURLConnection;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The ENDPOINT to add to the URL.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public enum ApiPeruEnpoint {
    /**
     * Endpoint for RUC (taxID) queries.
     */
    RUC("ruc", ApiPeruRUCProcessor::new),
    /**
     * Endpoint for DNI queries.
     */
    DNI("dni", ApiPeruDNIProcessor::new);
    /**
     * The text to add at the end of the URL.
     */
    public final String url;
    /**
     * Function to map a connection to a Person entity.
     */
    public final Supplier<Function<HttpsURLConnection, Person>> processor;

    /**
     * Default constructor.
     *
     * @param url       text to add at the end of the URL.
     * @param processor function to parse connection to a Person.
     */
    ApiPeruEnpoint(String url,
                   Supplier<Function<HttpsURLConnection, Person>> processor) {
        this.url = url;
        this.processor = processor;
    }
}
