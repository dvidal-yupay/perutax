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

package com.yupay.perutax.forms.inner;

import com.yupay.perutax.forms.Forms;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.yupay.perutax.forms.ErrorAlert.easy;

/**
 * Table cell implementation to read a user input as a
 * search term, so it goes to the DAO layer and performs
 * a query, the commited value will be the found value.
 * If more than one value is found, should show a dialog.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class SearchableCell<S, T> extends TableCell<S, T> {
    /**
     * The searchable info to build a propper dialog.
     */
    private final SearchableInfo<T> info;
    /**
     * The cell editor node.
     */
    private TextField editor;

    /**
     * Default constructor.
     *
     * @param info the search information.
     */
    public SearchableCell(@NotNull SearchableInfo<T> info) {
        this.info = info;
    }

    /**
     * Convenient method to create a factory for a searchable cell.
     *
     * @param info the search information (parameters).
     * @param <S>  type erasure of table view.
     * @param <T>  type erasure of column.
     * @return a new never null callback.
     */
    @Contract(pure = true)
    public static <S, T> @NotNull Callback<TableColumn<S, T>, TableCell<S, T>>
    forColumn(@NotNull SearchableInfo<T> info) {
        return c -> new SearchableCell<>(info);
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createEditor();
            setText(null);
            setGraphic(editor);
            editor.selectAll();
            editor.requestFocus();
        }
    }

    @Override
    public void commitEdit(T newValue) {
        super.commitEdit(newValue);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getString());
        setGraphic(null);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (editor != null) {
                    editor.setText(getString());
                }
                setText(null);
                setGraphic(editor);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    /**
     * Convenient method to create the cell editor.
     */
    private void createEditor() {
        editor = new TextField(getString());
        editor.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        editor.setOnAction(e -> runSearch(editor.getText()));
    }

    /**
     * Convenient method to compute the text to show in the cell.
     *
     * @return the computed text.
     */
    private String getString() {
        return getItem() == null ? "" : info.getFormatter().apply(getItem());
    }

    /**
     * Delegated method to run the search using the
     * search terms, which are typed by the user in
     * the cell prior to requesting a commit. If more
     * than one value is found in database, then a
     * dialog will be used to allow for user selection.
     *
     * @param terms the search terms.
     */
    private void runSearch(@NotNull String terms) {
        if (terms.isBlank()) {
            commitEdit(null);
            return;
        }
        try {
            var ls = info.getQuery().apply(terms);
            if (ls.isEmpty()) cancelEdit();
            else if (ls.size() == 1) commitEdit(ls.get(0));
            else runDialog(ls);
        } catch (RuntimeException e) {
            easy("No se pudo ejecutar la búsqueda.").accept(e);
            cancelEdit();
        }
    }

    /**
     * Runs the user selection dialog with a list of results.
     *
     * @param results the list of results.
     */
    private void runDialog(@NotNull List<T> results) {
        Forms.search(info)
                .withData(results)
                .showAndWait()
                .ifPresentOrElse(
                        this::commitEdit,
                        this::cancelEdit);
    }
}

