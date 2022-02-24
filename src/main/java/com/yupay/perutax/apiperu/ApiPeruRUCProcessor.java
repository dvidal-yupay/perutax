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
