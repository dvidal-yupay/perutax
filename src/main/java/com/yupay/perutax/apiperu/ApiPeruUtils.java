package com.yupay.perutax.apiperu;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;

/**
 * Utility methods (for maintenability and reusability sake).
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class ApiPeruUtils {
    /**
     * Private constructor because utility classes
     * shouldn't be instanciated.
     */
    private ApiPeruUtils() {
    }

    /**
     * Extracts data from an InputStreamReader that
     * is backed up by the response from an HttpsURLConnection,
     * connected to apiperu web service.
     *
     * @param input the stream reader.
     * @return a map with data.
     * @throws IllegalStateException if result is not success. The exception
     *                               message will be provided by server responses.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getData(InputStreamReader input) {
        var gson = new Gson();
        var json = gson.fromJson(input, Map.class);
        if (json.get("success") != Boolean.TRUE) {
            throw new IllegalStateException(Objects.toString(json.get("message")));
        }
        return (Map<String, Object>) json.get("data");
    }
}
