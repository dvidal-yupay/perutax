package com.yupay.perutax.apiperu;

import com.yupay.perutax.LocalUser;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.function.Supplier;

/**
 * Builds a request to consume the ApiPeru service.
 * This works as a cold supplier.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class ApiPeruRequestBuilder implements Supplier<HttpsURLConnection> {
    /**
     * The required endpoint, which determines the type of query.
     */
    private ApiPeruEnpoint endpoint;
    /**
     * The queried document of identification number.
     */
    private String document;

    /**
     * Fluent setter - with.
     *
     * @param endpoint new value to set in {@link #endpoint}
     * @return this instance.
     */
    public final ApiPeruRequestBuilder withEndpoint(@NotNull ApiPeruEnpoint endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    /**
     * Fluent setter - with.
     *
     * @param document new value to set in {@link #document}
     * @return this instance.
     */
    public final ApiPeruRequestBuilder withDocument(String document) {
        this.document = document;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #document}
     */
    public final String getDocument() {
        return document;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #endpoint}
     */
    public final ApiPeruEnpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public HttpsURLConnection get() {
        try {
            //Retrieve the token.
            var tknFile = LocalUser.TOKENS.resolve("apiperu.token");
            var tknStr = Files.readString(tknFile).strip();

            //build URL:
            var url = new URL("https://apiperu.dev/api/%s/%s?api_token=%s"
                    .formatted(getEndpoint().url, getDocument(), tknStr));
            System.out.println(url);
            //Prepare connection.
            var r = (HttpsURLConnection) url.openConnection();
            r.setRequestMethod("GET");
            r.setConnectTimeout(10000);
            r.setReadTimeout(10000);
            //Return ready to connect.
            return r;
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot prepare request.", e);
        }

    }
}
