package com.yupay.perutax.forms;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This is both an alert and a throwable consumer.
 * When a throwable is caught and sent to this consumer,
 * the error alert will be shown with a brief message and
 * the stack trace printed in an expandible text area.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class ErrorAlert
        implements Consumer<Throwable> {
    /**
     * The text area to print the stack trace.
     */
    private final TextArea expandable = new TextArea();
    /**
     * The alert, which is lazily initialized upon request.
     */
    private Alert alert;
    /**
     * The briefing text to show in error alerts.
     */
    private String briefing;

    /**
     * Short hand for new
     * {@code ErrorAlert().withBriefing(briefing);}
     *
     * @param briefing the brief error text.
     * @return a new alert.
     */
    @Contract("_->new")
    public static @NotNull ErrorAlert easy(@NotNull String briefing) {
        return new ErrorAlert().withBriefing(briefing);
    }

    /**
     * Checks if alert is null, and initializes
     * when necessary.
     *
     * @return the alert value.
     */
    private Alert computeAlert() {
        if (alert == null) {
            alert = new Alert(Alert.AlertType.ERROR,
                    "Ha ocurrido un error mientras se ejecutaba una operación. " +
                            "Revise sus datos y parámetros para volver a intentar. Si el " +
                            "error persiste, contacte a su implementador de software.\n" +
                            "Para ver detalles sobre el error, revise el contenido expandible.",
                    ButtonType.CLOSE);
            alert.setHeaderText(briefing);
            alert.getDialogPane().setMaxWidth(450);
            alert.setTitle("Error");
            alert.getDialogPane().setExpandableContent(expandable);
        }
        return alert;
    }

    /**
     * Fluent setter that writes the briefing text
     * in the alert header text property.
     *
     * @param briefing the briefing text. A brief description
     *                 (ie: Error while performing op A). Not null.
     * @return this instnace.
     */
    @Contract("_->this")
    public @NotNull ErrorAlert withBriefing(@NotNull String briefing) {
        this.briefing = briefing;
        return this;
    }

    @Override
    public void accept(@NotNull Throwable throwable) {
        var sw = new StringWriter();
        var pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        expandable.setText(sw.toString());
        computeAlert().show();
    }

    /**
     * Creates a function that wraps another function in a
     * try-catch block. On any runtime exception, will use
     * this as an error alert dialog and will return null.
     *
     * @param function the function to apply.
     * @param <T>      type erasure of function.
     * @param <R>      type erasure of function.
     * @return a new exception-safe function.
     */
    public <T, R> Function<T, R> mapCatch(Function<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (RuntimeException e) {
                accept(e);
                return null;
            }
        };
    }
}
