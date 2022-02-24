package com.yupay.perutax.forms.flows;

import com.yupay.perutax.dao.DAO;
import com.yupay.perutax.forms.ErrorAlert;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * The update one flow updates a one item
 * in the persistence database. The operation
 * is thread-blocking, and synchronous. Should
 * be executed in the JavaFX thread.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class UpdateOneFlow<T> {
    /**
     * What to do with managed entity if updateOne succeeds.
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
    public UpdateOneFlow<T> withOnSuccess(Consumer<T> onSuccess) {
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
    public UpdateOneFlow<T> withOnFail(Consumer<Throwable> onFail) {
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
    public @NotNull UpdateOneFlow<T> defaultFail(@NotNull String briefing) {
        return withOnFail(ErrorAlert.easy(briefing));
    }

    /**
     * Runs the flow. It should be invoked within javaFX thread.
     *
     * @param item the item to insert.
     */
    @SuppressWarnings("unchecked")
    public void run(@NotNull T item) {
        try {
            getOnSuccess().accept(DAO
                    .forEntity((Class<T>) item.getClass())
                    .updateOne(item));
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
        return this::run;
    }
}
