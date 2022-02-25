/*
 *  Perutax - Taxation software for Peru.
 *     Copyright (C) 2021-2022  Ingenieria Informatica Yupay SACS
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.yupay.perutax.forms;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A fluent alert is a workaround to simplify
 * the production of alert dialogs in a chain
 * of invocations. An example, using JavaFX
 * built-in Alert:
 * <pre>{@code
 * var alert = new Alert(AlertType.INFORMATION);
 * alert.setTitle("Unchained");
 * alert.setHeaderText("Hello world!");
 * alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
 * alert.showAndWait();
 * }</pre>
 * Can be replaced with this class with:
 * <pre>{@code
 * FluentAlert
 *      .info()
 *      .withTitle("Chained")
 *      .withHeader("Hello World!")
 *      .defaultButtons()
 *      .showAndWait();
 * }</pre>
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class FluentAlert extends Alert {
    /**
     * Creates an alert with the given AlertType (refer to the {@link AlertType}
     * documentation for clarification over which one is most appropriate).
     *
     * <p>By passing in an AlertType, default values for the
     * {@link #titleProperty() title}, {@link #headerTextProperty() headerText},
     * and {@link #graphicProperty() graphic} properties are set, as well as the
     * relevant {@link #getButtonTypes() buttons} being installed. Once the Alert
     * is instantiated, developers are able to modify the values of the alert as
     * desired.
     *
     * <p>It is important to note that the one property that does not have a
     * default value set, and which therefore the developer must set, is the
     * {@link #contentTextProperty() content text} property (or alternatively,
     * the developer may call {@code alert.getDialogPane().setContent(Node)} if
     * they want a more complex alert). If the contentText (or content) properties
     * are not set, there is no useful information presented to end users.
     *
     * @param alertType an alert with the given AlertType
     */
    private FluentAlert(AlertType alertType) {
        super(alertType);
    }

    /**
     * Static factory for an INFORMATION alert.
     *
     * @return {@code new FluentAlert(AlertType.INFORMATION);}
     */
    @Contract(" -> new")
    public static @NotNull FluentAlert info() {
        return new FluentAlert(AlertType.INFORMATION);
    }

    /**
     * Static factory for a WARNING alert.
     *
     * @return {@code new FluentAlert(AlertType.WARNING);}
     */
    @Contract(" -> new")
    public static @NotNull FluentAlert warn() {
        return new FluentAlert(AlertType.WARNING);
    }

    /**
     * Static factory for an ERROR alert.
     *
     * @return {@code new FluentAlert(AlertType.ERROR);}
     */
    @Contract(" -> new")
    public static @NotNull FluentAlert err() {
        return new FluentAlert(AlertType.ERROR);
    }

    /**
     * Setter of the title.
     *
     * @param title the new title.
     * @return this instance.
     * @see #setTitle(String)
     */
    @Contract("_->this")
    public @NotNull FluentAlert withTitle(@NotNull String title) {
        setTitle(title);
        return this;
    }

    /**
     * Setter of the header text.
     *
     * @param header the new header text.
     * @return this instance.
     * @see #setHeaderText(String)
     */
    @Contract("_->this")
    public @NotNull FluentAlert withHeader(@NotNull String header) {
        setHeaderText(header);
        return this;
    }

    /**
     * Setter of the button types.
     *
     * @param buttons the new button types.
     * @return this instance.
     * @see #getButtonTypes()
     */
    @Contract("_->this")
    public @NotNull FluentAlert withButtons(@NotNull ButtonType... buttons) {
        getButtonTypes().setAll(buttons);
        return this;
    }

    /**
     * Sets the button types to OK and CANCEL.
     *
     * @return this instance.
     */
    @Contract(" ->this")
    public @NotNull FluentAlert defaultButtons() {
        return withButtons(ButtonType.OK, ButtonType.CANCEL);
    }

    /**
     * Setter of the content text.
     *
     * @param content the new content text.
     * @return this instance.
     * @see #setContentText(String)
     */
    @Contract("_->this")
    public @NotNull FluentAlert withContent(@NotNull String content) {
        setContentText(content);
        return this;
    }

    /**
     * Creates a list view to hold the strings and sets as content node.
     *
     * @param list the list items.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull FluentAlert withStringList(@NotNull ObservableList<String> list) {
        var lv = new ListView<>(list);
        lv.setEditable(false);
        lv.setMaxHeight(400);
        getDialogPane().setContent(lv);
        return this;
    }
}
