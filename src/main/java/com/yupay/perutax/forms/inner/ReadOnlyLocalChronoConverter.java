package com.yupay.perutax.forms.inner;

import com.yupay.perutax.forms.FormUtils;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

/**
 * Read-only string converter (only performs toString) to show
 * local dates as dd/MM/uuuu and local date times (usually for timestamps)
 * in format dd/MM/uuuu HH:mm:ss.SSS
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class ReadOnlyLocalChronoConverter<T extends Temporal>
        extends StringConverter<T> {
    @Override
    public String toString(T o) {
        if (o instanceof LocalDate ld) {
            return ld.format(FormUtils.DATE_ONLY);
        } else if (o instanceof LocalDateTime ldt) {
            return ldt.format(FormUtils.STAMP);
        } else {
            return "";
        }
    }

    @Override
    public T fromString(String string) {
        return null;
    }
}
