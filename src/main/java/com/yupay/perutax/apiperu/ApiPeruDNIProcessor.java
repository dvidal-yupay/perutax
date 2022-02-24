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

import com.yupay.perutax.dao.DAO;
import com.yupay.perutax.entities.Person;
import com.yupay.perutax.entities.TypeDOI;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.Function;

/**
 * Processor to take the json result of the HttpsURLConnection
 * that represents a dni service invocation and parse the result
 * into a Person entity.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class ApiPeruDNIProcessor implements Function<HttpsURLConnection, Person> {
    /**
     * The requierd tpye of DOI. This is lazily filled
     * from the database, fetching a TypeDOI whose
     * id would be 1 (as it represents the DNI type).
     */
    private TypeDOI dniType;

    @Override
    public Person apply(@NotNull HttpsURLConnection cnx) {
        if (dniType == null) dniType = DAO.typeDOI().fetch("1");
        var r = new Person();
        r.setDoiType(dniType);
        try {
            var rcode = cnx.getResponseCode();
            if (rcode == 200) {
                try (var is = cnx.getInputStream();
                     var isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                    var data = ApiPeruUtils.getData(isr);
                    r.setDoiNum(Objects.toString(data.get("numero")));
                    r.setFullName(Objects.toString(data.get("nombre_completo")));
                    return r;
                }
            } else {
                throw new IllegalStateException("HTTP Response state is " + rcode);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read HTTP request.", e);
        }
    }
}
