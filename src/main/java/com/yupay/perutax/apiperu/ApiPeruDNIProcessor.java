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
