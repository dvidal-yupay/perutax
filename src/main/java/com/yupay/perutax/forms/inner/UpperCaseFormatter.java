package com.yupay.perutax.forms.inner;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;

/**
 * Text formatter that enforces only upper case input.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class UpperCaseFormatter extends TextFormatter<String> {
    /**
     * Default constructor that relies on
     * {@link TextFormatter#TextFormatter(StringConverter, Object, UnaryOperator)}
     * to create a new instance with {@link UpperCaseConverter} and that transforms
     * every single input character into upper case.
     */
    public UpperCaseFormatter() {
        super(new UpperCaseConverter(), "", c -> {
            c.setText(c.getText().toUpperCase());
            return c;
        });
    }
}
