package com.yupay.perutax.forms.inner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.jetbrains.annotations.NotNull;

/**
 * An abstract change listener that takes an old value,
 * if old value is not null will invoke a dettach method;
 * then clears the UI, and checks new value, if new value
 * is not null will invoke an attach method.</p>
 * This is useful to implement a UI when a value should
 * be attached/dettached to or from the UI (ie: an object editor).
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public abstract class BaseChangeListener<T> implements ChangeListener<T> {
    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        if (oldValue != null) dettach(oldValue);
        clear();
        if (newValue != null) attach(newValue);
    }

    /**
     * Attaches the value to the ui, binding each property
     * to the UI observables.
     *
     * @param value the value to attach (bind).
     */
    protected abstract void attach(@NotNull T value);

    /**
     * Dettaches the value from the ui, unbinding each
     * property from the UI observables.
     *
     * @param value the value to dettach (unbind).
     */
    protected abstract void dettach(@NotNull T value);

    /**
     * Clears the UI values, preparing the form
     * to accept a new value.
     */
    protected abstract void clear();
}
