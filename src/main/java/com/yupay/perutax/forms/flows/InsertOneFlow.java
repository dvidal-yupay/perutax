package com.yupay.perutax.forms.flows;

import com.yupay.perutax.dao.DAO;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static com.yupay.perutax.forms.ErrorAlert.easy;

/**
 * The insert one flow inserts a one item
 * in the persistence database. The operation
 * is thread-blocking, and synchronous. Should
 * be executed in the JavaFX thread.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class InsertOneFlow<T> {
    /**
     * What to do with managed entity if insertOne succeeds.
     */
    private Consumer<T> onSuccess;
    /**
     * What to do with exception if something fails.
     */
    private Consumer<Throwable> onFail;

    /**
     * Fluent setter - with.
     *
     * @param onSuccess new value to set in {@link #onSuccess}
     * @return this instance.
     */
    public InsertOneFlow<T> withOnSuccess(Consumer<T> onSuccess) {
        this.onSuccess = onSuccess;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #onSuccess}
     */
    public Consumer<T> getOnSuccess() {
        return onSuccess;
    }

    /**
     * Fluent setter - with.
     *
     * @param onFail new value to set in {@link #onFail}
     * @return this instance.
     */
    public InsertOneFlow<T> withOnFail(Consumer<Throwable> onFail) {
        this.onFail = onFail;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #onFail}
     */
    public Consumer<Throwable> getOnFail() {
        return onFail;
    }

    /**
     * Sets the onFail to an ErrorAlert with
     * a given briefing text. Shorthand of:
     * {@code withOnFail(new ErrorAlert().withBriefing(briefing));}
     *
     * @param briefing the given briefing text NEVER NULL.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull InsertOneFlow<T> defaultFail(@NotNull String briefing) {
        return withOnFail(easy(briefing));
    }

    /**
     * Runs the flow. It should be invoked withing javaFX thread.
     *
     * @param item the item to insert.
     */
    @SuppressWarnings("unchecked")
    public void run(@NotNull T item) {
        try {
            getOnSuccess().accept(DAO
                    .forEntity((Class<T>) item.getClass())
                    .insertOne(item));
        } catch (RuntimeException e) {
            getOnFail().accept(e);
        }
    }

    /**
     * Wraps the run method in a new consumer.
     *
     * @return the consumer object.
     */
    @Contract(pure = true, value = "->new")
    public @NotNull Consumer<T> asConsumer() {
        System.out.println("asConsumer...");
        return this::run;
    }
}
