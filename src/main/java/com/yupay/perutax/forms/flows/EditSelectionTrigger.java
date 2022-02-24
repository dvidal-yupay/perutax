package com.yupay.perutax.forms.flows;

import javafx.scene.control.TableView;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Triggers a user edit action over a selected item
 * in a table view.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class EditSelectionTrigger<T> {
    /**
     * The table view.
     */
    private TableView<T> view;
    /**
     * A function to create a copy of the instance.
     */
    private UnaryOperator<T> cloner;
    /**
     * A function that takes a user value and returns a userInput.
     * Usually a Dialog.
     */
    private Function<T, Optional<T>> userInput;
    /**
     * The error text.
     */
    private String errorText;
    /**
     * What to do after succeed.
     */
    private Consumer<T> onSuccess;

    /**
     * Fluent setter - with.
     *
     * @param view new value to set in {@link #view}
     * @return this instance.
     */
    public final EditSelectionTrigger<T> withView(TableView<T> view) {
        this.view = view;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #view}
     */
    public final TableView<T> getView() {
        return view;
    }

    /**
     * Fluent setter - with.
     *
     * @param cloner new value to set in {@link #cloner}
     * @return this instance.
     */
    public final EditSelectionTrigger<T> withCloner(UnaryOperator<T> cloner) {
        this.cloner = cloner;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #cloner}
     */
    public final UnaryOperator<T> getCloner() {
        return cloner;
    }

    /**
     * Fluent setter - with.
     *
     * @param userInput new value to set in {@link #userInput}
     * @return this instance.
     */
    public final EditSelectionTrigger<T> withUserInput(
            Function<T, Optional<T>> userInput) {
        this.userInput = userInput;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #userInput}
     */
    public final Function<T, Optional<T>> getUserInput() {
        return userInput;
    }

    /**
     * Fluent setter - with.
     *
     * @param errorText new value to set in {@link #errorText}
     * @return this instance.
     */
    public final EditSelectionTrigger<T> withErrorText(String errorText) {
        this.errorText = errorText;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #errorText}
     */
    public final String getErrorText() {
        return errorText;
    }

    /**
     * Fluent setter - with.
     *
     * @param onSuccess new value to set in {@link #onSuccess}
     * @return this instance.
     */
    public final EditSelectionTrigger<T> withOnSuccess(Consumer<T> onSuccess) {
        this.onSuccess = onSuccess;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #onSuccess}
     */
    public final Consumer<T> getOnSuccess() {
        return onSuccess;
    }

    /**
     * Runs an update one flow after requesting a usaer input.
     * If the table view has no selected item, nothing will happen.
     */
    public void run() {
        Optional.ofNullable(getView().getSelectionModel().getSelectedItem())
                .map(getCloner())
                .flatMap(getUserInput())
                .ifPresent(new UpdateOneFlow<T>()
                        .defaultFail(getErrorText())
                        .withOnSuccess(getOnSuccess())
                        .asConsumer());
    }
}
