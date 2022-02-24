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

package com.yupay.perutax;

import java.util.function.Consumer;

/**
 * A class implementing all functional interfaces required
 * by the application, with empty implementations (no-op).
 *
 * @param <T> type erasure of some functional interfaces.
 */
public class EmptyFunctionals<T> implements
        Consumer<T>, Runnable {

    @Override
    public void run() {

    }

    @Override
    public void accept(T t) {

    }
}
