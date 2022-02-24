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
