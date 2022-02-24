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
