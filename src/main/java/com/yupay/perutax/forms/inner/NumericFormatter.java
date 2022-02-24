package com.yupay.perutax.forms.inner;

import javafx.scene.control.TextFormatter;

/**
 * A text formatter that allows numeric only characters.
 * Will allow a maximum length.
 * Example: {@link new NumericFormatter(10);} will allow
 * the user to enter up to 10 numeric characters, any blank
 * space, or character other than digit, will be ignored.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class NumericFormatter extends TextFormatter<String> {
    /**
     * The default constructor.
     *
     * @param length maximum length of resulting text.
     */
    public NumericFormatter(int length) {
        super(
                new NumericConverter(length),
                "",
                c -> {
                    var m = c.getControlNewText().strip();
                    //If control text will be blank, do nothing
                    if (m.isBlank()) return c;
                    //If any character is not a digit, cancel editing.
                    if (m.codePoints()
                            .anyMatch(i -> !Character.isDigit(i)))
                        return null;
                    //If new control text is longer than length, cancel editing.
                    //Also, if max length is 0 or less, then should ignore it.
                    if (length > 0 && m.length() > length) return null;
                    //If all is OK, continue with edition.
                    return c;
                }
        );
    }
}
