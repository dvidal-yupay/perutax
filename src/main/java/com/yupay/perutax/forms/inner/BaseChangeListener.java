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
