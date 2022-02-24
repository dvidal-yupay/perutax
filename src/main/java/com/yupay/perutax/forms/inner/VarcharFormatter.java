package com.yupay.perutax.forms.inner;

import javafx.scene.control.TextFormatter;

/**
 * A text formatter to enforce user inputs
 * for a varchar database field, so the user
 * input will be striped up to a maximum length,
 * and then upper cased.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class VarcharFormatter
        extends TextFormatter<String> {
    /**
     * Default constructor that takes a maximum
     * length and upper case flag.
     *
     * @param length maximum length allowed.
     * @param upper  upper case flag. If true, every input
     *               will be upper cased.
     */
    public VarcharFormatter(int length, boolean upper) {
        super(
                new VarcharConverter(length, upper),
                "",
                c -> {
                    var nTxt = c.getControlNewText().strip();
                    if (nTxt.isBlank()) return c;
                    if (nTxt.length() > length) return null;
                    if (upper) c.setText(c.getText().toUpperCase());
                    return c;
                });
    }
}
