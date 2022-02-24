package com.yupay.perutax.forms.inner;

import javafx.util.StringConverter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

/**
 * A string converter designed to use in inputs
 * for varchar database fields. It also will
 * convert to upper case depending on upper flag.
 * Example: {@link  new VarcharConverter(5, true);}
 * will compute the string values as a maximum of 5
 * characters wide and in upper case. The conversion
 * will never return null, if a null value is passed,
 * an empty String is returned.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class VarcharConverter
        extends StringConverter<String>
        implements UnaryOperator<String> {
    /**
     * Maximum length of result.
     */
    private final int length;
    /**
     * Upper case flag. If true, result will
     * be upper cased.
     */
    private final boolean upper;

    /**
     * Default constructor.
     *
     * @param length value of {@link  #length}
     * @param upper  value of {@link  #upper}
     */
    public VarcharConverter(int length, boolean upper) {
        this.length = length;
        this.upper = upper;
    }

    @Override
    @Contract("null->!null")
    public String apply(@Nullable String s) {
        if (s == null) return "";
        var m = s.strip();
        if (m.length() > length) m = m.substring(0, length);
        return upper ? m.toUpperCase() : m;
    }

    @Override
    public String toString(String object) {
        return apply(object);
    }

    @Override
    public String fromString(String string) {
        return apply(string);
    }
}
