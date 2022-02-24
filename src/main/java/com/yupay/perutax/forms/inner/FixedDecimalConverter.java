package com.yupay.perutax.forms.inner;

import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The fixed decimal converter parses/formats a decimal with such many
 * decimal places as required by a scale factor. Will use rounding mode
 * {@link RoundingMode#HALF_UP}
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class FixedDecimalConverter extends StringConverter<BigDecimal> {
    /**
     * The scale expected on from/to String results.
     */
    private final int scale;

    /**
     * Default constructor.
     *
     * @param scale the scale on fromString an toString results.
     */
    public FixedDecimalConverter(int scale) {
        this.scale = scale;
    }

    @Override
    public String toString(BigDecimal object) {
        return object == null
                ? ""
                : scale < 1 ? "%.0f".formatted(object)
                : ("%." + scale + "f").formatted(object);
    }

    @Override
    public BigDecimal fromString(String string) {
        if (string == null || string.isBlank())
            return null;
        if (string.matches("[+-]?\\d*\\.?\\d*"))
            return new BigDecimal(string).setScale(scale, RoundingMode.HALF_UP);
        if (string.matches("[+-]?\\."))
            return BigDecimal.ZERO.setScale(scale, RoundingMode.HALF_UP);
        return null;
    }
}
