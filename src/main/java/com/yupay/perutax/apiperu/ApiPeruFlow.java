package com.yupay.perutax.apiperu;

import com.yupay.perutax.entities.Person;
import com.yupay.perutax.forms.ErrorAlert;
import freetimelabs.io.reactorfx.schedulers.FxSchedulers;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.function.Consumer;

import static com.yupay.perutax.forms.ErrorAlert.easy;

/**
 * This is the class responsible of prepare
 * and execute a query to the ApiPeru web service,
 * then will expect a response and invoke a consumer.
 * The flow will occur within a non-blocking reactive
 * implementation (project reactor).
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class ApiPeruFlow {
    /**
     * The endpoint determines the type of query.
     */
    private final ApiPeruEnpoint endpoint;
    /**
     * Document to fetch.
     */
    private String document;
    /**
     * What to do when a query is successful.
     */
    private Consumer<Person> onSuccess;
    /**
     * What to do when a query throws an exception.
     */
    private Consumer<Throwable> onError;

    /**
     * Establishes a type of query.
     *
     * @param endpoint the type of query.
     */
    public ApiPeruFlow(ApiPeruEnpoint endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Fluent setter - with.
     *
     * @param document new value to set in {@link #document}
     * @return this instance.
     */
    public ApiPeruFlow withDocument(String document) {
        this.document = document;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #document}
     */
    public String getDocument() {
        return document;
    }

    /**
     * Fluent setter - with.
     *
     * @param onSuccess new value to set in {@link #onSuccess}
     * @return this instance.
     */
    public ApiPeruFlow withOnSuccess(Consumer<Person> onSuccess) {
        this.onSuccess = onSuccess;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #onSuccess}
     */
    public Consumer<Person> getOnSuccess() {
        return onSuccess;
    }

    /**
     * Fluent setter - with.
     *
     * @param onError new value to set in {@link #onError}
     * @return this instance.
     */
    public ApiPeruFlow withOnError(Consumer<Throwable> onError) {
        this.onError = onError;
        return this;
    }

    /**
     * Sets the onError consumer to an {@link ErrorAlert}
     * with a standard briefing.
     *
     * @return this instance.
     */
    @Contract("->this")
    public @NotNull ApiPeruFlow defaultError() {
        return withOnError(easy("Ocurrió un error al consultar el documento en ApiPeru.dev"));
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #onError}
     */
    public Consumer<Throwable> getOnError() {
        return onError;
    }

    /**
     * Executes the request flow.
     */
    public void execute() {
        var processor = endpoint.processor.get();
        Mono.fromSupplier(new ApiPeruRequestBuilder()
                        .withDocument(getDocument())
                        .withEndpoint(endpoint))
                .subscribeOn(Schedulers.single())
                .publishOn(FxSchedulers.fxThread())
                .map(processor)
                .subscribe(getOnSuccess(), getOnError());

    }
}
