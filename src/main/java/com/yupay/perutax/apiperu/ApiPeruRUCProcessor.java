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
 * Cold function to open an HttpsURLConnection, read
 * the response and parse it into a Person entity.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class ApiPeruRUCProcessor implements Function<HttpsURLConnection, Person> {
    /**
     * The requierd tpye of DOI. This is lazily filled
     * from the database, fetching a TypeDOI whose
     * id would be 6 (as it represents the RUC type).
     */
    private TypeDOI rucType;

    @Override
    public Person apply(@NotNull HttpsURLConnection cnx) {
        if (rucType == null) rucType = DAO.typeDOI().fetch("6");
        var r = new Person();
        r.setDoiType(rucType);
        try {
            var rcode = cnx.getResponseCode();
            if (rcode == 200) {
                try (var is = cnx.getInputStream();
                     var isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                    var data = ApiPeruUtils.getData(isr);
                    r.setAddress(Objects.toString(data.get("direccion_completa")));
                    r.setDoiNum(Objects.toString(data.get("ruc")));
                    r.setFullName(Objects.toString(data.get("nombre_o_razon_social")));
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
