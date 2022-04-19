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


import com.yupay.perutax.forms.inner.*;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.DragEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Utility class for javaFX forms.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class FormUtils {
    /**
     * Decimal format symbols for use in the application, where the
     * grouping separator is , and the decimal separator is .
     */
    public static final DecimalFormatSymbols DFS_PERU = new DecimalFormatSymbols() {
        @Override
        public char getGroupingSeparator() {
            return ',';
        }

        @Override
        public char getDecimalSeparator() {
            return '.';
        }

        @Override
        public char getMonetaryDecimalSeparator() {
            return '.';
        }

        @Override
        public char getMonetaryGroupingSeparator() {
            return ',';
        }
    };
    /**
     * Decimal format to parse/format big decimals using
     * {@link #DFS_PERU} and pattern #,##0.00
     */
    public static final DecimalFormat DF_PERU = new DecimalFormat("#,##0.00", DFS_PERU);
    /**
     * Decimal format to parse/format big decimals using
     * {@link #DFS_PERU} and pattern 0.00%
     */
    public static final DecimalFormat DF_RATE = new DecimalFormat("0.00%", DFS_PERU);
    /**
     * DateTime formatter that only shows a date: dd/MM/uuuu
     */
    public static final DateTimeFormatter DATE_ONLY
            = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    /**
     * Date time formatter to use with time stamps: dd/MM/uuuu HH:mm:ss.SSS
     */
    public static final DateTimeFormatter STAMP
            = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss.SSS");

    //Setup some formatters.
    static {
        DF_PERU.setParseBigDecimal(true);
        DF_RATE.setParseBigDecimal(true);
    }

    /**
     * Private constructor for utility class.
     *
     * @throws IllegalAccessException always.
     */
    @Contract("->fail")
    private FormUtils() throws IllegalAccessException {
        throw new IllegalAccessException("Don't initialize utility classes.");
    }

    /**
     * Utility method to setup table columns with a TextFieldTableCell
     * cell factory, using the default JavaFX behavior.
     *
     * @param columns the string columns to setup.
     * @param <T>     type erasure of table view.
     */
    @SafeVarargs
    static <T> void stringTableColumns(TableColumn<T, String> @NotNull ... columns) {
        Stream.of(columns).forEach(FormUtils::stringTableColumns);
    }

    /**
     * Utility method to setup one table column with a TextFieldTableCell
     * cell factory, using the default JavaFX behavior.
     *
     * @param column the single string column to setup.
     * @param <T>    type erasure of table view.
     */
    static <T> void stringTableColumns(@NotNull TableColumn<T, String> column) {
        column.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    /**
     * Utility method to setup one table column with a TextFieldTableCell
     * cell factory, using a {@link ReadOnlyLocalChronoConverter} as a
     * StringConverter. The column won't be editable.
     *
     * @param column the single column to setup.
     * @param <S>    type erasure of table view.
     * @param <T>    type erasure of column.
     */
    static <S, T extends Temporal> void dateTableColumns(@NotNull TableColumn<S, T> column) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(new ReadOnlyLocalChronoConverter<>()));
    }

    /**
     * Utility method to setup table columns with a TextFieldTableCell
     * cell factory, using a {@link ReadOnlyLocalChronoConverter} as a
     * StringConverter. The columns won't be editable.
     *
     * @param columns the columns to setup.
     * @param <S>     type erasure of table view.
     * @param <T>     type erasure of column.
     */
    @SafeVarargs
    static <S, T extends Temporal> void dateTableColumns(@NotNull TableColumn<S, T>... columns) {
        Stream.of(columns).forEach(FormUtils::dateTableColumns);
    }

    /**
     * Utility method to setup table columns with a CheckBoxTableColumn
     * cell factory, using the default JavaFX behavior.
     *
     * @param columns the boolean columns to setup.
     * @param <T>     type erasure of table view.
     */
    @SafeVarargs
    static <T> void booleanTableColumns(TableColumn<T, Boolean> @NotNull ... columns) {
        Stream.of(columns).forEach(FormUtils::booleanTableColumns);
    }

    /**
     * Utility method to setup one table column with a CheckBoxTableColumn
     * cell factory, using the default JavaFX behavior.
     *
     * @param column the boolean column to setup.
     * @param <T>    type erasure of table view.
     */
    static <T> void booleanTableColumns(@NotNull TableColumn<T, Boolean> column) {
        column.setCellFactory(CheckBoxTableCell.forTableColumn(column));
    }

    /**
     * Utility method to create a callback usable as a table cell value factory,
     * out from a mapping function. The mapping function should extract the
     * javafx property (usually using {@code entity::xProperty} lambdas).
     *
     * @param mapper the mapping function.
     * @param <S>    the type erasure of table view.
     * @param <T>    the type erasure of table column.
     * @return a callback ready to use as value factory.
     */
    @Contract(pure = true, value = "_->new")
    static <S, T> @NotNull Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>>
    propertyValueFactory(@NotNull Function<S, ObservableValue<T>> mapper) {
        return f -> {
            var m = f.getValue();
            return m == null ? null : mapper.apply(m);
        };
    }

    /**
     * Utility method to create cellValueFactory using
     * {@link  #propertyValueFactory(Function)} and then
     * setts it to the column in a {@code TableColumn<Long>}
     *
     * @param column the table column.
     * @param mapper the mapping function.
     * @param <S>    type erasure of table view.
     */
    static <S> void longColumnValueFactory(@NotNull TableColumn<S, Long> column,
                                           @NotNull Function<S, LongProperty> mapper) {
        column.setCellValueFactory(longPropertyValueFactory(mapper));
    }

    /**
     * Utility method to create a callback usable as a table cell value factory,
     * out from a mapping function. The mapping function should extract the
     * javafx property (usually using {@code entity::myLongProperty} lambdas)
     * for a LongProperty.
     *
     * @param mapper the mapping function.
     * @param <S>    the type erasure of table view.
     * @return a callback ready to use as value factory.
     */
    @Contract(pure = true, value = "_->new")
    static <S> @NotNull Callback<TableColumn.CellDataFeatures<S, Long>, ObservableValue<Long>>
    longPropertyValueFactory(@NotNull Function<S, LongProperty> mapper) {
        return f -> {
            var m = f.getValue();
            return m == null ? null : mapper.apply(m).asObject();
        };
    }

    /**
     * Utility method to create cellValueFactory using
     * {@link  #propertyValueFactory(Function)} and then
     * setts it to the column in a {@code TableColumn<Integer>}
     *
     * @param column the table column.
     * @param mapper the mapping function.
     * @param <S>    type erasure of table view.
     */
    static <S> void intColumnValueFactory(@NotNull TableColumn<S, Integer> column,
                                          @NotNull Function<S, IntegerProperty> mapper) {
        column.setCellValueFactory(intPropertyValueFactory(mapper));
    }

    /**
     * Utility method to create a callback usable as a table cell value factory,
     * out from a mapping function. The mapping function should extract the
     * javafx property (usually using {@code entity::myIntegerProperty} lambdas)
     * for a IntegerProperty.
     *
     * @param mapper the mapping function.
     * @param <S>    the type erasure of table view.
     * @return a callback ready to use as value factory.
     */
    @Contract(pure = true, value = "_->new")
    static <S> @NotNull Callback<TableColumn.CellDataFeatures<S, Integer>, ObservableValue<Integer>>
    intPropertyValueFactory(@NotNull Function<S, IntegerProperty> mapper) {
        return f -> {
            var m = f.getValue();
            return m == null ? null : mapper.apply(m).asObject();
        };
    }

    /**
     * Utility method to create cellValueFactory using
     * {@link  #propertyValueFactory(Function)} and then
     * setts it to the column.
     *
     * @param column the table column.
     * @param mapper the mapping function.
     * @param <S>    type erasure of table column.
     * @param <T>    type erasure of table column.
     */
    static <S, T> void columnValueFactory(@NotNull TableColumn<S, T> column,
                                          @NotNull Function<S, ObservableValue<T>> mapper) {
        column.setCellValueFactory(propertyValueFactory(mapper));
    }

    /**
     * Sets up a TableColumn with a TextFieldTableCell and a custom
     * string converter for that cell.
     *
     * @param converter the converter for cell.
     * @param column    the table column.
     * @param <S>       type erasure of table view.
     * @param <T>       type erasure of table column.
     */
    static <S, T> void customTableColumns(
            @NotNull StringConverter<T> converter,
            @NotNull TableColumn<S, T> column
    ) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(converter));
    }

    /**
     * Sets up many TableColumn with a TextFieldTableCell and a custom
     * string converter for those cells.
     *
     * @param converter the converter for cell.
     * @param columns   the table column.
     * @param <S>       type erasure of table view.
     * @param <T>       type erasure of table column.
     */
    @SafeVarargs
    static <S, T> void customTableColumns(
            @NotNull StringConverter<T> converter,
            @NotNull TableColumn<S, T>... columns
    ) {
        Stream.of(columns).forEach(c -> customTableColumns(converter, c));
    }

    /**
     * Utility method to create and set a text field table cell factory
     * for a TableColumn, using a string converter that takes a function
     * to convert into a String but won't parse the text. It's useful
     * for read-only columns.
     *
     * @param column the column to setup. Not null.
     * @param mapper the function to map. Not null.
     * @param <S>    type erasure of table column.
     * @param <T>    type erasure of table column.
     */
    static <S, T> void objectTableColumn(
            @NotNull TableColumn<S, T> column,
            @NotNull Function<T, String> mapper) {
        column.setCellFactory(TextFieldTableCell
                .forTableColumn(new StringConverterTo<>(mapper)));
    }

    /**
     * Utility method to create and set a text field table cell factory
     * for a BigDecimal TableColumn, using the
     * {@link  com.yupay.perutax.forms.inner.FixedDecimalConverter}
     * implementation.
     *
     * @param scale  the scale for the UI.
     * @param column the table column to set up.
     * @param <S>    type erasure of table view.
     */
    static <S> void decimalTableColumns(
            int scale, @NotNull TableColumn<S, BigDecimal> column) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(
                new FixedDecimalConverter(scale)));
    }

    /**
     * Utility method to create and set a text field table cell factory
     * for a BigDcimal TableColumn using a converter to show in format 0.00%
     *
     * @param column the table column.
     * @param <S>    table view type erasure.
     */
    static <S> void percentTableColumns(@NotNull TableColumn<S, BigDecimal> column) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(
                new RateDecimalConverter()));
    }

    /**
     * Utility method to create and set a text field table cell factory
     * for many BigDcimal TableColumns using a converter to show in format 0.00%
     *
     * @param columns the table columns.
     * @param <S>     table view type erasure.
     */
    @SafeVarargs
    static <S> void percentTableColumns(@NotNull TableColumn<S, BigDecimal> @NotNull ... columns) {
        Stream.of(columns).forEach(FormUtils::percentTableColumns);
    }

    /**
     * Utility method to create and set a text field table cell factory
     * for a BigDecimal TableColumn, using the
     * {@link  com.yupay.perutax.forms.inner.AmountDecimalConverter}
     * implementation.
     *
     * @param column the table column to set up.
     * @param <S>    type erasure of table view.
     */
    static <S> void amountTableColumns(@NotNull TableColumn<S, BigDecimal> column) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(new AmountDecimalConverter()));
    }

    /**
     * Utility method to create and set a text field table cell factory
     * for many BigDecimal TableColumns, using the
     * {@link  com.yupay.perutax.forms.inner.AmountDecimalConverter}
     * implementation.
     *
     * @param columns the table columns to set up.
     * @param <S>     type erasure of table view.
     */
    @SafeVarargs
    static <S> void amountTableColumns(@NotNull TableColumn<S, BigDecimal>... columns) {
        Stream.of(columns).forEach(FormUtils::amountTableColumns);
    }

    /**
     * Utility method to create and set a text field table cell factory
     * for many BigDecimal TableColumns, using the
     * {@link  com.yupay.perutax.forms.inner.FixedDecimalConverter}
     * implementation.
     *
     * @param scale   the scale for the UI.
     * @param columns the table columns to set up.
     * @param <S>     type erasure of table view.
     */
    @SafeVarargs
    static <S> void decimalTableColumns(
            int scale, @NotNull TableColumn<S, BigDecimal>... columns) {
        Stream.of(columns).forEach(c -> decimalTableColumns(scale, c));
    }


    /**
     * Convenient method to create a predicate that always returns true.
     *
     * @param <T> type erasure of predicate.
     * @return {@code  t -> true;}
     */
    @Contract(pure = true, value = "->new")
    static <T> @NotNull Predicate<T> always() {
        return t -> true;
    }

    /**
     * Finds the element item in the list elements,
     * if item exists ({@code indexOf>0} replaces it,
     * otherwise will add.
     *
     * @param item     the item to "upsert".
     * @param elements list of items.
     * @param <T>      type erasure of list.
     */
    static <T> void upsertList(T item, @NotNull List<T> elements) {
        var ix = elements.indexOf(item);
        if (ix >= 0) elements.set(ix, item);
        else elements.add(item);
    }

    /**
     * Creates a consumer that invokes {@link  #upsertList(Object, List)}
     *
     * @param elements list containing items.
     * @param <T>      type erasure of list.
     * @return a consumer that finds if element t exists, replaces or
     * adds it to the list respectively.s
     */
    @Contract(pure = true, value = "_->new")
    static <T> @NotNull Consumer<T> upsertList(@NotNull List<T> elements) {
        return x -> upsertList(x, elements);
    }

    /**
     * Initializes a child stage to be Application modal,
     * and child of the app primary stage.
     *
     * @param child the child stage.
     */
    static void initChild(@NotNull Stage child) {
        child.initModality(Modality.APPLICATION_MODAL);
        child.initOwner(PeruTaxFXApp.getPrimary());
    }

    /**
     * Setup a table view with filtering mechanisms.
     * This is a utility method to take care of all boilerplate code.
     *
     * @param table      the table view.
     * @param data       the in-memory list where all data is loaded.
     * @param predicator a callable to create a predicate to filter.
     * @param filters    the filter dependencies.
     * @param <T>        type erasure of table view.
     */
    static <T> void setupTableFilter(@NotNull TableView<T> table,
                                     @NotNull ObservableList<T> data,
                                     @NotNull Callable<Predicate<T>> predicator,
                                     @NotNull Observable... filters) {
        var filtered = new FilteredList<>(data, always());
        filtered.predicateProperty().bind(Bindings.createObjectBinding(predicator, filters));
        var sorted = new SortedList<>(filtered);
        sorted.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sorted);
    }

    /**
     * Convenient method for sake of reusability to
     * check the dragboard for files in a dragDropped
     * event, and then runs an import task. This is a
     * boilerplate code.
     *
     * @param event     the event object.
     * @param runImport the import task.
     */
    static void onImportDragDropped(
            @NotNull DragEvent event,
            @NotNull Consumer<Path> runImport) {
        if (event.isConsumed()) return;
        var db = event.getDragboard();
        var success = false;
        if (db.hasFiles()) {
            var pth = db.getFiles()
                    .stream()
                    .findFirst()
                    .map(File::toPath)
                    .orElse(null);
            success = pth != null;
            if (success) runImport.accept(pth);
        }
        event.setDropCompleted(success);
        event.consume();
    }

    /**
     * Clears the given formatters setting the values
     * to a reference one. This value may be null.
     *
     * @param clean      the value to set as a clean value.
     * @param formatters the formatters to clean.
     * @param <T>        type erasure of text formatters.
     */
    @SafeVarargs
    static <T> void clearFormatters(@Nullable T clean,
                                    @NotNull TextFormatter<T> @NotNull ... formatters) {
        for (var f : formatters) f.setValue(clean);
    }

    /**
     * Clears all the text fields.
     *
     * @param fields the text fields to clear.
     */
    static void clearTextFields(@NotNull TextField @NotNull ... fields) {
        for (var f : fields) f.clear();
    }

    /**
     * Clears the date value of the pickers.
     *
     * @param pickers the pickers.
     */
    static void clearDatePickers(@NotNull DatePicker @NotNull ... pickers) {
        for (var d : pickers) d.setValue(null);
    }

    /**
     * Sets all check boxes selection to false.
     *
     * @param checkBoxes the check boxes to clear.
     */
    static void clearCheckBoxes(@NotNull CheckBox @NotNull ... checkBoxes) {
        for (var x : checkBoxes) x.setSelected(false);
    }

    /**
     * Clears all combo boxes setting values to null.
     *
     * @param comboBoxes combo boxes to clear.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    static void clearComboBoxes(@NotNull ComboBox @NotNull ... comboBoxes) {
        for (var x : comboBoxes) x.setValue(null);
    }

    /**
     * Clears all labeled controls setting text to "".
     *
     * @param labeleds labeleds to clear.
     */
    static void clearLabeled(@NotNull Labeled @NotNull ... labeleds) {
        for (var x : labeleds) x.setText("");
    }

    /**
     * Sets the date picker converter to a new smart date converter.
     *
     * @param picker the date picker to make smart.
     * @see SmartDateConverter
     */
    static void smartDatePickers(@NotNull DatePicker picker) {
        picker.setConverter(new SmartDateConverter());
    }

    /**
     * Sets the date picker converter to a new smart date converter.
     *
     * @param pickers the date pickers to make smart.
     * @see SmartDateConverter
     */
    static void smartDatePickers(@NotNull DatePicker @NotNull ... pickers) {
        for (var p : pickers) smartDatePickers(p);
    }
}
