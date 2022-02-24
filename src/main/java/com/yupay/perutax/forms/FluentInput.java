package com.yupay.perutax.forms;

import com.yupay.perutax.forms.inner.NumericFormatter;
import javafx.scene.control.TextInputDialog;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A fluent dialog for user text input.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class FluentInput extends TextInputDialog {

    /**
     * Fluent setter.
     *
     * @param title the value of title
     * @return this instance.
     * * @see #settitle
     */
    @Contract("_->this")
    public @NotNull FluentInput withTitle(@NotNull String title) {
        setTitle(title);
        return this;
    }

    /**
     * Fluent setter.
     *
     * @param headerText the value of headerText
     * @return this instance.
     * @see #setHeaderText
     */
    @Contract("_->this")
    public @NotNull FluentInput withHeaderText(@NotNull String headerText) {
        setHeaderText(headerText);
        return this;
    }

    /**
     * Sets a text formatter to the input editor in
     * order to enforce digits-only characters up to
     * a maximum length.
     *
     * @param maxLength the maximum length allowed for a user input.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull FluentInput withNumericInput(int maxLength) {
        getEditor().setTextFormatter(new NumericFormatter(maxLength));
        return this;
    }


    /**
     * Fluent setter.
     *
     * @param contentText the value of contentText
     * @return this instance.
     * @see #setContentText
     */
    @Contract("_->this")
    public @NotNull FluentInput withContentText(@NotNull String contentText) {
        setContentText(contentText);
        return this;
    }

}
