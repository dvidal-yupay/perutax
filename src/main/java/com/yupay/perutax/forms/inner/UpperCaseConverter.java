package com.yupay.perutax.forms.inner;

import javafx.util.StringConverter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
/**
 * Converter to parse and format to upper case.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class UpperCaseConverter extends StringConverter<String> {
    /**
     * Convenient method to process the strings.
     *
     * @param value the value to process.
     * @return {@code value.toUpperCase}
     */
    @Contract("null->!null")
    private @NotNull String call(@Nullable String value) {
        return value == null ? "" : value.toUpperCase();
    }

    @Override
    public String toString(@Nullable String object) {
        return call(object);
    }

    @Override
    public String fromString(@Nullable String string) {
        return call(string);
    }
}
