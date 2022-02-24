package com.yupay.perutax.forms.inner;

import javafx.util.StringConverter;

import java.util.function.Function;

/**
 * A string converter that only performs toString conversions.
 * This is useful for read-only table columns.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class StringConverterTo<T> extends StringConverter<T> {
    /**
     * The string function to map a T into String.
     * The value passed to the function is never null.
     */
    private final Function<T, String> mapper;

    /**
     * Default constructor.
     *
     * @param mapper function to map a T into String.
     */
    public StringConverterTo(Function<T, String> mapper) {
        this.mapper = mapper;
    }

    @Override
    public String toString(T object) {
        return object == null
                ? ""
                : mapper.apply(object);
    }

    @Override
    public T fromString(String string) {
        return null;
    }
}
