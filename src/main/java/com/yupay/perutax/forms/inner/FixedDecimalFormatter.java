package com.yupay.perutax.forms.inner;

import javafx.scene.control.TextFormatter;

import java.math.BigDecimal;

/**
 * A formatter for a fixed decimal, stating a scale.
 * The numbers will be rounded with {@link  java.math.RoundingMode#HALF_UP}
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class FixedDecimalFormatter extends TextFormatter<BigDecimal> {
    /**
     * Default constructor.
     *
     * @param scale a scale for number parsing or formatting.
     */
    public FixedDecimalFormatter(int scale) {
        super(new FixedDecimalConverter(scale), null, c -> {
            var str = c.getControlNewText().strip();
            if (str.isBlank()) return c;
            if (str.matches("[-+]?\\d*\\.?\\d*")) return c;
            return null;
        });
    }
}
