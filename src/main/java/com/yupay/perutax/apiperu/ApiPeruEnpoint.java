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
