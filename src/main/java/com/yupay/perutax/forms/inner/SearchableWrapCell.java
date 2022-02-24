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

import com.yupay.perutax.dao.PersonRole;
import com.yupay.perutax.entities.JournalDtPerson;
import com.yupay.perutax.forms.Forms;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.function.Function;

import static com.yupay.perutax.forms.ErrorAlert.easy;

/**
 * A searchable wrap cell is a special use case in which the
 * cell behaves exactly like a SearchableCell in the boundary
 * layer, but the user input will be transformed before being
 * comitted. For instance, in a journal line, if user selects
 * a Person, this person must be transformed in a JournalDt Person
 * object before being commited. In general, the type erasure
 * {@code S, T, U} means that the cell will be {@code <S, U>} but
 * the user input will result in a T object, so T object will be
 * converted in a U object before commit. Example:
 * <pre>{@code
 * //The user searchs for a person in the table view.
 * T result = runSearch(userInput);
 * //But the result is a Person, while the cell requires a JournalDtPerson
 * U wrap = wrapper.apply(result);
 * //And now, I can commit:
 * commitEdit(wrap);
 * }</pre>
 *
 * @param <S> type erasure of the table view.
 * @param <T> type erasure of intermediate search result.
 * @param <U> type erasure of the column.
 * @author InfoYupay SACS
 * @version 1.0
 */
public class SearchableWrapCell<S, T, U> extends TableCell<S, U> {
    /**
     * The searchable info object.
     */
    private final SearchableInfo<T> info;
    /**
     * Function to wrap the result in a U object.
     */
    private final Function<T, U> wrapper;
    /**
     * A function to propperly show the cell text.
     */
    private final Function<U, String> formatter;
    /**
     * The editor to accept user inputs.
     */
    private TextField editor;

    /**
     * Default constructor.
     *
     * @param info      the searchable information object.
     * @param wrapper   function to wrap the result in a U object.
     * @param formatter function to propperly show the cell text.
     */
    public SearchableWrapCell(@NotNull SearchableInfo<T> info,
                              @NotNull Function<T, U> wrapper,
                              @NotNull Function<U, String> formatter) {
        this.info = info;
        this.wrapper = wrapper;
        this.formatter = formatter;
    }

    /**
     * Convenient method to create a cell factory.
     *
     * @param info      the searchable information object.
     * @param wrapper   the function to wrap search results.
     * @param formatter function to propperly show the cell text.
     * @param <S>       type erasure of table view.
     * @param <T>       type erasure of intermediate object.
     * @param <U>       type erasure of column.
     * @return a new callback.
     */
    @Contract(pure = true, value = "_,_,_->new")
    public static <S, T, U> @NotNull Callback<TableColumn<S, U>, TableCell<S, U>>
    forColumn(@NotNull SearchableInfo<T> info,
              @NotNull Function<T, U> wrapper,
              @NotNull Function<U, String> formatter) {
        return c -> new SearchableWrapCell<>(info, wrapper, formatter);
    }

    /**
     * Convenient method to create a cell factory to search for
     * a Person and wrap results as a JournalDtPerson object.
     *
     * @param roles the roles of persons to allow.
     * @param <S>   type erasure of table view.
     * @return a new (never null) callback.
     */
    public static <S> @NotNull Callback<TableColumn<S, JournalDtPerson>, TableCell<S, JournalDtPerson>>
    journalDtPerson(@NotNull PersonRole @NotNull ... roles) {
        return forColumn(SearchableInfo.person(roles),
                JournalDtPerson::new,
                JournalDtPerson.formatter());
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
    public void commitEdit(U newValue) {
        super.commitEdit(newValue);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getString());
        setGraphic(null);
    }

    @Override
    protected void updateItem(U item, boolean empty) {
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
     * Convenient method to compute
     * the text to show in the cell.
     *
     * @return the text to show in cell.
     */
    private String getString() {
        return getItem() == null ? "" : formatter.apply(getItem());
    }

    /**
     * Convenient method to run the search in DAO layer.
     *
     * @param terms the user input terms.
     */
    private void runSearch(@NotNull String terms) {
        if (terms.isBlank()) {
            commitEdit(null);
            return;
        }
        try {
            var ls = info.getQuery().apply(terms);
            if (ls.isEmpty()) cancelEdit();
            else if (ls.size() == 1) commitEdit(wrapper.apply(ls.get(0)));
            else runDialog(ls);
        } catch (RuntimeException e) {
            easy("No se pudo ejecutar la búsqueda.").accept(e);
            cancelEdit();
        }
    }

    /**
     * Effectively runs the dialog if user search
     * fetches more than one result.
     *
     * @param results the multiple results.
     */
    private void runDialog(@NotNull @Unmodifiable List<T> results) {
        Forms.search(info)
                .withData(results)
                .showAndWait()
                .map(wrapper)
                .ifPresentOrElse(
                        this::commitEdit,
                        this::cancelEdit);
    }
}

