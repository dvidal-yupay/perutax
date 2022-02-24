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

import com.yupay.perutax.EmptyFunctionals;
import freetimelabs.io.reactorfx.schedulers.FxSchedulers;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import reactor.core.Disposables;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * The data flow base is an abstract class that holds
 * boilerplate (common fields) for a determined flow.
 * Ie: every data flow needs a forEach consumer, then
 * there's a forEach field with its setter.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public sealed abstract class DataFlowBase<T> permits SelectActiveFlow, SelectAllFlow {
    /**
     * The entity class.
     */
    protected final Class<T> entity;
    /**
     * What to do with each queried element. By default, does nothing.
     */
    protected Consumer<T> forEach = new EmptyFunctionals<>();
    /**
     * What to do if an error is thrown. By default, does nothing.
     */
    protected Consumer<Throwable> onError = new EmptyFunctionals<>();
    /**
     * What to do on successfull completion. By default, does nothing.
     */
    protected Runnable onComplete = new EmptyFunctionals<>();
    /**
     * What to do after completion, whatever the result is.
     */
    protected Runnable onFinally = new EmptyFunctionals<>();
    /**
     * What to do before execution.
     */
    protected Runnable first = new EmptyFunctionals<>();

    /**
     * Default constructor.
     *
     * @param entity the entity class.
     */
    public DataFlowBase(Class<T> entity) {
        this.entity = entity;
    }

    /**
     * Fluent setter.
     *
     * @param forEach value to set {@link  #forEach} never null.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull DataFlowBase<T> forEach(@NotNull Consumer<T> forEach) {
        this.forEach = forEach;
        return this;
    }

    /**
     * Fluent setter.
     *
     * @param onError value to set {@link  #onError} never null.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull DataFlowBase<T> onError(@NotNull Consumer<Throwable> onError) {
        this.onError = onError;
        return this;
    }

    /**
     * Fluent setter.
     *
     * @param onComplete value to set {@link  #onComplete} never null.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull DataFlowBase<T> onComplete(@NotNull Runnable onComplete) {
        this.onComplete = onComplete;
        return this;
    }

    /**
     * Fluent setter.
     *
     * @param first value to set {@link  #first} never null.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull DataFlowBase<T> first(@NotNull Runnable first) {
        this.first = first;
        return this;
    }

    /**
     * Should execute a Flux or Mono using the protected fields
     * for first, doFinally, and subscribe.
     */
    public void execute() {
        var cnt = Disposables.swap();
        var dsp = Flux.fromStream(this::getQuery)
                .doFirst(first)
                .subscribeOn(Schedulers.single())
                .doFinally(s -> onFinally.run())
                .publishOn(FxSchedulers.fxThread())
                .doAfterTerminate(cnt::dispose)
                .subscribe(forEach, onError, onComplete);
        cnt.replace(dsp);
    }

    /**
     * Creates the query as a Stream, and returns it.
     *
     * @return the query stream.
     */
    protected abstract @NotNull Stream<T> getQuery();
}
