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
