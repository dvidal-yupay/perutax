package com.yupay.perutax.forms.inner;

import com.yupay.perutax.dao.FolioContext;
import com.yupay.perutax.entities.JournalDtFolio;
import com.yupay.perutax.forms.Forms;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Table cell specialization that shows a custom dialog
 * when user tries to edit.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class DialogCell<S, T> extends TableCell<S, T> {
    /**
     * A function that creates a dialog when user chooses edit.
     */
    private final Function<T, Dialog<T>> dialogBuilder;
    /**
     * The string converter to format user input.
     * If the fromString returns false, then dialog builder
     * is used.
     */
    private final StringConverter<T> converter;
    /**
     * A text field editor.
     */
    private TextField editor;

    /**
     * Default constructor.
     *
     * @param dialogBuilder the dialog build function.
     * @param converter     the converter to format/parse values.
     */
    public DialogCell(@NotNull Function<T, Dialog<T>> dialogBuilder,
                      @NotNull StringConverter<T> converter) {
        this.dialogBuilder = dialogBuilder;
        this.converter = converter;
    }

    /**
     * Utility method to create a suitable factory for a DialogCell that
     * reads a folio context in format folioType-folioSerie-folioNum, if
     * the input is invalid will show a {@link Forms#folioInfo(FolioContext...)}
     * so the user may type an input in a friendly manner.
     *
     * @param contexts the folio type contexts.
     * @param <S>      type erasure of table view.
     * @return a new, never null callback.
     */
    @Contract(pure = true, value = "_->new")
    public static <S> @NotNull Callback<TableColumn<S, JournalDtFolio>, TableCell<S, JournalDtFolio>>
    forJournalFolio(@NotNull FolioContext @NotNull ... contexts) {
        return c -> new DialogCell<>(
                f -> {
                    var r = Forms.folioInfo(contexts);
                    return f == null ? r.creator() : r.withValue(f);
                },
                new JournalFolioInfoConverter()
        );
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
     * Convenient method to initialize the text editor.
     */
    private void createEditor() {
        editor = new TextField(getString());
        editor.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        editor.setOnAction(e -> runDialog(editor.getText()));
    }

    /**
     * Convenient method to compute the string to show
     * in the cell content.
     *
     * @return a never null string.
     */
    private String getString() {
        return getItem() == null ? "" : converter.toString(getItem());
    }

    /**
     * Runs the dialog for a propper input. This should
     * be invoked only if {@link #converter}.fromString
     * returns a null value. If terms is blank, should
     * commit a null value, which should "clear" the value.
     *
     * @param terms Terms of the user's text input.
     */
    private void runDialog(@NotNull String terms) {
        if (terms.isBlank()) {
            commitEdit(null);
            return;
        }
        var x = converter.fromString(terms.strip());
        if (x == null) {
            dialogBuilder.apply(null)
                    .showAndWait()
                    .ifPresentOrElse(this::commitEdit, this::cancelEdit);
        } else {
            commitEdit(x);
        }
    }
}
